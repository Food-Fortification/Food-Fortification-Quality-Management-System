package com.beehyv.iam.manager;

import com.beehyv.iam.dao.DocTypeDao;
import com.beehyv.iam.model.DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocTypeManagerTest {

    @Mock
    private DocTypeDao docTypeDao;

    @InjectMocks
    private DocTypeManager docTypeManager;

    @BeforeEach
    void setUp() {
        // You can initialize any mock behavior here
    }

    @Test
    void testFindAll() {
        // Prepare test data
        List<DocType> expectedDocTypes = List.of(new DocType(), new DocType());
        when(docTypeDao.findAll(anyInt(), anyInt())).thenReturn(expectedDocTypes);

        // Call the method to be tested
        List<DocType> actualDocTypes = docTypeManager.findAll(1, 1);

        // Verify the result
        assertEquals(expectedDocTypes, actualDocTypes);
    }

    // Add more test cases for other methods as needed
}
