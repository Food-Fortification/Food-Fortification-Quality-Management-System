package com.beehyv.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerDispatchableQuantityRequestDtoTest {

    @Test
    public void testNoArgsConstructor() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        assertNull(dto.getFromDate());
        assertNull(dto.getToDate());
        assertNull(dto.getManufacturerIds());
        assertNull(dto.getDistrictGeoIds());
        assertNull(dto.getSourceManufacturerId());
        assertNull(dto.getCategoryId());
        assertNull(dto.getGroupBy());
    }

    @Test
    public void testAllArgsConstructor() {
        Date fromDate = new Date();
        Date toDate = new Date();
        List<Long> manufacturerIds = Arrays.asList(1L, 2L);
        List<String> districtGeoIds = Arrays.asList("geo1", "geo2");
        Long sourceManufacturerId = 3L;
        Long categoryId = 4L;
        String groupBy = "groupByValue";

        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto(
                fromDate, toDate, manufacturerIds, districtGeoIds, sourceManufacturerId, categoryId, groupBy);

        assertEquals(fromDate, dto.getFromDate());
        assertEquals(toDate, dto.getToDate());
        assertEquals(manufacturerIds, dto.getManufacturerIds());
        assertEquals(districtGeoIds, dto.getDistrictGeoIds());
        assertEquals(sourceManufacturerId, dto.getSourceManufacturerId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(groupBy, dto.getGroupBy());
    }

    @Test
    public void testSettersAndGetters() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        Date fromDate = new Date();
        Date toDate = new Date();
        List<Long> manufacturerIds = Arrays.asList(1L, 2L);
        List<String> districtGeoIds = Arrays.asList("geo1", "geo2");
        Long sourceManufacturerId = 3L;
        Long categoryId = 4L;
        String groupBy = "groupByValue";

        dto.setFromDate(fromDate);
        dto.setToDate(toDate);
        dto.setManufacturerIds(manufacturerIds);
        dto.setDistrictGeoIds(districtGeoIds);
        dto.setSourceManufacturerId(sourceManufacturerId);
        dto.setCategoryId(categoryId);
        dto.setGroupBy(groupBy);

        assertEquals(fromDate, dto.getFromDate());
        assertEquals(toDate, dto.getToDate());
        assertEquals(manufacturerIds, dto.getManufacturerIds());
        assertEquals(districtGeoIds, dto.getDistrictGeoIds());
        assertEquals(sourceManufacturerId, dto.getSourceManufacturerId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(groupBy, dto.getGroupBy());
    }

    @Test
    public void testEqualsAndHashCode() {
        Date fromDate = new Date();
        Date toDate = new Date();
        List<Long> manufacturerIds = Arrays.asList(1L, 2L);
        List<String> districtGeoIds = Arrays.asList("geo1", "geo2");
        Long sourceManufacturerId = 3L;
        Long categoryId = 4L;
        String groupBy = "groupByValue";

        ManufacturerDispatchableQuantityRequestDto dto1 = new ManufacturerDispatchableQuantityRequestDto(
                fromDate, toDate, manufacturerIds, districtGeoIds, sourceManufacturerId, categoryId, groupBy);
        ManufacturerDispatchableQuantityRequestDto dto2 = new ManufacturerDispatchableQuantityRequestDto(
                fromDate, toDate, manufacturerIds, districtGeoIds, sourceManufacturerId, categoryId, groupBy);
        ManufacturerDispatchableQuantityRequestDto dto3 = new ManufacturerDispatchableQuantityRequestDto();

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
