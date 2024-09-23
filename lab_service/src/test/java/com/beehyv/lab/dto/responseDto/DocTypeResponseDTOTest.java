package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocTypeResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "DocumentType";

        // When
        DocTypeResponseDTO docTypeResponseDTO = new DocTypeResponseDTO(id, name);

        // Then
        assertNotNull(docTypeResponseDTO);
        assertEquals(id, docTypeResponseDTO.getId());
        assertEquals(name, docTypeResponseDTO.getName());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        DocTypeResponseDTO docTypeResponseDTO = new DocTypeResponseDTO(1L,"m");

        // Then
        assertNotNull(docTypeResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        DocTypeResponseDTO docTypeResponseDTO = new DocTypeResponseDTO(1L,"m");
        Long id = 1L;
        String name = "DocumentType";

        // When
        docTypeResponseDTO.setId(id);
        docTypeResponseDTO.setName(name);

        // Then
        assertEquals(id, docTypeResponseDTO.getId());
        assertEquals(name, docTypeResponseDTO.getName());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        String name = "DocumentType";
        DocTypeResponseDTO docTypeResponseDTO = new DocTypeResponseDTO(id, name);

        // When
        String result = docTypeResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("DocTypeResponseDTO"));

    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Long id = 1L;
        String name = "DocumentType";
        DocTypeResponseDTO docTypeResponseDTO1 = new DocTypeResponseDTO(id, name);
        DocTypeResponseDTO docTypeResponseDTO2 = new DocTypeResponseDTO(id, name);


    }
}
