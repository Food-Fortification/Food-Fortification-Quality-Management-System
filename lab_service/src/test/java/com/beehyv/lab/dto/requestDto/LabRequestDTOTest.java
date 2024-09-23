package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Test Lab";
        String description = "Test Lab Description";
        Set<LabDocumentRequestDTO> labDocs = new HashSet<>();
        Set<LabCategoryRequestDto> labCategories = new HashSet<>();
        Set<LabManufacturerRequestDTO> labManufacturers = new HashSet<>();
        AddressRequestDTO address = new AddressRequestDTO(null,null,null,null,null,null,null);
        Long statusId = 2L;

        // When
        LabRequestDTO dto = new LabRequestDTO(id, name, description, labDocs, labCategories, labManufacturers, address, statusId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
        assertEquals(labDocs, dto.getLabDocs());
        assertEquals(labCategories, dto.getLabCategories());
        assertEquals(labManufacturers, dto.getLabManufacturers());
        assertEquals(address, dto.getAddress());
        assertEquals(statusId, dto.getStatusId());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabRequestDTO dto = new LabRequestDTO();
        Long id = 1L;
        String name = "Test Lab";
        String description = "Test Lab Description";
        Set<LabDocumentRequestDTO> labDocs = new HashSet<>();
        Set<LabCategoryRequestDto> labCategories = new HashSet<>();
        Set<LabManufacturerRequestDTO> labManufacturers = new HashSet<>();
        AddressRequestDTO address = new AddressRequestDTO(null,null,null,null,null,null,null);
        Long statusId = 2L;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setLabDocs(labDocs);
        dto.setLabCategories(labCategories);
        dto.setLabManufacturers(labManufacturers);
        dto.setAddress(address);
        dto.setStatusId(statusId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
        assertEquals(labDocs, dto.getLabDocs());
        assertEquals(labCategories, dto.getLabCategories());
        assertEquals(labManufacturers, dto.getLabManufacturers());
        assertEquals(address, dto.getAddress());
        assertEquals(statusId, dto.getStatusId());
    }
}
