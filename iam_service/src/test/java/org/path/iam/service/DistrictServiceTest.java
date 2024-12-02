package org.path.iam.service;

import org.path.iam.dto.requestDto.DistrictRequestDto;
import org.path.iam.dto.responseDto.DistrictResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.LocationResponseDto;
import org.path.iam.manager.DistrictManager;
import org.path.iam.model.District;
import org.path.iam.model.State;
import org.path.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DistrictServiceTest {

    @Mock
    private DistrictManager districtManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private DistrictService districtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateDistrict() {
        DistrictRequestDto requestDto = new DistrictRequestDto();
        District district = new District();
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(district);

        assertDoesNotThrow(() -> districtService.create(requestDto));
        verify(districtManager, times(1)).create(district);
    }

    @Test
    public void testUpdateDistrict() {
        DistrictRequestDto requestDto = new DistrictRequestDto();
        District district = new District();
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(district);

        assertDoesNotThrow(() -> districtService.update(requestDto));
        verify(districtManager, times(1)).update(district);
    }


    @Test
    public void testDeleteDistrict() {
        Long id = 1L;
        doNothing().when(districtManager).delete(id);

        assertDoesNotThrow(() -> districtService.delete(id));
        verify(districtManager, times(1)).delete(id);
    }

    @Test
    public void testGetDistrictById() {
        Long id = 1L;
        District district = new District();
        when(districtManager.findById(id)).thenReturn(district);
        DistrictResponseDto responseDto = new DistrictResponseDto();
        when(dtoMapper.mapToResponseDto(district)).thenReturn(responseDto);

        DistrictResponseDto result = districtService.getById(id);

        assertEquals(responseDto, result);
    }

    @Test
    public void testGetAllDistricts() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<District> districts = Collections.emptyList();
        when(districtManager.findAll(pageNumber, pageSize)).thenReturn(districts);
        Long count = 0L;
        when(districtManager.getCount(districts.size(), pageNumber, pageSize)).thenReturn(count);
        ListResponse<DistrictResponseDto> expectedResponse = new ListResponse<>(count, Collections.emptyList());

        ListResponse<DistrictResponseDto> result = districtService.getAll(pageNumber, pageSize);

        assertEquals(expectedResponse.getCount(), result.getCount());
        assertEquals(expectedResponse.getData(), result.getData());
    }

    @Test
    public void testGetAllDistrictsByStateId() {
        Long stateId = 1L;
        String search = "districtName";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<District> districts = Collections.emptyList();
        when(districtManager.findAllByStateId(stateId, search, pageNumber, pageSize)).thenReturn(districts);
        Long count = 0L;
        when(districtManager.getCountByStateId(stateId, search)).thenReturn(count);
        ListResponse<LocationResponseDto> expectedResponse = new ListResponse<>(count, Collections.emptyList());

        ListResponse<LocationResponseDto> result = districtService.getAllDistrictsByStateId(stateId, search, pageNumber, pageSize);

        assertEquals(expectedResponse.getCount(), result.getCount());
        assertEquals(expectedResponse.getData(), result.getData());
    }

    @Test
    public void testGetAllDistrictsByStateGeoId() {
        String stateGeoId = "stateGeoId";
        District district1 = new District();
        district1.setGeoId("geoId1");
        district1.setName("District 1");
        district1.setState(new State());
        District district2 = new District();
        district2.setGeoId("geoId2");
        district2.setName("District 2");
        district2.setState(new State());
        List<District> districts = Arrays.asList(district1, district2);
        when(districtManager.findAllByStateGeoId(stateGeoId)).thenReturn(districts);

        List<DistrictResponseDto> result = districtService.getAllDistrictsByStateGeoId(stateGeoId);

        assertEquals(2, result.size());
        assertEquals("geoId1", result.get(0).getGeoId());
        assertEquals("District 1", result.get(0).getName());
        assertEquals("geoId2", result.get(1).getGeoId());
        assertEquals("District 2", result.get(1).getName());
    }

    @Test
    public void testGetAllByGeoIds() {
        List<String> geoIds = Arrays.asList("geoId1", "geoId2");
        District district1 = new District();
        district1.setGeoId("geoId1");
        district1.setName("District 1");
        district1.setState(new State());
        District district2 = new District();
        district2.setGeoId("geoId2");
        district2.setName("District 2");
        district2.setState(new State());
        List<District> districts = Arrays.asList(district1, district2);
        when(districtManager.findAllByGeoIds(geoIds)).thenReturn(districts);

        List<DistrictResponseDto> result = districtService.getAllByGeoIds(geoIds);

        assertEquals(2, result.size());
        assertEquals("geoId1", result.get(0).getGeoId());
        assertEquals("District 1", result.get(0).getName());
        assertEquals("geoId2", result.get(1).getGeoId());
        assertEquals("District 2", result.get(1).getName());
    }
}
