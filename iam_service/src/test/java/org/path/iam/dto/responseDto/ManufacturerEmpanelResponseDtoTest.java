package org.path.iam.dto.responseDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerEmpanelResponseDtoTest {

    private ManufacturerEmpanelResponseDto manufacturerEmpanelResponseDto;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        manufacturerEmpanelResponseDto = new ManufacturerEmpanelResponseDto();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerEmpanelResponseDto dto = new ManufacturerEmpanelResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getManufacturerId());
        assertNull(dto.getCategoryId());
        assertNull(dto.getStateGeoId());
        assertNull(dto.getFromDate());
        assertNull(dto.getToDate());
    }

    @Test
    public void testSettersAndGetters() {
        Long id = 1L;
        Long manufacturerId = 2L;
        Long categoryId = 3L;
        String stateGeoId = "State123";
        Date fromDate = new Date();
        Date toDate = new Date();

        manufacturerEmpanelResponseDto.setId(id);
        manufacturerEmpanelResponseDto.setManufacturerId(manufacturerId);
        manufacturerEmpanelResponseDto.setCategoryId(categoryId);
        manufacturerEmpanelResponseDto.setStateGeoId(stateGeoId);
        manufacturerEmpanelResponseDto.setFromDate(fromDate);
        manufacturerEmpanelResponseDto.setToDate(toDate);

        assertEquals(id, manufacturerEmpanelResponseDto.getId());
        assertEquals(manufacturerId, manufacturerEmpanelResponseDto.getManufacturerId());
        assertEquals(categoryId, manufacturerEmpanelResponseDto.getCategoryId());
        assertEquals(stateGeoId, manufacturerEmpanelResponseDto.getStateGeoId());
        assertEquals(fromDate, manufacturerEmpanelResponseDto.getFromDate());
        assertEquals(toDate, manufacturerEmpanelResponseDto.getToDate());
    }

    @Test
    public void testJsonFormat() throws Exception {
        String json = "{"
                + "\"id\":1,"
                + "\"manufacturerId\":2,"
                + "\"categoryId\":3,"
                + "\"stateGeoId\":\"State123\","
                + "\"fromDate\":\"2023-06-01\","
                + "\"toDate\":\"2023-12-31\""
                + "}";

        ManufacturerEmpanelResponseDto dto = objectMapper.readValue(json, ManufacturerEmpanelResponseDto.class);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getManufacturerId());
        assertEquals(3L, dto.getCategoryId());
        assertEquals("State123", dto.getStateGeoId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2023-06-01");
        Date toDate = sdf.parse("2023-12-31");

        assertEquals(fromDate, dto.getFromDate());
        assertEquals(toDate, dto.getToDate());
    }
}
