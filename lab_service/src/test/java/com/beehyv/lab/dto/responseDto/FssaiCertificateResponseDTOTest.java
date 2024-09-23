package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FssaiCertificateResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        String districtName = "District";
        String stateName = "State";
        Long labId = 1L;
        Long pincode = 123456L;
        String labName = "Lab";
        Date certificateValidUpTo = new Date();
        String contactPerson = "John Doe";
        String address = "123 Main St";
        String certificateNo = "123456789";
        String contactMailPerson = "john@example.com";

        // When
        FssaiCertificateResponseDTO fssaiCertificateResponseDTO = new FssaiCertificateResponseDTO(districtName, stateName,
                labId, pincode, labName, certificateValidUpTo, contactPerson, address, certificateNo, contactMailPerson);

        // Then
        assertNotNull(fssaiCertificateResponseDTO);
        assertEquals(districtName, fssaiCertificateResponseDTO.getDistrictName());
        assertEquals(stateName, fssaiCertificateResponseDTO.getStateName());
        assertEquals(labId, fssaiCertificateResponseDTO.getLabId());
        assertEquals(pincode, fssaiCertificateResponseDTO.getPincode());
        assertEquals(labName, fssaiCertificateResponseDTO.getLabName());
        assertEquals(certificateValidUpTo, fssaiCertificateResponseDTO.getCertificateValidUpTo());
        assertEquals(contactPerson, fssaiCertificateResponseDTO.getContactPerson());
        assertEquals(address, fssaiCertificateResponseDTO.getAddress());
        assertEquals(certificateNo, fssaiCertificateResponseDTO.getCertificateNo());
        assertEquals(contactMailPerson, fssaiCertificateResponseDTO.getContactMailPerson());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        FssaiCertificateResponseDTO fssaiCertificateResponseDTO = new FssaiCertificateResponseDTO();

        // Then
        assertNotNull(fssaiCertificateResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        FssaiCertificateResponseDTO fssaiCertificateResponseDTO = new FssaiCertificateResponseDTO();
        String districtName = "District";
        String stateName = "State";
        Long labId = 1L;
        Long pincode = 123456L;
        String labName = "Lab";
        Date certificateValidUpTo = new Date();
        String contactPerson = "John Doe";
        String address = "123 Main St";
        String certificateNo = "123456789";
        String contactMailPerson = "john@example.com";

        // When
        fssaiCertificateResponseDTO.setDistrictName(districtName);
        fssaiCertificateResponseDTO.setStateName(stateName);
        fssaiCertificateResponseDTO.setLabId(labId);
        fssaiCertificateResponseDTO.setPincode(pincode);
        fssaiCertificateResponseDTO.setLabName(labName);
        fssaiCertificateResponseDTO.setCertificateValidUpTo(certificateValidUpTo);
        fssaiCertificateResponseDTO.setContactPerson(contactPerson);
        fssaiCertificateResponseDTO.setAddress(address);
        fssaiCertificateResponseDTO.setCertificateNo(certificateNo);
        fssaiCertificateResponseDTO.setContactMailPerson(contactMailPerson);

        // Then
        assertEquals(districtName, fssaiCertificateResponseDTO.getDistrictName());
        assertEquals(stateName, fssaiCertificateResponseDTO.getStateName());
        assertEquals(labId, fssaiCertificateResponseDTO.getLabId());
        assertEquals(pincode, fssaiCertificateResponseDTO.getPincode());
        assertEquals(labName, fssaiCertificateResponseDTO.getLabName());
        assertEquals(certificateValidUpTo, fssaiCertificateResponseDTO.getCertificateValidUpTo());
        assertEquals(contactPerson, fssaiCertificateResponseDTO.getContactPerson());
        assertEquals(address, fssaiCertificateResponseDTO.getAddress());
        assertEquals(certificateNo, fssaiCertificateResponseDTO.getCertificateNo());
        assertEquals(contactMailPerson, fssaiCertificateResponseDTO.getContactMailPerson());
    }

    @Test
    void testToString() {
        // Given
        FssaiCertificateResponseDTO fssaiCertificateResponseDTO = new FssaiCertificateResponseDTO("District", "State", 1L,
                123456L, "Lab", new Date(), "John Doe", "123 Main St", "123456789", "john@example.com");

        // When
        String result = fssaiCertificateResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("FssaiCertificateResponseDTO"));

    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        FssaiCertificateResponseDTO fssaiCertificateResponseDTO1 = new FssaiCertificateResponseDTO("District", "State", 1L,
                123456L, "Lab", new Date(), "John Doe", "123 Main St", "123456789", "john@example.com");
        FssaiCertificateResponseDTO fssaiCertificateResponseDTO2 = new FssaiCertificateResponseDTO("District", "State", 1L,
                123456L, "Lab", new Date(), "John Doe", "123 Main St", "123456789", "john@example.com");


    }
}
