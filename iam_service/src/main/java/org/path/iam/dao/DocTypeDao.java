package org.path.iam.dao;


import org.path.iam.model.DocType;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
@Component
public class DocTypeDao extends BaseDao<DocType> {
    public DocTypeDao(EntityManager em) {
        super(em, DocType.class);
    }
}
