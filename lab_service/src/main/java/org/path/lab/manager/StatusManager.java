package org.path.lab.manager;

import org.path.lab.dao.StatusDao;
import org.path.lab.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusManager extends BaseManager<Status, StatusDao>{
  private final StatusDao dao;
  public StatusManager(StatusDao dao) {
    super(dao);
    this.dao = dao;
  }
  public Status findByName(String name){
    return dao.findByName(name);
  }
}
