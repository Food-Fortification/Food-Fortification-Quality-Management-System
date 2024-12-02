package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.LabTestReferenceMethodRequestDTO;
import org.path.lab.dto.requestDto.LabTestTypeRequestDTO;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabTestTypeResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.LabTestReferenceMethod;
import org.path.lab.entity.LabTestType;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.manager.LabTestTypeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LabTestTypeServiceImplTest {

    @Mock
    private LabTestTypeManager labTestTypeManager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private LabTestTypeServiceImpl labTestTypeService;

    private LabTestType labTestType;
    private LabTestTypeRequestDTO labTestTypeRequestDTO;
    private LabTestTypeResponseDTO labTestTypeResponseDTO;
    private SearchListRequest searchListRequest;

    @BeforeEach
    void setUp() {

        labTestType = new LabTestType();
        labTestType.setId(1L);
        labTestTypeRequestDTO = new LabTestTypeRequestDTO(1L,"test",1L,true, LabTestType.Type.PHYSICAL,Arrays.asList(new LabTestReferenceMethodRequestDTO(null,null,null,null,null,null,null,null)));
        labTestTypeResponseDTO = new LabTestTypeResponseDTO();
        searchListRequest = new SearchListRequest();

    }

    @Test
    void testGetLabTestTypesByCategoryId() {
        List<LabTestType> labTestTypes = Arrays.asList(labTestType);
        when(labTestTypeManager.findAllByCategoryId(1L, "geoId", 0, 10)).thenReturn(labTestTypes);
        when(mapper.mapEntityToDtoLabTestType(labTestType)).thenReturn(labTestTypeResponseDTO);

        List<LabTestTypeResponseDTO> result = labTestTypeService.getLabTestTypesByCategoryId(1L, "geoId", 0, 10);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetLabTestTypesByType() {
        List<LabTestType> labTestTypes = Arrays.asList(labTestType);
        when(labTestTypeManager.findAllByType(LabTestType.Type.PHYSICAL, 1L, 0, 10)).thenReturn(labTestTypes);
        when(mapper.mapEntityToDtoLabTestType(labTestType)).thenReturn(labTestTypeResponseDTO);

        List<LabTestTypeResponseDTO> result = labTestTypeService.getLabTestTypesByType(LabTestType.Type.PHYSICAL, 1L, 0, 10);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetAllLabTestTypes() {
        List<LabTestType> labTestTypes = Arrays.asList(labTestType);
        when(labTestTypeManager.findAllLabTestType(searchListRequest, 0, 10)).thenReturn(labTestTypes);
        when(mapper.mapEntityToDtoLabTestTypeForGetAll(labTestType)).thenReturn(labTestTypeResponseDTO);
        when(labTestTypeManager.getCountForAllLabTestType(searchListRequest)).thenReturn(1L);

        ListResponse<LabTestTypeResponseDTO> result = labTestTypeService.getAllLabTestTypes(searchListRequest, 0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());

    }

    @Test
    void testGetLabTestTypeById() {
        when(labTestTypeManager.findById(1L)).thenReturn(labTestType);
        when(mapper.mapEntityToDtoLabTestType(labTestType)).thenReturn(labTestTypeResponseDTO);

        LabTestTypeResponseDTO result = labTestTypeService.getLabTestTypeById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetLabTestTypeByIdNotFound() {
        when(labTestTypeManager.findById(1L)).thenReturn(null);

        LabTestTypeResponseDTO result = labTestTypeService.getLabTestTypeById(1L);

        assertNull(result);
    }

    @Test
    void testAddLabTestType() {
        labTestType.setId(2L);
        List<LabTestReferenceMethod> methods= new ArrayList<>();
        LabTestReferenceMethod method = new LabTestReferenceMethod();
        method.setId(1L);
        methods.add(method);
        labTestType.setLabTestReferenceMethods(methods);

        labTestTypeService.addLabTestType(labTestTypeRequestDTO);

        verify(labTestTypeManager, times(1)).create(any());
    }

    @Test
    void testUpdateLabTestTypeById() {
        when(labTestTypeManager.findById(1L)).thenReturn(labTestType);
        when(mapper.mapDtoToEntityLabTestType(labTestTypeRequestDTO)).thenReturn(labTestType);

        labTestTypeService.updateLabTestTypeById(1L, labTestTypeRequestDTO);

        verify(labTestTypeManager, times(1)).update(any());
    }

    @Test
    void testUpdateLabTestTypeByIdNotFound() {
        when(labTestTypeManager.findById(1L)).thenReturn(null);

        labTestTypeService.updateLabTestTypeById(1L, labTestTypeRequestDTO);

        verify(labTestTypeManager, times(0)).update(any(LabTestType.class));
    }

    @Test
    void testDeleteLabTestTypeById() {
        when(labTestTypeManager.findById(1L)).thenReturn(labTestType);

        labTestTypeService.deleteLabTestTypeById(1L);

        verify(labTestTypeManager, times(1)).delete(1L);
    }

    @Test
    void testDeleteLabTestTypeByIdNotFound() {
        when(labTestTypeManager.findById(1L)).thenReturn(null);

        labTestTypeService.deleteLabTestTypeById(1L);

        verify(labTestTypeManager, times(0)).delete(anyLong());
    }
}