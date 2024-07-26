package com.beehyv.iam.manager;

import com.beehyv.iam.dao.UserRoleCategoryDao;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.model.UserRoleCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleCategoryManager extends BaseManager<UserRoleCategory, UserRoleCategoryDao> {
    private final UserRoleCategoryDao dao;
    public UserRoleCategoryManager(UserRoleCategoryDao dao) {
        super(dao);
        this.dao = dao;
    }
    public List<UserRoleCategory> findAllUsersBySearchAndFilter(SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize, Long manufacturerId){
        return dao.findAllUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId);
    }
    public Long getCountForALlUsersBySearchAndFilter(SearchListRequest searchListRequest, Long manufacturerId){
        return dao.getCountForALlUsersBySearchAndFilter(searchListRequest, manufacturerId);
    }
    public List<UserRoleCategory> findAllLabUsersBySearchAndFilter(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize) {
        return dao.findAllLabUsersBySearchAndFilter(searchRequest, pageNumber, pageSize);
    }

    public Long getCountForAllLabUsersBySearchAndFilter(SearchListRequest searchRequest) {
        return dao.getCountForAllLabUsersBySearchAndFilter(searchRequest);
    }
    public List<UserRoleCategory> findByUserId(Long userId){
        return dao.findByUserId(userId);
    }

    public List<UserRoleCategory> findAllInspectionUsers(SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize) {
        return dao.findAllInspectionUsers(searchListRequest, pageNumber, pageSize);
    }

    public Long getCountForInspectionUsers(SearchListRequest searchListRequest) {
        return dao.getCountForAllInspectionUsers(searchListRequest);
    }
}
