package org.path.iam.dao;

import org.path.iam.model.Role;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import java.util.List;

@Component
public class RoleDao extends BaseDao<Role> {
    private final EntityManager em;
    public RoleDao(EntityManager em) {
        super(em, Role.class);
        this.em =em;
    }
    public Role findByName(String roleName) {
            return em.createQuery("FROM Role r where r.name = :roleName", Role.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
    }

    public List<Role> findByNames(List<String> roleNames) {
        return em.createQuery("from Role r where r.name in :roleNames", Role.class)
                .setParameter("roleNames",roleNames)
                .getResultList();
    }
}
