package org.path.iam.controller;

import org.path.iam.dto.requestDto.ManufacturerRequestDto;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.dto.responseDto.*;
import org.path.iam.enums.GeoType;
import org.path.iam.enums.UserType;
import org.path.iam.manager.ManufacturerManager;
import org.path.iam.service.ManufacturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ManufacturerControllerTest {

    @Mock
    private ManufacturerService manufacturerService;

    @Mock
    private ManufacturerManager manufacturerManager;

    @InjectMocks
    private ManufacturerController manufacturerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllManufacturers() {
        ListResponse<ManufacturerResponseDto> responseList = new ListResponse<>();
        when(manufacturerService.getALlManufacturers(anyString(), any(), any())).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerController.getAllManufacturers(0, 10, "search");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetAllSourceMaterialManufacturers() {
        ListResponse<ManufacturerResponseDto> responseList = new ListResponse<>();
        when(manufacturerService.getAllMaterialManufacturers(anyLong(), anyString(), any(), any())).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerController.getAllSourceMaterialManufacturers(0, 10, "search", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetById() {
        ManufacturerResponseDto responseDto = new ManufacturerResponseDto();
        when(manufacturerService.getManufacturerByID(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = manufacturerController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetManufacturerNameById() {
        String manufacturerName = "Test Manufacturer";
        when(manufacturerService.getManufacturerNameById(anyLong())).thenReturn(manufacturerName);

        ResponseEntity<?> response = manufacturerController.getManufacturerNameById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(manufacturerName, response.getBody());
    }

    @Test
    void testUpdateManufacturer() {
        ManufacturerRequestDto dto = new ManufacturerRequestDto();
        doNothing().when(manufacturerService).update(any(ManufacturerRequestDto.class));

        ResponseEntity<?> response = manufacturerController.updateManufacturer(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testDeleteManufacturerById() {
        doNothing().when(manufacturerService).delete(anyLong());

        ResponseEntity<?> response = manufacturerController.deleteManufacturerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetManufacturerDetailsForLoggedInUser() {
        ManufacturerDetailsResponseDto responseDto = new ManufacturerDetailsResponseDto();
        when(manufacturerService.getManufacturerDetails(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = manufacturerController.getManufacturerDetailsForLoggedInUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }


    @Test
    void testListDetails() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<NameAddressResponseDto> responseList = List.of(new NameAddressResponseDto());
        when(manufacturerService.getListDetails(ids)).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerController.listDetails(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetListByType() {
        ListResponse<ManufacturerDetailsResponseDto> responseList = new ListResponse<>();
        when(manufacturerService.getByType(anyString(), anyString(), any(), any(), any(), any())).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerController.getListByType("type", "search", 1L, 1L, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetAllRoleCategoriesForManufacturerId() {
        Map<String, CategoryRoleResponseDto> roleCategories = new HashMap<>();
        when(manufacturerService.getAllRoleCategoriesForManufacturer(anyLong())).thenReturn(roleCategories);

        ResponseEntity<?> response = manufacturerController.getAllRoleCategoriesForManufacturerId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roleCategories, response.getBody());
    }

    @Test
    void testGetAllManufacturerFromCategoryIds() {
        ListResponse<ManufacturerResponseDto> responseList = new ListResponse<>();
        when(manufacturerService.getAllManufacturerFromCategoryIds(anyString(), any(), any(), any(), any())).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerController.getAllManufacturerFromCategoryIds(List.of(1L, 2L, 3L), UserType.INSPECTION, "search", 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetTestManufacturerIds() {
        List<Long> testManufacturerIds = List.of(1L, 2L, 3L);
        when(manufacturerService.getTestManufacturerIds()).thenReturn(testManufacturerIds);

        ResponseEntity<?> response = manufacturerController.getTestManufacturerIds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testManufacturerIds, response.getBody());
    }

    @Test
    void testGetAllManufacturersWithGeoFilter() {
        ListResponse<ManufacturerDetailsResponseDto> responseList = new ListResponse<>();
        when(manufacturerService.getAllManufacturersWithGeoFilter(anyString(), anyLong(), anyLong(), any(), any())).thenReturn(responseList);

        ResponseEntity<ListResponse<ManufacturerDetailsResponseDto>> response = manufacturerController.getAllManufacturersWithGeoFilter("search", 1L, 1L, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetAllManufacturersBySearchAndFilter() {
        ListResponse<ManufacturerDetailsResponseDto> responseList = new ListResponse<>();
        SearchListRequest request = new SearchListRequest();
        when(manufacturerService.getAllManufacturersBySearchAndFilter(any(SearchListRequest.class), any(), any())).thenReturn(responseList);

        ResponseEntity<ListResponse<ManufacturerDetailsResponseDto>> response = manufacturerController.getAllManufacturersBySearchAndFilter(request, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetManufacturerIdsByAgency() {
        List<Long> manufacturerIds = List.of(1L, 2L, 3L);
        when(manufacturerService.getManufacturerIdsByAgency(anyString())).thenReturn(manufacturerIds);

        ResponseEntity<?> response = manufacturerController.getManufacturerIdsByAgency("agency");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(manufacturerIds, response.getBody());
    }

    @Test
    void testGetManufacturerAgenciesByIds() {
        List<ManufacturerAgencyResponseDto> agencies = new ArrayList<>();
        when(manufacturerService.getManufacturerAgenciesByIds(any(), any(), anyString(), any())).thenReturn(agencies);

        ResponseEntity<?> response = manufacturerController.getManufacturerAgenciesByIds(1L, GeoType.state, "geoId", List.of(1L, 2L));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agencies, response.getBody());
    }

    @Test
    void testGetDistrictGeoIdByManufacturerId() {
        String districtGeoId = "geoId";
        when(manufacturerService.getDistrictGeoIdByManufacturerId(anyLong())).thenReturn(districtGeoId);

        ResponseEntity<?> response = manufacturerController.getDistrictGeoIdByManufacturerId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(districtGeoId, response.getBody());
    }

    @Test
    void testFilterManufacturersByCategoryId() {
        List<Long> responseList = new ArrayList<>();
        when(manufacturerService.filterManufacturersByCategoryId(anyLong(), any())).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerController.filterManufacturersByCategoryId(1L, List.of(1L, 2L));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetManufacturerNamesByIds() {
        List<ManufacturerAgencyResponseDto> manufacturerNames = new ArrayList<>();
        when(manufacturerService.getManufacturerNamesByIds(any())).thenReturn(manufacturerNames);

        ResponseEntity<?> response = manufacturerController.getManufacturerNamesByIds(List.of(1L, 2L));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(manufacturerNames, response.getBody());
    }

    @Test
    void testGetManufacturerNamesByIdsAndCategoryId() {
        Map<Long, String> manufacturerNames = new HashMap<>();
        when(manufacturerService.getManufacturerNamesByIdsAndCategoryId(any(), anyLong())).thenReturn(manufacturerNames);

        ResponseEntity<?> response = manufacturerController.getManufacturerNamesByIdsAndCategoryId(List.of(1L, 2L), 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(manufacturerNames, response.getBody());
    }

}
