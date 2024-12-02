package org.path.iam.manager;

import org.path.iam.dao.RoleDao;
import org.path.iam.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleManager extends BaseManager<Role, RoleDao> {
    private final RoleDao dao;
    public RoleManager(RoleDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<Role> findByNames(List<String> roleNames) {
        return dao.findByNames(roleNames);
    }

    public Role findByName(String roleName){
        return dao.findByName(roleName);
    }
}
