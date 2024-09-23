package com.beehyv.lab.service.impl;
import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

        import com.beehyv.lab.dto.requestDto.CountryRequestDTO;
import com.beehyv.lab.dto.responseDto.CountryResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.Country;
import com.beehyv.lab.manager.CountryManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    private CountryManager countryManager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private CountryServiceImpl countryService;

    private CountryRequestDTO countryRequestDTO;
    private CountryResponseDTO countryResponseDTO;
    private Country country;


    @BeforeEach
    void setUp() {
        // Initialize CountryRequestDTO with the expected constructor arguments
        countryRequestDTO = new CountryRequestDTO(1L, "Test Country");

        // Initialize CountryResponseDTO
        countryResponseDTO = new CountryResponseDTO();

        // Initialize Country entity
        country = new Country();
        country.setId(1L);
        country.setName("Test Country");
        // Set other necessary fields
        country.setCreatedBy(null);
        country.setCreatedDate(null);
        country.setModifiedBy(null);
        country.setModifiedDate(null);
        country.setUuid(null);
        country.setIsDeleted(false);
        country.setStatus(null);
    }

    @Test
    void testCreateCountry() {

        countryService.createCountry(countryRequestDTO);

        verify(countryManager, times(1)).create(refEq(country));
    }
    @Test
    void testGetCountryById() {
        when(countryManager.findById(1L)).thenReturn(country);

        CountryResponseDTO result = countryService.getCountryById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Country", result.getName());
    }

    @Test
    void testGetCountryById_NotFound() {
        when(countryManager.findById(1L)).thenReturn(null);

        CountryResponseDTO result = countryService.getCountryById(1L);

        assertNull(result);
    }
    @Test
    void testUpdateCountry() {

        countryService.updateCountry(countryRequestDTO);

        verify(countryManager, times(1)).update(refEq(country));
    }


    @Test
    void testDeleteCountry() {
        countryService.deleteCountry(1L);

        verify(countryManager, times(1)).delete(1L);
    }

    @Test
    void testGetAllCountries() {
        List<Country> countries = Collections.singletonList(country);
        when(countryManager.findAll("Test", 0, 10)).thenReturn(countries);
        when(countryManager.getCount("Test")).thenReturn(1L);

        ListResponse<CountryResponseDTO> result = countryService.getAllCountries("Test", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals("Test Country", result.getData().get(0).getName());
    }
}
