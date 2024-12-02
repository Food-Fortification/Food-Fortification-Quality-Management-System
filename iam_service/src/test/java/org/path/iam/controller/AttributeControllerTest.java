package org.path.iam.controller;

import org.path.iam.dto.requestDto.AttributeRequestDto;
import org.path.iam.dto.responseDto.AttributeResponseDto;
import org.path.iam.service.AttributeService;
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
import static org.mockito.Mockito.when;

class AttributeControllerTest {

    @Mock
    private AttributeService attributeService;

    @InjectMocks
    private AttributeController attributeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_ReturnsAttributeResponseDto_WhenValidIdProvided() {
        AttributeResponseDto responseDto = new AttributeResponseDto();
        when(attributeService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<AttributeResponseDto> responseEntity = attributeController.getById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }


    @Test
    void create_ReturnsHttpStatusCreated_WhenValidRequestDtoProvided() {
        when(attributeService.create(any(AttributeRequestDto.class))).thenReturn(1L);

        ResponseEntity<Long> responseEntity = attributeController.create(new AttributeRequestDto());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody());
    }

    @Test
    void update_ReturnsHttpStatusOk_WhenValidIdAndRequestDtoProvided() {
        ResponseEntity<?> responseEntity = attributeController.update(1L, new AttributeRequestDto());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully Updated", responseEntity.getBody());
    }

    @Test
    void delete_ReturnsHttpStatusOk_WhenValidIdProvided() {
        ResponseEntity<?> responseEntity = attributeController.delete(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
