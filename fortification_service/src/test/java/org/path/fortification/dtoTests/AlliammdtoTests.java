package org.path.fortification.dtoTests;

import org.path.fortification.dto.iam.*;
import org.path.fortification.dto.iam.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlliammdtoTests {

    @Test
    public void testAddressResponseDto() {
        AddressResponseDto addressResponseDto = new AddressResponseDto();

        Long id = 1L;
        String laneOne = "laneOne";
        String laneTwo = "laneTwo";
        String pinCode = "pinCode";
        VillageResponseDto village = new VillageResponseDto();
        Double latitude = 100.0;
        Double longitude = 200.0;

        addressResponseDto.setId(id);
        addressResponseDto.setLaneOne(laneOne);
        addressResponseDto.setLaneTwo(laneTwo);
        addressResponseDto.setPinCode(pinCode);
        addressResponseDto.setVillage(village);
        addressResponseDto.setLatitude(latitude);
        addressResponseDto.setLongitude(longitude);

        assertEquals(id, addressResponseDto.getId());
        assertEquals(laneOne, addressResponseDto.getLaneOne());
        assertEquals(laneTwo, addressResponseDto.getLaneTwo());
        assertEquals(pinCode, addressResponseDto.getPinCode());
        assertEquals(village, addressResponseDto.getVillage());
        assertEquals(latitude, addressResponseDto.getLatitude());
        assertEquals(longitude, addressResponseDto.getLongitude());
    }

    @Test
    public void testCountryResponseDto() {
        CountryResponseDto countryResponseDto = new CountryResponseDto();

        Long id = 1L;
        String name = "name";
        String geoId = "geoId";

        countryResponseDto.setId(id);
        countryResponseDto.setName(name);
        countryResponseDto.setGeoId(geoId);

        assertEquals(id, countryResponseDto.getId());
        assertEquals(name, countryResponseDto.getName());
        assertEquals(geoId, countryResponseDto.getGeoId());
    }

    @Test
    public void testDistrictResponseDto() {
        DistrictResponseDto districtResponseDto = new DistrictResponseDto();

        Long id = 1L;
        String name = "name";
        String geoId = "geoId";
        StateResponseDto state = new StateResponseDto();

        districtResponseDto.setId(id);
        districtResponseDto.setName(name);
        districtResponseDto.setGeoId(geoId);
        districtResponseDto.setState(state);

        assertEquals(id, districtResponseDto.getId());
        assertEquals(name, districtResponseDto.getName());
        assertEquals(geoId, districtResponseDto.getGeoId());
        assertEquals(state, districtResponseDto.getState());
    }

    @Test
    public void testStateResponseDto() {
        StateResponseDto stateResponseDto = new StateResponseDto();

        Long id = 1L;
        String name = "name";
        String geoId = "geoId";
        CountryResponseDto country = new CountryResponseDto();

        stateResponseDto.setId(id);
        stateResponseDto.setName(name);
        stateResponseDto.setGeoId(geoId);
        stateResponseDto.setCountry(country);

        assertEquals(id, stateResponseDto.getId());
        assertEquals(name, stateResponseDto.getName());
        assertEquals(geoId, stateResponseDto.getGeoId());
        assertEquals(country, stateResponseDto.getCountry());
    }

    @Test
    public void testNameAddressResponseDto() {
        NameAddressResponseDto nameAddressResponseDto = new NameAddressResponseDto();

        Long id = 1L;
        String name = "name";
        String completeAddress = "completeAddress";
        String licenseNo = "licenseNo";

        nameAddressResponseDto.setId(id);
        nameAddressResponseDto.setName(name);
        nameAddressResponseDto.setCompleteAddress(completeAddress);
        nameAddressResponseDto.setLicenseNo(licenseNo);

        assertEquals(id, nameAddressResponseDto.getId());
        assertEquals(name, nameAddressResponseDto.getName());
        assertEquals(completeAddress, nameAddressResponseDto.getCompleteAddress());
        assertEquals(licenseNo, nameAddressResponseDto.getLicenseNo());
    }

}
