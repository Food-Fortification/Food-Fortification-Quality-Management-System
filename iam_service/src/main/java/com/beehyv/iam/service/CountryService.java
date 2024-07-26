package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.LocationRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.LocationResponseDto;
import com.beehyv.iam.manager.CountryManager;
import com.beehyv.iam.model.Country;
import com.beehyv.iam.utils.DtoMapper;
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
