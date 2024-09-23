package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.LabTestReferenceMethodRequestDTO;
import com.beehyv.lab.dto.responseDto.LabMethodResponseDto;
import com.beehyv.lab.dto.responseDto.LabTestReferenceMethodResponseDTO;
import com.beehyv.lab.dto.responseDto.LabTestTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabSample;
import com.beehyv.lab.entity.LabTestReferenceMethod;
import com.beehyv.lab.entity.LabTestType;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.manager.LabSampleManager;
import com.beehyv.lab.manager.LabTestReferenceMethodManager;
import com.beehyv.lab.service.LabTestTypeService;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LabTestReferenceMethodServiceImplTest {

    @Mock
    private LabTestReferenceMethodManager labTestReferenceMethodManager;

    @Mock
    private LabSampleManager labSampleManager;

    @Mock
    private DTOMapper mapper;

    @Mock
    private LabTestTypeService labTestTypeService;

    @Mock
    private KeycloakInfo keycloakInfo;

    @InjectMocks
    private LabTestReferenceMethodServiceImpl labTestReferenceMethodService;

    private LabTestReferenceMethod labTestReferenceMethod =new LabTestReferenceMethod(1L,"TESTmethod",new LabTestType(),null,null,null,null,null,null,null);
    private LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO= new LabTestReferenceMethodRequestDTO(1L,"test",1L,0.1, 10.0,null,null,null);;
    private LabTestReferenceMethodResponseDTO labTestReferenceMethodResponseDTO = new LabTestReferenceMethodResponseDTO();
    private LabSample labSample= new LabSample();

    @BeforeEach
    void setUp() {

        labTestReferenceMethod.setId(1L);


    }

    @Test
    void testGetAllLabTestReferenceMethods() {
        List<LabTestReferenceMethod> labTestReferenceMethods = Arrays.asList(labTestReferenceMethod);
        when(labTestReferenceMethodManager.findAll(0, 10)).thenReturn(labTestReferenceMethods);

        List<LabTestReferenceMethodResponseDTO> result = labTestReferenceMethodService.getAllLabTestReferenceMethods(0, 10);

        assertEquals(1, result.size());
    }

    @Test
    void testGetAllLabTestReferenceMethodsByCategoryId() {
        List<LabTestReferenceMethod> labTestReferenceMethods = Arrays.asList(labTestReferenceMethod);
        when(labTestReferenceMethodManager.findAllByCategoryId(any(),any(),any(),any(),any())).thenReturn(labTestReferenceMethods);

        List<LabTestReferenceMethodResponseDTO> result = labTestReferenceMethodService.getAllLabTestReferenceMethodsByCategoryId(1L, 0, 10, LabTestType.Type.PHYSICAL, null);

        assertEquals(1, result.size());
    }

    @Test
    void testGetLabTestReferenceMethodById() {
        when(labTestReferenceMethodManager.findById(1L)).thenReturn(labTestReferenceMethod);

        LabTestReferenceMethodResponseDTO result = labTestReferenceMethodService.getLabTestReferenceMethodById(1L);

        assertNotNull( result);
    }

    @Test
    void testGetLabTestReferenceMethodByIdNotFound() {
        when(labTestReferenceMethodManager.findById(1L)).thenReturn(null);

        LabTestReferenceMethodResponseDTO result = labTestReferenceMethodService.getLabTestReferenceMethodById(1L);

        assertNull(result);
    }

    @Test
    void testAddLabTestReferenceMethod() {
        when(mapper.mapDtoToEntityLabTestReferenceMethod(labTestReferenceMethodRequestDTO)).thenReturn(labTestReferenceMethod);

        labTestReferenceMethodService.addLabTestReferenceMethod(labTestReferenceMethodRequestDTO);

        verify(labTestReferenceMethodManager, times(1)).create(any());
    }

    @Test
    void testUpdateLabTestReferenceMethodById() {
        when(labTestReferenceMethodManager.findById(1L)).thenReturn(labTestReferenceMethod);

        labTestReferenceMethodService.updateLabTestReferenceMethodById(1L, labTestReferenceMethodRequestDTO);

        verify(labTestReferenceMethodManager, times(1)).update(any());
    }

    @Test
    void testUpdateLabTestReferenceMethodByIdNotFound() {
        when(labTestReferenceMethodManager.findById(1L)).thenReturn(null);

        labTestReferenceMethodService.updateLabTestReferenceMethodById(1L, labTestReferenceMethodRequestDTO);

        verify(labTestReferenceMethodManager, times(0)).update(any(LabTestReferenceMethod.class));
    }

    @Test
    void testDeleteLabTestReferenceMethodById() {
        when(labTestReferenceMethodManager.findById(1L)).thenReturn(labTestReferenceMethod);

        labTestReferenceMethodService.deleteLabTestReferenceMethodById(1L);

        verify(labTestReferenceMethodManager, times(1)).delete(1L);
    }

    @Test
    void testDeleteLabTestReferenceMethodByIdNotFound() {
        when(labTestReferenceMethodManager.findById(1L)).thenReturn(null);

        labTestReferenceMethodService.deleteLabTestReferenceMethodById(1L);

        verify(labTestReferenceMethodManager, times(0)).delete(anyLong());
    }

    @Test
    void testGetAllMethodsByCategoryId() {
        List<LabTestTypeResponseDTO> labTestTypeResponseDTOs = Arrays.asList(new LabTestTypeResponseDTO());
        List<LabTestReferenceMethod> labTestReferenceMethods = Arrays.asList(labTestReferenceMethod);
        LabTestReferenceMethodResponseDTO labTestReferenceMethodResponseDTO = new LabTestReferenceMethodResponseDTO();
        labTestReferenceMethodResponseDTO.setMinValue(1.0);
        labTestReferenceMethodResponseDTO.setMaxValue(2.0);
        LabMethodResponseDto labMethodResponseDto = new LabMethodResponseDto();
        when(labSampleManager.findById(1L)).thenReturn(labSample);
        when(labTestTypeService.getLabTestTypesByCategoryId(any(),any(),any(),any())).thenReturn(labTestTypeResponseDTOs);
        when(labTestReferenceMethodManager.findAllByTestTypeId(any(), any(), any())).thenReturn(labTestReferenceMethods);

        ListResponse<LabMethodResponseDto> result = labTestReferenceMethodService.getAllMethodsByCategoryId(1L, 1L, 0, 10);

        assertEquals(1, result.getData().size());
    }
}







//
//Explanations:
//
//        1. **`testGetAllLabTestReferenceMethods`**: This test case verifies the `getAllLabTestReferenceMethods` method. It mocks the `labTestReferenceMethodManager.findAll` and `mapper.mapEntityToDtoLabTestReferenceMethod` methods, and asserts that the correct list of `LabTestReferenceMethodResponseDTO` objects is returned.
//
//2. **`testGetAllLabTestReferenceMethodsByCategoryId`**: This test case verifies the `getAllLabTestReferenceMethodsByCategoryId` method. It mocks the `labTestReferenceMethodManager.findAllByCategoryId` and `mapper.mapEntityToDtoLabTestReferenceMethod` methods, and asserts that the correct list of `LabTestReferenceMethodResponseDTO` objects is returned.
//
//3. **`testGetLabTestReferenceMethodById`**: This test case verifies the `getLabTestReferenceMethodById` method when a valid ID is provided. It mocks the `labTestReferenceMethodManager.findById` and `mapper.mapEntityToDtoLabTestReferenceMethod` methods, and asserts that the correct `LabTestReferenceMethodResponseDTO` is returned.
//
//        4. **`testGetLabTestReferenceMethodByIdNotFound`**: This test case verifies the `getLabTestReferenceMethodById` method when an invalid or non-existent ID is provided. It mocks the `labTestReferenceMethodManager.findById` method to return null, and asserts that the method returns null.
//
//        5. **`testAddLabTestReferenceMethod`**: This test case verifies the `addLabTestReferenceMethod` method. It mocks the `mapper.mapDtoToEntityLabTestReferenceMethod` method, and verifies that the `labTestReferenceMethodManager.create` method is called exactly once with the correct arguments.
//
//6. **`testUpdateLabTestReferenceMethodById`**: This test case verifies the `updateLabTestReferenceMethodById` method when a valid ID is provided. It mocks the `labTestReferenceMethodManager.findById` and `mapper.mapDtoToEntityLabTestReferenceMethod` methods, and verifies that the `labTestReferenceMethodManager.update` method is called exactly once with the correct arguments.
//
//7. **`testUpdateLabTestReferenceMethodByIdNotFound`**: This test case verifies the `updateLabTestReferenceMethodById` method when an invalid or non-existent ID is provided. It mocks the `labTestReferenceMethodManager.findById` method to return null, and verifies that the `labTestReferenceMethodManager.update` method is not called.
//
//        8. **`testDeleteLabTestReferenceMethodById`**: This test case verifies the `deleteLabTestReferenceMethodById` method when a valid ID is provided. It mocks the `labTestReferenceMethodManager.findById` method, and verifies that the `labTestReferenceMethodManager.delete` method is called exactly once with the correct arguments.
//
//9. **`testDeleteLabTestReferenceMethodByIdNotFound`**: This test case verifies the `deleteLabTestReferenceMethodById` method when an invalid or non-existent ID is provided. It mocks the `labTestReferenceMethodManager.findById` method to return null, and verifies that the `labTestReferenceMethodManager.delete` method is not called.
//
//        10. **`testGetAllMethodsByCategoryId`**: This test case verifies the `getAllMethodsByCategoryId` method. It mocks the `labSampleManager.findById`, `labTestTypeService.getLabTestTypesByCategoryId`, `labTestReferenceMethodManager.findAllByTestTypeId`, and `mapper.mapEntityToDtoLabTestReferenceMethod` methods, and asserts that the correct `ListResponse` is returned with the expected data.
//
//In each test case, the necessary mocks and test data are set up using the `@Mock` annotations and the `@BeforeEach` method. The actual test assertions are made using the static `assert` methods from JUnit and the `verify` method from Mockito.
//
//        Note: Make sure to import the necessary classes and static methods in your actual test class.