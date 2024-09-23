package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabDashboardResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "LabName";
        String state = "State";
        String district = "District";
        Long samplesReceived = 100L;
        Long samplesRejected = 10L;
        Long samplesInLab = 50L;
        Long samplesToReceive = 20L;
        Long testsDone = 30L;
        String nablCertificate = "Certificate";

        // When
        LabDashboardResponseDto labDashboardResponseDto = new LabDashboardResponseDto(id, name, state, district,
                samplesReceived, samplesRejected, samplesInLab, samplesToReceive, testsDone, nablCertificate);

        // Then
        assertNotNull(labDashboardResponseDto);
        assertEquals(id, labDashboardResponseDto.getId());
        assertEquals(name, labDashboardResponseDto.getName());
        assertEquals(state, labDashboardResponseDto.getState());
        assertEquals(district, labDashboardResponseDto.getDistrict());
        assertEquals(samplesReceived, labDashboardResponseDto.getSamplesReceived());
        assertEquals(samplesRejected, labDashboardResponseDto.getSamplesRejected());
        assertEquals(samplesInLab, labDashboardResponseDto.getSamplesInLab());
        assertEquals(samplesToReceive, labDashboardResponseDto.getSamplesToReceive());
        assertEquals(testsDone, labDashboardResponseDto.getTestsDone());
        assertEquals(nablCertificate, labDashboardResponseDto.getNablCertificate());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabDashboardResponseDto labDashboardResponseDto = new LabDashboardResponseDto();

        // Then
        assertNotNull(labDashboardResponseDto);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabDashboardResponseDto labDashboardResponseDto = new LabDashboardResponseDto();
        Long id = 1L;
        String name = "LabName";
        String state = "State";
        String district = "District";
        Long samplesReceived = 100L;
        Long samplesRejected = 10L;
        Long samplesInLab = 50L;
        Long samplesToReceive = 20L;
        Long testsDone = 30L;
        String nablCertificate = "Certificate";

        // When
        labDashboardResponseDto.setId(id);
        labDashboardResponseDto.setName(name);
        labDashboardResponseDto.setState(state);
        labDashboardResponseDto.setDistrict(district);
        labDashboardResponseDto.setSamplesReceived(samplesReceived);
        labDashboardResponseDto.setSamplesRejected(samplesRejected);
        labDashboardResponseDto.setSamplesInLab(samplesInLab);
        labDashboardResponseDto.setSamplesToReceive(samplesToReceive);
        labDashboardResponseDto.setTestsDone(testsDone);
        labDashboardResponseDto.setNablCertificate(nablCertificate);

        // Then
        assertEquals(id, labDashboardResponseDto.getId());
        assertEquals(name, labDashboardResponseDto.getName());
        assertEquals(state, labDashboardResponseDto.getState());
        assertEquals(district, labDashboardResponseDto.getDistrict());
        assertEquals(samplesReceived, labDashboardResponseDto.getSamplesReceived());
        assertEquals(samplesRejected, labDashboardResponseDto.getSamplesRejected());
        assertEquals(samplesInLab, labDashboardResponseDto.getSamplesInLab());
        assertEquals(samplesToReceive, labDashboardResponseDto.getSamplesToReceive());
        assertEquals(testsDone, labDashboardResponseDto.getTestsDone());
        assertEquals(nablCertificate, labDashboardResponseDto.getNablCertificate());
    }

    @Test
    void testToString() {
        // Given
        LabDashboardResponseDto labDashboardResponseDto = new LabDashboardResponseDto(1L, "LabName", "State",
                "District", 100L, 10L, 50L, 20L, 30L, "Certificate");

        // When
        String result = labDashboardResponseDto.toString();

        // Then
        assertNotNull(result);
        assert (result.contains("LabDashboardResponseDto"));
        assert (result.contains("id=1"));
        assert (result.contains("name=LabName"));
        assert (result.contains("state=State"));
        assert (result.contains("district=District"));
        assert (result.contains("samplesReceived=100"));
        assert (result.contains("samplesRejected=10"));
        assert (result.contains("samplesInLab=50"));
        assert (result.contains("samplesToReceive=20"));
        assert (result.contains("testsDone=30"));
        assert (result.contains("nablCertificate=Certificate"));
    }
}