package org.path.Immudb.service;

import org.path.Immudb.dto.HistoryRequestDto;
import org.path.Immudb.entity.BatchEventEntity;
import org.path.Immudb.dto.HistoryResponseDto;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ImmudbService {

    public <T> void put(BatchEventEntity event, Class<T> cls) throws java.sql.SQLException;

    public Map<String,List<HistoryResponseDto>> getHistoryForEntities(List<Long> entityIds, String type) throws SQLException;

    Map<String, List<HistoryResponseDto>> getHistory(HistoryRequestDto dto);
}
