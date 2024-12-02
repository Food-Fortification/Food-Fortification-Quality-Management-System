package org.path.lab.validations;

import org.path.lab.dto.requestDto.SearchCriteria;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabSampleResponseDto;
import org.path.lab.helper.RestHelper;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorityCheckerTest {

    @Mock
    private RestHelper restHelper;

    @Mock
    private KeycloakInfo keycloakInfo;

    @InjectMocks
    private AuthorityChecker authorityChecker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void hasSuperAdminAccessForCategory_Success() {
        // Mock setup
        when(restHelper.checkAccess(anyString(), anyString())).thenReturn(true);

        // Test
        boolean result = authorityChecker.hasSuperAdminAccessForCategory(1L);

        // Verify
        assertFalse(result);
    }

    @Test
    void hasSuperMonitorAccess_Success() {
        // Mock setup
        Set<String> roles = new HashSet<>(Collections.singletonList("MONITOR"));
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("roles", roles));

        // Test
        boolean result = authorityChecker.hasSuperMonitorAccess();

        // Verify
        assertTrue(result);
    }

    @Test
    void hasSuperAdminAccessForCategory_SearchListRequest_Success() {
        // Mock setup
        SearchListRequest searchListRequest = new SearchListRequest();
        SearchCriteria searchCriteria = new SearchCriteria("categoryId", "1");
        searchListRequest.setSearchCriteriaList(Collections.singletonList(searchCriteria));
        when(restHelper.checkAccess(anyString(), anyString())).thenReturn(true);

        // Test
        boolean result = authorityChecker.hasSuperAdminAccessForCategory(searchListRequest);

        // Verify
        assertFalse(result);
    }

    @Test
    void isCurrentLabUser_Success() {
        // Mock setup
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("labId", "1"));

        // Test
        boolean result = authorityChecker.isCurrentLabUser(1L);

        // Verify
        assertTrue(result);
    }

    @Test
    void isCurrentLabUser_LabSampleResponseDto_Success() {
        // Mock setup
        LabSampleResponseDto labSampleResponseDto = new LabSampleResponseDto();
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("labId", "1"));

        // Test
        boolean result = authorityChecker.isCurrentLabUser(labSampleResponseDto);

        // Verify
        assertFalse(result);
    }

    // Add more test cases for other methods as needed
}
