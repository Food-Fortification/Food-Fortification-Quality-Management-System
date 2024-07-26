package com.beehyv.iam.manager;

import com.beehyv.iam.dao.VillageDao;
import com.beehyv.iam.model.Village;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VillageManagerTest {

    @Mock
    private VillageDao villageDao;

    @InjectMocks
    private VillageManager villageManager;

    public VillageManagerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllByDistrictId() {
        // Prepare test data
        Long districtId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Village> expectedVillages = Collections.singletonList(new Village()); // Create a dummy list of Village objects

        // Mock behavior of VillageDao
        when(villageDao.findAllByDistrictId(districtId, pageNumber, pageSize)).thenReturn(expectedVillages);

        // Call method under test
        List<Village> actualVillages = villageManager.findAllByDistrictId(districtId, pageNumber, pageSize);

        // Verify
        assertEquals(expectedVillages, actualVillages);
        verify(villageDao, times(1)).findAllByDistrictId(districtId, pageNumber, pageSize);
    }

    @Test
    public void testFindByDistrictNameAndVillageName() {
        String villageName = "TestVillage";
        String districtName = "TestDistrict";
        Village expectedVillage = new Village();
        when(villageDao.findByDistrictNameAndVillageName(villageName, districtName)).thenReturn(expectedVillage);

        Village actualVillage = villageManager.findByDistrictNameAndVillageName(villageName, districtName);

        assertEquals(expectedVillage, actualVillage);
    }

    @Test
    public void testFindByNameAndDistrictId() {
        String villageName = "TestVillage";
        Long districtId = 1L;
        Village expectedVillage = new Village();
        when(villageDao.findByNameAndDistrictId(villageName, districtId)).thenReturn(expectedVillage);

        Village actualVillage = villageManager.findByNameAndDistrictId(villageName, districtId);

        assertEquals(expectedVillage, actualVillage);
    }
    // Add similar test methods for other methods in VillageManager
}
