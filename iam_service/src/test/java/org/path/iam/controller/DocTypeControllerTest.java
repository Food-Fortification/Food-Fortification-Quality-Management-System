package org.path.iam.controller;

import org.path.iam.dto.requestDto.DocTypeRequestDto;
import org.path.iam.dto.responseDto.DocTypeResponseDto;
import org.path.iam.service.DocTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class DocTypeControllerTest {

    @Mock
    private DocTypeService docTypeService;

    @InjectMocks
    private DocTypeController docTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        DocTypeRequestDto dto = new DocTypeRequestDto();
        doNothing().when(docTypeService).create(any(DocTypeRequestDto.class));

        ResponseEntity<?> response = docTypeController.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully Created", response.getBody());
    }

    @Test
    void testGetById() {
        DocTypeResponseDto docType = new DocTypeResponseDto();
        docType.setId(1L);
        when(docTypeService.getById(anyLong())).thenReturn(docType);

        ResponseEntity<?> response = docTypeController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(docType, response.getBody());
    }


    @Test
    void testUpdate() {
        DocTypeRequestDto dto = new DocTypeRequestDto();
        doNothing().when(docTypeService).update(any(DocTypeRequestDto.class));

        ResponseEntity<?> response = docTypeController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully updated", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(docTypeService).delete(anyLong());

        ResponseEntity<?> response = docTypeController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted", response.getBody());
    }
}
