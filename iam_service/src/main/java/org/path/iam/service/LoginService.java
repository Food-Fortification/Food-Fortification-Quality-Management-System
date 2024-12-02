package org.path.iam.service;

import org.path.iam.config.KeycloakCustomConfig;
import org.path.iam.dto.requestDto.LoginRequestDto;
import org.path.iam.manager.ExternalMetaDataManager;
import org.path.parent.exceptions.CustomException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoginService {

    private final KeycloakCustomConfig keycloakCustomConfig;
    private final ExternalMetaDataManager externalMetaDataManager;


    @Value("${config.realm.resource}")
    private String realmResource;
    private final KeycloakInfo keycloakInfo;


    public AccessTokenResponse login(LoginRequestDto loginRequestDto, String mobileVersion){
        String curMobileVersion = externalMetaDataManager.findByKey("apkVersion").getValue();
        if(Objects.equals(mobileVersion, curMobileVersion)){
            return keycloakCustomConfig.getAccessToken(loginRequestDto);
        }
        throw new CustomException("Please update to the latest APK",HttpStatus.FORBIDDEN);
    }

    public void logout(){
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccessToken token = keycloakPrincipal.getKeycloakSecurityContext().getToken();
        String userName = token.getSubject();
        UserResource userResource = keycloakCustomConfig.getInstance().realm(realmResource).users().get(userName);
        userResource.logout();
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        keycloakAuthenticationToken.eraseCredentials();
    }

    public AccessTokenResponse refreshToken(String refreshToken) {
        return keycloakCustomConfig.refreshToken(refreshToken);
    }

    public Map<String,Object> getUserDetails() {
        StringBuilder role = new StringBuilder();
        String roleString = role.substring(0,role.length()-1);
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        AccessToken token = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        Map<String,Object> map = new HashMap<>();
        map.put("firstName", token.getGivenName());
        map.put("lastName", token.getFamilyName());
        map.put("userName", token.getPreferredUsername());
        map.put("email", token.getEmail());
        map.put("role", roleString);
        String stateGeoId = keycloakInfo.getUserInfo().getOrDefault("stateGeoId", "").toString();
        map.put("stateGeoId", stateGeoId);
        String districtGeoId = keycloakInfo.getUserInfo().getOrDefault("districtGeoId", "").toString();
        map.put("districtGeoId", districtGeoId);
        return map;
    }
}
