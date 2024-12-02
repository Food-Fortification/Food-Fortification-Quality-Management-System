package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.BatchRequestDto;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.entity.*;
import org.path.parent.utils.FileValidator;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.path.fortification.mapper.BaseMapper.*;

public class BatchMapper implements Mappable<BatchResponseDto, BatchRequestDto, Batch>, ListMappable<BatchListResponseDTO, BatchRequestDto, Batch> {
    // Convert User JPA Entity into BatchDto
    @Override
    public BatchResponseDto toDto(Batch entity) {
        if(entity == null) return null;
        BatchResponseDto dto = new BatchResponseDto();
        BeanUtils.copyProperties(entity, dto);
        if(entity.getCategory() != null) dto.setCategory(categoryMapper.toDto(entity.getCategory()));
        if(entity.getUom() != null) dto.setUom(uomMapper.toDto(entity.getUom()));
        if(entity.getState() != null) dto.setState(stateMapper.toDto(entity.getState()));
        if(entity.getBatchProperties() != null) {
            Set<BatchPropertyResponseDto> list = entity.getBatchProperties()
                    .stream().map(batchPropertyMapper::toDto)
//                    .peek(d -> d.setBatchId(entity.getId()))
                    .collect(Collectors.toSet());
            dto.setBatchProperties(list);
        }
        entity.getBatchProperties().stream().filter(bp -> bp.getName().equals("batch_name")).findFirst()
                .ifPresent(bp -> dto.setBatchName(bp.getValue()));
        if(entity.getBatchDocs() != null) {
            Set<BatchDocResponseDto> list = entity.getBatchDocs()
                    .stream().map(batchDocMapper::toDto)
//                    .peek(d -> d.setBatchId(entity.getId()))
                    .collect(Collectors.toSet());
            dto.setBatchDocs(list);
        }
        if(entity.getSizeUnits() != null) {
            Set<SizeUnitResponseDto> set = entity.getSizeUnits()
                    .stream().map(sizeUnitMapper::toDto)
//                    .peek(d -> d.setBatchId(entity.getId()))
                    .sorted(Comparator.comparing(SizeUnitResponseDto::getId).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            dto.setSizeUnits(set);
        }
        if(entity.getLots() != null) {
            Set<LotListResponseDTO> set = entity.getLots()
                    .stream().map(lotListMapper::toListDTO)
                    .sorted(Comparator.comparing(LotListResponseDTO::getId).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            dto.setLots(set);
        }
        if(entity.getMixes() != null) {
            Set<MixMappingResponseDto> set = entity.getMixes()
                    .stream()
                    .map(mixMapper::toDto)
                    .sorted(Comparator.comparing(MixMappingResponseDto::getId).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            dto.setMixes(set);
        }
        if(entity.getWastes() != null) {
            Set<WastageResponseDto> set = entity.getWastes()
                    .stream().map(wastageMapper::toDto)
                    .sorted(Comparator.comparing(WastageResponseDto::getId).reversed())
//                    .peek(d -> d.setBatchId(entity.getId()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            dto.setWastes(set);
        }
        return dto;
    }

    // Convert BatchDto into Batch JPA Entity
    @Override
    public Batch toEntity(BatchRequestDto dto) {
        Batch batch = new Batch();
        BeanUtils.copyProperties(dto, batch, "batchNo");
        setProperties(dto, batch);
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AccessToken token = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        BatchProperty batchProperty = new BatchProperty(null, "createdBy", token.getName(), batch);
        Set<BatchProperty> batchProperties = batch.getBatchProperties();
        batchProperties.add(batchProperty);
        batch.setBatchProperties(batchProperties);
        return batch;
    }

    public static Batch toEntity(BatchRequestDto batchRequestDto, Batch batch1) {
        Batch batch = new Batch();
        BeanUtils.copyProperties(batch1, batch);
        BeanUtils.copyProperties(batchRequestDto, batch, "id", "uuid", "createdBy", "createdDate", "state", "uomId", "remainingQuantity", "isLabTested", "lotSequence", "batchNo", "comments", "isBlocked");
        setProperties(batchRequestDto, batch);
        batch.getBatchProperties().add(batch1.getBatchProperties().stream().filter(batchProperty -> batchProperty.getName().equals("createdBy")).findFirst().orElse(null));
        return batch;
    }

    private static void setProperties(BatchRequestDto dto, Batch batch) {
        if (dto.getUomId() != null) {
            batch.setUom(new UOM(dto.getUomId()));
        }
        if (dto.getCategoryId() != null) {
            batch.setCategory(new Category(dto.getCategoryId()));
        }
        if(dto.getBatchProperties() != null) {
            Set<BatchProperty> list = dto.getBatchProperties()
                    .stream().map(batchPropertyMapper::toEntity)
                    .peek(d -> {
                        d.setBatch(batch);
                    })
                    .collect(Collectors.toSet());
            batch.setBatchProperties(list);
        }
        dto.getBatchDocs().forEach(d -> FileValidator.validateFileUpload(d.getPath()));
        if(dto.getBatchDocs() != null) {
            Set<BatchDoc> list = dto.getBatchDocs()
                    .stream().map(batchDocMapper::toEntity)
                    .peek(d -> {
                        d.setBatch(batch);
                    })
                    .collect(Collectors.toSet());
            batch.setBatchDocs(list);
        }
        if(dto.getLots() != null) {
            Set<Lot> set = dto.getLots()
                    .stream().map(lotMapper::toEntity)
                    .peek(d -> d.setBatch(batch, d))
                    .collect(Collectors.toSet());
            batch.setLots(set);
        }
        if(dto.getMixes() != null) {
            Set<MixMapping> set = dto.getMixes()
                    .stream().map(mixMapper::toEntity)
                    .peek(d -> d.setTargetBatch(batch))
                    .collect(Collectors.toSet());
            batch.setMixes(set);
        }
        if(dto.getWastes() != null) {
            Set<Wastage> set = dto.getWastes()
                    .stream().map(wastageMapper::toEntity)
                    .peek(d -> {
                        d.setBatch(batch);
                    })
                    .collect(Collectors.toSet());
            batch.setWastes(set);
        }
    }

    @Override
    public BatchListResponseDTO toListDto(Batch entity) {
        BatchListResponseDTO batchListResponseDTO = new BatchListResponseDTO();
        BeanUtils.copyProperties(entity, batchListResponseDTO);
        entity.getBatchProperties().stream()
                .filter(bp -> bp.getName().equals("manufacture_batchNumber"))
                .findFirst()
                .ifPresent(bp -> batchListResponseDTO.setManufacturerBatchNumber(bp.getValue()));
        if(entity.getCategory() != null) batchListResponseDTO.setCategory(categoryMapper.toDto(entity.getCategory()));
        if(entity.getState() != null) batchListResponseDTO.setState(entity.getState().getDisplayName());
        if(entity.getUom() != null) batchListResponseDTO.setUom(entity.getUom().getName());
        entity.getBatchProperties().stream().filter(batchProperty -> batchProperty.getName().equals("batch_name"))
                .findFirst().ifPresent(batchProperty -> batchListResponseDTO.setName(batchProperty.getValue()));
        batchListResponseDTO.setCreatedBy(null);
        batchListResponseDTO.setBatchProperties(entity.getBatchProperties()
                .stream().map(batchPropertyMapper::toDto)
                .collect(Collectors.toSet()));
        entity.getBatchProperties().stream().filter(batchProperty -> batchProperty.getName().equals("createdBy"))
                .findFirst().ifPresent(batchProperty -> batchListResponseDTO.setCreatedBy(batchProperty.getValue()));
        return batchListResponseDTO;
    }
}
