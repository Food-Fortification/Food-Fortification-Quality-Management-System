package org.path.iam.dao;

import org.path.iam.model.LabUsers;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class LabUsersDao extends BaseDao<LabUsers>{
    private final EntityManager em;
    public LabUsersDao(EntityManager em) {
        super(em, LabUsers.class);
        this.em=em;
    }

    public List<LabUsers>findUsersByLabId(Long labId){
        return em.createQuery("from LabUsers lu where lu.labId = :labId", LabUsers.class)
                .setParameter("labId",labId)
                .getResultList();
    }
}
