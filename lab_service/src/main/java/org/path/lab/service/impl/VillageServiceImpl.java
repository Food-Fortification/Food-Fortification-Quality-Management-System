package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.VillageRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.dto.responseDto.VillageResponseDTO;
import org.path.lab.entity.Village;
import org.path.lab.manager.VillageManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.VillageService;
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
