package com.beehyv.lab.dto.responseDto;

import com.beehyv.lab.dto.responseDto.LabSampleResultResponseDto;
import com.beehyv.lab.enums.LabSampleResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabSampleResultResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        // Given
        LabSampleResult labSampleResult = LabSampleResult. TEST_PASSED;
        Boolean isExternalTest = true;
        Long labId = 1L;

        // When
        LabSampleResultResponseDto labSampleResultResponseDto = new LabSampleResultResponseDto();
        labSampleResultResponseDto.setLabSampleResult(labSampleResult);
        labSampleResultResponseDto.setIsExternalTest(isExternalTest);
        labSampleResultResponseDto.setLabId(labId);

        // Then
        assertEquals(labSampleResult, labSampleResultResponseDto.getLabSampleResult());
        assertEquals(isExternalTest, labSampleResultResponseDto.getIsExternalTest());
        assertEquals(labId, labSampleResultResponseDto.getLabId());
    }
}
