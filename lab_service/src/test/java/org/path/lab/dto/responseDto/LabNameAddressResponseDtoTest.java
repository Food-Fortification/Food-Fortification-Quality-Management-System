package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabNameAddressResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "Lab Name";
        String completeAddress = "Complete Address";
        String labCertificateNumber = "Certificate Number";

        // When
        LabNameAddressResponseDto labNameAddressResponseDto = new LabNameAddressResponseDto(id, name, completeAddress, labCertificateNumber);

        // Then
        assertNotNull(labNameAddressResponseDto);
        assertEquals(id, labNameAddressResponseDto.getId());
        assertEquals(name, labNameAddressResponseDto.getName());
        assertEquals(completeAddress, labNameAddressResponseDto.getCompleteAddress());
        assertEquals(labCertificateNumber, labNameAddressResponseDto.getLabCertificateNumber());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabNameAddressResponseDto labNameAddressResponseDto = new LabNameAddressResponseDto();

        // Then
        assertNotNull(labNameAddressResponseDto);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabNameAddressResponseDto labNameAddressResponseDto = new LabNameAddressResponseDto();
        Long id = 1L;
        String name = "Lab Name";
        String completeAddress = "Complete Address";
        String labCertificateNumber = "Certificate Number";

        // When
        labNameAddressResponseDto.setId(id);
        labNameAddressResponseDto.setName(name);
        labNameAddressResponseDto.setCompleteAddress(completeAddress);
        labNameAddressResponseDto.setLabCertificateNumber(labCertificateNumber);

        // Then
        assertEquals(id, labNameAddressResponseDto.getId());
        assertEquals(name, labNameAddressResponseDto.getName());
        assertEquals(completeAddress, labNameAddressResponseDto.getCompleteAddress());
        assertEquals(labCertificateNumber, labNameAddressResponseDto.getLabCertificateNumber());
    }
}
