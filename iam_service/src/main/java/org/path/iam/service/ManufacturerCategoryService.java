package org.path.iam.service;

import org.path.iam.dto.requestDto.ManufacturerCategoryRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.ManufacturerCategoryResponseDto;
import org.path.iam.manager.ManufacturerCategoryManager;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.ManufacturerCategoryMapper;
import org.path.iam.model.ManufacturerCategory;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerCategoryService {

    private final ManufacturerCategoryManager manufacturerCategoryManager;
    private final KeycloakInfo keycloakInfo;
    private final BaseMapper<ManufacturerCategoryResponseDto, ManufacturerCategoryRequestDto,ManufacturerCategory> manufacturerCategoryMapper = BaseMapper.getForClass(ManufacturerCategoryMapper.class);

    public void create(ManufacturerCategoryRequestDto manufacturerCategoryRequestDto){
        ManufacturerCategory entity = manufacturerCategoryMapper.toEntity(manufacturerCategoryRequestDto);
        entity.setAction(manufacturerCategoryRequestDto.getAction());
        manufacturerCategoryManager.create(entity);
    }
    public void update(ManufacturerCategoryRequestDto manufacturerCategoryRequestDto){
        ManufacturerCategory entity = manufacturerCategoryMapper.toEntity(manufacturerCategoryRequestDto);
        entity.setAction(manufacturerCategoryRequestDto.getAction());
        entity.setIsDeleted(false);
        manufacturerCategoryManager.update(entity);
    }
    public ManufacturerCategoryResponseDto getById(Long id){
        ManufacturerCategory manufacturerCategory = manufacturerCategoryManager.findById(id);
        return manufacturerCategoryMapper.toDto(manufacturerCategory);
    }
    public ListResponse<ManufacturerCategoryResponseDto> getAll(Integer pageNumber,Integer pageSize){
        List<ManufacturerCategory> entities = manufacturerCategoryManager.findAll(pageNumber,pageSize);
        Long count = manufacturerCategoryManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities,manufacturerCategoryMapper::toDto,count);
    }
    public void deleteById(Long id){
        manufacturerCategoryManager.delete(id);
    }

    public List<Long> getCategoriesForManufacturer(Long manufacturerId){
        if (manufacturerId==null) manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<ManufacturerCategory> manufacturerCategories = manufacturerCategoryManager.findAllByManufacturerId(manufacturerId);
        return manufacturerCategories.stream().map(ManufacturerCategory::getCategoryId).toList();
    }

    public Boolean getCanSkipRawMaterialsForManufacturerAndCategory(Long manufacturerId, Long categoryId){
        return manufacturerCategoryManager.getCanSkipRawMaterialsForManufacturerAndCategory(manufacturerId, categoryId);
    }

    public String getActionNameByManufacturerIdAndCategoryId(Long manufacturerId, Long categoryId){
        return manufacturerCategoryManager.getActionNameByManufacturerIdAndCategoryId(manufacturerId, categoryId);
    }


}
