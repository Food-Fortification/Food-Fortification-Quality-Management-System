package com.beehyv.iam.manager;

import com.beehyv.iam.dao.UserDao;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.requestDto.UserRequestDto;
import com.beehyv.iam.dto.requestDto.UserRoleCategoryRequestDto;
import com.beehyv.iam.dto.responseDto.NotificationUserTokenResponseDto;
import com.beehyv.iam.dto.responseDto.UserNotificationResponseDto;
import com.beehyv.iam.dto.responseDto.UserResponseDto;
import com.beehyv.iam.dto.responseDto.UserRoleCategoryResponseDto;
import com.beehyv.iam.enums.ActionType;
import com.beehyv.iam.enums.RoleCategoryType;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.mapper.UserMapper;
import com.beehyv.iam.mapper.UserRoleCategoryMapper;
import com.beehyv.iam.model.NotificationStateRoleMapping;
import com.beehyv.iam.model.User;

import java.util.ArrayList;

import com.beehyv.iam.model.UserRoleCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserManager extends BaseManager<User, UserDao> {
    private final UserDao dao;

    public UserManager(UserDao dao) {
        super(dao);
        this.dao = dao;
    }

    private final BaseMapper<UserResponseDto, UserRequestDto, User> mapper = BaseMapper.getForClass(UserMapper.class);
    private final BaseMapper<UserRoleCategoryResponseDto, UserRoleCategoryRequestDto, UserRoleCategory> userRoleCategoryMapper = BaseMapper.getForClass(UserRoleCategoryMapper.class);

    public User findByName(String userName) {
        return dao.findByName(userName);
    }

    public User findByEmail(String emailId) {
        return dao.findByEmail(emailId);
    }

    public List<User> findAllByManufacturerId(Long manufacturerId) {
        return dao.findAllByManufacturerId(manufacturerId);
    }

    public User findByLabUserId(Long labUserId){
        return dao.findByLabUserId(labUserId);
    }

    public List<User> findAllBySearchAndFilter(SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize, Long manufacturerId) {
        return dao.findAllBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId);
    }

    public Long getCountBySearchAndFilter(SearchListRequest searchListRequest, Long manufacturerId) {
        return dao.getCountBySearchAndFilter(searchListRequest, manufacturerId);
    }



    public List<NotificationUserTokenResponseDto> getRegistrationTokens(Long manufacturerId, Long labId, Long targetManufacturerId, List<String> labUserRoles, List<NotificationStateRoleMapping> notificationStateRoleMappingList) {
        List<User> users = dao.getRegistrationTokens(manufacturerId, labId, targetManufacturerId, labUserRoles);
        List<NotificationUserTokenResponseDto> notificationTokenList = new ArrayList<>();

        users.stream().map(u -> {
                    UserNotificationResponseDto dto = new UserNotificationResponseDto();
                    dto.setManufacturerId(u.getManufacturer() != null ? u.getManufacturer().getId() : null);
                    dto.setLabId(u.getLabUsers() != null ? u.getLabUsers().getId() : null);
                    dto.setRoleCategory(u.getRoleCategories().stream().map(userRoleCategoryMapper::toDto).collect(Collectors.toSet()));
                    dto.setNotificationUserTokens(u.getNotificationUserTokens().stream().map(n -> new NotificationUserTokenResponseDto(null, null, n.getRegistrationToken())).collect(Collectors.toSet()));
                    return dto;
                })
                .filter(user -> user.getLabId() != null || (user.getManufacturerId() != null && user.getRoleCategory().stream()
                        .anyMatch(roleCategory -> notificationStateRoleMappingList.stream()
                                .anyMatch(mapping -> mapping.getCategoryName().equals(roleCategory.getCategory()) && mapping.getRole().getId().equals(roleCategory.getRole().getId()))
                        )))
                .forEach(u -> {
                    ActionType actionType = ActionType.action;
                    RoleCategoryType type;
                    if (u.getManufacturerId() != null) type = RoleCategoryType.MODULE;
                    else type = RoleCategoryType.LAB;
                    u.getNotificationUserTokens().forEach(token -> notificationTokenList.add(new NotificationUserTokenResponseDto(type, actionType, token.getRegistrationToken())));
                });
        return notificationTokenList;
    }

    public void deleteRegistrationToken(String registrationToken) {
        dao.deleteRegistrationToken(registrationToken);
    }


}
