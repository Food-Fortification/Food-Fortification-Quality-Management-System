package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "Lab Name";
        String description = "Lab Description";
        String completeAddress = "Complete Address";
        Set<LabDocumentResponseDTO> labDocs = new HashSet<>();
        AddressResponseDTO address = new AddressResponseDTO();
        Set<LabCategoryResponseDto> labCategories = new HashSet<>();
        Set<LabManufacturerResponseDTO> labManufacturers = new HashSet<>();
        StatusResponseDto status = new StatusResponseDto();
        String certificateNo = "Certificate Number";

        // When
        LabResponseDTO labResponseDTO = new LabResponseDTO(id, name, description, completeAddress, labDocs, address, labCategories, labManufacturers, status, certificateNo);

        // Then
        assertNotNull(labResponseDTO);
        assertEquals(id, labResponseDTO.getId());
        assertEquals(name, labResponseDTO.getName());
        assertEquals(description, labResponseDTO.getDescription());
        assertEquals(completeAddress, labResponseDTO.getCompleteAddress());
        assertEquals(labDocs, labResponseDTO.getLabDocs());
        assertEquals(address, labResponseDTO.getAddress());
        assertEquals(labCategories, labResponseDTO.getLabCategories());
        assertEquals(labManufacturers, labResponseDTO.getLabManufacturers());
        assertEquals(status, labResponseDTO.getStatus());
        assertEquals(certificateNo, labResponseDTO.getCertificateNo());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabResponseDTO labResponseDTO = new LabResponseDTO();

        // Then
        assertNotNull(labResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabResponseDTO labResponseDTO = new LabResponseDTO();
        Long id = 1L;
        String name = "Lab Name";
        String description = "Lab Description";
        String completeAddress = "Complete Address";
        Set<LabDocumentResponseDTO> labDocs = new HashSet<>();
        AddressResponseDTO address = new AddressResponseDTO();
        Set<LabCategoryResponseDto> labCategories = new HashSet<>();
        Set<LabManufacturerResponseDTO> labManufacturers = new HashSet<>();
        StatusResponseDto status = new StatusResponseDto();
        String certificateNo = "Certificate Number";

        // When
        labResponseDTO.setId(id);
        labResponseDTO.setName(name);
        labResponseDTO.setDescription(description);
        labResponseDTO.setCompleteAddress(completeAddress);
        labResponseDTO.setLabDocs(labDocs);
        labResponseDTO.setAddress(address);
        labResponseDTO.setLabCategories(labCategories);
        labResponseDTO.setLabManufacturers(labManufacturers);
        labResponseDTO.setStatus(status);
        labResponseDTO.setCertificateNo(certificateNo);

        // Then
        assertEquals(id, labResponseDTO.getId());
        assertEquals(name, labResponseDTO.getName());
        assertEquals(description, labResponseDTO.getDescription());
        assertEquals(completeAddress, labResponseDTO.getCompleteAddress());
        assertEquals(labDocs, labResponseDTO.getLabDocs());
        assertEquals(address, labResponseDTO.getAddress());
        assertEquals(labCategories, labResponseDTO.getLabCategories());
        assertEquals(labManufacturers, labResponseDTO.getLabManufacturers());
        assertEquals(status, labResponseDTO.getStatus());
        assertEquals(certificateNo, labResponseDTO.getCertificateNo());
    }
}
