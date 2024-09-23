package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.CountryRequestDTO;
import com.beehyv.lab.dto.responseDto.CountryResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.Country;
import com.beehyv.lab.manager.CountryManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.CountryService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    private final CountryManager countryManager;

    @Override
    public void createCountry(CountryRequestDTO countryRequestDTO) {
        countryManager.create(mapper.mapDtoToEntityCountry(countryRequestDTO));
    }

    @Override
    public CountryResponseDTO getCountryById(Long id) {
        Country country = countryManager.findById(id);
        if(country != null) {
            return mapper.mapEntityToDtoCountry(country);
        } else {
            return null;
        }
    }

    @Override
    public void updateCountry(CountryRequestDTO countryRequestDTO) {
        countryManager.update(mapper.mapDtoToEntityCountry(countryRequestDTO));
    }

    @Override
    public void deleteCountry(Long id) {
        countryManager.delete(id);
    }

    @Override
    public ListResponse<CountryResponseDTO> getAllCountries(String search, Integer pageNumber, Integer pageSize) {
        List<Country> entities = countryManager.findAll(search, pageNumber,pageSize);
        Long count = countryManager.getCount(search);
        return ListResponse.from(entities, mapper::mapEntityToDtoCountry,count);
    }
}
