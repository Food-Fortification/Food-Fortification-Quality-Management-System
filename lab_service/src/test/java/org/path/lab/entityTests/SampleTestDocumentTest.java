package org.path.lab.entityTests;

import org.path.lab.entity.SampleTestDocument;
import org.path.lab.entity.CategoryDocumentRequirement;
import org.path.lab.entity.LabSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SampleTestDocumentTest {

    @Mock
    private CategoryDocumentRequirement categoryDocumentRequirement;

    @Mock
    private LabSample labSample;

    @InjectMocks
    private SampleTestDocument sampleTestDocument;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Document";
        String pathValue = "Test Path";

        when(categoryDocumentRequirement.getId()).thenReturn(1L);
        when(labSample.getId()).thenReturn(1L);

        sampleTestDocument.setId(idValue);
        sampleTestDocument.setName(nameValue);
        sampleTestDocument.setCategoryDocumentRequirement(categoryDocumentRequirement);
        sampleTestDocument.setPath(pathValue);
        sampleTestDocument.setLabSample(labSample);

        assertEquals(idValue, sampleTestDocument.getId());
        assertEquals(nameValue, sampleTestDocument.getName());
        assertEquals(1L, sampleTestDocument.getCategoryDocumentRequirement().getId());
        assertEquals(pathValue, sampleTestDocument.getPath());
        assertEquals(1L, sampleTestDocument.getLabSample().getId());
    }
}