package org.path.lab.controller;

import org.path.lab.dto.requestDto.CountryRequestDTO;
import org.path.lab.dto.responseDto.CountryResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        CountryResponseDTO expectedResponse = new CountryResponseDTO();

        when(countryService.getCountryById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = countryController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(countryService, times(1)).getCountryById(id);
        verifyNoMoreInteractions(countryService);
    }

    @Test
    void testGetAllCountries() {
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<CountryResponseDTO> expectedResponse = new ListResponse<>();

        when(countryService.getAllCountries(search, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = countryController.getAllCountries(search, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(countryService, times(1)).getAllCountries(search, pageNumber, pageSize);
        verifyNoMoreInteractions(countryService);
    }

    @Test
    void testCreate() {
        CountryRequestDTO dto = new CountryRequestDTO(null,null);

        doNothing().when(countryService).createCountry(dto);

        ResponseEntity<?> responseEntity = countryController.create(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Successfully Created", responseEntity.getBody());

        verify(countryService, times(1)).createCountry(dto);
        verifyNoMoreInteractions(countryService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        CountryRequestDTO dto = new CountryRequestDTO(null,null);
        dto.setId(id);

        doNothing().when(countryService).updateCountry(dto);

        ResponseEntity<?> responseEntity = countryController.update(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("successfully updated", responseEntity.getBody());

        verify(countryService, times(1)).updateCountry(dto);
        verifyNoMoreInteractions(countryService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(countryService).deleteCountry(id);

        ResponseEntity<?> responseEntity = countryController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(countryService, times(1)).deleteCountry(id);
        verifyNoMoreInteractions(countryService);
    }
}