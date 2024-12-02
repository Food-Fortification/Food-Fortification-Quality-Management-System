package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchListRequestTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        searchCriteriaList.add(new SearchCriteria("key1", "value1"));
        searchCriteriaList.add(new SearchCriteria("key2", "value2"));

        // When
        SearchListRequest request = new SearchListRequest(searchCriteriaList);

        // Then
        assertEquals(searchCriteriaList, request.getSearchCriteriaList());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        SearchListRequest request = new SearchListRequest();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        searchCriteriaList.add(new SearchCriteria("key1", "value1"));
        searchCriteriaList.add(new SearchCriteria("key2", "value2"));

        // When
        request.setSearchCriteriaList(searchCriteriaList);

        // Then
        assertEquals(searchCriteriaList, request.getSearchCriteriaList());
    }

    @Test
    void toString_ReturnsExpectedString() {
        // Given
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        searchCriteriaList.add(new SearchCriteria("key1", "value1"));
        searchCriteriaList.add(new SearchCriteria("key2", "value2"));
        SearchListRequest request = new SearchListRequest(searchCriteriaList);

        // When
        String toStringResult = request.toString();

        // Then
        assertTrue(toStringResult.contains("searchCriteriaList=" + searchCriteriaList));
    }
}
