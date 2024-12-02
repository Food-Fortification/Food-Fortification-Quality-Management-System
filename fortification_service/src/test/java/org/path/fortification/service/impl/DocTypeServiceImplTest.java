package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.DocTypeRequestDto;
import org.path.fortification.dto.responseDto.DocTypeResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.entity.DocType;
import org.path.fortification.entity.Status;
import org.path.fortification.manager.DocTypeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DocTypeServiceImplTest {

    @Mock
    private DocTypeManager manager;

    @InjectMocks
    private DocTypeServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateDocType() {
        DocTypeRequestDto requestDto = new DocTypeRequestDto();
        DocType entity = new DocType();

        when(manager.create(any(DocType.class))).thenReturn(entity);

        service.createDocType(requestDto);

        verify(manager, times(1)).create(any(DocType.class));
    }


    @Test
    void testGetDocTypeById() {
        long id = 1L;
        DocType expectedEntity = new DocType();
        when(manager.findById(id)).thenReturn(expectedEntity);


        DocTypeResponseDto result = service.getDocTypeById(id);

        assertNotNull(result);
    }

    @Test
    void testGetAllDocTypes() {
        int pageNumber = 1;
        int pageSize = 10;
        List<DocType> entities = Collections.singletonList(new DocType());
        long count = 1L;
        when(manager.findAll(pageNumber, pageSize)).thenReturn(entities);
        when(manager.getCount(entities.size(), pageNumber, pageSize)).thenReturn(count);


        ListResponse<DocTypeResponseDto> result = service.getAllDocTypes(pageNumber, pageSize);

        assertNotNull(result);
    }

    @Test
    void testUpdateDocType() {
        DocTypeRequestDto requestDto = new DocTypeRequestDto();
        requestDto.setId(1L);
        requestDto.setName("New Name");
        requestDto.setStatusId(1L);
        DocType existingDocType = new DocType();
        existingDocType.setId(1L);
        Status status = new Status();
        status.setId(1L);
        existingDocType.setStatus(status);
        when(manager.findById(requestDto.getId())).thenReturn(existingDocType);
        when(manager.update(any(DocType.class))).thenReturn(existingDocType);

        service.updateDocType(requestDto);

        verify(manager, times(1)).update(any(DocType.class));
    }


    @Test
    void testDeleteDocType() {
        long id = 1L;
        doNothing().when(manager).delete(id);

        service.deleteDocType(id);

        verify(manager, times(1)).delete(id);
    }
}
