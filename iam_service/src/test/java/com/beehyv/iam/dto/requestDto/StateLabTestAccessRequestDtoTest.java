package com.beehyv.iam.dto.requestDto;

import com.beehyv.iam.enums.EntityType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateLabTestAccessRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long stateId = 1L;
        EntityType entityType = EntityType.batch;
        Long categoryId = 2L;
        Boolean labSelectionAllowed = true;
        Boolean blockWorkflowForTest = false;

        StateLabTestAccessRequestDto requestDto = new StateLabTestAccessRequestDto(stateId, entityType, categoryId, labSelectionAllowed, blockWorkflowForTest);

        assertNotNull(requestDto);
        assertEquals(stateId, requestDto.getStateId());
        assertEquals(categoryId, requestDto.getCategoryId());
        assertEquals(labSelectionAllowed, requestDto.getLabSelectionAllowed());
        assertEquals(blockWorkflowForTest, requestDto.getBlockWorkflowForTest());
    }

    @Test
    public void testWithOptionalFields() {
        Long stateId = 2L;

        StateLabTestAccessRequestDto requestDto = new StateLabTestAccessRequestDto(stateId, null, null, true, null);

        assertNotNull(requestDto);
        assertEquals(stateId, requestDto.getStateId());
        assertNull(requestDto.getEntityType());
        assertNull(requestDto.getCategoryId());
        assertTrue(requestDto.getLabSelectionAllowed());
        assertNull(requestDto.getBlockWorkflowForTest());
    }
}
