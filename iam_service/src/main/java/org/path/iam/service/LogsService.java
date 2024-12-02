package org.path.iam.service;


import org.path.iam.dto.requestDto.LogsRequestDto;
import org.path.iam.manager.LogsManager;
import org.path.iam.model.Logs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LogsService {

    private final LogsManager logsManager;


    public void saveLogData(LogsRequestDto dto){
        Logs logs = new Logs();
        logs.setId(dto.getId());
        logs.setMessage(dto.getMessage());
        logs.setEntityName(dto.getEntityName());
        logs.setLevel(dto.getLevel());
        logs.setTimeStamp(dto.getTimestamp());
        logs.setUserId(dto.getUserId());
        logs.setManufacturerId(dto.getManufacturerId());
        logsManager.create(logs);
    }



}
