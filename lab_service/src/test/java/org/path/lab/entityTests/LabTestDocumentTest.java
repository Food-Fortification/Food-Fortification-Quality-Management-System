package org.path.lab.entityTests;

import org.path.lab.entity.LabTestDocument;
import org.path.lab.entity.CategoryDocumentRequirement;
import org.path.lab.entity.LabTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LabTestDocumentTest {

    @Mock
    private CategoryDocumentRequirement categoryDoc;

    @Mock
    private LabTest labTest;

    @InjectMocks
    private LabTestDocument labTestDocument;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Document";
        String pathValue = "Test Path";

        when(categoryDoc.getId()).thenReturn(1L);
        when(labTest.getId()).thenReturn(1L);

        labTestDocument.setId(idValue);
        labTestDocument.setName(nameValue);
        labTestDocument.setCategoryDocumentRequirement(categoryDoc);
        labTestDocument.setPath(pathValue);
        labTestDocument.setLabTest(labTest);

        assertEquals(idValue, labTestDocument.getId());
        assertEquals(nameValue, labTestDocument.getName());
        assertEquals(1L, labTestDocument.getCategoryDocumentRequirement().getId());
        assertEquals(pathValue, labTestDocument.getPath());
        assertEquals(1L, labTestDocument.getLabTest().getId());
    }
}