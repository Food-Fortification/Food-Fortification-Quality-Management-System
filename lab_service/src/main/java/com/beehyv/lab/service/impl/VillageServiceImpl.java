package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.VillageRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;
import com.beehyv.lab.dto.responseDto.VillageResponseDTO;
import com.beehyv.lab.entity.Village;
import com.beehyv.lab.manager.VillageManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.VillageService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class VillageServiceImpl implements VillageService {

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    private final VillageManager villageManager;


    @Override
    public Long create(VillageRequestDTO villageRequestDTO) {
       Village village = mapper.mapDtoToEntityVillage(villageRequestDTO);
       village =  villageManager.create(village);
       return village.getId();
    }

    @Override
    public VillageResponseDTO getById(Long id) {
        Village village = villageManager.findById(id);
        if(village != null) {
            return mapper.mapToResponseDto(village);
        } else {
            return null;
        }
    }

    @Override
    public void update(VillageRequestDTO villageRequestDTO) {
        villageManager.update(mapper.mapDtoToEntityVillage(villageRequestDTO));
    }

    @Override
    public void delete(Long id) {
        villageManager.delete(id);
    }

    @Override
    public ListResponse<VillageResponseDTO> findAll(Integer pageNumber,Integer pageSize){
        List<Village> entities = villageManager.findAll(pageNumber,pageSize);
        Long count = villageManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities, mapper::mapToResponseDto,count);
    }

    @Override
    public ListResponse<LocationResponseDto> getAllVillagesByDistrictId(Long districtId, Integer pageNumber, Integer pageSize){
        List<Village> entities = villageManager.findAllByDistrictId(districtId, pageNumber,pageSize);
        Long count = villageManager.getCount(districtId);
        return ListResponse.from(entities, mapper::mapToDto,count);
    }
}
