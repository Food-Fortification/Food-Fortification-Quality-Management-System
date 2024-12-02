package org.path.lab.controller;

import org.path.lab.dto.requestDto.CategoryDocumentRequirementRequestDTO;
import org.path.lab.dto.responseDto.CategoryDocumentRequirementResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.SampleRequirementsResponseDTO;
import org.path.lab.entity.LabTestType;
import org.path.lab.enums.CategoryDocRequirementType;
import org.path.lab.service.CategoryDocumentRequirementService;
import org.path.lab.service.LabTestReferenceMethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryDocumentRequirementControllerTest {

    @Mock
    private CategoryDocumentRequirementService categoryDocumentRequirementService;

    @Mock
    private LabTestReferenceMethodService labTestReferenceMethodService;

    @InjectMocks
    private CategoryDocumentRequirementController categoryDocumentRequirementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategoryDocumentRequirements() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<CategoryDocumentRequirementResponseDTO> expectedResponse = new ListResponse<>();

        when(categoryDocumentRequirementService.getAllCategoryDocumentRequirements(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<CategoryDocumentRequirementResponseDTO>> responseEntity = categoryDocumentRequirementController.getAllCategoryDocumentRequirements(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(categoryDocumentRequirementService, times(1)).getAllCategoryDocumentRequirements(pageNumber, pageSize);
        verifyNoMoreInteractions(categoryDocumentRequirementService);
    }

    @Test
    void testGetAllCategoryDocumentRequirementsByCategoryId() {
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        LabTestType.Type type = LabTestType.Type.PHYSICAL; // Replace with actual type
        Long manufacturerId = 1L;
        SampleRequirementsResponseDTO expectedResponse = new SampleRequirementsResponseDTO();

        when(categoryDocumentRequirementService.getAllCategoryDocumentRequirementsByCategoryIdAndType(categoryId, CategoryDocRequirementType.TEST, pageNumber, pageSize)).thenReturn(new ArrayList<>());
        when(labTestReferenceMethodService.getAllLabTestReferenceMethodsByCategoryId(categoryId, pageNumber, pageSize, type, manufacturerId)).thenReturn(new ArrayList<>());

        ResponseEntity<SampleRequirementsResponseDTO> responseEntity = categoryDocumentRequirementController.getAllCategoryDocumentRequirementsByCategoryId(categoryId, pageNumber, pageSize, type, manufacturerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(categoryDocumentRequirementService, times(1)).getAllCategoryDocumentRequirementsByCategoryIdAndType(categoryId, CategoryDocRequirementType.TEST, pageNumber, pageSize);
        verify(labTestReferenceMethodService, times(1)).getAllLabTestReferenceMethodsByCategoryId(categoryId, pageNumber, pageSize, type, manufacturerId);
        verifyNoMoreInteractions(categoryDocumentRequirementService, labTestReferenceMethodService);
    }

    @Test
    void testGetAllCategoryDocumentRequirementsForSample() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        String type = "TEST"; // Replace with actual type
        ListResponse<CategoryDocumentRequirementResponseDTO> expectedResponse = new ListResponse<>();
        when(categoryDocumentRequirementService.getAllCategoryDocumentRequirementsByType(CategoryDocRequirementType.valueOf(type.toUpperCase()), pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<CategoryDocumentRequirementResponseDTO>> responseEntity = categoryDocumentRequirementController.getAllCategoryDocumentRequirementsForSample(pageNumber, pageSize, type);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(categoryDocumentRequirementService, times(1)).getAllCategoryDocumentRequirementsByType(CategoryDocRequirementType.valueOf(type.toUpperCase()), pageNumber, pageSize);
        verifyNoMoreInteractions(categoryDocumentRequirementService);
    }

    @Test
    void testGetCategoryRequirementById() {
        Long documentRequiredId = 1L;
        CategoryDocumentRequirementResponseDTO expectedResponse = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);

        when(categoryDocumentRequirementService.getCategoryDocumentRequirementById(documentRequiredId)).thenReturn(expectedResponse);

        ResponseEntity<CategoryDocumentRequirementResponseDTO> responseEntity = categoryDocumentRequirementController.getCategoryRequirementById(documentRequiredId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(categoryDocumentRequirementService, times(1)).getCategoryDocumentRequirementById(documentRequiredId);
        verifyNoMoreInteractions(categoryDocumentRequirementService);
    }

    @Test
    void testAddCategoryDocumentRequirement() {
        CategoryDocumentRequirementRequestDTO dto = new CategoryDocumentRequirementRequestDTO(null,null,null,null,null,null);

        doNothing().when(categoryDocumentRequirementService).createCategoryDocumentRequirement(dto);

        ResponseEntity<String> responseEntity = categoryDocumentRequirementController.addCategoryDocumentRequirement(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(categoryDocumentRequirementService, times(1)).createCategoryDocumentRequirement(dto);
        verifyNoMoreInteractions(categoryDocumentRequirementService);
    }

    @Test
    void testUpdateCategoryDocumentRequirementById() {
        Long documentRequiredId = 1L;
        CategoryDocumentRequirementRequestDTO dto = new CategoryDocumentRequirementRequestDTO(null,null,null,null,null,null);
        dto.setId(documentRequiredId);

        doNothing().when(categoryDocumentRequirementService).updateCategoryDocumentRequirement(dto);

        ResponseEntity<String> responseEntity = categoryDocumentRequirementController.updateCategoryDocumentRequirementById(documentRequiredId, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(categoryDocumentRequirementService, times(1)).updateCategoryDocumentRequirement(dto);
        verifyNoMoreInteractions(categoryDocumentRequirementService);
    }

    @Test
    void testDeleteCategoryDocumentRequirementById() {
        Long documentRequiredId = 1L;

        doNothing().when(categoryDocumentRequirementService).deleteCategoryDocumentRequirement(documentRequiredId);

        ResponseEntity<String> responseEntity = categoryDocumentRequirementController.deleteCategoryDocumentRequirementById(documentRequiredId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(categoryDocumentRequirementService, times(1)).deleteCategoryDocumentRequirement(documentRequiredId);
        verifyNoMoreInteractions(categoryDocumentRequirementService);
    }
}