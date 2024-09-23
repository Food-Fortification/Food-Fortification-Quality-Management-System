package com.beehyv.Immudb.service;

import com.beehyv.Immudb.dto.HistoryRequestDto;
import com.beehyv.Immudb.entity.BatchEventEntity;
import com.beehyv.Immudb.dto.HistoryResponseDto;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ImmudbService {

    public <T> void put(BatchEventEntity event, Class<T> cls) throws java.sql.SQLException;

    public Map<String,List<HistoryResponseDto>> getHistoryForEntities(List<Long> entityIds, String type) throws SQLException;

    Map<String, List<HistoryResponseDto>> getHistory(HistoryRequestDto dto);
}
