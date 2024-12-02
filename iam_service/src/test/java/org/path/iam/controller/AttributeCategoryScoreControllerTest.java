package org.path.iam.controller;

import org.path.iam.dto.requestDto.AttributeCategoryScoreRequestDto;
import org.path.iam.dto.responseDto.AttributeCategoryScoreResponseDto;
import org.path.iam.service.AttributeCategoryScoreService;
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

class AttributeCategoryScoreControllerTest {

    @Mock
    private AttributeCategoryScoreService attributeCategoryScoreService;

    @InjectMocks
    private AttributeCategoryScoreController attributeCategoryScoreController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAttributeCategoryScore_ReturnsHttpStatusCreated_WhenValidRequestDtoProvided() {
        when(attributeCategoryScoreService.create(any(AttributeCategoryScoreRequestDto.class))).thenReturn(1L);

        ResponseEntity<Long> responseEntity = attributeCategoryScoreController.createAttributeCategoryScore(new AttributeCategoryScoreRequestDto());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody());
    }

    @Test
    void update_ReturnsHttpStatusOk_WhenValidIdAndRequestDtoProvided() {
        ResponseEntity<?> responseEntity = attributeCategoryScoreController.update(1L, new AttributeCategoryScoreRequestDto());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("successfully updated", responseEntity.getBody());
    }

    @Test
    void delete_ReturnsHttpStatusOk_WhenValidIdProvided() {
        ResponseEntity<?> responseEntity = attributeCategoryScoreController.delete(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getById_ReturnsAttributeCategoryScoreResponseDto_WhenValidIdProvided() {
        AttributeCategoryScoreResponseDto responseDto = new AttributeCategoryScoreResponseDto();
        when(attributeCategoryScoreService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<AttributeCategoryScoreResponseDto> responseEntity = attributeCategoryScoreController.getById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }
}
