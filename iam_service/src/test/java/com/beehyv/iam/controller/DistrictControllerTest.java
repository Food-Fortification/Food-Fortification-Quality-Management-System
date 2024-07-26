package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.DistrictRequestDto;
import com.beehyv.iam.dto.responseDto.DistrictResponseDto;
import com.beehyv.iam.service.DistrictService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class DistrictControllerTest {

    @Mock
    private DistrictService districtService;

    @InjectMocks
    private DistrictController districtController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        DistrictResponseDto district = new DistrictResponseDto();
        district.setId(1L);
        when(districtService.getById(anyLong())).thenReturn(district);

        ResponseEntity<?> response = districtController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(district, response.getBody());
    }

    @Test
    void testCreate() {
        DistrictRequestDto dto = new DistrictRequestDto();

        ResponseEntity<?> response = districtController.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully Created", response.getBody());
    }

    @Test
    void testUpdate() {
        DistrictRequestDto dto = new DistrictRequestDto();
        dto.setId(1L);

        ResponseEntity<?> response = districtController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testDelete() {
        ResponseEntity<?> response = districtController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Deleted", response.getBody());
    }


    @Test
    void testGetAllByStateGeoId() {
        List<DistrictResponseDto> districts = Arrays.asList(new DistrictResponseDto(), new DistrictResponseDto());
        when(districtService.getAllDistrictsByStateGeoId(anyString())).thenReturn(districts);

        ResponseEntity<?> response = districtController.getAllByStateId("geoId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(districts, response.getBody());
    }

    @Test
    void testGetAllByGeoIds() {
        List<DistrictResponseDto> districts = Arrays.asList(new DistrictResponseDto(), new DistrictResponseDto());
        when(districtService.getAllByGeoIds(anyList())).thenReturn(districts);

        ResponseEntity<?> response = districtController.getAllByGeoIds(Arrays.asList("geoId1", "geoId2"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(districts, response.getBody());
    }
}
