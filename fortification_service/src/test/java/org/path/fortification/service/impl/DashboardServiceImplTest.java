package org.path.fortification.service.impl;

import org.path.fortification.dao.LotStateGeoDao;
import org.path.fortification.dto.iam.*;
import org.path.fortification.dto.iam.StateResponseDto;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.entity.*;
import org.path.fortification.manager.*;
import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.enums.GeoAggregationType;
import org.path.fortification.enums.GeoManufacturerQuantityResponseType;
import org.path.fortification.helper.IamServiceRestHelper;
import org.path.fortification.service.CategoryService;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DashboardServiceImplTest {

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private BatchStateGeoManager batchStateGeoManager;

    @Mock
    private CategoryService categoryService;

    @Mock
    private LotStateGeoDao lotStateGeoDao;

    @Mock
    private BatchManager batchManager;

    @Mock
    private StateManager stateManager;

    @Mock
    private LotManager lotManager;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CategoryManager categoryManager;

    @Mock
    private LotStateGeoManager lotStateGeoManager;

    @Mock
    private IamServiceRestHelper iamServiceRestHelper;

    private MockedStatic<IamServiceRestHelper> mockStatic;


    @BeforeEach
    public void setup() {
        mockStatic = mockStatic(IamServiceRestHelper.class);
    }

    @AfterEach
    void tearDown() {
        mockStatic.close();
    }

    @Test
    public void testGetDashboardCountData() {

        Integer year = 2022;
        DashboardRequestDto dto = new DashboardRequestDto();

        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("manufacturerId", "1"));
        when(categoryService.getAllCategoriesForManufacturer(anyBoolean())).thenReturn(List.of(new CategoryResponseDto(1L, "1", true, 1, new ProductResponseDto(), Set.of(new CategoryResponseDto(1L, "1", false, 1, null, null, null)), null)));
        when(lotStateGeoDao.findByCategoryIdsAndManufacturerId(any(), any())).thenReturn(Collections.emptyList());
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("manufacturerId", "1"));
        List<Object[]> lotStateGeos = new ArrayList<>();
        Object[] lotStateGeoArray = new Object[9];
        LotStateGeo lotStateGeo = new LotStateGeo();
        lotStateGeo.setInTransitQuantity(10.0);
        lotStateGeo.setReceivedQuantity(20.0);
        lotStateGeo.setApprovedQuantity(30.0);
        lotStateGeo.setRejectedQuantity(40.0);
        lotStateGeo.setUsedQuantity(50.0);
        lotStateGeo.setSampleInTransitQuantity(60.0);
        lotStateGeo.setTestInProgressQuantity(70.0);
        lotStateGeo.setTestedQuantity(80.0);
        lotStateGeoArray[0] = 1L; // CategoryId
        lotStateGeoArray[1] = lotStateGeo.getInTransitQuantity();
        lotStateGeoArray[2] = lotStateGeo.getReceivedQuantity();
        lotStateGeoArray[3] = lotStateGeo.getApprovedQuantity();
        lotStateGeoArray[4] = lotStateGeo.getRejectedQuantity();
        lotStateGeoArray[5] = lotStateGeo.getUsedQuantity();
        lotStateGeoArray[6] = lotStateGeo.getSampleInTransitQuantity();
        lotStateGeoArray[7] = lotStateGeo.getTestInProgressQuantity();
        lotStateGeoArray[8] = lotStateGeo.getTestedQuantity();
        lotStateGeos.add(lotStateGeoArray);

        when(lotStateGeoDao.findByCategoryIdsAndManufacturerId(any(), any())).thenReturn(lotStateGeos);
        Object[] batchStateGeo = new Object[17];
        batchStateGeo[0] = 1L; // CategoryId
        batchStateGeo[1] = 100.0; // TotalQuantity
        batchStateGeo[2] = 50.0; // InProductionQuantity
        batchStateGeo[3] = 30.0; // ProducedQuantity
        batchStateGeo[4] = 10.0; // InTransitQuantity
        batchStateGeo[5] = 5.0; // ReceivedQuantity
        batchStateGeo[6] = 3.0; // ApprovedQuantity
        batchStateGeo[7] = 2.0; // RejectedQuantity
        batchStateGeo[8] = 1.0; // BatchSampleInTransitQuantity
        batchStateGeo[9] = 1.0; // BatchSampleTestInProgressQuantity
        batchStateGeo[10] = 1.0; // BatchTestedQuantity
        batchStateGeo[11] = 1.0; // LotSampleInTransitQuantity
        batchStateGeo[12] = 1.0; // LotSampleTestInProgressQuantity
        batchStateGeo[13] = 1.0; // LotTestedQuantity
        batchStateGeo[14] = 1.0; // LotRejected
        batchStateGeo[15] = 1.0; // RejectedInTransitQuantity
        batchStateGeo[16] = 1.0; // ReceivedRejectedQuantity

        List<Object[]> batchStateGeos = new ArrayList<>();
        batchStateGeos.add(batchStateGeo);
        when(batchStateGeoManager.findByCategoryIdsAndManufacturerId(any(), any(), any(), any())).thenReturn(batchStateGeos);

        List<StateGeoDto> result = dashboardService.getDashboardCountData(year, dto);

        verify(keycloakInfo, times(1)).getUserInfo();
        verify(categoryService, times(1)).getAllCategoriesForManufacturer(true);
        verify(lotStateGeoDao, times(1)).findByCategoryIdsAndManufacturerId(any(), any());

        //setting category dto independent false
        when(categoryService.getAllCategoriesForManufacturer(anyBoolean())).thenReturn(List.of(new CategoryResponseDto(1L, "1", true, 1, new ProductResponseDto(), Set.of(new CategoryResponseDto(1L, "1", true, 1, null, null, null)), null)));
        when(lotManager.getQuantitySum(any(), any())).thenReturn(new Double[2]);
        result = dashboardService.getDashboardCountData(year, dto);

        verify(keycloakInfo, times(2)).getUserInfo();
        verify(categoryService, times(2)).getAllCategoriesForManufacturer(true);
        verify(lotStateGeoDao, times(2)).findByCategoryIdsAndManufacturerId(any(), any());

    }

    @Test
    public void testGetManufacturersQuantities_CaseBatch() {
        // Arrange
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.state;
        String geoId = "geoId";
        String search = "search";
        Integer year = 2022;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        DashboardRequestDto dashboardRequestDto = new DashboardRequestDto();
        GeoManufacturerQuantityResponseType responseType = GeoManufacturerQuantityResponseType.production;

        Map<Long, ManufacturerListDashboardResponseDto> manufacturers = new HashMap<>();
        ManufacturerListDashboardResponseDto manufacturer = new ManufacturerListDashboardResponseDto();
        manufacturers.put(1L, manufacturer);

        List<BatchStateGeo> results = new ArrayList<>();
        BatchStateGeo batchStateGeo = new BatchStateGeo();
        batchStateGeo.setGeoStateId(new GeoStateId());
        results.add(batchStateGeo);

        when(IamServiceRestHelper.getManufacturersWithGeoFilter(any(), any(), any(), any(), any(), any(), any())).thenReturn(manufacturers);
        when(batchStateGeoManager.findManufacturerQuantityByCategoryIdAndState(any(Long.class), anyString(), anyString(), anyString(), any(Set.class), anyInt(), anyInt(), anyInt(), any(DashboardRequestDto.class))).thenReturn(results);
        when(batchStateGeoManager.findManufacturerQuantityByCategoryIdAndStateCount(anyInt(), any(Long.class), anyString(), anyString(), anyInt(), anyInt(), anyInt(), any(DashboardRequestDto.class))).thenReturn(1L);

        // Act
        ListResponse<?> response = dashboardService.getManufacturersQuantities(responseType, categoryId, type, geoId, search, year, pageNumber, pageSize, dashboardRequestDto);

        // Assert
        verify(batchStateGeoManager, times(1)).findManufacturerQuantityByCategoryIdAndState(categoryId, "stateGeoId", geoId, "producedQuantity", manufacturers.keySet(), year, pageNumber, pageSize, dashboardRequestDto);
        verify(batchStateGeoManager, times(1)).findManufacturerQuantityByCategoryIdAndStateCount(results.size(), categoryId, "stateGeoId", geoId, year, pageNumber, pageSize, dashboardRequestDto);

        assertNotNull(response);
    }


    @Test
    void testRecompileGeoData() {
        // Arrange
        StateType stateType = StateType.BATCH;
        State state = new State();
        state.setType(StateType.BATCH);
        List<State> states = Collections.singletonList(state);

        BatchStateGeo batchStateGeo = new BatchStateGeo();
        batchStateGeo.setGeoStateId(new GeoStateId(1L, 1L, new Date()));
        List<BatchStateGeo> batchStateGeos = Collections.singletonList(batchStateGeo);

        LotStateGeo lotStateGeo = new LotStateGeo();
        lotStateGeo.setGeoStateId(new GeoStateId(1L, 1L, new Date()));
        List<LotStateGeo> lotStateGeos = Collections.singletonList(lotStateGeo);

        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setVillage(new VillageResponseDto());
        addressResponseDto.getVillage().setDistrict(new DistrictResponseDto());
        addressResponseDto.getVillage().getDistrict().setState(new StateResponseDto());
        addressResponseDto.getVillage().getDistrict().getState().setCountry(new CountryResponseDto());

        when(stateManager.findAll(null, null)).thenReturn(states);
        when(batchManager.findAllAggregateByManufacturerIdAndCategoryId(anyInt(), anyInt(), anyList())).thenReturn(batchStateGeos);
        when(lotManager.findAllAggregateByManufacturerIdAndCategoryId(anyInt(), anyInt(), anyList())).thenReturn(lotStateGeos);
        when(IamServiceRestHelper.getManufacturerAddress(any(), any())).thenReturn(addressResponseDto);

        // Act
        dashboardService.recompileGeoData(stateType);

        // Assert
        verify(batchStateGeoManager, times(1)).update(any(BatchStateGeo.class));
        verify(lotStateGeoDao, times(0)).update(any(LotStateGeo.class));

        // Change stateType to LOT and run the method again
        stateType = StateType.LOT;
        dashboardService.recompileGeoData(stateType);

        // Assert
        verify(batchStateGeoManager, times(1)).update(any(BatchStateGeo.class)); // No additional calls
        verify(lotStateGeoDao, times(1)).update(any(LotStateGeo.class)); // One call
    }

    @Test
    public void testGetYearFromDate() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JANUARY, 1);
        Date date = calendar.getTime();

        // Act
        Integer year = dashboardService.getYearFromDate(date);

        // Assert
        assertEquals(2022, year);
    }

}