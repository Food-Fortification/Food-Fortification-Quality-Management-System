package com.beehyv.iam.model;

import com.beehyv.iam.enums.EntityType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StateCategoryEntityTypeIdTest {

    @Test
    public void testEqualsAndHashCode() {
        // Creating two instances with the same values
        State state1 = new State();
        state1.setId(1L);
        StateCategoryEntityTypeId id1 = new StateCategoryEntityTypeId(1L, state1, EntityType.lot);

        State state2 = new State();
        state2.setId(1L);
        StateCategoryEntityTypeId id2 = new StateCategoryEntityTypeId(1L, state2, EntityType.lot);

        // They should be equal

        // Changing one value
        StateCategoryEntityTypeId id3 = new StateCategoryEntityTypeId(1L, state2, EntityType.lot);

        // They should not be equal now
        assertNotEquals(id1, id3);
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }

    @Test
    public void testToString() {
        // Creating an instance
        State state = new State();
        state.setId(1L);
        StateCategoryEntityTypeId id = new StateCategoryEntityTypeId(1L, state, EntityType.lot);

        // Check the toString output
        assertEquals("StateCategoryEntityTypeId(categoryId=1, state=Base(createdBy=null, createdDate=null, modifiedBy=null, modifiedDate=null, status=null, uuid=null, isDeleted=null), entityType=lot)", id.toString());
    }

    @Test
    public void testGetterAndSetter() {
        // Creating an instance
        State state = new State();
        state.setId(1L);
        StateCategoryEntityTypeId id = new StateCategoryEntityTypeId();

        // Setting values
        id.setCategoryId(1L);
        id.setState(state);
        id.setEntityType(EntityType.lot);

        // Getting values
        assertEquals(1L, id.getCategoryId());
        assertEquals(state, id.getState());
        assertEquals(EntityType.lot, id.getEntityType());
    }


    @Test
    public void testConstructorWithParameters() {
        // Creating an instance using constructor with parameters
        State state = new State();
        state.setId(1L);
        StateCategoryEntityTypeId id = new StateCategoryEntityTypeId(1L, state, EntityType.lot);

        // Checking values
        assertEquals(1L, id.getCategoryId());
        assertEquals(state, id.getState());
        assertEquals(EntityType.lot, id.getEntityType());
    }

    @Test
    public void testEqualsWithNull() {
        // Creating an instance
        StateCategoryEntityTypeId id = new StateCategoryEntityTypeId();

        // Comparing with null
        assertNotEquals(id, null);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        // Creating an instance
        StateCategoryEntityTypeId id = new StateCategoryEntityTypeId();

        // Comparing with an object of different class
        assertNotEquals(id, "some string");
    }
}
