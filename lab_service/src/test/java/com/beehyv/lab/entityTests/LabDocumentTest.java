package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.LabDocument;
import com.beehyv.lab.entity.CategoryDocumentRequirement;
import com.beehyv.lab.entity.Lab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LabDocumentTest {

    @Mock
    private CategoryDocumentRequirement categoryDoc;

    @Mock
    private Lab lab;

    @InjectMocks
    private LabDocument labDocument;

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
        when(lab.getId()).thenReturn(1L);

        labDocument.setId(idValue);
        labDocument.setName(nameValue);
        labDocument.setCategoryDoc(categoryDoc);
        labDocument.setPath(pathValue);
        labDocument.setLab(lab);

        assertEquals(idValue, labDocument.getId());
        assertEquals(nameValue, labDocument.getName());
        assertEquals(1L, labDocument.getCategoryDoc().getId());
        assertEquals(pathValue, labDocument.getPath());
        assertEquals(1L, labDocument.getLab().getId());
    }
}