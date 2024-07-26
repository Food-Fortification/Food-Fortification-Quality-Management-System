package com.beehyv.lab.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.*;
import com.beehyv.lab.dto.requestDto.LabRequestDTO;
import com.beehyv.lab.dto.requestDto.DashboardRequestDto;
import com.beehyv.lab.entity.*;
import com.beehyv.lab.manager.LabManager;
import com.beehyv.lab.manager.LabCategoryManager;
import com.beehyv.lab.manager.LabManufacturerCategoryManager;
import com.beehyv.lab.manager.ExternalMetaDataManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LabServiceImplTest {

    @Mock
    private LabManager labManager;

    @Mock
    private LabCategoryManager labCategoryManager;

    @Mock
    private LabManufacturerCategoryManager labManufacturerCategoryManager;

    @Mock
    private ExternalMetaDataManager externalMetaDataManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private LabServiceImpl labService;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(DTOMapper.class);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", "1"));
    }

    @Test
    void testGetAllLabs() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest searchRequest = new SearchListRequest();
        Lab lab = new Lab();
        lab.setId(1L);
        List<Lab> labs = Arrays.asList(lab);

        when(labManager.findAllLabs(eq(searchRequest), eq(null), eq(pageNumber), eq(pageSize)))
                .thenReturn(labs);
        when(labManager.getCountForAllLabs(eq(searchRequest), eq(null)))
                .thenReturn(1L);

        ListResponse<LabResponseDTO> response = labService.getAllLabs(searchRequest, null, pageNumber, pageSize);

        assertNotNull(response);
        assertEquals(1, response.getCount());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testGetLabById() {
        Long labId = 1L;
        Lab lab = new Lab();
        lab.setId(labId);

        when(labManager.findById(eq(labId))).thenReturn(lab);

        LabResponseDTO response = labService.getLabById(labId);

        assertNotNull(response);
        assertEquals(labId, response.getId());
    }

    @Test
    void testCreateLab() {
        LabRequestDTO labRequestDTO = new LabRequestDTO();
        Lab lab = new Lab();
        lab.setId(1L);

        when(labManager.create(any(Lab.class))).thenReturn(lab);

        Long createdLabId = labService.createLab(labRequestDTO);

        assertNotNull(createdLabId);
        assertEquals(1L, createdLabId);
    }

    @Test
    void testUpdateLabById() {
        Long labId = 1L;
        LabRequestDTO labRequestDTO = new LabRequestDTO();
        labRequestDTO.setId(labId);
        Lab lab = new Lab();
        lab.setId(labId);

        when(labManager.findById(eq(labId))).thenReturn(lab);
        when(labManager.update(any(Lab.class))).thenReturn(lab);

        labService.updateLabById(labId, labRequestDTO);

        verify(labManager, times(1)).update(any(Lab.class));
    }

    @Test
    void testDeleteLabById() {
        Long labId = 1L;
        Lab lab = new Lab();
        lab.setId(labId);

        when(labManager.findById(eq(labId))).thenReturn(lab);

        labService.deleteLabById(labId);

        verify(labManager, times(1)).delete(eq(labId));
    }

    @Test
    void testGetNearestLab() {
        String address = "{\"latitude\": 12.9716, \"longitude\": 77.5946, \"pinCode\": \"560001\", \"village\": {\"name\": \"Village1\"}, \"district\": {\"name\": \"District1\"}}";
        Long categoryId = 1L;
        Long manufacturerId = 1L;
        Lab lab = new Lab();
        lab.setId(1L);
        lab.setName("Sample Lab");
        lab.setAddress(new Address(1L,null,null,new Village(1L,"village",new District(null,"null",null,null,null),null,null),"pincode",10.0,10.0));
        lab.setUuid("sample-lab-uuid");
        lab.setLabCategoryMapping(new HashSet<>());
        lab.setLabManufacturerCategoryMapping(new HashSet<>());
        lab.setLabDocuments(new HashSet<>());
        List<Lab> labs = Arrays.asList(lab);

        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", "1"));
        when(labManufacturerCategoryManager.findAllLabsByManufacturerIdAndCategoryId(any(),any(),any(),any(),any()))
                .thenReturn(labs.stream().map(l -> new LabManufacturerCategoryMapping(
                        1L,1L,lab,1L
                )).toList());
        when(labCategoryManager.findNearestLab(any(), eq(categoryId)))
                .thenReturn(labs.stream().map(l -> new LabCategory()).toList());

        LabResponseDTO response = labService.getNearestLab(address, categoryId, manufacturerId);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetAllActiveLabsForCategory() {
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Lab lab = new Lab();
        lab.setId(1L);
        List<Lab> labs = Arrays.asList(lab);

        when(labManager.findAllActiveLabsForCategory(eq(null), eq(categoryId), eq(pageNumber), eq(pageSize)))
                .thenReturn(labs);
        when(labManager.getCountForAllActiveLabsCategory(eq(null), eq(categoryId)))
                .thenReturn(1L);

        ListResponse<LabListResponseDTO> response = labService.getAllActiveLabsForCategory(null, null, categoryId, pageNumber, pageSize);

        assertNotNull(response);
        assertEquals(1, response.getCount());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testGetLabsAggregate() {
        DashboardRequestDto dto = new DashboardRequestDto();
        Lab lab = new Lab();
        lab.setId(1L);
        List<Object[]> objects = Arrays.asList(new Object[][]{
                {1L, "Lab1", "District1", "State1", 10L, 5L, 3L, 20L, "Certificate1", 2L}
        });


        when(labManager.getLabsAggregateByCategoryId(eq(dto))).thenReturn(objects);

        ListResponse<LabDashboardResponseDto> response = labService.getLabsAggregate(dto);

        assertNotNull(response);
        assertEquals(1, response.getCount());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testGetLabSamplesDetails() {
        DashboardRequestDto dto = new DashboardRequestDto();
        Long labId = 1L;
        String type = "samplesReceived";
        LabSample sample = new LabSample();
        sample.setId(1L);
        List<LabSample> samples = Arrays.asList(sample);

        when(labManager.getLabSamplesDetails(eq(dto), eq(labId), anyList())).thenReturn(samples);

        ListResponse<LabSampleDetailsResponseDto> response = labService.getLabSamplesDetails(dto, labId, type);

        assertNotNull(response);
        assertEquals(1, response.getCount());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testFindCertificateByName() {
        String name = "Certificate1";

        when(labManager.findCertificateByName(eq(name))).thenReturn("CERT123");

        String certificateNo = labService.findCertificateByName(name);

        assertNotNull(certificateNo);
        assertEquals("CERT123", certificateNo);
    }
}
