package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListResponseTest {

    @Test
    void testConstructorWithDataAndMapper() {
        // Given
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        Function<Integer, String> mapper = Object::toString;
        Long count = 3L;

        // When
        ListResponse<String> listResponse = new ListResponse<>(integerList, mapper, count);

        // Then
        assertEquals(count, listResponse.getCount());
        assertEquals(3, listResponse.getData().size());
        assertEquals("1", listResponse.getData().get(0));
        assertEquals("2", listResponse.getData().get(1));
        assertEquals("3", listResponse.getData().get(2));
    }

    @Test
    void testFromMethod() {
        // Given
        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");
        Long count = 3L;

        // When
        ListResponse<String> listResponse = ListResponse.from(stringList, Function.identity(), count);

        // Then
        assertEquals(count, listResponse.getCount());
        assertEquals(3, listResponse.getData().size());
        assertEquals("one", listResponse.getData().get(0));
        assertEquals("two", listResponse.getData().get(1));
        assertEquals("three", listResponse.getData().get(2));
    }

    @Test
    void testConstructorWithData() {
        // Given
        List<String> stringList = new ArrayList<>();
        stringList.add("A");
        stringList.add("B");
        stringList.add("C");
        Long count = 3L;

        // When
        ListResponse<String> listResponse = new ListResponse<>(stringList, count);

        // Then
        assertEquals(count, listResponse.getCount());
        assertEquals(3, listResponse.getData().size());
        assertEquals("A", listResponse.getData().get(0));
        assertEquals("B", listResponse.getData().get(1));
        assertEquals("C", listResponse.getData().get(2));
    }
}
