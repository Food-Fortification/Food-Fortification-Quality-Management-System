package org.path.iam.manager;

import org.path.iam.dao.LogsDao;
import org.path.iam.model.Logs;
import org.springframework.stereotype.Component;

@Component
public class LogsManager extends BaseManager<Logs, LogsDao> {

    private final LogsDao logsDao;

    public LogsManager(LogsDao logsDao){
        super(logsDao);
        this.logsDao = logsDao;
    }

}
