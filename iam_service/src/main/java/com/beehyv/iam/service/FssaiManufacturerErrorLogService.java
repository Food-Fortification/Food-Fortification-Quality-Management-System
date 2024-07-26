package com.beehyv.iam.service;

import com.beehyv.iam.manager.FssaiManufacturerErrorLogManager;
import com.beehyv.iam.model.FssaiManufacturerErrorLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FssaiManufacturerErrorLogService {

    private final FssaiManufacturerErrorLogManager fssaiManufacturerErrorLogManager;
    public void create(String data, String exceptionMessage) {
        FssaiManufacturerErrorLog entity = new FssaiManufacturerErrorLog();
        entity.setData(data);
        entity.setExceptionMessage(exceptionMessage);
        fssaiManufacturerErrorLogManager.create(entity);
    }
}
