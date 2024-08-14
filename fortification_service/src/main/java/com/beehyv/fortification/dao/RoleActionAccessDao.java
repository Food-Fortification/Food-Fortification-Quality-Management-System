package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.RoleActionAccess;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleActionAccessDao extends BaseDao<RoleActionAccess> {
    private final EntityManager em;

    public RoleActionAccessDao(EntityManager em) {
        super(em, RoleActionAccess.class);
        this.em = em;
    }


    public List<RoleActionAccess> getByRoleAndAction(String role, String action) {
        List<RoleActionAccess> roleActionAccesses = new ArrayList<>();
        try {
            roleActionAccesses = em.createQuery("SELECT r from RoleActionAccess r where r.role = :role and r.action = :action", RoleActionAccess.class)
                    .setParameter("role", role)
                    .setParameter("action", action)
                    .getResultList();
        } catch (NoResultException ignored) {

        }
        return roleActionAccesses;
    }


}