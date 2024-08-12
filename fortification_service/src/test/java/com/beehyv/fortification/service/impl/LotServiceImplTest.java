package com.beehyv.fortification.service.impl;


import com.beehyv.fortification.dto.external.ExternalResponseDto;
import com.beehyv.fortification.dto.external.SourceTargetLotExternalRequestDto;
import com.beehyv.fortification.dto.external.TargetLotsExternalRequestDto;
import com.beehyv.fortification.dto.iam.AddressResponseDto;
import com.beehyv.fortification.dto.iam.CountryResponseDto;
import com.beehyv.fortification.dto.iam.DistrictResponseDto;
import com.beehyv.fortification.dto.iam.VillageResponseDto;
import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.helper.ExternalRestHelper;
import com.beehyv.fortification.helper.FunctionUtils;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.helper.LabServiceManagementHelper;
import com.beehyv.fortification.manager.*;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.LotMapper;
import com.beehyv.fortification.service.MixMappingService;
import com.beehyv.fortification.service.WastageService;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.fileUpload.service.StorageClient;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static com.beehyv.fortification.mapper.BaseMapper.stateMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LotServiceImplTest {

    Map<Long, LabNameAddressResponseDto> labMap = new HashMap<>();
    ListResponse<MixMappingResponseDto> mixes = new ListResponse<>();
    MixMappingResponseDto mix = new MixMappingResponseDto();
    UOMResponseDto uomDto = new UOMResponseDto();
    Long categoryId = 1L;
    Batch batch = new Batch();
    State s = new State(null, null, null, null, "batchToDispatch", null, null);
    State s2 = new State(null, null, null, null, "dispatched", null, null);
    Category c = new Category();
    EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
    @Mock
    private LotManager manager;
    @Mock
    private BatchManager batchManager;
    @Mock
    private BatchLotMappingManager batchLotMappingManager;
    @Mock
    private KeycloakInfo keycloakInfo;
    @Mock
    private ActivitiManager activitiManager;
    @Mock
    private CategoryManager categoryManager;
    @Mock
    private StateManager stateManager;
    @Mock
    private RoleCategoryManager roleCategoryManager;
    @Mock
    private TaskService taskService;
    @Mock
    private FunctionUtils functionUtils;
    @Mock
    private MessageManager messageManager;
    @Mock
    private UOMManager uomManager;
    @Mock
    private MixMappingService mixMappingService;
    @Mock
    private FunctionUtils utils;
    @Mock
    private ExternalMetaDataManager externalMetaDataManager;
    @Mock
    private StorageClient storageClient;
    @Mock
    private WastageService wastageService;
    @Mock
    private ExternalRestHelper externalRestHelper;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private KeycloakAuthenticationToken authentication;
    @Mock
    private LabConfigCategoryManager labConfigCategoryManager;
    @Spy
    @InjectMocks
    private LotServiceImpl lotService;
    private final BaseMapper<LotResponseDto, LotRequestDto, Lot> mapper = BaseMapper.getForClass(LotMapper.class);
    private Map<String, Object> userInfo;
    private final AccessToken accessToken = new AccessToken();
    private final UOM uom = new UOM();
    private final LotRequestDto dto = new LotRequestDto();
    private final HashSet<SizeUnitRequestDto> sizeUnit = new HashSet<>();
    private final Lot entity = new Lot();
    private MockedStatic<FunctionUtils> mockUtils;
    private MockedStatic<IamServiceRestHelper> mockStatic;
    private MockedStatic<LabServiceManagementHelper> mockLabServiceManagementHelper;

    @BeforeEach
    void setup() {

        userInfo = new HashMap<>();
        userInfo.put("roles", new HashSet<>(List.of("SUPERADMIN")));
        accessToken.setName("test");
        uom.setId(1L);
        uom.setConversionFactorKg(1L);
        sizeUnit.add(new SizeUnitRequestDto(1L, null, null, 1L, 10L, 10.0, false));
        dto.setSizeUnits(sizeUnit);
        dto.setUomId(1L);

        entity.setId(1L);
        entity.setTotalQuantity(50.0);
        entity.setUom(uom);
        dto.setTotalQuantity(50.0);
        entity.setState(s);
        entity.setTargetManufacturerId(1L);
        entity.setLastActionDate(new Date());


        c.setIndependentBatch(false);
        entity.setCategory(c);
        c.setName("c");
        batch.setCategory(c);
        batch.setState(s);
        batch.setIsLabTested(true);
        dto.setDateOfDispatch(new Date(System.currentTimeMillis() + 1000000));
        batch.setLastActionDate(new Date(System.currentTimeMillis() - 1000000));
        batch.setRemainingQuantity(60.0);
        entity.setBatch(batch, entity);
        SizeUnit sizeUnit = new SizeUnit(1L, null, null, uom, 10L, 11.0, false);
        Set<SizeUnit> sizeUnits = new HashSet<>();
        sizeUnits.add(sizeUnit);
        batch.setSizeUnits(sizeUnits);
        entityStateRequestDTO.setDateOfAction(new Date(System.currentTimeMillis() + 1000000));

        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(keycloakInfo.getAccessToken()).thenReturn("a");
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        OidcKeycloakAccount keycloakAccount = mock(OidcKeycloakAccount.class);
        when(authentication.getAccount()).thenReturn(keycloakAccount);
        KeycloakSecurityContext keycloakSecurityContext = mock(KeycloakSecurityContext.class);
        when(keycloakAccount.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
        when(authentication.getAccount().getKeycloakSecurityContext().getToken()).thenReturn(accessToken);

        doNothing().when(lotService).validateLot(any(), any(), any());
        mix.setId(1L);
        mix.setQuantityUsed(10.0);
        uomDto.setConversionFactorKg(1L);
        mix.setQuantityUsed(10.0);
        mix.setUom(uomDto);
        List<MixMappingResponseDto> list = new ArrayList<>();
        list.add(mix);
        mixes.setData(list);
        when(mixMappingService.getAllMixMappingsBySourceLot(any(), any(), any())).thenReturn(mixes);
        when(labConfigCategoryManager.findByCategoryIds(any(), any())).thenReturn(new LabConfigCategory(null, "test", null, null));

        mockStatic = mockStatic(IamServiceRestHelper.class);
        mockLabServiceManagementHelper = mockStatic(LabServiceManagementHelper.class);

    }

    @AfterEach
    public void tearDown() {
        mockStatic.close();
        mockLabServiceManagementHelper.close();
    }


    @Test
    void testCreateLot() {
        // Arrange
        dto.setExternalTargetManufacturerId("1");
        dto.setTargetManufacturerId(1L);

        batch.setManufacturerId(1L);
        when(batchManager.findById(any())).thenReturn(batch);
        when(manager.create(any())).thenReturn(entity);
        when(manager.findById(any())).thenReturn(entity);
        when(uomManager.findById(any())).thenReturn(uom);
        Task taskMock = Mockito.mock(Task.class);
        State stateMock = Mockito.mock(State.class);
        when(activitiManager.startLotProcess(any(Lot.class), any(), any(), any())).thenReturn(taskMock);
        when(stateManager.findByName(any())).thenReturn(s2);
        when(uomManager.findAllByIds(any())).thenReturn(Collections.singletonList(uom));

        //last part of method mocking
        when(storageClient.save(anyString(), anyString())).thenReturn("a");
        doNothing().when(messageManager).statusChangeHandler(any(), any(), any(), any(), any(), anyBoolean());
        doNothing().when(messageManager).sendLotUpdate(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());

        when(IamServiceRestHelper.getDistrictGeoIdByManufacturerId(any(), anyString()))
                .thenReturn("test");
        when(IamServiceRestHelper.fetchResponse(any(), eq(Long.class), anyString())).thenReturn(1L);


        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setVillage(new VillageResponseDto());
        addressResponseDto.getVillage().setDistrict(new DistrictResponseDto());
        addressResponseDto.getVillage().getDistrict().setState(new com.beehyv.fortification.dto.iam.StateResponseDto());
        addressResponseDto.getVillage().getDistrict().getState().setCountry(new CountryResponseDto());

        when(IamServiceRestHelper.getManufacturerAddress(any(), anyString())).thenReturn(addressResponseDto);


        Long result = lotService.createLot(categoryId, dto);


        assertNotNull(result);
        verify(manager, times(1)).create(any());
    }

    @Test
    void test_ValidateLot() {
        when(uomManager.findById(any())).thenReturn(uom);
        when(uomManager.findAllByIds(any())).thenReturn(Collections.singletonList(uom));

        doCallRealMethod().when(lotService).validateLot(any(), any(), any());
        assertDoesNotThrow(() -> lotService.validateLot(dto, entity, batch));
    }

    @Test
    void testGetLotById_SuperAdminOrInspectionUser() {
        // Arrange

        LotResponseDto lotResponseDto = new LotResponseDto();
        when(categoryManager.isCategorySuperAdmin(categoryId, RoleCategoryType.MODULE)).thenReturn(true);
        when(manager.findById(any())).thenReturn(entity);
        when(manager.findByIdAndManufacturerId(any(), any())).thenReturn(entity);

        // Act
        LotResponseDto result = lotService.getLotById(1L, categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetLotById_RegularUserOwnsLot() {
        // Arrange

        LotResponseDto lotResponseDto = new LotResponseDto();
        when(categoryManager.isCategorySuperAdmin(categoryId, RoleCategoryType.MODULE)).thenReturn(false);
        when(categoryManager.isCategoryInspectionUser(categoryId, RoleCategoryType.MODULE)).thenReturn(false);
        Set<String> roles = new HashSet<>();
        roles.add("test");
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", 1L, "roles", roles));
        when(manager.findByIdAndManufacturerId(1L, 1L)).thenReturn(entity);

        // Act
        LotResponseDto result = lotService.getLotById(1L, categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetLotById_RegularUserDoesNotOwnLot() {
        // Arrange
        Long categoryId = 1L;
        Long lotId = 1L;
        Long manufacturerId = 1L;
        Lot lot = new Lot();
        lot.setManufacturerId(2L);
        when(categoryManager.isCategorySuperAdmin(categoryId, RoleCategoryType.MODULE)).thenReturn(false);
        when(categoryManager.isCategoryInspectionUser(categoryId, RoleCategoryType.MODULE)).thenReturn(false);
        Set<String> roles = new HashSet<>();
        roles.add("test");
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", 1L, "roles", roles));
        when(manager.findByIdAndManufacturerId(lotId, manufacturerId)).thenReturn(null);

        // Act & Assert
        assertThrows(Exception.class, () -> lotService.getLotById(lotId, categoryId));
    }

    @Test
    void testUpdateLotState_AllowedToUpdate() {
        // Arrange
        Long categoryId = 1L;

        entity.setTaskId("one");
        entity.setDateOfReceiving(new Date());

        State prevState = new State(1L, null, null, null, "prev", null, null);
        State newState = new State(1L, null, null, null, "approved", null, null);
        entityStateRequestDTO.setStateId(1L);
        entity.setState(prevState);
        when(manager.findById(any())).thenReturn(entity);
        when(stateManager.findById(entityStateRequestDTO.getStateId())).thenReturn(newState);
        doReturn(List.of(stateMapper.toDto(new State(1L, null, null, null, null, null, null)))).when(lotService).getLotActions(any(), any(Lot.class), any(), any());
        when(activitiManager.completeTask(entity.getTaskId(), keycloakInfo.getUserInfo(), newState, entity.getIsBlocked())).thenReturn(true);

        // Act
        boolean result = lotService.updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, false);

        // Assert
        assertTrue(result);
        verify(manager, times(1)).update(entity);
    }

    @Test
    void testUpdateLotState_AllowedToUpdate_StateName_sentlotsampletolabtest() {
        // Arrange
        Long categoryId = 1L;

        entity.setTaskId("one");
        entity.setDateOfReceiving(new Date());

        State prevState = new State(1L, null, null, null, "prev", null, null);
        State newState = new State(1L, null, null, null, "approved", null, null);
        entityStateRequestDTO.setStateId(1L);
        entity.setState(prevState);
        entity.getState().setName("sentlotsampletolabtest");
        when(manager.findById(any())).thenReturn(entity);
        when(stateManager.findById(entityStateRequestDTO.getStateId())).thenReturn(newState);
        doReturn(List.of(stateMapper.toDto(new State(1L, null, null, null, null, null, null)))).when(lotService).getLotActions(any(), any(Lot.class), any(), any());
        when(activitiManager.completeTask(entity.getTaskId(), keycloakInfo.getUserInfo(), newState, entity.getIsBlocked())).thenReturn(true);


        TaskQuery mockTaskQuery = mock(TaskQuery.class);
        Batch mockBatch = mock(Batch.class);
        when(mockBatch.getTaskId()).thenReturn("1");
        Task mockTask = mock(Task.class);
        when(taskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskId(any())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTask.getAssignee()).thenReturn("as_signee");

        LabSampleCreateResponseDto mockResponse = new LabSampleCreateResponseDto();
        mockResponse.setLabId(1L);
        mockLabServiceManagementHelper.when(() -> LabServiceManagementHelper.createSample(any(Lot.class), any(), any(), any())).thenReturn(mockResponse);

        doNothing().when(activitiManager).setVariable(any(), any(), any());
        // Act
        boolean result = lotService.updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, false);

        // Assert
        assertTrue(result);
        verify(manager, times(1)).update(entity);

    }

    @Test
    void testUpdateLotState_NotAllowedToUpdate() {

        Long categoryId = 1L;

        entity.setTaskId("one");
        entity.setDateOfReceiving(new Date());

        State prevState = new State(1L, null, null, null, "prev", null, null);
        State newState = new State(1L, null, null, null, "approved", null, null);
        entityStateRequestDTO.setStateId(2L);
        entity.setState(prevState);
        when(manager.findById(any())).thenReturn(entity);
        when(stateManager.findById(entityStateRequestDTO.getStateId())).thenReturn(newState);
        doReturn(List.of(stateMapper.toDto(new State(1L, null, null, null, null, null, null)))).when(lotService).getLotActions(any(), any(Lot.class), any(), any());
        when(activitiManager.completeTask(entity.getTaskId(), keycloakInfo.getUserInfo(), newState, entity.getIsBlocked())).thenReturn(true);


        // Act
        boolean result = lotService.updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, false);

        // Assert
        assertFalse(result);
        verify(manager, never()).update(entity);
    }

    @Test
    void testUpdateLotState_Conditions() {
        // Arrange
        Long categoryId = 1L;

        entity.setTaskId("one");
        entity.setDateOfReceiving(new Date());

        State prevState = new State(1L, null, null, null, "lotReceived", null, null);
        State newState = new State(1L, null, null, null, "approved", null, null);
        entityStateRequestDTO.setStateId(1L);
        entityStateRequestDTO.setIsInspectionSample(true);
        entity.setState(prevState);
        when(manager.findById(any())).thenReturn(entity);
        when(stateManager.findById(entityStateRequestDTO.getStateId())).thenReturn(newState);
        doReturn(List.of(stateMapper.toDto(new State(1L, null, null, null, null, null, null)))).when(lotService).getLotActions(any(), any(Lot.class), any(), any());
        when(activitiManager.completeTask(entity.getTaskId(), keycloakInfo.getUserInfo(), newState, entity.getIsBlocked())).thenReturn(true);


        TaskQuery mockTaskQuery = mock(TaskQuery.class);
        Batch mockBatch = mock(Batch.class);
        when(mockBatch.getTaskId()).thenReturn("1");
        Task mockTask = mock(Task.class);
        when(taskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskId(any())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTask.getAssignee()).thenReturn("as_signee");

        LabSampleCreateResponseDto mockResponse = new LabSampleCreateResponseDto();
        mockResponse.setLabId(1L);
        mockLabServiceManagementHelper.when(() -> LabServiceManagementHelper.createSample(any(Lot.class), any(), any(), any())).thenReturn(mockResponse);

        doNothing().when(activitiManager).setVariable(any(), any(), any());
        // Act
        boolean result = lotService.updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, true);
        assertTrue(result);
        verify(manager, times(1)).update(entity);

        //else condition_case
        entityStateRequestDTO.setIsInspectionSample(false);
        result = lotService.updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, true);
        assertTrue(result);


        //catch Exception_Case
        entityStateRequestDTO.setIsInspectionSample(true);
        doThrow(new RuntimeException()).when(messageManager).sendMessage(any(), any(), any(), any(), any(), any());
        assertThrows(Exception.class, () -> lotService.updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, true));

        //elseif condition_case
        doNothing().when(messageManager).sendMessage(any(), any(), any(), any(), any(), any());
        State finalState = new State(1L, null, null, null, "selfTestedLot", null, null);
        when(stateManager.findById(any())).thenReturn(finalState);
        when(activitiManager.completeTask(any(), any(), any(), anyBoolean())).thenReturn(true);

        entityStateRequestDTO.setIsInspectionSample(false);
        result = lotService.updateLotState(categoryId, entityStateRequestDTO, ActionType.module, SampleTestResult.TEST_PASSED, true);
        assertTrue(result);
    }


    @Test
    void testGetAllLots_SuperAdminOrInspectionUser() {
        // Arrange
        Long categoryId = 1L;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Lot> lots = new ArrayList<>();
        when(categoryManager.isCategorySuperAdmin(categoryId, RoleCategoryType.MODULE)).thenReturn(true);
        when(manager.findAllByCategoryId(null, categoryId, searchListRequest, pageNumber, pageSize)).thenReturn(lots);
        when(manager.getCount(lots.size(), null, categoryId, pageNumber, pageSize, searchListRequest)).thenReturn(10L);
        when(LabServiceManagementHelper.fetchLabNameAddressByLotIds(any(), any(), any())).thenReturn(labMap);
        // Act
        ListResponse<LotListResponseDTO> result = lotService.getAllLots(categoryId, pageNumber, pageSize, searchListRequest);
        // Assert
        assertNotNull(result);
        assertEquals(lots.size(), result.getData().size());
    }

    @Test
    void testGetAllLots_RegularUser() {
        // Arrange
        Long categoryId = 1L;
        Long manufacturerId = 1L;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Lot> lots = new ArrayList<>();
        when(categoryManager.isCategorySuperAdmin(categoryId, RoleCategoryType.MODULE)).thenReturn(false);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", manufacturerId));
        when(manager.findAllByCategoryId(manufacturerId, categoryId, searchListRequest, pageNumber, pageSize)).thenReturn(lots);
        when(manager.getCount(lots.size(), manufacturerId, categoryId, pageNumber, pageSize, searchListRequest)).thenReturn(10L);

        // Act
        ListResponse<LotListResponseDTO> result = lotService.getAllLots(categoryId, pageNumber, pageSize, searchListRequest);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getCount());
        assertEquals(lots.size(), result.getData().size());
    }

    @Test
    void testCreateTargetLotFromSourceLots_AllowedToCreate() {
        // Arrange
        Long categoryId = 1L;
        TargetLotRequestDto dto = new TargetLotRequestDto();
        dto.setExternalTargetManufacturerId("1");
        MixMappingRequestDto mixRequest = new MixMappingRequestDto();
        mixRequest.setSourceLotNo("1");
        mixRequest.setQuantityUsed(10.0);
        dto.setMixes(Set.of(mixRequest));
        dto.setTargetManufacturerId(1L);
        dto.setDateOfDispatch(new Date(System.currentTimeMillis() + 100000));
        entity.setRemainingQuantity(10.0);
        entity.getCategory().setId(1L);
        entity.getState().setName("approved");
        entity.setDateOfAcceptance(new Date());
        List<Long> lotIds = List.of(1L);
        when(uomManager.findByName(any())).thenReturn(uom);
        when(uomManager.findAllByIds(any())).thenReturn(Collections.singletonList(uom));
        when(manager.findByLotNo(any())).thenReturn(Collections.singletonList(entity));
        when(manager.findAllByIds(any())).thenReturn(Collections.singletonList(entity));
        when(manager.create(any())).thenReturn(entity);
        when(stateManager.findByName(any())).thenReturn(new State(1L, null, null, null, "dispatched", null, null));
        when(IamServiceRestHelper.fetchResponse(any(), eq(Long.class), anyString())).thenReturn(1L);

        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setVillage(new VillageResponseDto());
        addressResponseDto.getVillage().setDistrict(new DistrictResponseDto());
        addressResponseDto.getVillage().getDistrict().setState(new com.beehyv.fortification.dto.iam.StateResponseDto());
        addressResponseDto.getVillage().getDistrict().getState().setCountry(new CountryResponseDto());

        when(IamServiceRestHelper.getManufacturerAddress(any(), anyString())).thenReturn(addressResponseDto);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", 1L, "roles", new HashSet<>()));

        Task mockTask = mock(Task.class);

        when(activitiManager.startLotProcess(any(), any(), any(), any())).thenReturn(mockTask);
        // Act
        List<Long> result = lotService.createTargetLotFromSourceLots(dto, categoryId, false);
        // Assert
        assertNotNull(result);
        assertEquals(lotIds, result);

    }

    @Test
    void testCreateTargetLotsFromSourceLots_AllowedToCreate() {
        // Arrange
        Long categoryId = 1L;

        TargetLotsExternalRequestDto extdto = new TargetLotsExternalRequestDto();
        extdto.setDateOfDispatch(new Date(System.currentTimeMillis() - 100000));
        SourceTargetLotExternalRequestDto extLot = new SourceTargetLotExternalRequestDto();
        extLot.setDestinationTransId("1");
        extdto.setSourceTargetLots(Set.of(extLot));

        entity.setRemainingQuantity(10.0);
        entity.getCategory().setId(1L);
        entity.getState().setName("approved");
        entity.setDateOfAcceptance(new Date());
        List<Long> lotIds = List.of(1L);
        when(uomManager.findByName(any())).thenReturn(uom);
        when(uomManager.findAllByIds(any())).thenReturn(Collections.singletonList(uom));
        when(manager.findByLotNo(any())).thenReturn(Collections.singletonList(entity));
        when(manager.findAllByIds(any())).thenReturn(Collections.singletonList(entity));
        when(manager.findById(any())).thenReturn(entity);
        when(manager.create(any())).thenReturn(entity);
        when(stateManager.findByName(any())).thenReturn(new State(1L, null, null, null, "dispatched", null, null));
        doReturn(lotIds).when(lotService).createTargetLotFromSourceLots(any(), any(), anyBoolean());
        doReturn(true).when(lotService).receiveLot(any(), any(), any());
        doReturn(true).when(lotService).acceptLot(any(), any(), any());

        Task mockTask = mock(Task.class);

        when(activitiManager.startLotProcess(any(), any(), any(), any())).thenReturn(mockTask);
        // Act
        Map<String, String> resultMap = lotService.createTargetLotsFromSourceLots(extdto, categoryId);
        // Assert
        assertNotNull(resultMap);
    }


    @Test
    void testReceiveLot_Allowed() {
        // Arrange
        Long categoryId = 1L;
        LotReceiveRequestDto dto = new LotReceiveRequestDto();
        dto.setAcknowledgedQuantity(10.0);
        List<Long> lotIds = List.of(1L);
        entity.setRemainingQuantity(15.0);
        when(uomManager.findByName("Kg")).thenReturn(uom);
        when(manager.findAllByIds(any())).thenReturn(Collections.singletonList(entity));
        when(stateManager.findByName(any())).thenReturn(new State(1L, null, null, null, null, null, null));
        doReturn(true).when(lotService).updateLotState(any(), any(), any(), any(), any());
        // Act
        boolean result = lotService.receiveLot(dto, categoryId, lotIds);

        // Assert
        assertTrue(result);
    }


    @Test
    void testGetHistoryForLot_NoAccess() {
        // Arrange
        Long lotId = 1L;
        doReturn(false).when(lotService).checkLabAccess(lotId);

        // Act & Assert
        assertThrows(CustomException.class, () -> lotService.getHistoryForLot(lotId));
    }

    @Test
    void testGetSourceLotsByTargetLotId_AllowedAccess() {
        // Arrange
        Long targetLotId = 1L;
        Long manufacturerId = 1L;
        Lot targetLot = new Lot();
        List<LotResponseDto> sourceLotResponseDtos = new ArrayList<>();
        when(manager.findById(targetLotId)).thenReturn(targetLot);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", manufacturerId));

        // Act
        List<LotResponseDto> result = lotService.getSourceLotsByTargetLotId(targetLotId);

        // Assert
        assertNotNull(result);
        assertEquals(sourceLotResponseDtos, result);
    }

    @Test
    void testGetSourceLotsByTargetLotId_NoAccess() {
        // Arrange
        Long targetLotId = 1L;
        Long manufacturerId = 3L;
        Lot targetLot = entity;
        targetLot.setTargetManufacturerId(1L);
        targetLot.setManufacturerId(2L);
        targetLot.setTargetManufacturerId(3L);
        when(manager.findById(targetLotId)).thenReturn(targetLot);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", manufacturerId));

        // Act
        List<LotResponseDto> result = lotService.getSourceLotsByTargetLotId(targetLotId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testCallExternalPostAPi() throws JsonProcessingException {
        // Arrange
        String driverUid = "mock-driver-uid";
        String commodityId = "mock-commodity-id";
        String externalUrl = "mock-external-url";
        entity.setTransportQuantityDetails(new TransportQuantityDetails(null, null, null, null, null, null, null, 10.0, Set.of(new TransportVehicleDetails())));
        batch.setDateOfExpiry(new Date());
        batch.setDateOfManufacture(new Date());
        entity.setBatch(batch, entity);

        mockUtils = mockStatic(FunctionUtils.class);


        // Mock ExternalResponseDto
        ExternalResponseDto mockExternalResponseDto = mock(ExternalResponseDto.class);
        when(externalRestHelper.postApi(anyString(), anyString())).thenReturn(mockExternalResponseDto);

        // Mock IamServiceRestHelper
        String mockManufacturerId = "mock-manufacturer-id";
        String mockTargetManufacturerId = "mock-target-manufacturer-id";
        when(IamServiceRestHelper.fetchResponse(anyString(), eq(String.class), anyString())).thenReturn(mockManufacturerId, mockTargetManufacturerId);
        Map map = new HashMap<>(Map.of("1", new HashMap<>(Map.of("address", "{\"district\":{\"id\":\"1\",\"name\":null}}"))));
        when(IamServiceRestHelper.getNameAndAddress(any(), any())).thenReturn(map);


        // Mock FunctionUtils
        List<DistrictJsonDto> mockDistrictIdList = new ArrayList<>();
        mockDistrictIdList.add(new DistrictJsonDto("1", "1"));
        when(FunctionUtils.getDistrictIdList()).thenReturn(mockDistrictIdList);

        // Act
        ExternalResponseDto result = null;
        try {
            result = lotService.callExternalPostAPi(entity, "driver", "Cid", "a.com");
            // Code to handle successful execution
        } catch (Exception e) {
            // Code to handle exception
        }
        assertNotNull(result);
        // Assert
        verify(externalRestHelper, times(1)).postApi(any(), any());
    }

    @Test
    public void testGetLotActionsWithCategoryIdAndLotId() {
        Long categoryId = 1L;
        Long lotId = 1L;
        ActionType actionType = ActionType.module;
        String sampleState = "sampleState";

        Lot lot = entity;
        lot.setId(lotId);

        StateResponseDto stateResponseDto = new StateResponseDto();
        stateResponseDto.setId(1L);
        stateResponseDto.setName("stateName");
        stateResponseDto.setDisplayName("displayName");
        stateResponseDto.setActionType(ActionType.module);

        List<StateResponseDto> expectedResponse = Collections.singletonList(stateResponseDto);

        when(manager.findById(lotId)).thenReturn(lot);
        when(lotService.getLotActions(categoryId, lot, actionType, sampleState)).thenReturn(expectedResponse);

        List<StateResponseDto> actualResponse = lotService.getLotActions(categoryId, lotId, actionType, sampleState);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetLotActionsWithCategoryIdAndLot() {
        Long categoryId = 1L;
        Lot lot = entity;
        lot.setId(1L);
        ActionType actionType = ActionType.module;
        String sampleState = "sampleState";

        StateResponseDto stateResponseDto = new StateResponseDto();
        stateResponseDto.setId(1L);
        stateResponseDto.setName("stateName");
        stateResponseDto.setDisplayName("displayName");
        stateResponseDto.setActionType(ActionType.module);

        List<StateResponseDto> expectedResponse = Collections.singletonList(stateResponseDto);

        when(lotService.getLotActions(categoryId, lot, actionType, sampleState)).thenReturn(expectedResponse);

        List<StateResponseDto> actualResponse = lotService.getLotActions(categoryId, lot, actionType, sampleState);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testAcceptLot() {
        // Prepare test data
        LotReceiveRequestDto dto = new LotReceiveRequestDto();
        dto.setLotNo("testLotNo");
        dto.setComments("testComments");
        dto.setDateOfAction(new Date());
        dto.setExternalTargetManufacturerId("testExternalTargetManufacturerId");

        Long categoryId = 1L;
        List<Long> ids = new ArrayList<>();

        Lot lot = entity;
        lot.setLotNo("testLotNo");
        lot.setTargetManufacturerId(1L);


        State state = new State();
        state.setName("approved");

        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        entityStateRequestDTO.setLotId(lot.getId());
        entityStateRequestDTO.setStateId(state.getId());
        entityStateRequestDTO.setComments(dto.getComments());
        entityStateRequestDTO.setDateOfAction(dto.getDateOfAction());

        // Mock the dependencies
        when(manager.findByLotNo(dto.getLotNo())).thenReturn(Collections.singletonList(lot));
        when(manager.findAllByIds(ids)).thenReturn(Collections.singletonList(lot));
        when(stateManager.findByName("approved")).thenReturn(state);
        doReturn(true).when(lotService).updateLotState(any(), any(), any(), any(), anyBoolean());

        // Call the method under test
        boolean result = lotService.acceptLot(dto, categoryId, ids);

        // Assert the result
        assertTrue(result);
    }

    @Test
    public void testGetLotInventory() {
        // Arrange
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Boolean approvedSourceLots = true;
        SearchListRequest searchRequest = new SearchListRequest();

        // Act
        ListResponse<LotListResponseDTO> response = lotService.getLotInventory(categoryId, pageNumber, pageSize, approvedSourceLots, searchRequest);

        // Assert
        assertNotNull(response);
        // Add more assertions based on your requirements
    }

    @Test
    public void testGetAllLotsBySourceCategoryId() {
        // Arrange
        Long categoryId = 1L;
        Long manufacturerId = 1L;
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;

        // Act
        ListResponse<LotResponseDto> response = lotService.getAllLotsBySourceCategoryId(categoryId, manufacturerId, search, pageNumber, pageSize);

        // Assert
        assertNotNull(response);
    }

    @Test
    public void testGetDetailsForUUID() {
        // Arrange
        String uuid = "test-uuid";
        LotResponseDto expectedResponse = new LotResponseDto();
        entity.setBatch(batch, entity);
        when(manager.findByUUID(uuid)).thenReturn(entity);
        LotResponseDto response = lotService.getDetailsForUUID(uuid);

        assertNotNull(response);
    }

    @Test
    public void testCheckLabAccess() {
        // Arrange
        Long lotId = 1L;
        when(manager.findById(any())).thenReturn(entity);
        when(batchManager.findById(any())).thenReturn(batch);
        // Act
        Boolean response = lotService.checkLabAccess(lotId);
        // Assert
        assertNotNull(response);
        assertFalse(response);
    }

    @Test
    public void testUpdateBatchInspectionStatus() {
        // Arrange
        Long lotId = 1L;
        Boolean isBlocked = true;
        when(manager.findById(any())).thenReturn(entity);

        // Act
        lotService.updateBatchInspectionStatus(lotId, isBlocked);
        when(manager.update(entity)).thenReturn(entity);

        // Assert
        verify(manager, times(1)).update(any());
    }

    @Test
    public void testMigrateData() {
        Long[] longarray = new Long[2];
        longarray[0] = 1L;
        longarray[1] = 2L;
        List<Long[]> results = new ArrayList<>();
        results.add(longarray);
        when(manager.migrateData()).thenReturn(results);
        when(manager.findById(any())).thenReturn(entity);
        when(batchManager.findById(any())).thenReturn(batch);
        // Act
        lotService.migrateData();

        // Assert
        verify(manager, times(1)).update(any());
    }

    @Test
    void testGetAllLotsByBatchId() {
        // Arrange
        Long batchId = 1L;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        List<Lot> lots = new ArrayList<>();
        when(manager.findAllByBatchId(batchId, pageNumber, pageSize)).thenReturn(lots);
        when(manager.getCount(lots.size(), batchId, pageNumber, pageSize)).thenReturn(10L);

        // Act
        ListResponse<LotResponseDto> result = lotService.getAllLotsByBatchId(batchId, pageNumber, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getCount());
        assertEquals(lots.size(), result.getData().size());
    }

    @Test
    void testUpdateLot() {
        // Arrange
        Long categoryId = 1L;
        LotRequestDto dto = new LotRequestDto();
        dto.setId(1L);
        Lot existingLot = new Lot();
        existingLot.setId(1L);
        existingLot.setTotalQuantity(50.0);
        existingLot.setUom(uom);
        existingLot.setCategory(c);
        existingLot.setState(s);
        existingLot.setDateOfDispatch(new Date());
        existingLot.setIsLabTested(true);
        when(manager.findById(dto.getId())).thenReturn(existingLot);
        when(manager.update(existingLot)).thenReturn(existingLot);
        doNothing().when(messageManager).sendLotUpdate(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());

        // Act
        lotService.updateLot(categoryId, dto);

        // Assert
        verify(manager, times(1)).update(existingLot);
        verify(messageManager, times(1)).sendLotUpdate(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testDeleteLot() {
        Long id = 1L;
        doNothing().when(manager).delete(id);
        lotService.deleteLot(id);
        verify(manager, times(1)).delete(id);
    }

    @Test
    void testGetLotActions() {
        // Arrange
        Long categoryId = 1L;
        Lot lot = entity;
        lot.setTaskId("testTaskId");
        lot.setIsBlocked(false);
        ActionType actionType = ActionType.module;
        String sampleState = "";

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", new HashSet<>(Arrays.asList("ROLE_1", "ROLE_2")));
        Set<String> roles = (Set<String>) userInfo.get("roles");
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).filter(rc -> rc.length == 3).toList();

        RoleCategory roleCategory = new RoleCategory();
        roleCategory.setCategory(new Category(1L, "testCategory", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1));
        List<RoleCategory> roleCategories = Collections.singletonList(roleCategory);

        State state = new State();
        state.setName("testState");
        state.setActionType(ActionType.module);
        List<State> states = Collections.singletonList(state);

        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(roleCategoryManager.findAllByCategoryAndRoleNames(roleSplitList)).thenReturn(roleCategories);
        when(activitiManager.getTaskActions(lot, roleCategories, userInfo, Collections.singletonList(state.getName()))).thenReturn(Collections.singletonList(state.getName()));
        when(stateManager.findByNames(Collections.singletonList(state.getName()))).thenReturn(states);

        // Act
        List<StateResponseDto> result = lotService.getLotActions(categoryId, lot, actionType, sampleState);

        // Assert
        assertNotNull(result);
    }

}


//This includes tests for the following methods:
//
//        1. `createLot`
//        2. `getLotById` (with different scenarios)
//        3. `updateLotState` (with allowed and not allowed cases)
//        4. `getAllLots` (for super admin/inspection user and regular user)
//        5. `createTargetLotFromSourceLots` (with allowed and not allowed cases)
//        6. `receiveLot` (with allowed and not allowed cases)
//        7. `acceptLot` (with allowed and not allowed cases)
//        8. `getHistoryForLot` (with allowed access and no access)
//        9. `getSourceLotsByTargetLotId` (with allowed access and no access