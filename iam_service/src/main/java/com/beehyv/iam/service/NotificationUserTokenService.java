package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.NotificationUserTokenRequestDto;
import com.beehyv.iam.dto.responseDto.NotificationUserTokenResponseDto;
import com.beehyv.iam.enums.RoleCategoryType;
import com.beehyv.iam.manager.NotificationUserTokenManager;
import com.beehyv.iam.manager.NotificationStateRoleMappingManager;
import com.beehyv.iam.manager.UserManager;
import com.beehyv.iam.model.NotificationUserToken;
import com.beehyv.iam.model.NotificationStateRoleMapping;
import com.beehyv.iam.model.User;

import com.beehyv.parent.exceptions.CustomException;
import java.util.*;

import java.util.stream.Collectors;

import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
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
