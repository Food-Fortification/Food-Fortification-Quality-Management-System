package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.ManufacturerCategoryAttributesRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.ManufacturerCategoryAttributesResponseDto;
import com.beehyv.iam.enums.AttributeScoreType;
import com.beehyv.iam.manager.AttributeManager;
import com.beehyv.iam.manager.ManufacturerCategoryAttributesManager;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.manager.UserManager;
import com.beehyv.iam.model.*;
import com.beehyv.iam.utils.DtoMapper;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerCategoryAttributesService {
  
  private final ManufacturerCategoryAttributesManager manufacturerAttributesManager;
  private final ManufacturerManager manufacturerManager;
  private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);
  private final KeycloakInfo keycloakInfo;
  private final UserManager userManager;
  private final AttributeManager attributeManager;

  public Long create(ManufacturerCategoryAttributesRequestDto dto) {
    User loggedInuser = userManager.findByEmail(keycloakInfo.getUserInfo().getOrDefault("email", "").toString());
    dto.setUserId(loggedInuser.getId());
    ManufacturerCategoryAttributes entity = dtoMapper.mapToEntity(dto);
    manufacturerAttributesManager.create(entity);
    this.reCalculateManufacturerScore(entity.getManufacturer().getId(), entity);
    return entity.getId();
  }

  public void update(ManufacturerCategoryAttributesRequestDto dto) {
    User loggedInuser = userManager.findByEmail(keycloakInfo.getUserInfo().getOrDefault("email", "").toString());
    if (loggedInuser != null && loggedInuser.getId().equals(dto.getUserId())){
      ManufacturerCategoryAttributes entity = dtoMapper.mapToEntity(dto);
      manufacturerAttributesManager.update(entity);
      this.reCalculateManufacturerScore(entity.getManufacturer().getId(), entity);
    }else {
      throw new CustomException("Cannot update different users ratings", HttpStatus.BAD_REQUEST);
    }

  }


  public void reCalculateManufacturerScore(Long manufacturerId, ManufacturerCategoryAttributes attributeCategories) {
    Manufacturer manufacturer = manufacturerManager.findById(manufacturerId);
    List<Long> attributeIds = attributeCategories.getAttributeCategoryScores().stream()
            .flatMap(ac -> ac.getAttributeScore().stream().map(a -> a.getAttribute().getId()))
            .toList();
    List<Attribute> attributes = attributeManager.findByIds(attributeIds);
    double totalScore = attributeCategories.getAttributeCategoryScores().stream().mapToDouble(ac -> ac.getAttributeScore().stream().mapToDouble(a -> {
      if (attributes.stream()
              .filter(attribute -> a.getAttribute().getId().equals(attribute.getId()))
              .findFirst().get().getType().equals(AttributeScoreType.SCORE))
        return Double.parseDouble(a.getValue() == null ? "0" : a.getValue());
      return 0D;
    }).sum()).sum();
    manufacturer.setTotalScore(totalScore);
    manufacturerManager.update(manufacturer);
  }

  public void delete(Long id){
    manufacturerAttributesManager.delete(id);
  }

  public ManufacturerCategoryAttributesResponseDto getById(Long id){
     return dtoMapper.mapToDto(manufacturerAttributesManager.findById(id));
  }

  public ListResponse<ManufacturerCategoryAttributesResponseDto> findAll(Integer pageNumber, Integer pageSize) {
    List<ManufacturerCategoryAttributes> entities = manufacturerAttributesManager.findAll(pageNumber, pageSize);
    Long count = manufacturerAttributesManager.getCount(entities.size(), pageNumber, pageSize);
    return ListResponse.from(entities, dtoMapper::mapToDto, count);
  }

  public List<ManufacturerCategoryAttributesResponseDto> getAllManufacturerAttributesForManufacturer(Long manufacturerId, Long userId) {
    Long loggedInUserId = userManager.findByEmail(keycloakInfo.getUserInfo().getOrDefault("email", "").toString()).getId();
    List<ManufacturerCategoryAttributes> entities;
    if (userId!= null && userId!= 0L){
      entities = manufacturerAttributesManager.findByManufacturerIdAndUserId(manufacturerId, userId);
    }else {
      entities = manufacturerAttributesManager.findByManufacturerIdAndUserId(manufacturerId, loggedInUserId);
    }
    if (entities.isEmpty()) return new ArrayList<>();
    return entities.stream().map(dtoMapper::mapToDto).toList();
  }
}
