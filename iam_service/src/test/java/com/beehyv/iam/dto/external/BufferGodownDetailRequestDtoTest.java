package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BufferGodownDetailRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        BufferGodownDetailRequestDto dto = new BufferGodownDetailRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getBufferGodownId()).isNull();
        assertThat(dto.getAllotedQty()).isNull();
        assertThat(dto.getMoDestinationId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        BufferGodownDetailRequestDto dto = new BufferGodownDetailRequestDto(1L, "BG123", 100.0, "MD456");
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBufferGodownId()).isEqualTo("BG123");
        assertThat(dto.getAllotedQty()).isEqualTo(100.0);
        assertThat(dto.getMoDestinationId()).isEqualTo("MD456");
    }

    @Test
    void testSettersAndGetters() {
        BufferGodownDetailRequestDto dto = new BufferGodownDetailRequestDto();
        dto.setId(1L);
        dto.setBufferGodownId("BG123");
        dto.setAllotedQty(100.0);
        dto.setMoDestinationId("MD456");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBufferGodownId()).isEqualTo("BG123");
        assertThat(dto.getAllotedQty()).isEqualTo(100.0);
        assertThat(dto.getMoDestinationId()).isEqualTo("MD456");
    }

    @Test
    void testToString() {
        BufferGodownDetailRequestDto dto = new BufferGodownDetailRequestDto(1L, "BG123", 100.0, "MD456");
        String expectedString = "BufferGodownDetailRequestDto(id=1, bufferGodownId=BG123, allotedQty=100.0, moDestinationId=MD456)";
        assertThat(dto.toString()).isEqualTo(expectedString);
    }
}
