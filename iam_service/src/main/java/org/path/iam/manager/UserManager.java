package org.path.iam.manager;

import org.path.iam.dao.UserDao;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.dto.requestDto.UserRequestDto;
import org.path.iam.dto.requestDto.UserRoleCategoryRequestDto;
import org.path.iam.dto.responseDto.NotificationUserTokenResponseDto;
import org.path.iam.dto.responseDto.UserNotificationResponseDto;
import org.path.iam.dto.responseDto.UserResponseDto;
import org.path.iam.dto.responseDto.UserRoleCategoryResponseDto;
import org.path.iam.enums.ActionType;
import org.path.iam.enums.RoleCategoryType;
import org.path.iam.helper.EncryptionHelper;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.UserMapper;
import org.path.iam.mapper.UserRoleCategoryMapper;
import org.path.iam.model.NotificationStateRoleMapping;
import org.path.iam.model.User;

import java.util.ArrayList;

import org.path.iam.model.UserRoleCategory;
import org.path.parent.exceptions.CustomException;
import org.springframework.http.HttpStatus;
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

    public User findByName(String userName)  {
        try {
            return dao.findByName(EncryptionHelper.encrypt(userName));
        }
        catch (Exception e){
            throw new CustomException("Failed to encrypt username", HttpStatus.BAD_REQUEST);
        }
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

    public List<User> findallByManufacturersIds(List<Long> manufacturerIds){
        return dao.findallByManufacturerIds(manufacturerIds);
    }

}
