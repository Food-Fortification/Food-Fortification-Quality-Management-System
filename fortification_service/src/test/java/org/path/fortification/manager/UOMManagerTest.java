package org.path.fortification.manager;

import org.path.fortification.dao.UOMDao;
import org.path.fortification.entity.UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UOMManagerTest {

    @Mock
    private UOMDao uomDao;

    private UOMManager uomManager;

    private List<Long> ids;
    private Long conversionFactorToKg;
    private String name;
    private UOM uom;

    @BeforeEach
    public void setUp() {
        uomManager = new UOMManager(uomDao);
        ids = Arrays.asList(1L, 2L);
        conversionFactorToKg = 1L;
        name = "uom1";
        uom = new UOM();

        when(uomDao.findAllBIds(ids)).thenReturn(Collections.singletonList(uom));
        when(uomDao.findByConversionFactor(conversionFactorToKg)).thenReturn(uom);
        when(uomDao.findByName(name)).thenReturn(uom);
    }

    @Test
    public void testFindAllByIds() {
        List<UOM> result = uomManager.findAllByIds(ids);
        assertEquals(Collections.singletonList(uom), result);
    }

    @Test
    public void testFindByConversionFactor() {
        UOM result = uomManager.findByConversionFactor(conversionFactorToKg);
        assertEquals(uom, result);
    }

    @Test
    public void testFindByName() {
        UOM result = uomManager.findByName(name);
        assertEquals(uom, result);
    }
}