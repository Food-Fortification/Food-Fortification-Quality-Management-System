package org.path.lab.validations;

import org.path.lab.dto.requestDto.SearchCriteria;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabSampleResponseDto;
import org.path.lab.helper.Constants;
import org.path.lab.helper.RestHelper;
import org.path.parent.exceptions.CustomException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component("authorityChecker")
public class AuthorityChecker {
    private final RestHelper restHelper;
    private final KeycloakInfo keycloakInfo;

    public boolean hasSuperAdminAccessForCategory(Long categoryId) {
        String url = Constants.FORTIFICATION_BASE_URL + "category/" + categoryId + "/super-admin-access?roleCategoryType=LAB";
        return restHelper.checkAccess(url, keycloakInfo.getAccessToken());
    }

    public boolean hasSuperMonitorAccess(){
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return roles.stream().anyMatch(r -> r.contains("MONITOR"));
    }
    public boolean hasSuperAdminAccessForCategory(SearchListRequest searchListRequest) {
        Optional<Object> obj = searchListRequest.getSearchCriteriaList().stream().filter(c->c.getKey().equals("categoryId"))
                .map(SearchCriteria::getValue).findAny();
        if(obj.isEmpty() || obj.get().equals("")) throw new CustomException("Category id is not provided", HttpStatus.BAD_REQUEST);
        Long categoryId = Long.parseLong(obj.get().toString());
        return this.hasSuperAdminAccessForCategory(categoryId);
    }

    public boolean isCurrentLabUser(Long labId) {
        String labIdString = keycloakInfo.getUserInfo().getOrDefault("labId", "-1")
                .toString();
        Long labIdToken = Long.parseLong(labIdString);
        return labId.equals(labIdToken);
    }

    public boolean isCurrentLabUser(LabSampleResponseDto labSampleResponseDto) {
        if(labSampleResponseDto.getLab() != null){
            String labIdString = keycloakInfo.getUserInfo().getOrDefault("labId", "-1").toString();
            Long labIdToken = Long.parseLong(labIdString);
            return labSampleResponseDto.getLab().getId().equals(labIdToken);
        }
        if(labSampleResponseDto.getLotId() != null){
            String url = String.format("%s%s/lot/%s/lab-access",Constants.FORTIFICATION_BASE_URL,labSampleResponseDto.getCategoryId(),labSampleResponseDto.getLotId());
            return restHelper.checkAccess(url, keycloakInfo.getAccessToken());
        } else if (labSampleResponseDto.getBatchId() != null) {
            String url = String.format("%s%s/batch/%s/lab-access", Constants.FORTIFICATION_BASE_URL,labSampleResponseDto.getCategoryId(),labSampleResponseDto.getBatchId());
            return restHelper.checkAccess(url, keycloakInfo.getAccessToken());
        }
        return false;
    }

    public boolean isInspectionUser() {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("INSPECTION"));
    }

}
