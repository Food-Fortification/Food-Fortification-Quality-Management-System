package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.BaseDao;
import com.beehyv.fortification.entity.Base;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class BaseManagerTest {

    BaseImp base = new BaseImp();
    @Mock
    private BaseDao<Base> baseDao;
    @InjectMocks
    private BaseManagerImp baseManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {

        when(baseDao.create(base)).thenReturn(base);
        assertEquals(base, baseManager.create(base));
    }

    @Test
    public void testUpdate() {

        when(baseDao.update(base)).thenReturn(base);
        assertEquals(base, baseManager.update(base));
    }

    @Test
    public void testFindById() {

        when(baseDao.findById(1L)).thenReturn(base);
        assertEquals(base, baseManager.findById(1L));
    }

    @Test
    public void testGetCount() {
        when(baseDao.getCount()).thenReturn(10L);
        assertEquals(10L, baseManager.getCount(10, 1, 10));
    }

    @Test
    public void testFindAll() {
        List<Base> bases = Arrays.asList(new BaseImp(), new BaseImp());
        when(baseDao.findAll(1, 10)).thenReturn(bases);
        assertEquals(bases, baseManager.findAll(1, 10));
    }

    @Test
    public void testDelete() {
        doNothing().when(baseDao).delete(1L);
        baseManager.delete(1L);
        verify(baseDao, times(1)).delete(1L);
    }
}

class BaseImp extends Base {
}

class BaseManagerImp extends BaseManager<BaseImp, BaseDao<BaseImp>> {
    public BaseManagerImp(BaseDao<BaseImp> dao) {
        super(dao);
    }
}