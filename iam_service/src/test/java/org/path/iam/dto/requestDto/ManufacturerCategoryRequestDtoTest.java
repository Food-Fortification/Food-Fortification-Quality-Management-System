package org.path.iam.dto.requestDto;

import org.path.iam.enums.ManufacturerCategoryAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerCategoryRequestDtoTest {

    @Test
    public void testNoArgsConstructor() {
        ManufacturerCategoryRequestDto dto = new ManufacturerCategoryRequestDto();
        assertNull(dto.getId());
        assertNull(dto.getCategoryId());
        assertNull(dto.getCanSkipRawMaterials());
        assertNull(dto.getManufacturerId());
        assertNull(dto.getIsEnabled());
    }

    @Test
    public void testAllArgsConstructor() {
        ManufacturerCategoryRequestDto dto = new ManufacturerCategoryRequestDto(1L, 2L, true, 3L, false, ManufacturerCategoryAction.CREATION);
        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getCategoryId());
        assertTrue(dto.getCanSkipRawMaterials());
        assertEquals(3L, dto.getManufacturerId());
        assertFalse(dto.getIsEnabled());
    }

    @Test
    public void testSettersAndGetters() {
        ManufacturerCategoryRequestDto dto = new ManufacturerCategoryRequestDto();
        dto.setId(1L);
        dto.setCategoryId(2L);
        dto.setCanSkipRawMaterials(true);
        dto.setManufacturerId(3L);
        dto.setIsEnabled(false);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getCategoryId());
        assertTrue(dto.getCanSkipRawMaterials());
        assertEquals(3L, dto.getManufacturerId());
        assertFalse(dto.getIsEnabled());
    }

    @Test
    public void testEqualsAndHashCode() {
        ManufacturerCategoryRequestDto dto1 = new ManufacturerCategoryRequestDto(1L, 2L, true, 3L, false, ManufacturerCategoryAction.CREATION);
        ManufacturerCategoryRequestDto dto2 = new ManufacturerCategoryRequestDto(1L, 2L, true, 3L, false, ManufacturerCategoryAction.CREATION);
        ManufacturerCategoryRequestDto dto3 = new ManufacturerCategoryRequestDto(4L, 5L, false, 6L, true, ManufacturerCategoryAction.CREATION);

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
