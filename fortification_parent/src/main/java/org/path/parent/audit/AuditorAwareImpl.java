package org.path.parent.audit;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;
@Component("auditAwareImpl")
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest request;

    @Override
    public Optional<String> getCurrentAuditor() {
        String email = null;
        try {
            AccessToken accessToken = this.getKeycloakToken(request.getUserPrincipal());
            email = accessToken.getEmail();
        } catch (Exception e) {
            log.error("Bypassing current auditor");
        }
        return Optional.ofNullable(email);
    }

    private AccessToken getKeycloakToken(Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        return keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
    }
}

