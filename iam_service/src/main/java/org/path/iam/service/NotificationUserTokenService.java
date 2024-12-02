package org.path.iam.service;

import org.path.iam.dto.requestDto.NotificationUserTokenRequestDto;
import org.path.iam.dto.responseDto.NotificationUserTokenResponseDto;
import org.path.iam.enums.RoleCategoryType;
import org.path.iam.manager.NotificationUserTokenManager;
import org.path.iam.manager.NotificationStateRoleMappingManager;
import org.path.iam.manager.UserManager;
import org.path.iam.model.NotificationUserToken;
import org.path.iam.model.NotificationStateRoleMapping;
import org.path.iam.model.User;

import org.path.parent.exceptions.CustomException;
import java.util.*;

import java.util.stream.Collectors;

import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationUserTokenService {

    private final NotificationUserTokenManager notificationUserTokenManager;
    private final NotificationStateRoleMappingManager notificationStateRoleMappingManager;
    private final UserManager userManager;
    private final KeycloakInfo keycloakInfo;

    public Long create(NotificationUserTokenRequestDto dto){
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        long labId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("labId", 0).toString());
        if (manufacturerId == 0L && labId == 0L) return null;
        User user = userManager.findByName(dto.getUserName());
        if(user == null ) throw new CustomException("User not found with given name: " + dto.getUserName(), HttpStatus.BAD_REQUEST);
        NotificationUserToken token = notificationUserTokenManager.findByRegistrationToken(
            dto.getRegistrationToken(), user.getId());
        if(token != null){
            return token.getId();
        }
        NotificationUserToken registrationToken = new NotificationUserToken();
        registrationToken.setRegistrationToken(dto.getRegistrationToken());
        registrationToken.setUser(user);
        registrationToken = notificationUserTokenManager.create(registrationToken);
        log.info("Registration token saved: " + registrationToken.toString());
        return registrationToken.getId();
    }

    public  List<NotificationUserTokenResponseDto> getRegistrationTokens(Long categoryId,Long manufacturerId, Long labId, Long targetManufacturerId, String state){
        List<NotificationStateRoleMapping> notificationStateRoleMappingList = notificationStateRoleMappingManager.findByStateAndCategory(state,categoryId);
        Set<String> labRoles = notificationStateRoleMappingList.stream().filter(c -> c.getRoleCategoryType().equals(RoleCategoryType.LAB)).map(c -> c.getRole().getName()).collect(Collectors.toSet());
        return userManager.getRegistrationTokens(manufacturerId, labId, targetManufacturerId, labRoles.stream().toList(), notificationStateRoleMappingList);
    }
    public void deleteRegistrationToken(String registrationToken){
        userManager.deleteRegistrationToken(registrationToken);

    }


}
