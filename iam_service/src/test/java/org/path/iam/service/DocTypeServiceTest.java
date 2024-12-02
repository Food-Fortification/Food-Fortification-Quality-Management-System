package org.path.iam.service;

import org.path.iam.dto.requestDto.DocTypeRequestDto;
import org.path.iam.dto.responseDto.DocTypeResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.manager.DocTypeManager;
import org.path.iam.model.DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class DocTypeServiceTest {

    @Mock
    private DocTypeManager docTypeManager;

    private DocTypeService docTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        docTypeService = new DocTypeService(docTypeManager);
    }

    @Test
    void testCreate() {
        DocTypeRequestDto requestDto = new DocTypeRequestDto();
        docTypeService.create(requestDto);
        verify(docTypeManager).create(any(DocType.class));
    }

    @Test
    void testGetAllDocType() {
        int pageNumber = 1;
        int pageSize = 10;
        List<DocType> docTypes = Arrays.asList(new DocType(), new DocType());
        when(docTypeManager.findAll(pageNumber, pageSize)).thenReturn(docTypes);
        when(docTypeManager.getCount(docTypes.size(), pageNumber, pageSize)).thenReturn((long) docTypes.size());

        ListResponse<DocTypeResponseDto> response = docTypeService.getAllDocTpe(pageNumber, pageSize);

        assertEquals(docTypes.size(), response.getData().size());
    }

    @Test
    void testGetById() {
        Long docTypeId = 1L;
        DocType docType = new DocType();
        when(docTypeManager.findById(docTypeId)).thenReturn(docType);

        DocTypeResponseDto responseDto = docTypeService.getById(docTypeId);


        assertNull(responseDto.getStatus());
        assertNull(responseDto.getUuid());

    }


    @Test
    void testUpdate() {
        DocTypeRequestDto requestDto = new DocTypeRequestDto();
        docTypeService.update(requestDto);
        verify(docTypeManager).update(any(DocType.class));
    }

    @Test
    void testDelete() {
        Long docTypeId = 1L;
        docTypeService.delete(docTypeId);
        verify(docTypeManager).delete(docTypeId);
    }
}
