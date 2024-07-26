package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.CountryRequestDTO;
import com.beehyv.lab.dto.responseDto.CountryResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;


public interface CountryService {

    void createCountry(CountryRequestDTO CountryRequestDTO);

    CountryResponseDTO getCountryById(Long id);

    void updateCountry(CountryRequestDTO CountryRequestDTO);

    void deleteCountry(Long id);

    ListResponse<CountryResponseDTO> getAllCountries(String search, Integer pageNumber, Integer pageSize);
}
