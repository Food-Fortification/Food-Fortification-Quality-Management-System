package org.path.iam.manager;
import org.path.iam.dao.StateLabTestAccessDao;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.enums.EntityType;
import org.path.iam.model.StateLabTestAccess;
import org.springframework.stereotype.Component;

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


