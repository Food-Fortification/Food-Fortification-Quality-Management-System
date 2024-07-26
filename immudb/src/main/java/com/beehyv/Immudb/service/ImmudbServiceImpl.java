package com.beehyv.Immudb.service;

import com.beehyv.Immudb.dto.HistoryRequestDto;
import com.beehyv.Immudb.entity.BatchEventEntity;
import com.beehyv.Immudb.utils.HttpUtils;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.codenotary.immudb4j.ImmuClient;
import io.codenotary.immudb4j.sql.SQLQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.*;


@Service
@Slf4j
public class ImmudbServiceImpl implements ImmudbService{
    private boolean databaseInitialised = false;

    @Value("${immudb.url}")
    private String url;

    @Value("${immudb.database}")
    private String database;
    @Value("${immudb.username}")
    private String username;
    @Value("${immudb.password}")
    private String password;

    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl ;

    private Connection connection;

    @Autowired
    private KeycloakInfo keycloakInfo;

    @Autowired
    private ImmuClient client;

    @PostConstruct
    public void init() {
        this.databaseInitialised = this.verifyConnectionAndInitializeDatabase(5);
    }

    private boolean verifyConnectionAndInitializeDatabase(int retryCount) {
        boolean connectionVerified = this.verifyConnection(5);
        if(!connectionVerified) return false;
        if (this.databaseInitialised) return true;
        try {
            log.info("Initialising Immudb tables");
            String createTable ="CREATE TABLE IF NOT EXISTS "+ "batch_event_entity" + "(id INTEGER AUTO_INCREMENT, entityId VARCHAR, manufacturerName VARCHAR, manufacturerAddress VARCHAR, type VARCHAR, state VARCHAR, comments VARCHAR, dateOfAction TIMESTAMP, createdBy VARCHAR, createdDate TIMESTAMP, PRIMARY KEY id);";
            Statement statement = this.connection.createStatement();
            statement.execute(createTable);
            log.info("Initialised Immudb tables");
            statement.close();
            this.databaseInitialised = true;
        }  catch (java.sql.SQLException e) {
            log.error(" Exception Occurred while initiating Immudb database: {{}}", e.getMessage());
            this.databaseInitialised = false;
        }
        if (retryCount > 0) return this.verifyConnectionAndInitializeDatabase(retryCount - 1);
        return this.databaseInitialised;
    }

    public boolean verifyConnection(Integer retryCount) {
        try {
            if(this.connection != null && !this.connection.isClosed()) return true;
            log.info("Connecting to Immudb : " + url);
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            log.info("Connection successful");
            return true;
        } catch (ClassNotFoundException | SQLException exp) {
            log.error("Immudb Connection: {{}}", exp.getMessage());
        }
        if(retryCount > 0) return this.verifyConnection(retryCount - 1);
        return false;
    }

    @Override
    public <T> void put(BatchEventEntity event, Class<T> cls) {
        if(!this.verifyConnectionAndInitializeDatabase(5)) {
            throw new RuntimeException("Unable to verify database connection");
        }
        String tableName = "batch_event_entity";
        try {
            String query =
                    "INSERT INTO " + tableName + " " + event.getColumnNames() + "\n" +
                            "VALUES " + "("+ "'" +event.getEntityId()+"'"+"," + "'" + event.getManufacturerName() + "'" +
                            "," + "'" + event.getManufacturerAddress() +"'" + "," + "'" +event.getType()+"'" + "," +"'"+
                            event.getState()+"'" + "," + "'"+event.getComments()+"'" + "," +
                            (event.getDateOfAction() != null ? "CAST('"+event.getDateOfAction()+"' AS TIMESTAMP)" : "NULL") + "," +
                            "'"+event.getCreatedBy()+"'" + ","+ "NOW()" + ")"+ ";";

            Statement statement = this.connection.createStatement();
            statement.execute(query);
            statement.close();
        } catch (Exception e) {
            log.info("Exception occurred while writing to table " + e.getMessage());
        }
    }

    public List<BatchEventEntity> getHistory(Long entityId, String type) {
//        if(Objects.equals("batch",type.toLowerCase())) checkBatchAccess(entityId);
//        else if(Objects.equals("lot",type.toLowerCase())) checkLotAccess(entityId);
//        else throw new CustomException("Invalid type: " + type, HttpStatus.BAD_REQUEST);
        SQLQueryResult result;
        List<BatchEventEntity> list = new ArrayList<>();
        String tableName = "batch_event_entity";
        try {
            client.openSession(database, username, password);
            client.beginTransaction();
            result = client.sqlQuery("SELECT * FROM " + tableName + " WHERE entityId = '" + entityId + "'" +
                    " AND type = '" + type + "'");
            while (result.next()) {
                Map<String, Object> map = new HashMap<>();
                int j = result.getColumnsCount();
                int x = -1;
                while (++x < j) {
                    String column = result.getColumnName(x);
                    Object value = switch (result.getColumnType(x)) {
                        case "INTEGER" -> result.getInt(x);
                        case "VARCHAR" -> result.getString(x);
                        case "TIMESTAMP" -> result.getDate(x).getTime();
                        default -> null;
                    };
                    map.put(column, value);
                }
                BatchEventEntity batchEventEntity = convertToEntity(map);
                list.add(batchEventEntity);
            }
            client.commitTransaction();
        } catch (Exception ex) {
            log.error(" Exception in fetching the history of records" , ex);
        } finally {
            client.closeSession();
        }
        return list;
    }

    @Override
    public Map<String,List<BatchEventEntity>> getHistoryForEntities(List<Long> entityIds, String type) {
        Map<String,List<BatchEventEntity>> map = new HashMap<>();
        for(Long entityId : entityIds){
            List<BatchEventEntity> list = getHistory(entityId, type);
            map.put(entityId.toString(),list);
        }
        return map;
    }

    @Override
    public Map<String, List<BatchEventEntity>> getHistory(HistoryRequestDto dto) {
        Map<String,List<BatchEventEntity>> map = new HashMap<>();
        List<BatchEventEntity> batchList ;
        List<BatchEventEntity> historyList;
        List<BatchEventEntity> lotList ;
        if (dto.getBatchId() != null && dto.getLotId() != null){
//            checkBatchAccess(dto.getBatchId());
            batchList = getHistory(dto.getBatchId(),"BATCH");
            lotList = getHistory(dto.getLotId(),"LOT");
            historyList = new ArrayList<>(batchList);
            historyList.addAll(lotList);
            map.put("history",historyList);
        } else if (dto.getBatchId() != null) {
//            checkBatchAccess(dto.getBatchId());
            batchList = getHistory(dto.getBatchId(),"BATCH");
            historyList = new ArrayList<>(batchList);
            map.put("history",historyList);
        } else if (dto.getLotId() != null) {
//            checkLotAccess(dto.getLotId());
            lotList = getHistory(dto.getLotId(),"LOT");
            historyList = new ArrayList<>(lotList);
            map.put("history", historyList);
        }else {
            map.put("history", new ArrayList<>());
        }
        return map;
    }

    public BatchEventEntity convertToEntity(Map<String, Object> map){
        BatchEventEntity batchEventEntity = new BatchEventEntity();
        batchEventEntity.setId(Long.valueOf((Integer) map.getOrDefault("id", "")));
        batchEventEntity.setEntityId((String) map.getOrDefault("entityid", ""));
        batchEventEntity.setManufacturerName((String) map.getOrDefault("manufacturername", ""));
        batchEventEntity.setManufacturerAddress((String) map.getOrDefault("manufactureraddress", ""));
        batchEventEntity.setType((String) map.getOrDefault("type", ""));
        batchEventEntity.setState((String) map.getOrDefault("state", ""));
        batchEventEntity.setComments((String) map.getOrDefault("comments", ""));
        batchEventEntity.setDateOfAction(new Timestamp((Long) map.getOrDefault("dateofaction", "")));
        batchEventEntity.setCreatedBy((String) map.getOrDefault("createdby", ""));
        batchEventEntity.setCreatedDate(new Timestamp((Long) map.getOrDefault("createddate", "")));
        return batchEventEntity;
    }

    private void checkBatchAccess(Long batchId){
        String url = fortificationBaseUrl + "0/batch/" + batchId + "/lab-access" ;
        String response = HttpUtils.callGetAPI(url, keycloakInfo.getAccessToken());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            Boolean hasAccess = mapper.readValue(response, Boolean.class);
            if(hasAccess) return;
        }catch (Exception e){
            log.info("Exception in checkBatchAccess " + e.getMessage());
        }
        throw new CustomException("Permission denied", HttpStatus.UNAUTHORIZED);
    }

    private void checkLotAccess(Long lotId){
        String url = fortificationBaseUrl + "0/lot/" + lotId + "/lab-access" ;
        String response = HttpUtils.callGetAPI(url, keycloakInfo.getAccessToken());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            Boolean hasAccess = mapper.readValue(response, Boolean.class);
            if(hasAccess) return;
        }catch (Exception e){
            log.info("Exception in checkLotAccess " + e.getMessage());
        }
        throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
    }
}
