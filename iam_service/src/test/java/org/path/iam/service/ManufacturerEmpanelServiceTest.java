package org.path.iam.service;

import org.path.iam.dto.requestDto.ManufacturerEmpanelRequestDto;
import org.path.iam.dto.responseDto.DashboardCountResponseDto;
import org.path.iam.dto.responseDto.DashboardResponseDto;
import org.path.iam.dto.responseDto.ManufacturerEmpanelResponseDto;
import org.path.iam.enums.GeoType;
import org.path.iam.manager.ManufacturerEmpanelManager;
import org.path.iam.manager.ManufacturerManager;
import org.path.iam.model.ManufacturerEmpanel;
import org.path.iam.utils.DtoMapper;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManufacturerEmpanelServiceTest {

    @Mock
    RestTemplate restTemplate;
    @Mock
    private ManufacturerEmpanelManager manager;
    @Mock
    private DtoMapper dtoMapper;
    @Mock
    private KeycloakInfo keycloakInfo;
    @Mock
    private ManufacturerManager manufacturerManager;

    @InjectMocks
    private ManufacturerEmpanelService service;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
  /*  @AfterEach
    void tearDown(){
        mockStatic.close();
    }*/

    @Test
    void testCreate() {
        ManufacturerEmpanelRequestDto requestDto = new ManufacturerEmpanelRequestDto();
        ManufacturerEmpanel empanel = new ManufacturerEmpanel();
        empanel.setId(1L);

        when(dtoMapper.mapToEntity(requestDto)).thenReturn(empanel);
        when(manager.create(empanel)).thenReturn(empanel);

        Long result = service.create(requestDto);

        assertEquals(1L, result);
        verify(dtoMapper).mapToEntity(requestDto);
        verify(manager).create(empanel);
    }

    @Test
    void testGetManufacturerEmpanelByID() {
        ManufacturerEmpanel empanel = new ManufacturerEmpanel();
        empanel.setId(1L);
        ManufacturerEmpanelResponseDto responseDto = new ManufacturerEmpanelResponseDto();

        when(manager.findById(1L)).thenReturn(empanel);
        when(dtoMapper.mapToDto(empanel)).thenReturn(responseDto);

        ManufacturerEmpanelResponseDto result = service.getManufacturerEmpanelByID(1L);

        assertEquals(responseDto, result);
        verify(manager).findById(1L);
        verify(dtoMapper).mapToDto(empanel);
    }

    @Test
    void testUpdate() {
        ManufacturerEmpanelRequestDto requestDto = new ManufacturerEmpanelRequestDto();
        ManufacturerEmpanel empanel = new ManufacturerEmpanel();
        empanel.setId(1L);

        when(dtoMapper.mapToEntity(requestDto)).thenReturn(empanel);
        when(manager.update(empanel)).thenReturn(empanel);

        Long result = service.update(requestDto);

        assertEquals(1L, result);
        verify(dtoMapper).mapToEntity(requestDto);
        verify(manager).update(empanel);
    }

    @Test
    void testDelete() {
        service.delete(1L);

        verify(manager).delete(1L);
    }

    @Test
    void testGetAllEmpanelledManufacturers() {
        ManufacturerEmpanelRequestDto requestDto = new ManufacturerEmpanelRequestDto();
        requestDto.setSourceCategoryId(1L);
        requestDto.setTargetCategoryId(2L);

        List<Long> sourceManufacturers = Arrays.asList(1L, 2L);
        List<Long> targetManufacturers = Arrays.asList(3L, 4L);

        when(manager.getAllEmpanelledManufacturers(1L, null, null, null)).thenReturn(sourceManufacturers);
        when(manager.getAllEmpanelledManufacturers(2L, null, null, null)).thenReturn(targetManufacturers);

        Map<Long, List<Long>> expectedMap = new HashMap<>();
        expectedMap.put(1L, sourceManufacturers);
        expectedMap.put(2L, targetManufacturers);

        Map<Long, List<Long>> result = service.getAllEmpanelledManufacturers(requestDto);

        assertEquals(expectedMap, result);
        verify(manager).getAllEmpanelledManufacturers(1L, null, null, null);
        verify(manager).getAllEmpanelledManufacturers(2L, null, null, null);
    }


    @Test
    void testGetCategoryCounts() {
        GeoType geoType = GeoType.state;
        String geoId = "geoId";
        Long categoryId = 1L;

        List<DashboardCountResponseDto> dtoList = new ArrayList<>();
        DashboardCountResponseDto dashboardCountResponseDto = new DashboardCountResponseDto();
        dashboardCountResponseDto.setTotal(10L);
        dashboardCountResponseDto.setTotalManufacturerCategories(5L);
        dtoList.add(dashboardCountResponseDto);

        when(manager.getAllEmpanelledManufacturers(anyLong(), anyString(), any(), any())).thenReturn(Collections.singletonList(1L));
        when(manufacturerManager.getEmpanelDistrictCount(anyLong(), anyString(), anyList())).thenReturn(dtoList);

        DashboardResponseDto result = service.getCategoryCounts(categoryId, geoType, geoId);

        assertNotNull(result);
        assertEquals(5L, result.getTotalManufacturerCategories());
        assertEquals(10L, result.getTotal());
    }


}
