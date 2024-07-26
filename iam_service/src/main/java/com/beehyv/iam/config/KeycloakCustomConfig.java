package com.beehyv.iam.config;

import com.beehyv.iam.dto.requestDto.LoginRequestDto;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KeycloakCustomConfig {

    static Keycloak keycloak = null;
    @Value("${config.serverUrl}")
    private String serverUrl;
    @Value("${config.master.realm}")
    public String adminRealm;
    @Value("${config.admin.clientId}")
    private String clientId;
    @Value("${config.admin.username}")
    private String userName;
    @Value("${config.admin.password}")
    private String password;
    @Value("${config.realm.resource}")
    private String realmResource;
    @Value("${config.realm.clientId}")
    private String realmClientId;
    @Value("${config.realm.clientSecret}")
    private String realmClientSecret;

    public KeycloakCustomConfig(){

    }

    public Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(adminRealm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .build();
        }
        return keycloak;
    }
    public AccessTokenResponse getAccessToken(LoginRequestDto loginRequestDto){
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmResource)
                .grantType(OAuth2Constants.PASSWORD)
                .username(loginRequestDto.getUserName())
                .password(loginRequestDto.getPassword())
                .clientId(realmClientId)
                .clientSecret(realmClientSecret)
                .scope("openid")
                .build();
        return keycloak.tokenManager().getAccessToken();
    }
    public AccessTokenResponse refreshToken(String refreshToken) {
      String url = serverUrl + "/realms/" + realmResource + "/protocol/openid-connect/token";
        Http http = new Http(kcConfig(), (params, headers) -> {});

        return http.<AccessTokenResponse>post(url)
                .authentication()
                .client()
                .form()
                .param("grant_type", "refresh_token")
                .param("refresh_token", refreshToken)
                .param("client_id", realmClientId)
                .param("client_secret", realmClientSecret)
                .response()
                .json(AccessTokenResponse.class)
                .execute();
    }

    public org.keycloak.authorization.client.Configuration kcConfig() {
        Map<String, Object> credentials= new HashMap<>();
        credentials.put(realmClientId,realmClientSecret);
        return new org.keycloak.authorization.client.Configuration(
                serverUrl,
                realmResource,
                realmClientId,
                credentials,
                null
        );
    }

}
