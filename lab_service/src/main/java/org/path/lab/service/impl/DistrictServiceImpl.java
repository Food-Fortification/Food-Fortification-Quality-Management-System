package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.DistrictRequestDTO;
import org.path.lab.dto.responseDto.DistrictResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.entity.District;
import org.path.lab.manager.DistrictManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.DistrictService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    private final DistrictManager districtManager;

    @Override
    public void create(DistrictRequestDTO districtRequestDTO) {
        districtManager.create(mapper.mapDtoToEntityDistrict(districtRequestDTO));
    }

    @Override
    public DistrictResponseDTO getById(Long id) {
        District district = districtManager.findById(id);
        if(district != null) {
            return mapper.mapToResponseDto(district);
        } else {
            return null;
        }
    }

    @Override
    public void update(DistrictRequestDTO districtRequestDTO) {
        districtManager.update(mapper.mapDtoToEntityDistrict(districtRequestDTO));
    }

    @Override
    public void delete(Long id) {
       districtManager.delete(id);
    }

    @Override
    public ListResponse<DistrictResponseDTO> getAll(Integer pageNumber,Integer pageSize){
        List<District> entities = districtManager.findAll(pageNumber,pageSize);
        Long count = districtManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities, mapper::mapToResponseDto,count);
    }

    @Override
    public ListResponse<LocationResponseDto> getAllDistrictsByStateId(Long stateId, String search, Integer pageNumber, Integer pageSize){
        List<District> entities = districtManager.findAllByStateId(stateId, search, pageNumber,pageSize);
        Long count = districtManager.getCountByStateId(stateId, search);
        return ListResponse.from(entities, mapper::mapToDto, count);
    }
}
