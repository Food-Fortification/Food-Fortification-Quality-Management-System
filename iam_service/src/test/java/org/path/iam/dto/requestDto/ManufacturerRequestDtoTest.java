package org.path.iam.dto.requestDto;

import org.path.iam.enums.ManufacturerType;
import org.path.iam.enums.VendorType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerRequestDtoTest {

    @Test
    public void testNoArgsConstructor() {
        ManufacturerRequestDto dto = new ManufacturerRequestDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getType());
        assertNull(dto.getAccreditedByAgency());
        assertNull(dto.getVendorType());
        assertNull(dto.getAgencyName());
        assertNull(dto.getLicenseNumber());
        assertNull(dto.getExternalManufacturerId());
        assertNull(dto.getTargetManufacturers());
        assertNull(dto.getManufacturerDocs());
        assertNull(dto.getAddress());
        assertNull(dto.getManufacturerCategory());
        assertNull(dto.getManufacturerType());
        assertNull(dto.getIsRawMaterialsProcured());
        assertNull(dto.getManufacturerProperties());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String name = "Test Manufacturer";
        String type = "Type A";
        Boolean accreditedByAgency = true;
        VendorType vendorType = VendorType.Manufacturer;
        String agencyName = "Test Agency";
        String licenseNumber = "12345";
        String externalManufacturerId = "ext-123";
        Set<Long> targetManufacturers = new HashSet<>();
        Set<ManufacturerDocsRequestDto> manufacturerDocs = new HashSet<>();
        AddressRequestDto address = new AddressRequestDto();
        Set<ManufacturerCategoryRequestDto> manufacturerCategory = new HashSet<>();
        ManufacturerType manufacturerType = ManufacturerType.PRIVATE;
        Boolean isRawMaterialsProcured = true;
        Set<ManufacturerPropertyRequestDto> manufacturerProperties = new HashSet<>();

        ManufacturerRequestDto dto = new ManufacturerRequestDto(id, name, type, accreditedByAgency, vendorType, agencyName, licenseNumber, externalManufacturerId, targetManufacturers, manufacturerDocs, address, manufacturerCategory, manufacturerType, isRawMaterialsProcured, manufacturerProperties);

        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(type, dto.getType());
        assertEquals(accreditedByAgency, dto.getAccreditedByAgency());
        assertEquals(vendorType, dto.getVendorType());
        assertEquals(agencyName, dto.getAgencyName());
        assertEquals(licenseNumber, dto.getLicenseNumber());
        assertEquals(externalManufacturerId, dto.getExternalManufacturerId());
        assertEquals(targetManufacturers, dto.getTargetManufacturers());
        assertEquals(manufacturerDocs, dto.getManufacturerDocs());
        assertEquals(address, dto.getAddress());
        assertEquals(manufacturerCategory, dto.getManufacturerCategory());
        assertEquals(manufacturerType, dto.getManufacturerType());
        assertEquals(isRawMaterialsProcured, dto.getIsRawMaterialsProcured());
        assertEquals(manufacturerProperties, dto.getManufacturerProperties());
    }

    @Test
    public void testSettersAndGetters() {
        ManufacturerRequestDto dto = new ManufacturerRequestDto();
        Long id = 1L;
        String name = "Test Manufacturer";
        String type = "Type A";
        Boolean accreditedByAgency = true;
        VendorType vendorType = VendorType.Manufacturer;
        String agencyName = "Test Agency";
        String licenseNumber = "12345";
        String externalManufacturerId = "ext-123";
        Set<Long> targetManufacturers = new HashSet<>();
        Set<ManufacturerDocsRequestDto> manufacturerDocs = new HashSet<>();
        AddressRequestDto address = new AddressRequestDto();
        Set<ManufacturerCategoryRequestDto> manufacturerCategory = new HashSet<>();
        ManufacturerType manufacturerType = ManufacturerType.PRIVATE;
        Boolean isRawMaterialsProcured = true;
        Set<ManufacturerPropertyRequestDto> manufacturerProperties = new HashSet<>();

        dto.setId(id);
        dto.setName(name);
        dto.setType(type);
        dto.setAccreditedByAgency(accreditedByAgency);
        dto.setVendorType(vendorType);
        dto.setAgencyName(agencyName);
        dto.setLicenseNumber(licenseNumber);
        dto.setExternalManufacturerId(externalManufacturerId);
        dto.setTargetManufacturers(targetManufacturers);
        dto.setManufacturerDocs(manufacturerDocs);
        dto.setAddress(address);
        dto.setManufacturerCategory(manufacturerCategory);
        dto.setManufacturerType(manufacturerType);
        dto.setIsRawMaterialsProcured(isRawMaterialsProcured);
        dto.setManufacturerProperties(manufacturerProperties);

        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(type, dto.getType());
        assertEquals(accreditedByAgency, dto.getAccreditedByAgency());
        assertEquals(vendorType, dto.getVendorType());
        assertEquals(agencyName, dto.getAgencyName());
        assertEquals(licenseNumber, dto.getLicenseNumber());
        assertEquals(externalManufacturerId, dto.getExternalManufacturerId());
        assertEquals(targetManufacturers, dto.getTargetManufacturers());
        assertEquals(manufacturerDocs, dto.getManufacturerDocs());
        assertEquals(address, dto.getAddress());
        assertEquals(manufacturerCategory, dto.getManufacturerCategory());
        assertEquals(manufacturerType, dto.getManufacturerType());
        assertEquals(isRawMaterialsProcured, dto.getIsRawMaterialsProcured());
        assertEquals(manufacturerProperties, dto.getManufacturerProperties());
    }

    @Test
    public void testEqualsAndHashCode() {
        Long id = 1L;
        String name = "Test Manufacturer";
        String type = "Type A";
        Boolean accreditedByAgency = true;
        VendorType vendorType = VendorType.Manufacturer;
        String agencyName = "Test Agency";
        String licenseNumber = "12345";
        String externalManufacturerId = "ext-123";
        Set<Long> targetManufacturers = new HashSet<>();
        Set<ManufacturerDocsRequestDto> manufacturerDocs = new HashSet<>();
        AddressRequestDto address = new AddressRequestDto();
        Set<ManufacturerCategoryRequestDto> manufacturerCategory = new HashSet<>();
        ManufacturerType manufacturerType = ManufacturerType.MATERIAL;
        Boolean isRawMaterialsProcured = true;
        Set<ManufacturerPropertyRequestDto> manufacturerProperties = new HashSet<>();

        ManufacturerRequestDto dto1 = new ManufacturerRequestDto(id, name, type, accreditedByAgency, vendorType, agencyName, licenseNumber, externalManufacturerId, targetManufacturers, manufacturerDocs, address, manufacturerCategory, manufacturerType, isRawMaterialsProcured, manufacturerProperties);
        ManufacturerRequestDto dto2 = new ManufacturerRequestDto(id, name, type, accreditedByAgency, vendorType, agencyName, licenseNumber, externalManufacturerId, targetManufacturers, manufacturerDocs, address, manufacturerCategory, manufacturerType, isRawMaterialsProcured, manufacturerProperties);
        ManufacturerRequestDto dto3 = new ManufacturerRequestDto();

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
