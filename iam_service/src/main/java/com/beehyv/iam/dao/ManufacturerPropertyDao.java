package com.beehyv.iam.dao;

import com.beehyv.iam.model.ManufacturerProperty;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class ManufacturerPropertyDao extends BaseDao<ManufacturerProperty> {
    public ManufacturerPropertyDao(EntityManager em) {
        super(em, ManufacturerProperty.class);
    }
}