package com.beehyv.fortification.dtoTests.external;

import com.beehyv.fortification.dto.external.BatchExternalRequestDto;
import com.beehyv.fortification.dto.external.ExternalLabTest;
import com.beehyv.fortification.dto.external.MixMappingExternalRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BatchExternalRequestDtoTest {

    private BatchExternalRequestDto batchExternalRequestDto;

    @BeforeEach
    public void setUp() {
        batchExternalRequestDto = new BatchExternalRequestDto();
    }

    @Test
    public void testAllFields() {
        Date date = new Date();
        String id = "transId";
        String sourceExternalManufacturerId = "sourceId";
        String targetExternalManufacturerId = "targetId";
        Set<MixMappingExternalRequestDto> mixes = new HashSet<>();
        Double totalQuantity = 100.0;
        Double acknowledgedQuantity = 80.0;
        String comments = "Test comments";
        String batchName = "Test batch";
        Set<ExternalLabTest> labTests = new HashSet<>();

        batchExternalRequestDto.setDateOfManufacture(date);
        batchExternalRequestDto.setDateOfAcceptance(date);
        batchExternalRequestDto.setDestinationTransId(id);
        batchExternalRequestDto.setSourceExternalManufacturerId(sourceExternalManufacturerId);
        batchExternalRequestDto.setTargetExternalManufacturerId(targetExternalManufacturerId);
        batchExternalRequestDto.setMixes(mixes);
        batchExternalRequestDto.setTotalQuantity(totalQuantity);
        batchExternalRequestDto.setAcknowledgedQuantity(acknowledgedQuantity);
        batchExternalRequestDto.setComments(comments);
        batchExternalRequestDto.setBatchName(batchName);
        batchExternalRequestDto.setLabTests(labTests);

        assertEquals(date, batchExternalRequestDto.getDateOfManufacture());
        assertEquals(date, batchExternalRequestDto.getDateOfAcceptance());
        assertEquals(id, batchExternalRequestDto.getDestinationTransId());
        assertEquals(sourceExternalManufacturerId, batchExternalRequestDto.getSourceExternalManufacturerId());
        assertEquals(targetExternalManufacturerId, batchExternalRequestDto.getTargetExternalManufacturerId());
        assertEquals(mixes, batchExternalRequestDto.getMixes());
        assertEquals(totalQuantity, batchExternalRequestDto.getTotalQuantity());
        assertEquals(acknowledgedQuantity, batchExternalRequestDto.getAcknowledgedQuantity());
        assertEquals(comments, batchExternalRequestDto.getComments());
        assertEquals(batchName, batchExternalRequestDto.getBatchName());
        assertEquals(labTests, batchExternalRequestDto.getLabTests());
    }

}