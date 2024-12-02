package org.path.iam.service;

import org.path.iam.dto.requestDto.AttributeRequestDto;
import org.path.iam.dto.responseDto.AttributeResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.manager.AttributeManager;
import org.path.iam.model.Attribute;
import org.path.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttributeServiceTest {

    @Mock
    private AttributeManager attributeManager;

    @InjectMocks
    private AttributeService attributeService;

    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    @BeforeEach
    void setUp() {
        attributeService = new AttributeService(attributeManager);
    }

    @Test
    void testGetById() {
        Attribute attribute = new Attribute();
        attribute.setId(1L);
        when(attributeManager.findById(1L)).thenReturn(attribute);

        AttributeResponseDto responseDto = attributeService.getById(1L);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getId());
    }

    @Test
    void testFindAll() {
        Attribute attribute = new Attribute();
        attribute.setId(1L);
        List<Attribute> attributes = List.of(attribute);
        when(attributeManager.findAll(0, 10)).thenReturn(attributes);
        when(attributeManager.getCount(1, 0, 10)).thenReturn(1L);

        ListResponse<AttributeResponseDto> response = attributeService.findAll(0, 10);

        assertNotNull(response);

    }

    @Test
    void testCreate() {
        AttributeRequestDto requestDto = new AttributeRequestDto();
        requestDto.setName("Test Attribute");

        Attribute attribute = new Attribute();
        attribute.setId(1L);
        attribute.setName("Test Attribute");

        when(attributeManager.create(any(Attribute.class))).thenReturn(attribute);

        Long id = attributeService.create(requestDto);

        assertNotNull(id);
        assertEquals(1L, id);
    }

    @Test
    void testUpdate() {
        AttributeRequestDto requestDto = new AttributeRequestDto();
        requestDto.setId(1L);
        requestDto.setName("Updated Attribute");

        Attribute attribute = new Attribute();
        attribute.setId(1L);
        attribute.setName("Updated Attribute");

        when(attributeManager.update(any(Attribute.class))).thenReturn(attribute);

        assertDoesNotThrow(() -> attributeService.update(requestDto));
    }

    @Test
    void testDelete() {
        assertDoesNotThrow(() -> attributeService.delete(1L));
    }
}
