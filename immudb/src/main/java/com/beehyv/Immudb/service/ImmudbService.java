package com.beehyv.Immudb.service;

import com.beehyv.Immudb.dto.HistoryRequestDto;
import com.beehyv.Immudb.entity.BatchEventEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ImmudbService {

    public <T> void put(BatchEventEntity event, Class<T> cls) throws java.sql.SQLException;

    public Map<String,List<BatchEventEntity>> getHistoryForEntities(List<Long> entityIds, String type) throws SQLException;

    Map<String, List<BatchEventEntity>> getHistory(HistoryRequestDto dto);
}
