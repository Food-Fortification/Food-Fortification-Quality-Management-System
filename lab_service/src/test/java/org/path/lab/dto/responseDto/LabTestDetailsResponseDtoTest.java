package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabTestDetailsResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        // Given
        Long batchId = 1L;
        String labName = "Lab";
        String performedBy = "Tester";
        String testName = "Test";

        // When
        LabTestDetailsResponseDto labTestDetailsResponseDto = new LabTestDetailsResponseDto();
        labTestDetailsResponseDto.setBatchId(batchId);
        labTestDetailsResponseDto.setLabName(labName);
        labTestDetailsResponseDto.setPerformedBy(performedBy);
        labTestDetailsResponseDto.setTestName(testName);

        // Then
        assertEquals(batchId, labTestDetailsResponseDto.getBatchId());
        assertEquals(labName, labTestDetailsResponseDto.getLabName());
        assertEquals(performedBy, labTestDetailsResponseDto.getPerformedBy());
        assertEquals(testName, labTestDetailsResponseDto.getTestName());
    }
}
