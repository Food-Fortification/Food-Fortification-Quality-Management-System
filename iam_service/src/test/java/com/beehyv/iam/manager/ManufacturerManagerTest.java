package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerDao;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.responseDto.DashboardCountResponseDto;
import com.beehyv.iam.dto.responseDto.ManufacturerAgencyResponseDto;
import com.beehyv.iam.enums.ManufacturerType;
import com.beehyv.iam.model.Manufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ManufacturerManagerTest {

    @Mock
    private ManufacturerDao manufacturerDao;

    @InjectMocks
    private ManufacturerManager manufacturerManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByIds() {
        // Prepare test data
        List<Long> ids = Collections.singletonList(1L);
        List<Manufacturer> expectedManufacturers = Collections.singletonList(new Manufacturer());

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findByIds(ids)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findByIds(ids);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).findByIds(ids);
    }

    @Test
    public void testFindByIdAndType() {
        // Prepare test data
        Long manufacturerId = 1L;
        ManufacturerType type = ManufacturerType.PRIVATE;
        String search = "search";
        List<Long> targetCategoryIds = Collections.singletonList(1L);
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = Collections.singletonList(new Manufacturer());

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findByIdAndType(manufacturerId, type, search, targetCategoryIds, pageNumber, pageSize))
                .thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findByIdAndType(
                manufacturerId, type, search, targetCategoryIds, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1))
                .findByIdAndType(manufacturerId, type, search, targetCategoryIds, pageNumber, pageSize);
    }

    @Test
    public void testFindByTypeAndStatus() {
        // Prepare test data
        ManufacturerType type = ManufacturerType.PRIVATE;
        String search = "search";
        List<Long> targetCategoryIds = Collections.singletonList(1L);
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = Collections.singletonList(new Manufacturer());

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findByTypeAndStatus(type, search, targetCategoryIds, pageNumber, pageSize))
                .thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findByTypeAndStatus(
                type, search, targetCategoryIds, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1))
                .findByTypeAndStatus(type, search, targetCategoryIds, pageNumber, pageSize);
    }

    // Test method for getCountForIdAndType
    @Test
    public void testGetCountForIdAndType() {
        // Prepare test data
        Long manufacturerId = 1L;
        ManufacturerType type = ManufacturerType.PRIVATE;
        String search = "search";
        List<Long> targetCategoryIds = Collections.singletonList(1L);
        Long expectedCount = 5L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getCountForIdAndType(manufacturerId, type, search, targetCategoryIds))
                .thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.getCountForIdAndType(manufacturerId, type, search, targetCategoryIds);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1))
                .getCountForIdAndType(manufacturerId, type, search, targetCategoryIds);
    }

    // Test method for getCountForTypeAndStatus
    @Test
    public void testGetCountForTypeAndStatus() {
        // Prepare test data
        ManufacturerType type = ManufacturerType.PRIVATE;
        String search = "search";
        List<Long> targetCategoryIds = Collections.singletonList(1L);
        Long expectedCount = 5L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getCountForTypeAndStatus(type, search, targetCategoryIds))
                .thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.getCountForTypeAndStatus(type, search, targetCategoryIds);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1))
                .getCountForTypeAndStatus(type, search, targetCategoryIds);
    }

    @Test
    public void testGetManufacturerNamesByIds() {
        // Prepare test data
        List<Long> manufacturerIds = new ArrayList<>();
        manufacturerIds.add(1L);
        manufacturerIds.add(2L);
        Object[] manufacturerName1 = new Object[]{"Manufacturer 1"};
        Object[] manufacturerName2 = new Object[]{"Manufacturer 2"};
        List<Object[]> expectedNames = new ArrayList<>();
        expectedNames.add(manufacturerName1);
        expectedNames.add(manufacturerName2);

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getManufacturerNamesByIds(manufacturerIds))
                .thenReturn(expectedNames);

        // Call method under test
        List<Object[]> actualNames = manufacturerManager.getManufacturerNamesByIds(manufacturerIds);

        // Verify
        assertEquals(expectedNames, actualNames);
        verify(manufacturerDao, times(1))
                .getManufacturerNamesByIds(manufacturerIds);
    }

    // Test method for getTestManufacturerIds
    @Test
    public void testGetTestManufacturerIds() {
        // Prepare test data
        List<Long> expectedManufacturerIds = new ArrayList<>();
        expectedManufacturerIds.add(1L);
        expectedManufacturerIds.add(2L);

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getTestManufacturerIds()).thenReturn(expectedManufacturerIds);

        // Call method under test
        List<Long> actualManufacturerIds = manufacturerManager.getTestManufacturerIds();

        // Verify
        assertEquals(expectedManufacturerIds, actualManufacturerIds);
        verify(manufacturerDao, times(1)).getTestManufacturerIds();
    }

    // Test method for getAllManufacturersWithGeoFilter
    @Test
    public void testGetAllManufacturersWithGeoFilter() {
        // Prepare test data
        String search = "test";
        Long stateId = 1L;
        Long districtId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();
        // Add expected manufacturers to the list

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getAllManufacturersWithGeoFilter(search, stateId, districtId, pageNumber, pageSize))
                .thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.getAllManufacturersWithGeoFilter(search, stateId, districtId, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1))
                .getAllManufacturersWithGeoFilter(search, stateId, districtId, pageNumber, pageSize);
    }

    // Test method for getAllManufacturersCountWithGeoFilter
    @Test
    public void testGetAllManufacturersCountWithGeoFilter() {
        // Prepare test data
        String search = "test";
        Long stateId = 1L;
        Long districtId = 1L;
        Long expectedCount = 10L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getAllManufacturersCountWithGeoFilter(search, stateId, districtId))
                .thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.getAllManufacturersCountWithGeoFilter(search, stateId, districtId);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1))
                .getAllManufacturersCountWithGeoFilter(search, stateId, districtId);
    }

    // Test method for getManufacturerNamesByIdsAndCategoryId
    @Test
    public void testGetManufacturerNamesByIdsAndCategoryId() {
        // Prepare test data
        List<Long> manufacturerIds = new ArrayList<>();
        manufacturerIds.add(1L);
        manufacturerIds.add(2L);
        Long categoryId = 1L;
        Object[] manufacturerName1 = new Object[]{"Manufacturer 1"};
        Object[] manufacturerName2 = new Object[]{"Manufacturer 2"};
        List<Object[]> expectedNames = new ArrayList<>();
        expectedNames.add(manufacturerName1);
        expectedNames.add(manufacturerName2);

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getManufacturerNamesByIdsAndCategoryId(manufacturerIds, categoryId))
                .thenReturn(expectedNames);

        // Call method under test
        List<Object[]> actualNames = manufacturerManager.getManufacturerNamesByIdsAndCategoryId(manufacturerIds, categoryId);

        // Verify
        assertEquals(expectedNames, actualNames);
        verify(manufacturerDao, times(1))
                .getManufacturerNamesByIdsAndCategoryId(manufacturerIds, categoryId);
    }

    // Add similar test methods for other methods in ManufacturerManager
    // Test method for findByExternalManufacturerId
    @Test
    public void testFindByExternalManufacturerId() {
        // Prepare test data
        String externalManufacturerId = "XYZ";
        Manufacturer expectedManufacturer = new Manufacturer();
        expectedManufacturer.setId(1L);

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findByExternalManufacturerId(externalManufacturerId)).thenReturn(expectedManufacturer);

        // Call method under test
        Manufacturer actualManufacturer = manufacturerManager.findByExternalManufacturerId(externalManufacturerId);

        // Verify
        assertEquals(expectedManufacturer, actualManufacturer);
        verify(manufacturerDao, times(1)).findByExternalManufacturerId(externalManufacturerId);
    }

    // Test method for findByLicenseNo
    @Test
    public void testFindByLicenseNo() {
        // Prepare test data
        String licenseNo = "XYZ";
        Manufacturer expectedManufacturer = new Manufacturer();
        expectedManufacturer.setId(1L);

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findByLicenseNo(licenseNo)).thenReturn(expectedManufacturer);

        // Call method under test
        Manufacturer actualManufacturer = manufacturerManager.findByLicenseNo(licenseNo);

        // Verify
        assertEquals(expectedManufacturer, actualManufacturer);
        verify(manufacturerDao, times(1)).findByLicenseNo(licenseNo);
    }

    // Test method for findByLicenseNoAndCategoryId


    // Test method for findManufacturerIdsByAgency
    @Test
    public void testFindManufacturerIdsByAgency() {
        // Prepare test data
        String agency = "XYZ";
        List<Long> expectedManufacturerIds = new ArrayList<>();
        expectedManufacturerIds.add(1L);
        expectedManufacturerIds.add(2L);

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findManufacturerIdsByAgency(agency)).thenReturn(expectedManufacturerIds);

        // Call method under test
        List<Long> actualManufacturerIds = manufacturerManager.findManufacturerIdsByAgency(agency);

        // Verify
        assertEquals(expectedManufacturerIds, actualManufacturerIds);
        verify(manufacturerDao, times(1)).findManufacturerIdsByAgency(agency);
    }

    // Test method for findManufacturerNameById
    @Test
    public void testFindManufacturerNameById() {
        // Prepare test data
        Long manufacturerId = 1L;
        String expectedManufacturerName = "ABC";

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findManufacturerNameById(manufacturerId)).thenReturn(expectedManufacturerName);

        // Call method under test
        String actualManufacturerName = manufacturerManager.findManufacturerNameById(manufacturerId);

        // Verify
        assertEquals(expectedManufacturerName, actualManufacturerName);
        verify(manufacturerDao, times(1)).findManufacturerNameById(manufacturerId);
    }

    // Test method for findManufacturers
    @Test
    public void testFindManufacturers() {
        // Prepare test data
        Long start = 1L;
        Long end = 10L;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();
        expectedManufacturers.add(new Manufacturer());
        expectedManufacturers.add(new Manufacturer());

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findManufacturers(start, end)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findManufacturers(start, end);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).findManufacturers(start, end);
    }
    // Test method for findByExternalManufacturerId


    // Test method for findLicenseNumberByName
    @Test
    public void testFindLicenseNumberByName() {
        // Prepare test data
        String name = "ABC Manufacturer";
        String expectedLicenseNo = "LICENSE123";

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findLicenseNumberByName(name)).thenReturn(expectedLicenseNo);

        // Call method under test
        String actualLicenseNo = manufacturerManager.findLicenseNumberByName(name);

        // Verify
        assertEquals(expectedLicenseNo, actualLicenseNo);
        verify(manufacturerDao, times(1)).findLicenseNumberByName(name);
    }

    @Test
    public void testGetDistrictCount() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        List<DashboardCountResponseDto> expectedCount = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getDistrictCount(categoryId, geoId)).thenReturn(expectedCount);

        // Call method under test
        List<DashboardCountResponseDto> actualCount = manufacturerManager.getDistrictCount(categoryId, geoId);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getDistrictCount(categoryId, geoId);
    }

    @Test
    public void testGetEmpanelDistrictCount() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        List<Long> empanelledManufacturers = new ArrayList<>();
        List<DashboardCountResponseDto> expectedCount = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getEmpanelDistrictCount(categoryId, geoId, empanelledManufacturers)).thenReturn(expectedCount);

        // Call method under test
        List<DashboardCountResponseDto> actualCount = manufacturerManager.getEmpanelDistrictCount(categoryId, geoId, empanelledManufacturers);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getEmpanelDistrictCount(categoryId, geoId, empanelledManufacturers);
    }

    @Test
    public void testGetManufacturerAgenciesByIds() {
        // Prepare test data
        Long categoryId = 1L;
        String filterBy = "filter";
        String geoId = "123";
        List<Long> manufacturerIds = new ArrayList<>();
        List<ManufacturerAgencyResponseDto> expectedResponse = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getManufacturerAgenciesByIds(categoryId, filterBy, geoId, manufacturerIds)).thenReturn(expectedResponse);

        // Call method under test
        List<ManufacturerAgencyResponseDto> actualResponse = manufacturerManager.getManufacturerAgenciesByIds(categoryId, filterBy, geoId, manufacturerIds);

        // Verify
        assertEquals(expectedResponse, actualResponse);
        verify(manufacturerDao, times(1)).getManufacturerAgenciesByIds(categoryId, filterBy, geoId, manufacturerIds);
    }

    @Test
    public void testGetVillageCount() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        List<DashboardCountResponseDto> expectedCount = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getVillageCount(categoryId, geoId)).thenReturn(expectedCount);

        // Call method under test
        List<DashboardCountResponseDto> actualCount = manufacturerManager.getVillageCount(categoryId, geoId);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getVillageCount(categoryId, geoId);
    }


    @Test
    public void testGetStateCount() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        List<DashboardCountResponseDto> expectedCount = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getStateCount(categoryId, geoId)).thenReturn(expectedCount);

        // Call method under test
        List<DashboardCountResponseDto> actualCount = manufacturerManager.getStateCount(categoryId, geoId);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getStateCount(categoryId, geoId);
    }

    @Test
    public void testGetEmpanelStateCount() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        List<Long> empanelledManufacturers = new ArrayList<>();
        List<DashboardCountResponseDto> expectedCount = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getEmpanelStateCount(categoryId, geoId, empanelledManufacturers)).thenReturn(expectedCount);

        // Call method under test
        List<DashboardCountResponseDto> actualCount = manufacturerManager.getEmpanelStateCount(categoryId, geoId, empanelledManufacturers);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getEmpanelStateCount(categoryId, geoId, empanelledManufacturers);
    }

    @Test
    public void testFindAllByDistrictGeoId() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findAllByDistrictGeoId(categoryId, geoId, search, pageNumber, pageSize)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findAllByDistrictGeoId(categoryId, geoId, search, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).findAllByDistrictGeoId(categoryId, geoId, search, pageNumber, pageSize);
    }

    @Test
    public void testFindCountByDistrictGeoId() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        String search = "test";
        Long expectedCount = 5L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findCountByDistrictGeoId(categoryId, geoId, search)).thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.findCountByDistrictGeoId(categoryId, geoId, search);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).findCountByDistrictGeoId(categoryId, geoId, search);
    }

    @Test
    public void testFindAllByStateGeoId() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        String search = "search";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findAllByStateGeoId(categoryId, geoId, search, pageNumber, pageSize)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findAllByStateGeoId(categoryId, geoId, search, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).findAllByStateGeoId(categoryId, geoId, search, pageNumber, pageSize);
    }

    @Test
    public void testFindCountByStateGeoId() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        String search = "search";
        Long expectedCount = 10L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findCountByStateGeoId(categoryId, geoId, search)).thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.findCountByStateGeoId(categoryId, geoId, search);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).findCountByStateGeoId(categoryId, geoId, search);
    }

// Similarly, write test cases for the remaining methods...

    @Test
    public void testFindAllByCountryGeoId() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        String search = "search";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findAllByCountryGeoId(categoryId, geoId, search, pageNumber, pageSize)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findAllByCountryGeoId(categoryId, geoId, search, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).findAllByCountryGeoId(categoryId, geoId, search, pageNumber, pageSize);
    }

    @Test
    public void testFindCountByCountryGeoId() {
        // Prepare test data
        Long categoryId = 1L;
        String geoId = "123";
        String search = "search";
        Long expectedCount = 10L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findCountByCountryGeoId(categoryId, geoId, search)).thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.findCountByCountryGeoId(categoryId, geoId, search);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).findCountByCountryGeoId(categoryId, geoId, search);
    }

    @Test
    public void testFindAllManufacturers() {
        // Prepare test data
        String search = "search";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findAllManufacturers(pageNumber, pageSize, search)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findAllManufacturers(search, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).findAllManufacturers(pageNumber, pageSize, search);
    }

    @Test
    public void testGetCountForAllManufacturers() {
        // Prepare test data
        String search = "search";
        Long expectedCount = 10L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getCountForAllManufacturers(search)).thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.getCountForAllManufacturers(search);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getCountForAllManufacturers(search);
    }

    @Test
    public void testFindByCategoryIds() {
        // Prepare test data
        String search = "search";
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.findByCategoryIds(search, categoryIds, pageNumber, pageSize)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.findByCategoryIds(search, categoryIds, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).findByCategoryIds(search, categoryIds, pageNumber, pageSize);
    }

    @Test
    public void testGetCountForFindByCategoryIds() {
        // Prepare test data
        String search = "search";
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        Long expectedCount = 10L;

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getCountForFindByCategoryIds(search, categoryIds)).thenReturn(expectedCount);

        // Call method under test
        Long actualCount = manufacturerManager.getCountForFindByCategoryIds(search, categoryIds);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getCountForFindByCategoryIds(search, categoryIds);
    }

    @Test
    public void testGetSourceManufacturers() {
        // Prepare test data
        Long manufacturerId = 1L;
        Long categoryId = 2L;
        String search = "search";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();

        // Mock behavior of ManufacturerDao
        when(manufacturerDao.getSourceManufacturers(manufacturerId, categoryId, search, pageNumber, pageSize)).thenReturn(expectedManufacturers);

        // Call method under test
        List<Manufacturer> actualManufacturers = manufacturerManager.getSourceManufacturers(manufacturerId, categoryId, search, pageNumber, pageSize);

        // Verify
        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).getSourceManufacturers(manufacturerId, categoryId, search, pageNumber, pageSize);
    }

    @Test
    public void testGetAllManufacturersCountBySearchAndFilter() {
        SearchListRequest searchRequest = new SearchListRequest();
        Long expectedCount = 10L;

        when(manufacturerDao.getAllManufacturersCountBySearchAndFilter(searchRequest)).thenReturn(expectedCount);

        Long actualCount = manufacturerManager.getAllManufacturersCountBySearchAndFilter(searchRequest);

        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getAllManufacturersCountBySearchAndFilter(searchRequest);
    }

    @Test
    public void testGetAllManufacturersBySearchAndFilter() {
        SearchListRequest searchRequest = new SearchListRequest();
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Manufacturer> expectedManufacturers = new ArrayList<>();

        when(manufacturerDao.getAllManufacturersBySearchAndFilter(searchRequest, pageNumber, pageSize)).thenReturn(expectedManufacturers);

        List<Manufacturer> actualManufacturers = manufacturerManager.getAllManufacturersBySearchAndFilter(searchRequest, pageNumber, pageSize);

        assertEquals(expectedManufacturers, actualManufacturers);
        verify(manufacturerDao, times(1)).getAllManufacturersBySearchAndFilter(searchRequest, pageNumber, pageSize);
    }

    @Test
    public void testGetSourceManufacturersCount() {
        Long manufacturerId = 1L;
        Long categoryId = 2L;
        String search = "test";
        Long expectedCount = 5L;

        when(manufacturerDao.getSourceManufacturersCount(manufacturerId, categoryId, search)).thenReturn(expectedCount);

        Long actualCount = manufacturerManager.getSourceManufacturersCount(manufacturerId, categoryId, search);

        assertEquals(expectedCount, actualCount);
        verify(manufacturerDao, times(1)).getSourceManufacturersCount(manufacturerId, categoryId, search);
    }

    @Test
    public void testGetEmpanelVillageCount() {
        Long categoryId = 1L;
        String geoId = "testGeoId";
        List<Long> empanelledManufacturers = Arrays.asList(1L, 2L);
        List<DashboardCountResponseDto> expectedCount = new ArrayList<>();


        List<DashboardCountResponseDto> actualCount = manufacturerManager.getEmpanelVillageCount(categoryId, geoId, empanelledManufacturers);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testFindByLicenseNoAndCategoryId_Long() {
        String licenseNo = "123";
        Long categoryId = 1L;
        Manufacturer expectedManufacturer = new Manufacturer();

        when(manufacturerDao.findByLicenseNoAndCategoryId(licenseNo, categoryId)).thenReturn(expectedManufacturer);

        Manufacturer actualManufacturer = manufacturerManager.findByLicenseNoAndCategoryId(licenseNo, categoryId);

        assertEquals(expectedManufacturer, actualManufacturer);
        verify(manufacturerDao, times(1)).findByLicenseNoAndCategoryId(licenseNo, categoryId);
    }

    @Test
    public void testFindByLicenseNoAndCategoryId_List() {
        String licenseNo = "123";
        List<Long> targetCategoryIds = Arrays.asList(1L, 2L);
        Manufacturer expectedManufacturer = new Manufacturer();

        when(manufacturerDao.findByLicenseNoAndCategoryId(licenseNo, targetCategoryIds)).thenReturn(expectedManufacturer);

        Manufacturer actualManufacturer = manufacturerManager.findByLicenseNoAndCategoryId(licenseNo, targetCategoryIds);

        assertEquals(expectedManufacturer, actualManufacturer);
        verify(manufacturerDao, times(1)).findByLicenseNoAndCategoryId(licenseNo, targetCategoryIds);
    }

    @Test
    public void testFindExternalManufacturerIdByManufacturerId() {
        Long manufacturerId = 1L;
        Manufacturer expectedManufacturer = new Manufacturer();

        when(manufacturerDao.findExternalManufacturerIdByManufacturerId(manufacturerId)).thenReturn(expectedManufacturer);

        Manufacturer actualManufacturer = manufacturerManager.findExternalManufacturerIdByManufacturerId(manufacturerId);

        assertEquals(expectedManufacturer, actualManufacturer);
        verify(manufacturerDao, times(1)).findExternalManufacturerIdByManufacturerId(manufacturerId);
    }
}
