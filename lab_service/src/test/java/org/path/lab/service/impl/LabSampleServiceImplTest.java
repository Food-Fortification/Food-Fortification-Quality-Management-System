package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.LabSampleRequestDTO;
import org.path.lab.dto.requestDto.LabTestRequestDTO;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabSampleCreateResponseDto;
import org.path.lab.dto.responseDto.LabSampleResponseDto;
import org.path.lab.dto.responseDto.LabSampleResultResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.Lab;
import org.path.lab.entity.LabSample;
import org.path.lab.entity.SampleState;
import org.path.lab.enums.LabSampleResult;
import org.path.lab.manager.LabSampleManager;
import org.path.lab.manager.LabTestReferenceMethodManager;
import org.path.lab.manager.MessageManager;
import org.path.lab.manager.SampleStateManager;
import org.path.lab.mapper.DTOMapper;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.validation.ValidationException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LabSampleServiceImplTest {

    @Mock
    private LabSampleManager labSampleManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private SampleStateManager sampleStateManager;

    @Mock
    private LabTestReferenceMethodManager labTestReferenceMethodManager;

    @Mock
    private DTOMapper mapper;

    @Mock
    private MessageManager messg;

    @InjectMocks
    private LabSampleServiceImpl labSampleService;

    private Map<String, Object> userInfo;

    @BeforeEach
    void setUp() {
        userInfo = new HashMap<>();
        userInfo.put("roles", new HashSet<>(Arrays.asList("Test")));
        userInfo.put("labId", "1");
        mapper = Mappers.getMapper(DTOMapper.class);
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        labSampleService.setKeycloakInfo(keycloakInfo);

    }

    @Test
    void testGetAllLabSamples() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest searchRequest = new SearchListRequest();
        LabSample sample = new LabSample();
        sample.setId(1L);
        List<LabSample> labSamples = Arrays.asList(sample);


        when(labSampleManager.findAll(anyLong(), eq(pageNumber), eq(pageSize), eq(searchRequest)))
                .thenReturn(labSamples);
        when(labSampleManager.getCount(anyInt(), anyLong(), eq(pageNumber), eq(pageSize), eq(searchRequest)))
                .thenReturn(1L);
        LabSampleResponseDto responseDto = new LabSampleResponseDto();
//        when(mapper.mapEntityToDtoLabSample(any())).thenReturn(responseDto);

        ListResponse<LabSampleResponseDto> response = labSampleService.getAllLabSamples(pageNumber, pageSize, searchRequest);

        assertNotNull(response);
        assertEquals(1, response.getCount());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testCreateLabSample_valid() {
        LabSampleRequestDTO requestDTO = new LabSampleRequestDTO();
        requestDTO.setSampleSentDate(new Date());
        requestDTO.setReceivedDate(new Date());

        LabSample sample = new LabSample();
        sample.setId(1L);

//        when(mapper.mapDtoToEntityLabSample(any(LabSampleRequestDTO.class))).thenReturn(sample);
        when(sampleStateManager.findByName(anyString())).thenReturn(new SampleState());
        when(labSampleManager.create(any(LabSample.class))).thenReturn(sample);

        LabSampleCreateResponseDto response = labSampleService.createLabSample(requestDTO, true);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testCreateLabSample_invalidDate() {
        LabSampleRequestDTO requestDTO = new LabSampleRequestDTO();
        requestDTO.setSampleSentDate(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        requestDTO.setReceivedDate(cal.getTime());

        assertThrows(ValidationException.class, () -> {
            labSampleService.createLabSample(requestDTO, true);
        });
    }

    @Test
    void testUpdateLabSample_valid() {
        LabSampleRequestDTO requestDTO = new LabSampleRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setSampleSentDate(new Date());
        requestDTO.setReceivedDate(new Date());
        LabTestRequestDTO test = new LabTestRequestDTO();
        requestDTO.setLabTests(new HashSet<>(Arrays.asList(test)));

        LabSample sample = new LabSample();
        sample.setId(1L);

        when(labSampleManager.findById(anyLong())).thenReturn(sample);
//        when(mapper.mapDtoToEntityLabSample(any(LabSampleRequestDTO.class), any(LabSample.class)))
//                .thenReturn(sample);
        when(labSampleManager.update(any(LabSample.class))).thenReturn(sample);

        LabSampleResultResponseDto response = labSampleService.updateLabSample(requestDTO);

        assertNotNull(response);
        assertEquals(LabSampleResult.TEST_PASSED, response.getLabSampleResult());
    }

    @Test
    void testUpdateLabSample_invalidDate() {
        LabSampleRequestDTO requestDTO = new LabSampleRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setSampleSentDate(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        requestDTO.setReceivedDate(cal.getTime());

        assertThrows(ValidationException.class, () -> {
            labSampleService.updateLabSample(requestDTO);
        });
    }

    @Test
    void testDeleteLabSample() {
        LabSample sample = new LabSample();
        sample.setId(1L);
        sample.setLab(new Lab(1L));
        sample.setSampleSentDate(new Date());

        doNothing().when(messg).send(any(),any(),any(),any(),any(),any(),any());

        when(labSampleManager.delete(anyLong())).thenReturn(sample);

        labSampleService.deleteLabSample(1L);

        verify(labSampleManager, times(1)).delete(1L);
    }

//    @Test
//    void testGetAllLabSamplesByLotId() {
//        Integer pageNumber = 1;
//        Integer pageSize = 10;
//        Long lotId = 1L;
//        LabSample sample = new LabSample();
//        sample.setId(1L);
//        List<LabSample> labSamples = Arrays.asList(sample);
//
//        when(labSampleManager.findAllByLotId(anyLong(), eq(pageNumber), eq(pageSize)))
//                .thenReturn(labSamples);
//        when(labSampleManager.getCountByLotId(anyLong())).thenReturn(1L);
//        when(mapper.mapEntityToDtoLabSample(any(LabSample.class))).thenReturn(new LabSampleResponseDto());
//
//        ListResponse<LabSampleResponseDto> response = labSampleService.getAllLabSamplesByLotId(lotId, pageNumber, pageSize);
//
//        assertNotNull(response);
//        assertEquals(1, response.getTotalCount());
//        assertEquals(1, response.getData().size());
//    }
}
