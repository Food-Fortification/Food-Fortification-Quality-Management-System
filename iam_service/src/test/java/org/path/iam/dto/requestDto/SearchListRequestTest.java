package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchListRequestTest {

    @Test
    public void testValidConstructionAndGetters() {
        List<SearchCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new SearchCriteria("name", "John"));
        criteriaList.add(new SearchCriteria("age", 30));

        SearchListRequest request = new SearchListRequest(criteriaList);

        assertNotNull(request);
        assertEquals(criteriaList, request.getSearchCriteriaList());
    }

    @Test
    public void testWithEmptyList() {
        SearchListRequest request = new SearchListRequest(new ArrayList<>());

        assertNotNull(request);
        assertTrue(request.getSearchCriteriaList().isEmpty());
    }


}
