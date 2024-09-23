package com.beehyv.parent.keycloakSecurity;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeycloakInfo {
    public Map<String,Object> getUserInfo(){
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AccessToken token =  keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        Map<String, Object> claims = token.getOtherClaims();
        claims.put("email",token.getEmail());
        Set<String> roles = token.getRealmAccess().getRoles();
        claims.put("roles",roles.stream().filter(r -> !r.contains("default")).collect(Collectors.toSet()));
        return claims;
    }
    public String getAccessToken(){
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getTokenString();
    }
}
