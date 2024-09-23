package com.beehyv.iam.manager;
import com.beehyv.iam.dao.StateLabTestAccessDao;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.enums.EntityType;
import com.beehyv.iam.enums.UserType;
import com.beehyv.iam.model.StateLabTestAccess;
import com.beehyv.iam.model.User;
import com.beehyv.iam.model.UserRoleCategory;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.TypedQuery;
import java.util.List;


@Component
public class StateLabTestAccessManager extends BaseManager<StateLabTestAccess, StateLabTestAccessDao> {
    private final StateLabTestAccessDao dao;
    public StateLabTestAccessManager(StateLabTestAccessDao dao) {
        super(dao);
        this.dao = dao;
    }
    public StateLabTestAccess findByStateAndCategoryAndEntityType(Long categoryId, EntityType entityType, Long stateId) {
        return dao.findByStateAndCategoryAndEntityType(categoryId,entityType,stateId);
    }


    public void deleteEntity(Long categoryId, EntityType entityType, Long stateId) {
         dao.deleteEntity(categoryId,entityType,stateId);
    }

    public List<StateLabTestAccess> findAllStateLabTestAccessBySearchAndFilter(SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize){
           return dao.findAllStateLabTestAccessBySearchAndFilter(searchListRequest,pageNumber,pageSize);
        }
    public Long getCount(SearchListRequest searchListRequest){

        return dao.getCountForAllStateTestLabAccess(searchListRequest);

    }

    }


