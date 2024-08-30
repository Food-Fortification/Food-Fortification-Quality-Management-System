package com.beehyv.iam.manager;

import com.beehyv.iam.dao.LogsDao;
import com.beehyv.iam.model.Logs;
import org.springframework.stereotype.Component;

@Component
public class LogsManager extends BaseManager<Logs, LogsDao> {

    private final LogsDao logsDao;

    public LogsManager(LogsDao logsDao){
        super(logsDao);
        this.logsDao = logsDao;
    }

}
