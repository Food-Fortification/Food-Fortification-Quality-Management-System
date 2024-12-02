package org.path.iam.service;

import org.path.iam.dto.requestDto.LocationRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.LocationResponseDto;
import org.path.iam.manager.CountryManager;
import org.path.iam.model.Country;
import org.path.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryService {
    private final CountryManager countryManager;
    private final DtoMapper dtoMapper;

    public void create(LocationRequestDto dto){
        countryManager.create(dtoMapper.mapToEntityCountry(dto));
    }

    public void update(LocationRequestDto dto){
        countryManager.update(dtoMapper.mapToEntityCountry(dto));
    }

    public void delete(Long countryId){
        countryManager.delete(countryId);
    }

    public LocationResponseDto getById(Long id){
       Country country = countryManager.findById(id);
       return dtoMapper.mapToDto(country);
    }
    public ListResponse<LocationResponseDto> getAllCountries(String search, Integer pageNumber, Integer pageSize){
        List<Country> entities = countryManager.findAll(search, pageNumber,pageSize);
        Long count = countryManager.getCount(search);
        return ListResponse.from(entities, dtoMapper::mapToDto,count);
    }
}
