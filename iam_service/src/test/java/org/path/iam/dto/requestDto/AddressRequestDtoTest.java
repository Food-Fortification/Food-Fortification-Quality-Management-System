package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        AddressRequestDto dto = new AddressRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getLaneOne()).isNull();
        assertThat(dto.getLaneTwo()).isNull();
        assertThat(dto.getVillageId()).isNull();
        assertThat(dto.getPinCode()).isNull();
        assertThat(dto.getManufacturerId()).isNull();
        assertThat(dto.getLongitude()).isNull();
        assertThat(dto.getLatitude()).isNull();
        assertThat(dto.getVillageName()).isNull();
        assertThat(dto.getDistrictName()).isNull();
        assertThat(dto.getStateName()).isNull();
        assertThat(dto.getCountryName()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        AddressRequestDto dto = new AddressRequestDto(
                1L, "Lane 1", "Lane 2", 100L, "123456", 200L, 77.1234, 28.5678,
                "Village Name", "District Name", "State Name", "Country Name"
        );

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getLaneOne()).isEqualTo("Lane 1");
        assertThat(dto.getLaneTwo()).isEqualTo("Lane 2");
        assertThat(dto.getVillageId()).isEqualTo(100L);
        assertThat(dto.getPinCode()).isEqualTo("123456");
        assertThat(dto.getManufacturerId()).isEqualTo(200L);
        assertThat(dto.getLongitude()).isEqualTo(77.1234);
        assertThat(dto.getLatitude()).isEqualTo(28.5678);
        assertThat(dto.getVillageName()).isEqualTo("Village Name");
        assertThat(dto.getDistrictName()).isEqualTo("District Name");
        assertThat(dto.getStateName()).isEqualTo("State Name");
        assertThat(dto.getCountryName()).isEqualTo("Country Name");
    }

    @Test
    void testSettersAndGetters() {
        AddressRequestDto dto = new AddressRequestDto();

        dto.setId(2L);
        dto.setLaneOne("New Lane 1");
        dto.setLaneTwo("New Lane 2");
        dto.setVillageId(101L);
        dto.setPinCode("654321");
        dto.setManufacturerId(201L);
        dto.setLongitude(78.1234);
        dto.setLatitude(29.5678);
        dto.setVillageName("New Village Name");
        dto.setDistrictName("New District Name");
        dto.setStateName("New State Name");
        dto.setCountryName("New Country Name");

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getLaneOne()).isEqualTo("New Lane 1");
        assertThat(dto.getLaneTwo()).isEqualTo("New Lane 2");
        assertThat(dto.getVillageId()).isEqualTo(101L);
        assertThat(dto.getPinCode()).isEqualTo("654321");
        assertThat(dto.getManufacturerId()).isEqualTo(201L);
        assertThat(dto.getLongitude()).isEqualTo(78.1234);
        assertThat(dto.getLatitude()).isEqualTo(29.5678);
        assertThat(dto.getVillageName()).isEqualTo("New Village Name");
        assertThat(dto.getDistrictName()).isEqualTo("New District Name");
        assertThat(dto.getStateName()).isEqualTo("New State Name");
        assertThat(dto.getCountryName()).isEqualTo("New Country Name");
    }


}
