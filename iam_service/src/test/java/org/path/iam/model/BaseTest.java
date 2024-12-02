package org.path.iam.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Entity
class BaseEntity extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Default constructor required by JPA
    public BaseEntity() {
    }
}

public class BaseTest {

    private BaseEntity baseEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        baseEntity = new BaseEntity();
    }

    @Test
    public void testPrePersist() {
        // Simulate the @PrePersist lifecycle event
        baseEntity.initEntity();

        assertNotNull(baseEntity.getUuid());
        assertNotNull(UUID.fromString(baseEntity.getUuid())); // Check if it's a valid UUID
        assertFalse(baseEntity.getIsDeleted());
    }

    @Test
    public void testPreRemove() {
        // Simulate the @PreRemove lifecycle event
        baseEntity.deleteEntity();

        assertTrue(baseEntity.getIsDeleted());
    }

    @Test
    public void testAuditingFields() {
        // Set auditing fields
        LocalDateTime now = LocalDateTime.now();
        baseEntity.setCreatedBy("testUser");
        baseEntity.setCreatedDate(now);
        baseEntity.setModifiedBy("testModifier");
        baseEntity.setModifiedDate(now);

        assertEquals("testUser", baseEntity.getCreatedBy());
        assertEquals(now, baseEntity.getCreatedDate());
        assertEquals("testModifier", baseEntity.getModifiedBy());
        assertEquals(now, baseEntity.getModifiedDate());
    }

    @Test
    public void testStatusField() {
        Status status = new Status();
        status.setId(1L);
        status.setName("Active");

        baseEntity.setStatus(status);

        assertNotNull(baseEntity.getStatus());
        assertEquals("Active", baseEntity.getStatus().getName());
    }
}
