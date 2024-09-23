package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchCriteriaTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        String key = "testKey";
        Object value = "testValue";

        // When
        SearchCriteria criteria = new SearchCriteria(key, value);

        // Then
        assertEquals(key, criteria.getKey());
        assertEquals(value, criteria.getValue());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        SearchCriteria criteria = new SearchCriteria();
        String key = "testKey";
        Object value = "testValue";

        // When
        criteria.setKey(key);
        criteria.setValue(value);

        // Then
        assertEquals(key, criteria.getKey());
        assertEquals(value, criteria.getValue());
    }
}
