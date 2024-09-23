package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StateLabTestAccessResponseDtoTest {

    private StateLabTestAccessResponseDto stateLabTestAccessResponseDto;

    @BeforeEach
    public void setUp() {
        stateLabTestAccessResponseDto = new StateLabTestAccessResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        StateLabTestAccessResponseDto dto = new StateLabTestAccessResponseDto();
        assertNull(dto.getStateResponseDto());
        assertNull(dto.getEntityType());
        assertNull(dto.getCategoryId());
        assertNull(dto.getLabSelectionAllowed());
        assertNull(dto.getBlockWorkflowForTest());
    }

    @Test
    public void testAllArgsConstructor() {
        StateResponseDto stateResponseDto = new StateResponseDto();
        EntityType entityType = EntityType.lot;
        Long categoryId = 123L;
        Boolean labSelectionAllowed = true;
        Boolean blockWorkflowForTest = false;

        StateLabTestAccessResponseDto dto = new StateLabTestAccessResponseDto(
                stateResponseDto, entityType, categoryId, labSelectionAllowed, blockWorkflowForTest);

        assertEquals(stateResponseDto, dto.getStateResponseDto());
        assertEquals(entityType, dto.getEntityType());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(labSelectionAllowed, dto.getLabSelectionAllowed());
        assertEquals(blockWorkflowForTest, dto.getBlockWorkflowForTest());
    }

    @Test
    public void testSettersAndGetters() {
        StateResponseDto stateResponseDto = new StateResponseDto();
        EntityType entityType = EntityType.lot;
        Long categoryId = 123L;
        Boolean labSelectionAllowed = true;
        Boolean blockWorkflowForTest = false;

        stateLabTestAccessResponseDto.setStateResponseDto(stateResponseDto);
        stateLabTestAccessResponseDto.setEntityType(entityType);
        stateLabTestAccessResponseDto.setCategoryId(categoryId);
        stateLabTestAccessResponseDto.setLabSelectionAllowed(labSelectionAllowed);
        stateLabTestAccessResponseDto.setBlockWorkflowForTest(blockWorkflowForTest);

        assertEquals(stateResponseDto, stateLabTestAccessResponseDto.getStateResponseDto());
        assertEquals(entityType, stateLabTestAccessResponseDto.getEntityType());
        assertEquals(categoryId, stateLabTestAccessResponseDto.getCategoryId());
        assertEquals(labSelectionAllowed, stateLabTestAccessResponseDto.getLabSelectionAllowed());
        assertEquals(blockWorkflowForTest, stateLabTestAccessResponseDto.getBlockWorkflowForTest());
    }
}
