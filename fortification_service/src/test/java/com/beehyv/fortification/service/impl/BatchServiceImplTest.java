package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.BatchDocRequestDto;
import com.beehyv.fortification.dto.requestDto.BatchRequestDto;
import com.beehyv.fortification.dto.requestDto.EntityStateRequestDTO;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.helper.FunctionUtils;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.helper.LabServiceManagementHelper;
import com.beehyv.fortification.manager.*;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.BatchMapper;
import com.beehyv.parent.fileUpload.service.StorageClient;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BatchServiceImplTest {

    @Mock
    TaskService taskService;
    Category category = new Category();
    BatchRequestDto batchRequestDto = new BatchRequestDto();
    Batch existingBatch = new Batch();
    Batch batch = new Batch();
    State state = new State();
    Lot lot = new Lot();
    Map<String, Object> userInfo = new HashMap<>();
    Map<Long, LabNameAddressResponseDto> labMap = new HashMap<>();
    String iamServiceUrl = "https://iam.service.url";
    @Spy
    @InjectMocks
    private BatchServiceImpl batchService;
    @Mock
    private BatchManager batchManager;
    @Mock
    private LotManager lotManager;
    @Mock
    private UOMManager uomManager;
    @Mock
    private StorageClient storageClient;
    @Mock
    private ActivitiManager activitiManager;
    @Mock
    private StateManager stateManager;
    @Mock
    private RoleCategoryManager roleCategoryManager;
    @Mock
    private KeycloakInfo keycloakInfo;
    @Mock
    private MessageManager messageManager;
    @Mock
    private CategoryManager categoryManager;
    @Mock
    private BatchNoSequenceManager batchNoSequenceManager;
    @Mock
    private LabConfigCategoryManager labConfigCategoryManager;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private KeycloakAuthenticationToken authentication;
    @Mock
    private IamServiceRestHelper iamServiceRestHelper;
    @Mock
    private UriComponentsBuilder uriComponentsBuilder;
    @Mock
    private UriComponents uriComponents;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private BaseMapper<BatchResponseDto, BatchRequestDto, Batch> batchMapperMock;
    @Mock
    private FunctionUtils utils;
    private final UOM uom = new UOM();
    private MockedStatic<IamServiceRestHelper> mockStatic;
    private MockedStatic<BatchMapper> batchMapperMockedStatic;
    private MockedStatic<LabServiceManagementHelper> labServiceManagementHelperMockedStatic;
    private final AccessToken accessToken = new AccessToken();
    private MockedStatic<UriComponentsBuilder> uriComponentsBuilderMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        uom.setId(1L);
        uom.setConversionFactorKg(1L);
        uom.setName("Kg");
        batch.setBatchNo("test");
        userInfo.put("roles", new HashSet<>(List.of("SUPERADMIN")));
        accessToken.setName("test");
        uriComponentsBuilderMockedStatic = Mockito.mockStatic(UriComponentsBuilder.class);
        batch.setDateOfManufacture(new Date());
        state.setName("test");
        state.setId(1L);
        state.setDisplayName("testd");
        batch.setState(state);
        batch.setLastActionDate(new Date());
        batch.setUom(uom);
        batch.setId(1L);
        batch.setTotalQuantity(10.0);
        batch.setDateOfExpiry(new Date());
        batch.setBatchProperties(new HashSet<>(List.of(new BatchProperty(1L, "batch_name", "a", null))));
        Lot sourceLot = new Lot();
        sourceLot.setId(1L);
        sourceLot.setBatch(new Batch(), lot);
        sourceLot.setCategory(category);
        batch.setMixes(new HashSet<>(List.of(new MixMapping(1L, sourceLot, null, 10.0, uom))));
        category.setIndependentBatch(true);
        category.setId(1L);
        category.setName("Test");
        category.setSourceCategories(Set.of(new Category()));
        lot.setId(1L);
        lot.setManufacturerId(1L);
        lot.setTargetManufacturerId(1L);
        lot.setBatch(batch, lot);
        batch.setCategory(category);
        batchRequestDto.setBatchDocs(new HashSet<>(List.of(new BatchDocRequestDto(1L, null, null, null, "file.pdf", null))));
        batchRequestDto.setTotalQuantity(0.0);
        batchRequestDto.setMixes(new HashSet<>(List.of()));
        existingBatch.setId(batchRequestDto.getId());
        existingBatch.setState(stateManager.findByName("batchToDispatch"));
        HashSet<BatchLotMapping> batchLotMappings = new HashSet<>();
        batchLotMappings.add(new BatchLotMapping(1L, lot, batch, lot, 1.0, "1"));
        batch.setBatchLotMapping(batchLotMappings);

        doReturn(batch).when(batchService).createMaterials(any(), any());
        when(batchManager.save(any())).thenReturn(batch);
        when(batchNoSequenceManager.findById(any(BatchNoId.class))).thenReturn(new BatchNoSequence());
        doReturn(batch).when(batchService).createMaterials(any(), any());
        Task taskMock = mock(Task.class);
        when(activitiManager.startBatchProcess(any(), any(), any(), any())).thenReturn(taskMock);
        when(categoryManager.isCategorySuperAdmin(anyLong(), any())).thenReturn(false);
        when(batchManager.findByIdAndManufacturerId(any(), any())).thenReturn(batch);
        when(batchManager.findById(any())).thenReturn(batch);
        doReturn(batch).when(batchService).createMaterials(any(), any());


        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(keycloakInfo.getAccessToken()).thenReturn("a");
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        OidcKeycloakAccount keycloakAccount = mock(OidcKeycloakAccount.class);
        when(authentication.getAccount()).thenReturn(keycloakAccount);
        KeycloakSecurityContext keycloakSecurityContext = mock(KeycloakSecurityContext.class);
        when(keycloakAccount.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
        when(authentication.getAccount().getKeycloakSecurityContext().getToken()).thenReturn(accessToken);
        when(categoryManager.findById(batchRequestDto.getCategoryId())).thenReturn(category);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", 1L, "roles", new HashSet<>(Set.of("abc", "admin"))));
        when(batchManager.create(any(Batch.class))).thenReturn(batch);
        when(batchMapperMock.toEntity(any())).thenReturn(batch);
        when(restTemplate.exchange(any(), any(), any(), (Class<Object>) any())).thenReturn(new ResponseEntity<>(labMap, HttpStatus.OK));
        when(restTemplate.exchange(any(), any(), any(), (ParameterizedTypeReference<Object>) any())).thenReturn(new ResponseEntity<>(labMap, HttpStatus.OK));
        when(restTemplate.execute(any(), any(), any(), any())).thenReturn(new ResponseEntity<>(labMap, HttpStatus.OK));
        UriComponentsBuilder uriComponentsBuilderSpy = Mockito.spy(UriComponentsBuilder.class);
        when(UriComponentsBuilder.fromHttpUrl(Mockito.anyString())).thenReturn(uriComponentsBuilderSpy);
        when(UriComponentsBuilder.fromHttpUrl(any(String.class))).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.queryParam(any(), anyCollection())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.queryParam(any(), (Object) any())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        when(uriComponents.toUriString()).thenReturn("https://example.com/api/endpoint");

        mockStatic = mockStatic(IamServiceRestHelper.class);
        batchMapperMockedStatic = mockStatic(BatchMapper.class);
        labServiceManagementHelperMockedStatic = mockStatic(LabServiceManagementHelper.class);
    }

    @AfterEach
    public void tearDown() {
        mockStatic.close();
        batchMapperMockedStatic.close();
        labServiceManagementHelperMockedStatic.close();
        uriComponentsBuilderMockedStatic.close();
    }

    @Test
    void testCreateBatch() {
        category.setIndependentBatch(false);
        batch.setCategory(category);
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(true);
        when(IamServiceRestHelper.fetchResponse(any(), (ParameterizedTypeReference<Object>) any(), any())).thenReturn(true);

        doNothing().when(batchService).setPrefetchedInstructions(any());
        doNothing().when(batchService).startBatchProcess(any(), any(), any());
        doNothing().when(batchService).setPrefetchedInstructions(any());


        // Act
        Long batchId = batchService.createBatch(batchRequestDto);

        // Assert
        verify(batchManager, times(1)).create(any(Batch.class));
    }

    @Test
    public void testCreateBatch_IndependentCategory() {

        category.setIndependentBatch(true);
        Long batchId = batchService.createBatch(batchRequestDto);

        verify(batchService, times(1)).createMaterials(any(), any());

    }

    @Test
    public void testCreateBatch_NonIndependentCategory() {
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(true);
        when(IamServiceRestHelper.fetchResponse(any(), (ParameterizedTypeReference<Object>) any(), any())).thenReturn(true);

        doNothing().when(batchService).setPrefetchedInstructions(any());
        doNothing().when(batchService).startBatchProcess(any(), any(), any());
        doNothing().when(batchService).setPrefetchedInstructions(any());


        category.setIndependentBatch(false);

        Long batchId = batchService.createBatch(batchRequestDto);

        verify(batchManager, times(1)).create(any());

    }

    @Test
    void testUpdateBatch() {
        // Arrange
        batchRequestDto.setTotalQuantity(10.0);
        batchRequestDto.setId(1L);
        batchRequestDto.setCategoryId(1L);
        batch.setUuid("testuuid");
        batch.setUom(uom);
        batchRequestDto.setUomId(1L);
        batch.setState(new State(null, null, null, null, "null", null, null));
        when(BatchMapper.toEntity(any(), any())).thenReturn(batch);
        doNothing().when(batchService).validateAndUpdateBatchData(any(), any(), any());

        batchService.updateBatch(batchRequestDto);
        // Act & Assert
        verify(batchManager, times(1)).update(any());
    }

    @Test
    void testGetBatchById() {
        // Arrange
        Long categoryId = 1L;
        Long batchId = 1L;

        batch.setId(batchId);
        Map<String, Map<String, String>> nameAddressMap = new HashMap<>();
        nameAddressMap.put("1", new HashMap<>());
        when(IamServiceRestHelper.getNameAndAddress(any(), any())).thenReturn(nameAddressMap);
        when(categoryManager.isCategorySuperAdmin(anyLong(), any())).thenReturn(false);
        when(categoryManager.isCategoryInspectionUser(anyLong(), any())).thenReturn(false);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", 1L));
        when(batchManager.findByIdAndManufacturerId(batchId, 1L)).thenReturn(batch);

        // Act
        BatchResponseDto result = batchService.getBatchById(categoryId, batchId);

        // Assert
        assertNotNull(result);
        assertEquals(batchId, result.getId());
    }

    @Test
    void testGetAllBatches() {
        // Arrange
        Long categoryId = 1L;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        SearchListRequest searchRequest = new SearchListRequest();

        List<Batch> batches = List.of(batch);

        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", 1L));
        when(batchManager.findAllBatches(anyLong(), anyLong(), anyInt(), anyInt(), any(SearchListRequest.class), eq(false))).thenReturn(batches);
        when(batchManager.getCount(anyLong(), anyLong(), any(), any(SearchListRequest.class))).thenReturn(2L);
        labMap.put(1L, new LabNameAddressResponseDto());
        when(LabServiceManagementHelper.fetchLabNameAddressByLotIds(any(), any(), any())).thenReturn(labMap);

        // Act
        ListResponse<BatchListResponseDTO> result = batchService.getAllBatches(categoryId, pageNumber, pageSize, searchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals(2L, result.getCount());
    }

    @Test
    void testGetBatchActions() {
        // Arrange
        Long categoryId = 1L;
        Long batchId = 1L;
        ActionType actionType = ActionType.module;
        String sampleState = null;

        Batch batch = new Batch();
        batch.setId(batchId);

        when(batchManager.findById(batchId)).thenReturn(batch);

        // Act
        List<StateResponseDto> result = batchService.getBatchActions(categoryId, batchId, actionType, sampleState);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testDispatchExternalBatch() {
        // Arrange
        Long categoryId = 1L;
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        entityStateRequestDTO.setDateOfAction(new Date());
        entityStateRequestDTO.setBatchId(1L);
        when(stateManager.findByName("batchToDispatch")).thenReturn(state);

        // Act & Assert
        assertDoesNotThrow(() -> batchService.dispatchExternalBatch(categoryId, entityStateRequestDTO));
    }

    @Test
    void testUpdateBatchStatus() {
        // Arrange
        Long categoryId = 1L;
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        entityStateRequestDTO.setBatchId(1L);
        entityStateRequestDTO.setDateOfAction(new Date());
        ActionType actionType = ActionType.module;
        SampleTestResult sampleTestResult = SampleTestResult.TEST_PASSED;
        BatchMapper batchMapper = new BatchMapper();
        entityStateRequestDTO.setStateId(1L);
        batch.setTaskId("1");
        state.setActionType(actionType);

        when(stateManager.findByNames(any())).thenReturn(Collections.singletonList(state));
        when(stateManager.findById(any())).thenReturn(state);
        when(batchManager.findById(entityStateRequestDTO.getBatchId())).thenReturn(batch);
        when(roleCategoryManager.findAllByCategoryAndRoleNames(any())).thenReturn(List.of(new RoleCategory(1L, "testRole", new Category(1L, null, null, null, null, true, null), null, null, new HashSet<>(Set.of(new TargetStatesAccess(1L, null, state, null))))));
        Map map = new HashMap();
        map.put("roles", Set.of("abc"));
        when(keycloakInfo.getUserInfo()).thenReturn(map);
        // Act & Assert
        // Assert
        Boolean result = batchService.updateBatchStatus(categoryId, entityStateRequestDTO, actionType, sampleTestResult);
        assertTrue(result);
    }

    @Test
    void testUpdateBatchStatus_SelfTestedBatch() {
        // Arrange
        Long categoryId = 1L;
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        entityStateRequestDTO.setBatchId(1L);
        entityStateRequestDTO.setDateOfAction(new Date());
        ActionType actionType = ActionType.module;
        SampleTestResult sampleTestResult = SampleTestResult.TEST_PASSED;
        BatchMapper batchMapper = new BatchMapper();
        entityStateRequestDTO.setStateId(1L);
        batch.setTaskId("1");
        state.setActionType(actionType);
        state.setName("selfTestedBatch");


        TaskQuery mockTaskQuery = mock(TaskQuery.class);
        Batch mockBatch = mock(Batch.class);
        when(mockBatch.getTaskId()).thenReturn("1");
        Task mockTask = mock(Task.class);

        when(taskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskId(any())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTask.getAssignee()).thenReturn("assignee");


        when(stateManager.findByNames(any())).thenReturn(Collections.singletonList(state));
        when(stateManager.findById(any())).thenReturn(state);
        when(batchManager.findById(entityStateRequestDTO.getBatchId())).thenReturn(batch);
        when(roleCategoryManager.findAllByCategoryAndRoleNames(any())).thenReturn(List.of(new RoleCategory(1L, "testRole", new Category(1L, null, null, null, null, true, null), null, null, new HashSet<>(Set.of(new TargetStatesAccess(1L, null, state, null))))));
        Map map = new HashMap();
        map.put("roles", Set.of("abc"));
        when(keycloakInfo.getUserInfo()).thenReturn(map);
        // Act & Assert
        // Assert
        Boolean result = batchService.updateBatchStatus(categoryId, entityStateRequestDTO, actionType, sampleTestResult);
        assertTrue(result);
    }

    @Test
    void testUpdateBatchStatus_activiti_completeTask() {
        // Arrange
        Long categoryId = 1L;
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        entityStateRequestDTO.setBatchId(1L);
        entityStateRequestDTO.setDateOfAction(new Date());
        ActionType actionType = ActionType.module;
        SampleTestResult sampleTestResult = SampleTestResult.TEST_PASSED;
        BatchMapper batchMapper = new BatchMapper();
        entityStateRequestDTO.setStateId(1L);
        batch.setTaskId("1");
        state.setActionType(actionType);
        state.setName("batchsampleinlab");

        TaskQuery mockTaskQuery = mock(TaskQuery.class);
        Batch mockBatch = mock(Batch.class);
        when(mockBatch.getTaskId()).thenReturn("1");
        Task mockTask = mock(Task.class);

        when(taskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskId(any())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTask.getAssignee()).thenReturn("assig_nee,assig_nee2");

        when(stateManager.findByNames(any())).thenReturn(Collections.singletonList(state));
        when(stateManager.findById(any())).thenReturn(state);
        when(batchManager.findById(entityStateRequestDTO.getBatchId())).thenReturn(batch);
        when(roleCategoryManager.findAllByCategoryAndRoleNames(any())).thenReturn(List.of(new RoleCategory(1L, "testRole", new Category(1L, null, null, null, null, true, null), null, null, new HashSet<>(Set.of(new TargetStatesAccess(1L, null, state, null))))));
        when(activitiManager.completeTask(any(), any(), any(), any(Boolean.class))).thenReturn(true);

        Map map = new HashMap();
        map.put("roles", Set.of("abc"));
        when(keycloakInfo.getUserInfo()).thenReturn(map);
        // Act & Assert
        // Assert
        Boolean result = batchService.updateBatchStatus(categoryId, entityStateRequestDTO, actionType, sampleTestResult);
        assertTrue(result);
    }


    @Test
    void testGetDetailsForUUID() {
        // Arrange
        String uuid = "test-uuid";

        Batch batch = new Batch();
        batch.setUuid(uuid);

        when(batchManager.findByUUID(uuid)).thenReturn(batch);

        // Act
        BatchResponseDto result = batchService.getDetailsForUUID(uuid);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void testDeleteBatch() {
        // Arrange
        Long batchId = 1L;

        // Act & Assert
        assertDoesNotThrow(() -> batchService.deleteBatch(batchId));
        verify(batchManager, times(1)).delete(batchId);
    }


    @Test
    void testUpdateBatchInspectionStatus() {
        // Arrange
        Long batchId = 1L;
        Boolean isBlocked = true;

        Batch batch = new Batch();
        batch.setId(batchId);

        when(batchManager.findById(batchId)).thenReturn(batch);

        // Act
        batchService.updateBatchInspectionStatus(batchId, isBlocked);

        // Assert
        verify(batchManager, times(1)).update(batch);
        assertEquals(isBlocked, batch.getIsBlocked());
    }

    @Test
    void testStartBatchProcess() {
        // Arrange
        Long batchId = 1L;
        batch.setState(state);
        batch.setUuid("check");
        when(stateManager.findByName(any())).thenReturn(state);
        when(batchManager.save(any())).thenReturn(batch);
        // Act & Assert
        assertDoesNotThrow(() -> batchService.startBatchProcess(batch, category, userInfo));

    }

    @Test
    void testSetPrefetchedInstructions() {
        // Arrange
        when(categoryManager.findById(any())).thenReturn(category);
        when(lotManager.getAllByIds(any())).thenReturn(Collections.singletonList(lot));
        when(uomManager.findAllByIds(any())).thenReturn(Collections.singletonList(uom));
        assertDoesNotThrow(() -> batchService.setPrefetchedInstructions(batch));
        assertDoesNotThrow(() -> batchService.setPrefetchedInstructionsForMaterials(batch));

    }

    @Test
    void testCreateLotFromBatch() {
        // Arrange
        Batch batch = new Batch();
        batch.setBatchNo("batchNo");
        batch.setUom(new UOM());
        batch.setManufacturerId(1L);
        batch.setDateOfManufacture(new Date());
        batch.setTotalQuantity(100.0);
        batch.setRemainingQuantity(100.0);
        batch.setCategory(new Category());
        batch.setPrefetchedInstructions("instructions");

        Set<BatchProperty> batchProperties = new HashSet<>();
        BatchProperty batchProperty = new BatchProperty();
        batchProperty.setName("manufacture_batchNumber");
        batchProperty.setValue("value");
        batchProperties.add(batchProperty);
        batch.setBatchProperties(batchProperties);

        State state = new State();
        state.setName("approved");
        when(stateManager.findByName("approved")).thenReturn(state);

        AccessToken token = new AccessToken();
        token.setPreferredUsername("username");
        KeycloakSecurityContext keycloakSecurityContext = mock(KeycloakSecurityContext.class);
        when(keycloakSecurityContext.getToken()).thenReturn(token);
        OidcKeycloakAccount keycloakAccount = mock(OidcKeycloakAccount.class);
        when(keycloakAccount.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
        KeycloakAuthenticationToken keycloakAuthenticationToken = mock(KeycloakAuthenticationToken.class);
        when(keycloakAuthenticationToken.getAccount()).thenReturn(keycloakAccount);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(keycloakAuthenticationToken);
        SecurityContextHolder.setContext(securityContext);

        Lot expectedLot = new Lot();
        expectedLot.setLotNo(batch.getBatchNo() + "_L01");
        expectedLot.setUom(batch.getUom());
        expectedLot.setState(state);
        expectedLot.setIsReceivedAtTarget(true);
        expectedLot.setTargetManufacturerId(batch.getManufacturerId());
        expectedLot.setDateOfReceiving(batch.getDateOfManufacture());
        expectedLot.setDateOfAcceptance(batch.getDateOfManufacture());
        expectedLot.setIsTargetAccepted(true);
        expectedLot.setIsTargetAcknowledgedReport(true);
        expectedLot.setTotalQuantity(batch.getTotalQuantity());
        expectedLot.setRemainingQuantity(batch.getRemainingQuantity());
        expectedLot.setManufacturerId(batch.getManufacturerId());
        expectedLot.setCategory(batch.getCategory());
        expectedLot.setLastActionDate(batch.getDateOfManufacture());
        expectedLot.setPrefetchedInstructions(batch.getPrefetchedInstructions());
        when(lotManager.create(any(Lot.class))).thenReturn(expectedLot);

        // Act
        Lot actualLot = batchService.createLotFromBatch(batch);

        // Assert
        assertEquals(expectedLot, actualLot);
    }

    @Test
    void testCreateMaterials() {
        doReturn(lot).when(batchService).createLotFromBatch(any());
        when(batchService.createMaterials(any(), any())).thenCallRealMethod();
        doCallRealMethod().when(batchService).createMaterials(any(), any());
        doNothing().when(batchService).setPrefetchedInstructions(any());
        doNothing().when(batchService).setGeneratedBatchNumber(any(), any(), any(), any());
        // Arrange
        BatchRequestDto mockBatchRequestDto = new BatchRequestDto();
        mockBatchRequestDto.setDateOfManufacture(Date.from(Instant.now()));

        // Act
        Batch actualBatch = batchService.createMaterials(mockBatchRequestDto, category);

        // Assert
        assertNotNull(actualBatch);
    }


    @Test
    void testGetHistoryForBatch() {
        // Arrange
        Long batchId = 1L;
        batch.setManufacturerId(1L);
        batch.getLots().stream().findFirst().get().setTargetManufacturerId(1L);

        when(batchManager.findById(batchId)).thenReturn(batch);

        assertDoesNotThrow(() -> batchService.getHistoryForBatch(batchId));

    }
}



