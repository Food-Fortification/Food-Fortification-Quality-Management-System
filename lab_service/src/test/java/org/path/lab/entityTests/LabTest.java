package org.path.lab.entityTests;

import org.path.lab.entity.Lab;
import org.path.lab.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LabTest {

    @Mock
    private Address address;

    @InjectMocks
    private Lab lab;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Lab";
        String descriptionValue = "Test Description";
        String certificateNoValue = "Test Certificate";
        Boolean isCertificateValidValue = true;

        when(address.getId()).thenReturn(1L);

        lab.setId(idValue);
        lab.setName(nameValue);
        lab.setDescription(descriptionValue);
        lab.setCertificateNo(certificateNoValue);
        lab.setAddress(address);

        assertEquals(idValue, lab.getId());
        assertEquals(nameValue, lab.getName());
        assertEquals(descriptionValue, lab.getDescription());
        assertEquals(certificateNoValue, lab.getCertificateNo());
        assertEquals(1L, lab.getAddress().getId());
    }
}