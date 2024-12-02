package org.path.lab.service;

import org.path.lab.dto.requestDto.CountryRequestDTO;
import org.path.lab.dto.responseDto.CountryResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;


public interface CountryService {

    void createCountry(CountryRequestDTO CountryRequestDTO);

    CountryResponseDTO getCountryById(Long id);

    void updateCountry(CountryRequestDTO CountryRequestDTO);

    void deleteCountry(Long id);

    ListResponse<CountryResponseDTO> getAllCountries(String search, Integer pageNumber, Integer pageSize);
}
