package com.beehyv.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchCriteriaTest {

    @Test
    public void testValidConstructionAndGetters() {
        String key = "name";
        String value = "John";

        SearchCriteria criteria = new SearchCriteria(key, value);

        assertNotNull(criteria);
        assertEquals(key, criteria.getKey());
        assertEquals(value, criteria.getValue());
    }

    @Test
    public void testWithDifferentValueTypes() {
        SearchCriteria criteria1 = new SearchCriteria("age", 30);
        SearchCriteria criteria2 = new SearchCriteria("active", true);

        assertNotNull(criteria1);
        assertEquals("age", criteria1.getKey());
        assertEquals(30, criteria1.getValue());

        assertNotNull(criteria2);
        assertEquals("active", criteria2.getKey());
        assertEquals(true, criteria2.getValue());
    }

}
