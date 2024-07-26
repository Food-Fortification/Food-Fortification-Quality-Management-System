package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FssaiDistrictJsonDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String fssaiDistrictName = "Ranga Reddy";
        String districtName = "Rangareddy District";
        String geoId = "in.tg.rangareddy";

        FssaiDistrictJsonDto dto = new FssaiDistrictJsonDto(fssaiDistrictName, districtName, geoId);

        assertNotNull(dto);
        assertEquals(fssaiDistrictName, dto.getFssaiDistrictName());
        assertEquals(districtName, dto.getDistrictName());
        assertEquals(geoId, dto.getGeoId());
    }

    @Test
    public void testWithOptionalFields() {
        String fssaiDistrictName = null;
        FssaiDistrictJsonDto dto = new FssaiDistrictJsonDto(fssaiDistrictName, "Adilabad District", "in.tg.adilabad");

        assertNotNull(dto);
        assertNull(dto.getFssaiDistrictName());
        assertEquals("Adilabad District", dto.getDistrictName());
        assertEquals("in.tg.adilabad", dto.getGeoId());
    }
}
