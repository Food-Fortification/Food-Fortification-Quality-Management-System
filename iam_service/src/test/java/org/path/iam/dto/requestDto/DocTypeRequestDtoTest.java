package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocTypeRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        DocTypeRequestDto dto = new DocTypeRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getStatusId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        DocTypeRequestDto dto = new DocTypeRequestDto(1L, "DocType 1");
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("DocType 1");
    }

    @Test
    void testSettersAndGetters() {
        DocTypeRequestDto dto = new DocTypeRequestDto();

        dto.setId(3L);
        dto.setName("DocType 2");
        dto.setStatusId(4L);

        assertThat(dto.getId()).isEqualTo(3L);
        assertThat(dto.getName()).isEqualTo("DocType 2");
        assertThat(dto.getStatusId()).isEqualTo(4L);
    }

    @Test
    void testToString() {
        DocTypeRequestDto dto = new DocTypeRequestDto(1L, "DocType 1");
        String dtoString = dto.toString();
    }
}
