package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.LocationRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.LocationResponseDto;
import com.beehyv.iam.manager.CountryManager;
import com.beehyv.iam.model.Country;
import com.beehyv.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CountryServiceTest {

    @Mock
    private CountryManager countryManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCountry() {
        LocationRequestDto requestDto = new LocationRequestDto();
        Country country = new Country();
        when(dtoMapper.mapToEntityCountry(requestDto)).thenReturn(country);

        countryService.create(requestDto);

        verify(countryManager, times(1)).create(country);
    }

    @Test
    public void testUpdateCountry() {
        LocationRequestDto requestDto = new LocationRequestDto();
        Country country = new Country();
        when(dtoMapper.mapToEntityCountry(requestDto)).thenReturn(country);

        countryService.update(requestDto);

        verify(countryManager, times(1)).update(country);
    }

    @Test
    public void testDeleteCountry() {
        Long countryId = 1L;

        countryService.delete(countryId);

        verify(countryManager, times(1)).delete(countryId);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Country country = new Country();
        LocationResponseDto responseDto = new LocationResponseDto();
        when(countryManager.findById(id)).thenReturn(country);
        when(dtoMapper.mapToDto(country)).thenReturn(responseDto);

        LocationResponseDto result = countryService.getById(id);

        assertEquals(responseDto, result);
    }

    @Test
    public void testGetAllCountries() {
        String search = "India";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        long count = 0L;
        List<Country> countries = Collections.emptyList();

        when(countryManager.findAll(search, pageNumber, pageSize)).thenReturn(countries);
        when(countryManager.getCount(search)).thenReturn(count);

        ListResponse<LocationResponseDto> expectedResponse = new ListResponse<>(count, Collections.emptyList());

        ListResponse<LocationResponseDto> result = countryService.getAllCountries(search, pageNumber, pageSize);

        assertEquals(expectedResponse.getCount(), result.getCount());
        assertEquals(expectedResponse.getData(), result.getData());
    }
}
