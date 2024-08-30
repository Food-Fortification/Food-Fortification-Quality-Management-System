package com.beehyv.iam.service;


import com.beehyv.iam.dto.requestDto.LogsRequestDto;
import com.beehyv.iam.manager.LogsManager;
import com.beehyv.iam.model.Logs;
import com.beehyv.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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
