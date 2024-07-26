package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NameAddressResponseDtoTest {

    private NameAddressResponseDto nameAddressResponseDto;

    @BeforeEach
    public void setUp() {
        nameAddressResponseDto = new NameAddressResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        NameAddressResponseDto dto = new NameAddressResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getCompleteAddress());
        assertNull(dto.getLicenseNo());
    }

    @Test
    public void testAllArgsConstructor() {
        NameAddressResponseDto dto = new NameAddressResponseDto(1L, "Name", "Complete Address", "License No");

        assertEquals(1L, dto.getId());
        assertEquals("Name", dto.getName());
        assertEquals("Complete Address", dto.getCompleteAddress());
        assertEquals("License No", dto.getLicenseNo());
    }

    @Test
    public void testSettersAndGetters() {
        nameAddressResponseDto.setId(1L);
        nameAddressResponseDto.setName("Name");
        nameAddressResponseDto.setCompleteAddress("Complete Address");
        nameAddressResponseDto.setLicenseNo("License No");

        assertEquals(1L, nameAddressResponseDto.getId());
        assertEquals("Name", nameAddressResponseDto.getName());
        assertEquals("Complete Address", nameAddressResponseDto.getCompleteAddress());
        assertEquals("License No", nameAddressResponseDto.getLicenseNo());
    }
}
