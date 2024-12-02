package org.path.lab.service.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.path.lab.dto.requestDto.DocTypeRequestDTO;
import org.path.lab.dto.responseDto.DocTypeResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.DocType;
import org.path.lab.manager.DocTypeManager;
import org.path.lab.mapper.DTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class DocTypeServiceImplTest {

    @Mock
    private DocTypeManager docTypeManager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private DocTypeServiceImpl docTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllDocTypes() {
        List<DocType> docTypes = Collections.singletonList(new DocType());
        when(docTypeManager.findAll(anyInt(), anyInt())).thenReturn(docTypes);
        when(docTypeManager.getCount(anyInt(), anyInt(), anyInt())).thenReturn(1L);

        ListResponse<DocTypeResponseDTO> result = docTypeService.getAllDocTypes(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
    }

    @Test
    void testGetDocTypeById() {
        DocType docType = new DocType();
        when(docTypeManager.findById(anyLong())).thenReturn(docType);
        when(mapper.mapEntityToDtoDocType(docType)).thenReturn(new DocTypeResponseDTO(1L, "Document Type 1"));

        DocTypeResponseDTO result = docTypeService.getDocTypeById(1L);

        assertNotNull(result);
    }

    @Test
    void testAddDocType() {
        DocTypeRequestDTO requestDTO = new DocTypeRequestDTO(1L, "Document Type 1");

        docTypeService.addDocType(requestDTO);

        verify(docTypeManager, times(1)).create(any(DocType.class));
    }

    @Test
    void testUpdateDocTypeById() {
        Long docTypeId = 1L;
        DocTypeRequestDTO requestDTO = new DocTypeRequestDTO(1L, "Document Type 1");
        DocType docType = new DocType();
        when(docTypeManager.findById(docTypeId)).thenReturn(docType);

        docTypeService.updateDocTypeById(docTypeId, requestDTO);

        verify(docTypeManager, times(1)).update(any(DocType.class));
    }

    @Test
    void testDeleteDocTypeById() {
        Long docTypeId = 1L;
        DocType docType = new DocType();
        when(docTypeManager.findById(docTypeId)).thenReturn(docType);

        docTypeService.deleteDocTypeById(docTypeId);

        verify(docTypeManager, times(1)).delete(docTypeId);
    }
}
