package org.path.fortification.helper;

import org.path.fortification.dto.responseDto.LabNameAddressResponseDto;
import org.path.fortification.dto.responseDto.LabSampleCreateResponseDto;
import org.path.fortification.entity.Batch;
import org.path.fortification.entity.Lot;
import org.path.fortification.enums.EntityType;
import org.path.parent.exceptions.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class LabServiceManagementHelper {

    public static LabSampleCreateResponseDto createSample(Batch batch, String token, Date actionDate, Long labId) {
        if (actionDate.before(batch.getDateOfManufacture())) {
            throw new CustomException("Sample Sent Date cannot be before Manufacturing Date", HttpStatus.BAD_REQUEST);
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.LAB_SERVICE_URL + "1/sample";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        Map jsonData = new HashMap();
        jsonData.put("batchId", batch.getId());
        jsonData.put("batchNo", batch.getBatchNo());
        jsonData.put("categoryId", batch.getCategory().getId());
        jsonData.put("requestStateId", batch.getState().getName());
        jsonData.put("sampleSentDate", actionDate);
        jsonData.put("manufacturerId", batch.getManufacturerId());
        jsonData.put("stateId", 1);
        if (labId != null) jsonData.put("labId", labId);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(jsonData), headers);
        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<LabSampleCreateResponseDto> response = restTemplate.postForEntity(url, entity , LabSampleCreateResponseDto.class);
        return response.getBody();
    }

    public static LabSampleCreateResponseDto createSample(Lot lot, String token, Date actionDate, Long labId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.LAB_SERVICE_URL + "1/sample";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        Map jsonData = new HashMap();
        jsonData.put("lotId", lot.getId());
        jsonData.put("lotNo", lot.getLotNo());
        jsonData.put("categoryId", lot.getCategory().getId());
        jsonData.put("requestStateId", lot.getState().getName());
        jsonData.put("sampleSentDate", actionDate);
        jsonData.put("manufacturerId", lot.getTargetManufacturerId());
        jsonData.put("stateId", 1);
        if (labId != null) jsonData.put("labId", labId);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(jsonData), headers);
        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<LabSampleCreateResponseDto> response = restTemplate.postForEntity(url, entity , LabSampleCreateResponseDto.class);
        return response.getBody();
    }

    public static void deleteSample(LabSampleCreateResponseDto dto,String token){
        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.LAB_SERVICE_URL+dto.getLabId()+"/sample/"+dto.getId();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        restTemplate.exchange(url, HttpMethod.DELETE,httpEntity,String.class);
    }

    public static LabNameAddressResponseDto fetchLabNameAddress(String url, String token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<LabNameAddressResponseDto> response;
        log.info("Url for lab address api: {}", url);
        response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, LabNameAddressResponseDto.class);
        LabNameAddressResponseDto dto = response.getBody();
        log.info("Response: {{}}: ", dto);
        return dto;
    }



    public static Map<Long,LabNameAddressResponseDto> fetchLabNameAddressByLotIds(String token, List<Long> lotIds, EntityType type){
        String url = Constants.LAB_SERVICE_URL + "4/sample/" + type;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<?> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(lotIds), requestHeaders);
        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


        log.info("Url for lab address api: {}", url);
        ResponseEntity<Map<Long,LabNameAddressResponseDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<Long, LabNameAddressResponseDto>>() {});
        Map<Long,LabNameAddressResponseDto> dto = response.getBody();
        log.info("Response: {{}}: ", dto);
        return dto;
    }
}
