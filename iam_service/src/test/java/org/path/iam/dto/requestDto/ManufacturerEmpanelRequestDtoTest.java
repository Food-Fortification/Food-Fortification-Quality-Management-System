package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerEmpanelRequestDtoTest {

    @Test
    public void testNoArgsConstructor() {
        ManufacturerEmpanelRequestDto dto = new ManufacturerEmpanelRequestDto();
        assertNull(dto.getId());
        assertNull(dto.getManufacturerId());
        assertNull(dto.getCategoryId());
        assertNull(dto.getStateGeoId());
        assertNull(dto.getFromDate());
        assertNull(dto.getToDate());
        assertNull(dto.getSourceCategoryId());
        assertNull(dto.getTargetCategoryId());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        Long manufacturerId = 2L;
        Long categoryId = 3L;
        String stateGeoId = "stateGeoId";
        Date fromDate = new Date();
        Date toDate = new Date();
        Long sourceCategoryId = 4L;
        Long targetCategoryId = 5L;

        ManufacturerEmpanelRequestDto dto = new ManufacturerEmpanelRequestDto();


    }

    @Test
    public void testSettersAndGetters() {
        ManufacturerEmpanelRequestDto dto = new ManufacturerEmpanelRequestDto();
        Long id = 1L;
        Long manufacturerId = 2L;
        Long categoryId = 3L;
        String stateGeoId = "stateGeoId";
        Date fromDate = new Date();
        Date toDate = new Date();
        Long sourceCategoryId = 4L;
        Long targetCategoryId = 5L;

        dto.setId(id);
        dto.setManufacturerId(manufacturerId);
        dto.setCategoryId(categoryId);
        dto.setStateGeoId(stateGeoId);
        dto.setFromDate(fromDate);
        dto.setToDate(toDate);
        dto.setSourceCategoryId(sourceCategoryId);
        dto.setTargetCategoryId(targetCategoryId);

        assertEquals(id, dto.getId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(stateGeoId, dto.getStateGeoId());
        assertEquals(fromDate, dto.getFromDate());
        assertEquals(toDate, dto.getToDate());
        assertEquals(sourceCategoryId, dto.getSourceCategoryId());
        assertEquals(targetCategoryId, dto.getTargetCategoryId());
    }

    @Test
    public void testEqualsAndHashCode() {
        Long id = 1L;
        Long manufacturerId = 2L;
        Long categoryId = 3L;
        String stateGeoId = "stateGeoId";
        Date fromDate = new Date();
        Date toDate = new Date();
        Long sourceCategoryId = 4L;
        Long targetCategoryId = 5L;

        ManufacturerEmpanelRequestDto dto1 = new ManufacturerEmpanelRequestDto();
        ManufacturerEmpanelRequestDto dto2 = new ManufacturerEmpanelRequestDto();
        ManufacturerEmpanelRequestDto dto3 = new ManufacturerEmpanelRequestDto();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
