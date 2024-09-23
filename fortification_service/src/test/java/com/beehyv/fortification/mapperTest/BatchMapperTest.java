package com.beehyv.fortification.mapperTest;

import com.beehyv.fortification.dto.requestDto.BatchRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchResponseDto;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.UOM;
import com.beehyv.fortification.mapper.BatchMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.stream.Collectors;

import static com.beehyv.fortification.mapper.BaseMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchMapperTest {

    @Mock
    KeycloakAuthenticationToken keycloakAuthenticationToken;
    @Mock
    OidcKeycloakAccount oidcKeycloakAccount;
    @Mock
    KeycloakSecurityContext keycloakSecurityContext;
    @Mock
    SecurityContext securityContext;
    MockedStatic<SecurityContextHolder> mockStatic;


    @Test
    void testAllMethods() {
        mockStatic = mockStatic(SecurityContextHolder.class);
        when(keycloakAuthenticationToken.getAccount()).thenReturn(oidcKeycloakAccount);
        when(oidcKeycloakAccount.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
        when(keycloakSecurityContext.getToken()).thenReturn(new AccessToken());
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(keycloakAuthenticationToken);
        // Arrange
        BatchRequestDto dto = new BatchRequestDto();
        dto.setId(1L);
        dto.setUomId(1L);
        dto.setCategoryId(1L);
        dto.setBatchProperties(new HashSet<>());
        dto.setBatchDocs(new HashSet<>());
        dto.setLots(new HashSet<>());
        dto.setMixes(new HashSet<>());
        dto.setWastes(new HashSet<>());

        Batch entity = new Batch();
        entity.setId(dto.getId());
        entity.setUom(new UOM(dto.getUomId()));
        entity.setCategory(new Category(dto.getCategoryId()));
        entity.setBatchProperties(dto.getBatchProperties().stream().map(batchPropertyMapper::toEntity).collect(Collectors.toSet()));
        entity.setBatchDocs(dto.getBatchDocs().stream().map(batchDocMapper::toEntity).collect(Collectors.toSet()));
        entity.setLots(dto.getLots().stream().map(lotMapper::toEntity).collect(Collectors.toSet()));
        entity.setMixes(dto.getMixes().stream().map(mixMapper::toEntity).collect(Collectors.toSet()));
        entity.setWastes(dto.getWastes().stream().map(wastageMapper::toEntity).collect(Collectors.toSet()));

        BatchMapper mapper = new BatchMapper();

        // Act
        Batch mappedEntity = mapper.toEntity(dto);
        BatchResponseDto mappedDto = mapper.toDto(entity);

        // Assert
        assertEquals(dto.getId(), mappedEntity.getId());
        assertEquals(dto.getUomId(), mappedEntity.getUom().getId());
        assertEquals(dto.getCategoryId(), mappedEntity.getCategory().getId());
        assertEquals(dto.getBatchDocs().size(), mappedEntity.getBatchDocs().size());
        assertEquals(dto.getLots().size(), mappedEntity.getLots().size());
        assertEquals(dto.getMixes().size(), mappedEntity.getMixes().size());
        assertEquals(dto.getWastes().size(), mappedEntity.getWastes().size());

        assertEquals(entity.getId(), mappedDto.getId());
        assertEquals(entity.getUom().getId(), mappedDto.getUom().getId());
        assertEquals(entity.getCategory().getId(), mappedDto.getCategory().getId());
        assertEquals(entity.getBatchProperties().size(), mappedDto.getBatchProperties().size());
        assertEquals(entity.getBatchDocs().size(), mappedDto.getBatchDocs().size());
        assertEquals(entity.getLots().size(), mappedDto.getLots().size());
        assertEquals(entity.getMixes().size(), mappedDto.getMixes().size());
        assertEquals(entity.getWastes().size(), mappedDto.getWastes().size());

        mockStatic.close();
    }
}