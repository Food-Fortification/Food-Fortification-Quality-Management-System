package com.beehyv.iam.dto.external;

import com.beehyv.iam.enums.UserCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FssaiLicenseResponseDtoTest {

    @Test
    void testNoArgsConstructor() {
        FssaiLicenseResponseDto dto = new FssaiLicenseResponseDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getPremix()).isNull();
        assertThat(dto.getMiller()).isNull();
        assertThat(dto.getFRK()).isNull();
        assertThat(dto.getLicenseNo()).isNull();
        assertThat(dto.getDistrictName()).isNull();
        assertThat(dto.getFboId()).isNull();
        assertThat(dto.getTalukName()).isNull();
        assertThat(dto.getDisplayrefid()).isNull();
        assertThat(dto.getCompanyName()).isNull();
        assertThat(dto.getVillageName()).isNull();
        assertThat(dto.getStatusdesc()).isNull();
        assertThat(dto.getAppTypeDesc()).isNull();
        assertThat(dto.getStateName()).isNull();
        assertThat(dto.getLicenseCategoryName()).isNull();
        assertThat(dto.getPremisePincode()).isNull();
        assertThat(dto.getPremiseAddress()).isNull();
        assertThat(dto.getRefid()).isNull();
        assertThat(dto.getLicenseCategoryId()).isNull();
        assertThat(dto.getLicenseActiveFlag()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        FssaiLicenseResponseDto dto = new FssaiLicenseResponseDto();
        dto.setPremix(UserCategory.Yes);
        dto.setMiller(UserCategory.Yes);
        dto.setFRK(UserCategory.Yes);
        dto.setLicenseNo("12345");
        dto.setDistrictName("TestDistrict");
        dto.setFboId("FBO123");
        dto.setTalukName("TestTaluk");
        dto.setDisplayrefid("Display123");
        dto.setCompanyName("TestCompany");
        dto.setVillageName("TestVillage");
        dto.setStatusdesc("Active");
        dto.setAppTypeDesc("ApplicationType");
        dto.setStateName("TestState");
        dto.setLicenseCategoryName("CategoryName");
        dto.setPremisePincode("123456");
        dto.setPremiseAddress("Test Address");
        dto.setRefid("Ref123");
        dto.setLicenseCategoryId(1L);
        dto.setLicenseActiveFlag(true);


        assertThat(dto.getLicenseNo()).isEqualTo("12345");
        assertThat(dto.getDistrictName()).isEqualTo("TestDistrict");
        assertThat(dto.getFboId()).isEqualTo("FBO123");
        assertThat(dto.getTalukName()).isEqualTo("TestTaluk");
        assertThat(dto.getDisplayrefid()).isEqualTo("Display123");
        assertThat(dto.getCompanyName()).isEqualTo("TestCompany");
        assertThat(dto.getVillageName()).isEqualTo("TestVillage");
        assertThat(dto.getStatusdesc()).isEqualTo("Active");
        assertThat(dto.getAppTypeDesc()).isEqualTo("ApplicationType");
        assertThat(dto.getStateName()).isEqualTo("TestState");
        assertThat(dto.getLicenseCategoryName()).isEqualTo("CategoryName");
        assertThat(dto.getPremisePincode()).isEqualTo("123456");
        assertThat(dto.getPremiseAddress()).isEqualTo("Test Address");
        assertThat(dto.getRefid()).isEqualTo("Ref123");
        assertThat(dto.getLicenseCategoryId()).isEqualTo(1L);
        assertThat(dto.getLicenseActiveFlag()).isTrue();
    }


}
