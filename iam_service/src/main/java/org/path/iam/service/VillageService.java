package org.path.iam.service;

import org.path.iam.dto.requestDto.VillageRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.LocationResponseDto;
import org.path.iam.dto.responseDto.VillageResponseDto;
import org.path.iam.manager.VillageManager;
import org.path.iam.model.Village;
import org.path.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VillageService {
    private final VillageManager villageManager;
    private final DtoMapper dtoMapper;

    public Long create(VillageRequestDto dto){
        Village village = dtoMapper.mapToEntity(dto);
       village = villageManager.create(village);
       return village.getId();
    }
    public void update(VillageRequestDto dto){
        Village village = dtoMapper.mapToEntity(dto);
        villageManager.update(village);
    }
    public VillageResponseDto getById(Long id){
        Village village = villageManager.findById(id);
        return dtoMapper.mapToResponseDto(village);
    }
    public ListResponse<VillageResponseDto> findAll(Integer pageNumber,Integer pageSize){
        List<Village> entities = villageManager.findAll(pageNumber,pageSize);
        Long count = villageManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities, dtoMapper::mapToResponseDto,count);
    }
    public void delete(Long id){
        villageManager.delete(id);
    }

    public ListResponse<LocationResponseDto> getAllVillagesByDistrictId(Long districtId, Integer pageNumber, Integer pageSize){
        List<Village> entities = villageManager.findAllByDistrictId(districtId, pageNumber,pageSize);
        Long count = villageManager.getCount(entities.size(), pageNumber,pageSize);
        return ListResponse.from(entities, dtoMapper::mapToDto,count);
    }
}
