package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.CreateSampleRequestDto;
import com.beehyv.lab.dto.requestDto.LabTestRequestDTO;
import com.beehyv.lab.dto.responseDto.LabTestResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabTest;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.manager.LabTestManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LabTestServiceImplTest {

    @Mock
    private LabTestManager labTestManager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private LabTestServiceImpl labTestService;

    private LabTest labTest;
    private LabTestRequestDTO labTestRequestDTO;
    private LabTestResponseDTO labTestResponseDTO;
    private CreateSampleRequestDto createSampleRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        labTest = new LabTest();
        labTest.setId(1L);
        labTestRequestDTO = new LabTestRequestDTO();
        labTestResponseDTO = new LabTestResponseDTO();
        List<LabTestRequestDTO> requests=new ArrayList<LabTestRequestDTO>();
        requests.add(labTestRequestDTO);
        createSampleRequestDto = new CreateSampleRequestDto();
        createSampleRequestDto.setTests(requests);
    }

    @Test
    void testGetAllLabTests() {
        List<LabTest> labTests = Arrays.asList(labTest);
        when(labTestManager.findAll(0, 10)).thenReturn(labTests);
        when(mapper.mapEntityToDtoLabTest(labTest)).thenReturn(labTestResponseDTO);
        when(labTestManager.getCount(1, 0, 10)).thenReturn(1L);

        ListResponse<LabTestResponseDTO> result = labTestService.getAllLabTests(0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(labTestResponseDTO, result.getData().get(0));
        assertEquals(1L, result.getCount());
    }

    @Test
    void testGetLabTestById() {
        when(labTestManager.findById(1L)).thenReturn(labTest);
        when(mapper.mapEntityToDtoLabTest(labTest)).thenReturn(labTestResponseDTO);

        LabTestResponseDTO result = labTestService.getLabTestById(1L);

        assertEquals(labTestResponseDTO, result);
    }

    @Test
    void testGetLabTestByIdNotFound() {
        when(labTestManager.findById(1L)).thenReturn(null);

        LabTestResponseDTO result = labTestService.getLabTestById(1L);

        assertNull(result);
    }

    @Test
    void testAddLabTest() {
        LabTestRequestDTO dto = new LabTestRequestDTO();
        dto.setLabTestReferenceMethodId(1L);
        dto.setValue("value");
        createSampleRequestDto.getTests().add(dto);
        createSampleRequestDto.setBatchId(1L);
        when(mapper.mapDtoToEntityLabTest(any())).thenReturn(labTest);

        labTestService.addLabTest(createSampleRequestDto);

        verify(labTestManager, times(2)).create(labTest);
    }

    @Test
    void testUpdateLabTestById() {
        when(labTestManager.findById(1L)).thenReturn(labTest);
        when(mapper.mapDtoToEntityLabTest(labTestRequestDTO)).thenReturn(labTest);

        labTestService.updateLabTestById(1L, labTestRequestDTO);

        verify(labTestManager, times(1)).update(labTest);
    }

    @Test
    void testUpdateLabTestByIdNotFound() {
        when(labTestManager.findById(1L)).thenReturn(null);

        labTestService.updateLabTestById(1L, labTestRequestDTO);

        verify(labTestManager, times(0)).update(any(LabTest.class));
    }

    @Test
    void testDeleteLabTestById() {
        when(labTestManager.findById(1L)).thenReturn(labTest);

        labTestService.deleteLabTestById(1L);

        verify(labTestManager, times(1)).delete(1L);
    }

    @Test
    void testDeleteLabTestByIdNotFound() {
        when(labTestManager.findById(1L)).thenReturn(null);

        labTestService.deleteLabTestById(1L);

        verify(labTestManager, times(0)).delete(anyLong());
    }

    @Test
    void testGetDetailsByBatchId() {
        List<LabTest> labTests = Arrays.asList(labTest);
        when(labTestManager.findDetailsByBatchId(1L, 0, 10)).thenReturn(labTests);
        when(mapper.mapEntityToDtoLabTest(labTest)).thenReturn(labTestResponseDTO);
        when(labTestManager.getCount(1, 0, 10)).thenReturn(1L);

        ListResponse<LabTestResponseDTO> result = labTestService.getDetailsByBatchId(1L, 0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(labTestResponseDTO, result.getData().get(0));
        assertEquals(1L, result.getCount());
    }
}