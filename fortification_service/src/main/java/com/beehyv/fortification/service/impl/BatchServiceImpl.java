package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.iam.AddressResponseDto;
import com.beehyv.fortification.dao.EventBodyDao;
import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.EntityType;
import com.beehyv.fortification.enums.EventTest;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.helper.Constants;
import com.beehyv.fortification.helper.FunctionUtils;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.helper.LabServiceManagementHelper;
import com.beehyv.fortification.manager.*;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.BatchMapper;
import com.beehyv.fortification.mapper.StateMapper;
import com.beehyv.fortification.service.BatchService;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.exceptions.ResourceNotFoundException;
import com.beehyv.parent.fileUpload.service.StorageClient;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.http.util.TextUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.NoResultException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.beehyv.fortification.helper.Constants.getCategoryNamePrefix;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class BatchServiceImpl implements BatchService {
    private final BaseMapper<BatchResponseDto, BatchRequestDto, Batch> batchMapper = BaseMapper.getForClass(BatchMapper.class);
    private final BaseMapper<BatchListResponseDTO, BatchRequestDto, Batch> batchListMapper = BaseMapper.getForListClass(BatchMapper.class);
    private final BaseMapper<StateResponseDto, StateRequestDto, State> stateMapper = BaseMapper.getForClass(StateMapper.class);
    @Autowired
    private BatchManager batchManager;
    @Autowired
    private BatchPropertyManager batchPropertyManager;
    @Autowired
    private LotManager lotManager;
    @Autowired
    private UOMManager uomManager;

    @Autowired
    private ActivitiManager activitiManager;
    @Autowired
    private StateManager stateManager;

    @Autowired
    private RoleCategoryManager roleCategoryManager;

    @Autowired
    private KeycloakInfo keycloakInfo;
    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private CategoryManager categoryManager;
    @Autowired
    private BatchNoSequenceManager batchNoSequenceManager;
    @Autowired
    private LabConfigCategoryManager labConfigCategoryManager;
    @Autowired
    private FunctionUtils utils;

    @Autowired
    private ExternalMetaDataManager externalMetaDataManager;

    @Autowired
    private EventBodyDao eventBodyDao;

    private final BatchStateGeoManager batchStateGeoManager;

    private final String[] lotDetailsColumnNames = {"SL. NO", "Name", "Manufacturer Lot Number", "Manufacturing Date", "Dispatch Date", "Expiry Date", "Manufacturer", "License Number", "Quantity Used", "Stage"};
    private final String[] inventoryDetailsColumnNames = {"SL. NO", "Batch Number", "Manufacturing Date", "Expiry Date", "Total Quantity(KG)", "Remaining Quantity(KG)", "Stage"};
    private final LotStateGeoManager lotStateGeoManager;

    @Override
    public Long createBatch(BatchRequestDto batchRequestDto) {
        Category category = categoryManager.findById(batchRequestDto.getCategoryId());
        Map<String, Object> userInfo = keycloakInfo.getUserInfo();
        Batch batch = null;
        if (category.isIndependentBatch()) {
            batch = createMaterials(batchRequestDto, category);
        } else {
            Batch entity = batchMapper.toEntity(batchRequestDto);
            this.validateAndUpdateBatchData(batchRequestDto, entity, true);
            Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
            entity.setDistrictGeoId(IamServiceRestHelper.getDistrictGeoIdByManufacturerId(manufacturerId, keycloakInfo.getAccessToken()));
            batch = batchManager.create(entity);
            startBatchProcess(batch, category, userInfo);
        }
        return batch.getId();
    }

    public void startBatchProcess(Batch batch, Category category, Map userInfo) {
        setGeneratedBatchNumber(batch, batch.getDateOfManufacture(), batch.getManufacturerId(), category);
        LabConfigCategory labConfigCategory =
                labConfigCategoryManager.findByCategoryIds(category.getId(), category.getId());
        String labOption = "";
        if (labConfigCategory == null) {
            labOption = "optional";
        } else {
            labOption = labConfigCategory.getLabOption().toLowerCase();
        }
        if (labOption.isBlank()) {
            labOption = "na";
        }
        Task task = activitiManager.startBatchProcess(batch, userInfo, category, labOption);
        batch.setState(stateManager.findByName(task.getTaskDefinitionKey()));
        batch.setLastActionDate(batch.getDateOfManufacture());
        batch = batchManager.save(batch);
        String categoryName = category.getName().toLowerCase();
        if (categoryName.split(" ").length > 1) {
            categoryName = String.join("-", categoryName.split(" "));
        }
        String qrCodeText = String.format("%s%s/%s/batch/code/%s", Constants.QRCODE_UI_URL, categoryName, category.getId(), batch.getUuid());
        storageClient.save(qrCodeText, Constants.FORTIFICATION_BATCH_QR_CODE);
        messageManager.sendBatchUpdate(
                batch.getManufacturerId(),
                category.getId(),
                batch.getTotalQuantity() * batch.getUom().getConversionFactorKg(),
                batch.getState(),
                userInfo,
                keycloakInfo.getAccessToken(),
                batch.getDateOfManufacture(),
                null,
                batch.getIsLabTested(),
                batch.getId());
        notificationBuilder(category, batch, new State(), batch.getState(), null, null);

        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        entityStateRequestDTO.setBatchId(batch.getId());
        entityStateRequestDTO.setComments(batch.getComments());
        entityStateRequestDTO.setDateOfAction(batch.getDateOfManufacture());
        messageManager.statusChangeHandler(entityStateRequestDTO, batch.getState(), userInfo, batch.getManufacturerId(),
                keycloakInfo.getAccessToken(), false);
    }

    public Lot createLotFromBatch(Batch batch) {
        Lot lot = new Lot();
        lot.setLotNo(batch.getBatchNo());
        lot.setUom(batch.getUom());
        lot.setState(stateManager.findByName("approved"));
        lot.setIsReceivedAtTarget(true);
        lot.setTargetManufacturerId(batch.getManufacturerId());
        lot.setDateOfReceiving(batch.getDateOfManufacture());
        lot.setDateOfAcceptance(batch.getDateOfManufacture());
        lot.setIsTargetAccepted(true);
        lot.setIsTargetAcknowledgedReport(true);
        lot.setTotalQuantity(batch.getTotalQuantity());
        lot.setRemainingQuantity(batch.getRemainingQuantity());
        lot.setManufacturerId(batch.getManufacturerId());
        lot.setCategory(batch.getCategory());
        lot.setLotNo(batch.getBatchNo() + "_L01");
        lot.setLastActionDate(batch.getDateOfManufacture());
        lot.setPrefetchedInstructions(batch.getPrefetchedInstructions());

        Set<LotProperty> lotProperties = lot.getLotProperties();
        if (lotProperties == null) {
            lotProperties = new HashSet<>();
        }

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AccessToken token = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        LotProperty createdByLotProperty = new LotProperty(null, "createdBy", token.getName(), lot);
        lotProperties.add(createdByLotProperty);

        Optional<BatchProperty> manufactureBatchNumber = batch.getBatchProperties().stream().filter(bp -> bp.getName().equals("manufacture_batchNumber")).findFirst();
        LotProperty manufacturerLotNo = new LotProperty(null, "manufacture_lotNumber", manufactureBatchNumber.isPresent() ? manufactureBatchNumber.get().getValue() : "", lot);
        lotProperties.add(manufacturerLotNo);

        return lotManager.create(lot);
    }

    @Override
    public void updateBatch(BatchRequestDto batchRequestDto) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Batch existingBatch;
        if (categoryManager.isCategorySuperAdmin(batchRequestDto.getCategoryId(), RoleCategoryType.MODULE)) {
            existingBatch = batchManager.findById(batchRequestDto.getId());
        } else {
            existingBatch = batchManager.findByIdAndManufacturerId(batchRequestDto.getId(), manufacturerId);
        }
        if (!Objects.equals(existingBatch.getState().getName(), "batchToDispatch")
                && !Objects.equals(existingBatch.getState().getName(), "partiallyDispatched")
                && !Objects.equals(existingBatch.getState().getName(), "fullyDispatched")) {
            batchRequestDto.setMixes(null);
            batchRequestDto.setComments(null);
            double prevQuantity = existingBatch.getTotalQuantity() * existingBatch.getUom().getConversionFactorKg();
            Batch batch = BatchMapper.toEntity(batchRequestDto, existingBatch);
            this.validateAndUpdateBatchData(batchRequestDto, batch, false);
            batch.setManufacturerId(existingBatch.getManufacturerId());
            batch.setLastActionDate(batchRequestDto.getDateOfManufacture());
            batchManager.update(batch);
            Double quantityDifference = batch.getTotalQuantity() * batch.getUom().getConversionFactorKg() - prevQuantity;
            messageManager.sendBatchUpdate(batch.getManufacturerId(), batchRequestDto.getCategoryId(), quantityDifference
                    , batch.getState(), keycloakInfo.getUserInfo(), keycloakInfo.getAccessToken(),
                    batch.getDateOfManufacture(),
                    null,
                    batch.getIsLabTested(), batch.getId());
        }
    }

    public void validateAndUpdateBatchData(BatchRequestDto batchRequestDto, Batch entity, Boolean state) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        if (!categoryManager.isCategorySuperAdmin(batchRequestDto.getCategoryId(), RoleCategoryType.MODULE)) {
            entity.setManufacturerId(manufacturerId);
        }
        String url = Constants.IAM_SERVICE_URL + "/manufacturer-category/can-skip-raw-materials";
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("manufacturerId", manufacturerId)
                .queryParam("categoryId", batchRequestDto.getCategoryId())
                .build();
        Boolean canSkipRawMaterials = IamServiceRestHelper.fetchResponse(uriBuilder.toUriString(), Boolean.class, keycloakInfo.getAccessToken());

        AtomicReference<Double> quantitySum = new AtomicReference<>(0D);
        if (state && !canSkipRawMaterials) {
            List<Lot> sourceLots = lotManager.getAllByIds(batchRequestDto.getMixes().stream().map(MixMappingRequestDto::getSourceLotId).collect(Collectors.toList()));
            if (sourceLots.stream().filter(d -> !Objects.equals(d.getTargetManufacturerId(), manufacturerId)).toList().size() > 0) {
                throw new ValidationException("All lots should belongs to your organization");
            }
            if (sourceLots.stream().filter(d -> !Objects.equals(d.getState().getName(), "approved")).toList().size() > 0) {
                throw new ValidationException("All lots should be approved");
            }
            Map<Long, Lot> lotsMap = sourceLots.stream().collect(Collectors.toMap(Lot::getId, d -> d));
            batchRequestDto.getMixes().forEach(mixMapping -> {
                Lot lot = lotsMap.get(mixMapping.getSourceLotId());
                UOM uom = uomManager.findById(mixMapping.getUomId());
                quantitySum.updateAndGet(v -> v + mixMapping.getQuantityUsed() * uom.getConversionFactorKg());
                if (lot == null) throw new ResourceNotFoundException("Mix's Lot", "id", mixMapping.getSourceLotId());
                double remQuantity = lot.getRemainingQuantity() * lot.getUom().getConversionFactorKg()
                        - mixMapping.getQuantityUsed() * uom.getConversionFactorKg();
                if (remQuantity < 0) {
                    throw new ValidationException("Lot with id " + lot.getLotNo() + " doesn't not contain enough quantity");
                }
                lot.setRemainingQuantity(remQuantity / lot.getUom().getConversionFactorKg());
                lotManager.save(lot);
                Category category = categoryManager.findById(lot.getCategory().getId());
                if (!category.isIndependentBatch()) {
                    messageManager.sendLotUpdate(
                            lot.getTargetManufacturerId(),
                            lot.getCategory().getId(),
                            entity.getManufacturerId(),
                            (mixMapping.getQuantityUsed() * uom.getConversionFactorKg()),
                            "used",
                            lot.getDateOfDispatch(),
                            null,
                            keycloakInfo.getAccessToken(),
                            lot.getBatch() != null ? lot.getBatch().getIsLabTested() : lot.getTargetBatchMapping().stream().map(t -> t.getSourceLot().getBatch().getIsLabTested()).findFirst().orElse(false),
                            lot.getId()
                    );
                }
            });
        }
        if (!state) {
            entity.getMixes().forEach(m -> quantitySum.updateAndGet(v -> v + m.getQuantityUsed() * m.getUom().getConversionFactorKg()));
        }
        if (batchRequestDto.getTotalQuantity() > 1.1 * quantitySum.get() || batchRequestDto.getTotalQuantity() < 0.9 * quantitySum.get()) {
            throw new CustomException("Total quantity should be in 10% variation of mixes", HttpStatus.BAD_REQUEST);
        }
        entity.setTotalQuantity(batchRequestDto.getTotalQuantity());
        entity.setRemainingQuantity(batchRequestDto.getTotalQuantity());
        if (entity.getWastes() != null && !entity.getWastes().isEmpty()) {
            double totalQuantity = batchRequestDto.getTotalQuantity();
            double wastageQuantity = entity.getWastes().stream()
                    .mapToDouble(w -> w.getWastageQuantity() * w.getUom().getConversionFactorKg())
                    .sum();
            entity.setRemainingQuantity(totalQuantity - wastageQuantity);
        }

        entity.setUom(uomManager.findByConversionFactor(1L));
        entity.getBatchProperties().removeIf(s -> TextUtils.isEmpty(s.getName())
                || TextUtils.isEmpty(s.getValue())
                || s.getValue().equalsIgnoreCase("undefined"));
        this.setPrefetchedInstructions(entity);
    }

    @Override
    public BatchResponseDto getBatchById(Long categoryId, Long id) {
        Batch batch;
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        if (categoryManager.isCategorySuperAdmin(categoryId, RoleCategoryType.MODULE) || categoryManager.isCategoryInspectionUser(categoryId, RoleCategoryType.MODULE) || roles.stream().anyMatch(r -> r.contains("MONITOR"))) {
            batch = batchManager.findById(id);
        } else {
            batch = batchManager.findByIdAndManufacturerId(id, manufacturerId);
        }
        return getBatchDetailsHelper(batch);
    }


    public BatchResponseDto getBatchDetailsHelper(Batch batch) {
        BatchResponseDto batchResponseDto = batchMapper.toDto(batch);
        batchResponseDto.setQrcode("batch_" + batch.getUuid());
        List<StateResponseDto> stateResponseDtos = getBatchActions(batch.getCategory().getId(), batch, ActionType.module, null);
        batchResponseDto.setActions(stateResponseDtos);
        Set<LotListResponseDTO> lots = batchResponseDto.getLots();
        if (!lots.isEmpty()) {
            List<Long> ids = lots.stream()
                    .map(LotListResponseDTO::getTargetManufacturerId)
                    .distinct().toList();
            Map<String, Map<String, String>> nameAddressMap = IamServiceRestHelper.getNameAndAddress(ids, keycloakInfo.getAccessToken());
            lots.forEach(l -> {
                Map<String, String> map = nameAddressMap.getOrDefault(l.getTargetManufacturerId().toString(), null);
                l.setManufacturerName(map.get("name"));
                l.setManufacturerAddress(map.get("address"));
                l.setLicenseNumber(map.get("licenseNo"));
                Optional<LotProperty> manufactureLotNumber = batch.getLots().stream().filter(p -> p.getId().equals(l.getId())).findFirst().get()
                        .getLotProperties().stream().filter(lp -> lp.getName().equals("manufacture_lotNumber")).findFirst();
                manufactureLotNumber.ifPresent(lotProperty -> l.setManufacturerLotNumber(lotProperty.getValue()));
            });
        }
        return batchResponseDto;
    }

    @Override
    public ListResponse getAllBatches(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        return this.getAllBatches(categoryId, pageNumber, pageSize, searchRequest, false);
    }

    @Override
    public ListResponse getBatchInventory(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        return this.getAllBatches(categoryId, pageNumber, pageSize, searchRequest, true);
    }

    public ListResponse getAllBatches(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, Boolean remQuantity) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<Batch> batches = batchManager.findAllBatches(
                categoryId, manufacturerId, pageNumber, pageSize, searchRequest, remQuantity);
        Long count = ((Integer) batches.size()).longValue();
        if (pageSize != null && pageNumber != null) {
            count = batchManager.getCount(categoryId, manufacturerId, null, searchRequest);
        }

        ListResponse<BatchListResponseDTO> dtos = ListResponse.from(batches, batchListMapper::toListDTO, count);
        List<Long> batchIds = dtos.getData().stream().map(BatchListResponseDTO::getId).toList();
        Map<Long, LabNameAddressResponseDto> labNameAddressResponseDtoMap = LabServiceManagementHelper.fetchLabNameAddressByLotIds(keycloakInfo.getAccessToken(), batchIds, EntityType.batch);
        dtos.getData()
                .forEach(dto -> {
                    LabNameAddressResponseDto labNameAddressResponseDto = labNameAddressResponseDtoMap.get(dto.getId());
                    if (labNameAddressResponseDto != null) {
                        dto.setLabName(labNameAddressResponseDto.getName());
                        dto.setLabId(labNameAddressResponseDto.getId());
                        dto.setLabCertificateNumber(labNameAddressResponseDto.getLabCertificateNumber());
                    }
                });
        return dtos;
    }

    public void setGeneratedBatchNumber(Batch batch, Date mfdDate, Long manufacturerId, Category category) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMM");
        BatchNoId batchNoId = new BatchNoId(category.getId(), manufacturerId, formatter.format(mfdDate));
        BatchNoSequence batchNoSequence = batchNoSequenceManager.findById(batchNoId);
        batch.setBatchNo(String.format("%s_%s_%d_B%03d", getCategoryNamePrefix(category.getName()), formatter.format(mfdDate), manufacturerId, batchNoSequence.getSequence()));
        batchNoSequenceManager.update(batchNoSequence);
    }

    public Batch createMaterials(BatchRequestDto batchRequestDto, Category category) {
        if (ChronoUnit.YEARS.between(batchRequestDto.getDateOfManufacture().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()) >= 2L)
            throw new CustomException("Date of Manufacturer is older than 2 years", HttpStatus.BAD_REQUEST);
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Batch entity = batchMapper.toEntity(batchRequestDto);
        entity.setManufacturerId(manufacturerId);
        entity.getBatchProperties().removeIf(s -> TextUtils.isEmpty(s.getName())
                || TextUtils.isEmpty(s.getValue())
                || s.getValue().equalsIgnoreCase("undefined"));
        Batch batch = batchManager.create(entity);
        batch.setState(stateManager.findByName("fullyDispatched"));
        batch.setRemainingQuantity(batch.getTotalQuantity());
        batch.setUom(uomManager.findByConversionFactor(1L));
        batch.setLastActionDate(batchRequestDto.getDateOfManufacture());
        setGeneratedBatchNumber(batch, batchRequestDto.getDateOfManufacture(), manufacturerId, category);
        setPrefetchedInstructionsForMaterials(batch);
        batch.addLot(createLotFromBatch(batch));
        batchManager.save(batch);
        Lot lot = batch.getLots().stream().findFirst().get();
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        entityStateRequestDTO.setLotId(lot.getId());
        entityStateRequestDTO.setComments(lot.getComments());
        entityStateRequestDTO.setDateOfAction(batchRequestDto.getDateOfManufacture());
        messageManager.statusChangeHandlerForMaterial(entityStateRequestDTO, lot.getState(), keycloakInfo.getUserInfo(), keycloakInfo.getAccessToken(), false, batchRequestDto.getBatchProperties());
        return batch;
    }

    @Override
    public List<StateResponseDto> getBatchActions(Long categoryId, Long batchId, ActionType actionType, String sampleState) {
        Batch batch = batchManager.findById(batchId);
        return getBatchActions(categoryId, batch, actionType, sampleState);
    }

    public List<StateResponseDto> getBatchActions(Long categoryId, Batch batch, ActionType actionType, String sampleState) {
        if (sampleState != null && !sampleState.isEmpty()) return getBatchActionsForInspection(sampleState);
        if (batch.getTaskId() == null || batch.getIsBlocked().equals(true)) return new ArrayList<>();
        Map<String, Object> userInfo = keycloakInfo.getUserInfo();
        Set<String> roles = (Set<String>) userInfo.get("roles");
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).filter(rc -> rc.length == 3).toList();
        List<RoleCategory> roleCategories = roleCategoryManager.findAllByCategoryAndRoleNames(roleSplitList);
        List roleStates = roleCategories.stream().filter(s ->
                s.getCategory().getId().equals(batch.getCategory().getId())).flatMap(
                d -> d.getTargetStates().stream()).map(s -> s.getState().getName()).collect(Collectors.toList());
        List<RoleCategory> categoryRoleList = roleCategories.stream().filter(s ->
                s.getCategory().getId().equals(batch.getCategory().getId())).collect(Collectors.toList());
        List<String> actions = activitiManager.getTaskActions(batch, categoryRoleList, userInfo, roleStates);
        List<State> states = stateManager.findByNames(actions);
        List<StateResponseDto> result = states.stream().map(stateMapper::toDto).filter(c -> c.getActionType().equals(actionType)).toList();
        return result;
    }

    private List<StateResponseDto> getBatchActionsForInspection(String sampleState) {
        List<String> actions = Constants.sampleStateMap.getOrDefault(sampleState, new ArrayList<>());
        if (actions.isEmpty()) return new ArrayList<>();
        List<State> states = stateManager.findByActionNames(actions, StateType.BATCH);
        return states.stream().map(stateMapper::toDto).filter(s -> s.getActionType().equals(ActionType.lab)).toList();
    }

    @Override
    public boolean dispatchExternalBatch(Long categoryId, EntityStateRequestDTO entityStateRequestDTO) {
        State state = stateManager.findByName("batchToDispatch");
        entityStateRequestDTO.setStateId(state.getId());
        return updateBatchStatus(categoryId, entityStateRequestDTO, ActionType.module, null);
    }

    @Override
    public boolean updateBatchStatus(Long categoryId, EntityStateRequestDTO entityStateRequestDTO, ActionType actionType, SampleTestResult sampleTestResult) {
        if (entityStateRequestDTO.getDateOfAction().after(new Date())) {
            throw new ValidationException(" Date of Action cannot be Future Date");
        }
        Map userInfo = keycloakInfo.getUserInfo();
        Batch batch = batchManager.findById(entityStateRequestDTO.getBatchId());
        State prevState = batch.getState();
        if (batch.getLastActionDate().after(entityStateRequestDTO.getDateOfAction())) {
            throw new ValidationException("Date of action cannot be before last action date");
        }
        if (entityStateRequestDTO.getIsInspectionSample() != null && entityStateRequestDTO.getIsInspectionSample())
            return true;
        List<StateResponseDto> stateResponseDtos = getBatchActions(categoryId, batch, actionType, null);
        if (stateResponseDtos.stream().noneMatch(state -> state.getId().equals(entityStateRequestDTO.getStateId()))) {
            return false;
        }
        State state = stateManager.findById(entityStateRequestDTO.getStateId());
        List<String> roleCategoryNames = this.roleCategoryNames();
        userInfo.put("RoleNames", roleCategoryNames);

        if (Arrays.asList("batchSampleLabTestDone", "selfTestedBatch").contains(state.getName())) {
            Task task = taskService.createTaskQuery().taskId(batch.getTaskId()).singleResult();
            long labId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("labId", 0).toString());
            Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
            boolean hasLabAccess = false;
            boolean hasModuleAccess = false;
            for (String role : roleCategoryNames) {
                if (!batch.getIsBlocked() && (task.getAssignee().contains(role + '_' + labId + "_" + "LAB"))) {
                    hasLabAccess = true;
                    break;
                }
            }
            for (String role : roleCategoryNames) {
                if (!batch.getIsBlocked() && (task.getAssignee().contains(role + '_' + manufacturerId + "_" + "MODULE"))) {
                    hasModuleAccess = true;
                    break;
                }
            }

            if (hasLabAccess || hasModuleAccess)
                activitiManager.setVariable(task.getExecutionId(), "sampleTestResult", sampleTestResult.name());
        }
        Long labSampleId = null;
        if (activitiManager.completeTask(batch.getTaskId(), userInfo, state, batch.getIsBlocked())) {
            Batch savedBatch = batchManager.findById(batch.getId());
            String taskId = savedBatch.getTaskId();
            State savedState = savedBatch.getState();
            batch.setTaskId(taskId);
            batch.setState(savedState);
            String newStateName = savedState.getName();
            List<String> labStatus = List.of("sentbatchsampletolabtest", "batchsampleinlab");
            LabSampleCreateResponseDto labSampleDto = null;
            String labId;
            if (labStatus.contains(newStateName.toLowerCase())) {
                String token = keycloakInfo.getAccessToken();
                Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
                if (newStateName.toLowerCase().equals(labStatus.get(0))) {
                    if (entityStateRequestDTO.getLabId() != null && entityStateRequestDTO.getLabId() != 0L) {
                        labSampleDto = LabServiceManagementHelper.createSample(batch, token, entityStateRequestDTO.getDateOfAction(), entityStateRequestDTO.getLabId());
                    } else {
                        labSampleDto = LabServiceManagementHelper.createSample(batch, token, entityStateRequestDTO.getDateOfAction(), null);
                    }
                    labId = labSampleDto.getLabId().toString();
                    activitiManager.setVariable(task.getExecutionId(), "batchLabId", labId);
                    labSampleId = labSampleDto.getId();
                } else {
                    labId = activitiManager.getVariable(task.getExecutionId(), "batchLabId");
                }
                String[] assigneesList = task.getAssignee().split(",");
                List<String> newAssignees = new ArrayList<>();
                Arrays.stream(assigneesList).forEach(d -> {
                    String[] assignees = d.split("_");
                    assignees[1] = labId;
                    newAssignees.add(String.join("_", assignees));
                });
                task.setAssignee(String.join(",", newAssignees));
                taskService.saveTask(task);
            } else {
                labId = null;
            }
            if (Arrays.asList("batchSampleLabTestDone", "selfTestedBatch").contains(state.getName())) {
                batch.setIsLabTested(true);
            }
            batch.setLastActionDate(entityStateRequestDTO.getDateOfAction());
            batchManager.update(batch);
            try {
                if ((savedState.getName().equals("rejected") || savedState.getName().equals("batchToDispatch")
                        && (state.getName().equals("batchSampleLabTestDone") || state.getName().equals("selfTestedBatch")))) {
                    messageManager.sendBatchUpdate(
                            batch.getManufacturerId(),
                            categoryId,
                            batch.getTotalQuantity() * batch.getUom().getConversionFactorKg(),
                            state, keycloakInfo.getUserInfo(), keycloakInfo.getAccessToken(),
                            batch.getDateOfManufacture(),
                            sampleTestResult,
                            batch.getIsLabTested(), batch.getId());
                    notificationBuilder(batch.getCategory(), batch, prevState, state, labId, labSampleId);
                    notificationBuilder(batch.getCategory(), batch, state, savedState, labId, labSampleId);
                    Map<State, Boolean> stateMap = new LinkedHashMap<>();
                    stateMap.put(state, false);
                    stateMap.put(savedState, true);
                    sendStatusHandlerMessage(stateMap, entityStateRequestDTO, userInfo, keycloakInfo.getAccessToken(), batch);
                } else {
                    messageManager.statusChangeHandler(entityStateRequestDTO, savedState, userInfo, batch.getManufacturerId(), keycloakInfo.getAccessToken(), false);
                    notificationBuilder(batch.getCategory(), batch, prevState, state, labId, labSampleId);
                }
                messageManager.sendBatchUpdate(
                        batch.getManufacturerId(),
                        categoryId,
                        batch.getTotalQuantity() * batch.getUom().getConversionFactorKg(),
                        savedState, keycloakInfo.getUserInfo(), keycloakInfo.getAccessToken(),
                        batch.getDateOfManufacture(),
                        null,
                        batch.getIsLabTested(), batch.getId());
            } catch (Exception e) {
                if (labSampleDto != null)
                    LabServiceManagementHelper.deleteSample(labSampleDto, keycloakInfo.getAccessToken());
                throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
            }
        }
        return true;
    }

    @Async
    public void sendStatusHandlerMessage(Map<State, Boolean> stateMap, EntityStateRequestDTO entityStateRequestDTO, Map userInfo, String token, Batch batch) {
        for (Map.Entry<State, Boolean> entry : stateMap.entrySet()) {
            State state = entry.getKey();
            Boolean isFlowSkipped = entry.getValue();
            messageManager.statusChangeHandler(
                    entityStateRequestDTO,
                    state,
                    userInfo,
                    batch.getManufacturerId(),
                    token,
                    isFlowSkipped
            );
        }
    }

    private void notificationBuilder(Category category, Batch batch, State prevState, State state, String labId, Long labSampleId) {
        messageManager.sendNotification(
                FirebaseEvent
                        .builder()
                        .id(batch.getId())
                        .entity(EntityType.batch)
                        .targetManufacturerId(null)
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .manufacturerId(batch.getManufacturerId())
                        .labId(labId)
                        .notificationDate(LocalDateTime.now())
                        .entityNo(batch.getBatchNo())
                        .isIndependentBatch(category.isIndependentBatch())
                        .currentStateName(state.getName())
                        .previousStateName(prevState.getName())
                        .currentStateDisplayName(state.getDisplayName())
                        .previousStateDisplayName(prevState.getDisplayName())
                        .labSampleId(labSampleId)
                        .build()
        );
    }

    private boolean isSuperAdmin() {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
    }

    private List<String> roleCategoryNames() {
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).filter(rc -> rc.length == 3).toList();
        List<RoleCategory> roleCategories = roleCategoryManager.findAllByCategoryAndRoleNames(roleSplitList);
        return roleCategories.stream()
                .map(RoleCategory::getRoleName).collect(Collectors.toList());
    }

    @Override
    public BatchMonitorResponseDto getHistoryForBatch(Long batchId) {
        Batch batch = batchManager.findById(batchId);
        if (!checkLabAccess(batchId)) throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        return utils.checkBatchHistory(batch);
    }

    @Override
    public BatchResponseDto getDetailsForUUID(String uuid) {
        Batch batch = batchManager.findByUUID(uuid);
        return batchMapper.toDto(batch);
    }


    @Override
    public void deleteBatch(Long id) {
        batchManager.delete(id);
    }

    public void setPrefetchedInstructions(Batch batch) {
        Category category = categoryManager.findById(batch.getCategory().getId());
        List<Lot> sourceLots = lotManager.getAllByIds(batch.getMixes().stream().map(d -> d.getSourceLot().getId()).toList());
        List<UOM> uoms = uomManager.findAllByIds(batch.getMixes().stream().map(d -> d.getUom().getId()).toList());
        List<String> mixesList = batch.getMixes().stream().map(d -> {
            Lot sourceLot = sourceLots.stream().filter(l -> d.getSourceLot().getId().equals(l.getId())).findFirst().get();
            UOM uom = uoms.stream().filter(u -> d.getUom().getId().equals(u.getId())).findFirst().get();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            String doe;
            AtomicReference<Date> expiryDate = new AtomicReference<>(new Date(Long.MAX_VALUE));
            if (sourceLot.getBatch() != null) {
                doe = sdf.format(sourceLot.getBatch().getDateOfExpiry());
            } else {
                sourceLot.getTargetBatchMapping().forEach(batchLotMapping -> {
                    if (expiryDate.get().after(batchLotMapping.getSourceLot().getBatch().getDateOfExpiry())) {
                        expiryDate.set(batchLotMapping.getSourceLot().getBatch().getDateOfExpiry());
                    }
                });
                doe = sdf.format(expiryDate.get());
            }
            String qused = d.getQuantityUsed() + " " + uom.getName();
            String lotNo = sourceLot.getLotNo();

            return qused + " of " + lotNo + " having expiry " + doe;
        }).toList();
        List<String> properties = batch.getBatchProperties().stream().filter(d -> d.getName()
                .contains("percentage")
        ).map(BatchProperty::getValue).toList();
        String result = "";

        Optional<BatchProperty> manufacturerBatchNo = batch.getBatchProperties().stream()
                .filter(bp -> bp.getName().equals("manufacture_batchNumber"))
                .findFirst();
        if (manufacturerBatchNo.isPresent()) {
            result = "Manufacturer Batch number is " + manufacturerBatchNo.get().getValue() + ", ";
        }

        if (!mixesList.isEmpty()) {
            String helper = "";
            if (mixesList.size() > 1) {
                helper = "are";
            } else {
                helper = "is";
            }
            result += String.join(", ", mixesList) + " " + helper + " used in preparing this " + category.getName();
        }
        batch.setPrefetchedInstructions(result);
    }

    public void setPrefetchedInstructionsForMaterials(Batch batch) {
        Category category = categoryManager.findById(batch.getCategory().getId());
        String result = "";
        Optional<BatchProperty> manufacturerBatchNo = batch.getBatchProperties().stream()
                .filter(bp -> bp.getName().equals("manufacture_batchNumber"))
                .findFirst();
        if (manufacturerBatchNo.isPresent()) {
            result = "Manufacturer Batch number is " + manufacturerBatchNo.get().getValue() + ". ";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String doe = sdf.format(batch.getDateOfExpiry());

        Optional<BatchProperty> batchName = batch.getBatchProperties().stream()
                .filter(bp -> bp.getName().equals("batch_name"))
                .findFirst();
        result += "Batch name is " + batchName.get().getValue() + " having expiry " + doe + ". ";

        batch.setPrefetchedInstructions(result);
    }


    @Override
    public Boolean checkLabAccess(Long batchId) {
        Batch batch = batchManager.findById(batchId);
        if (categoryManager.isCategorySuperAdmin(batch.getCategory().getId(), RoleCategoryType.MODULE)) return true;
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        if (roles.stream().anyMatch(r -> r.contains("MONITOR"))) return true;
        if (categoryManager.isCategoryInspectionUser(batch.getCategory().getId(), RoleCategoryType.MODULE))
            return true;
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        return Objects.equals(manufacturerId, batch.getManufacturerId()) || batch.getLots().stream().anyMatch(d -> Objects.equals(d.getTargetManufacturerId(), manufacturerId));
    }

    @Override
    public void updateBatchInspectionStatus(Long batchId, Boolean isBlocked) {
        Batch batch = batchManager.findById(batchId);
        batch.setIsBlocked(isBlocked);
        batchManager.update(batch);
    }

    @Override
    public Resource getBatchInventoryExcel(ListResponse<BatchListResponseDTO> batches) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Lot Usage Details");
            Row headerRow = sheet.createRow(0);

            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Write field names as the first row
            for (int i = 0; i < inventoryDetailsColumnNames.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(inventoryDetailsColumnNames[i]);
                cell.setCellStyle(headerCellStyle);
            }

            CellStyle districtDataCellStyle = workbook.createCellStyle();
            districtDataCellStyle.setAlignment(HorizontalAlignment.LEFT);

            List<BatchListResponseDTO> batchInventory = batches.getData();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            // Write DashboardResponseDto objects to Excel
            for (int rowIndex = 0; rowIndex < batchInventory.size(); rowIndex++) {
                Row dataRow = sheet.createRow(rowIndex + 1); // Start from row 1 (row 0 is for header)
                BatchListResponseDTO dto = batchInventory.get(rowIndex);

                // Set cell values from the DashboardResponseDto
                dataRow.createCell(0).setCellValue(rowIndex + 1);
                dataRow.createCell(1).setCellValue(dto.getBatchNo());
                dataRow.createCell(2).setCellValue(sdf.format(dto.getDateOfManufacture()));
                dataRow.createCell(3).setCellValue(sdf.format(dto.getDateOfExpiry()));
                dataRow.createCell(4).setCellValue(dto.getTotalQuantity());
                dataRow.createCell(5).setCellValue(dto.getRemainingQuantity());
                dataRow.createCell(6).setCellValue(dto.getState());

                for (int k = 0; k < inventoryDetailsColumnNames.length; k++)
                    dataRow.getCell(k).setCellStyle(districtDataCellStyle);
            }

            // Auto-size columns to fit content
            for (int i = 0; i < inventoryDetailsColumnNames.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return new InputStreamResource(inputStream);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public ListResponse<BatchListResponseDTO> getFilteredBatch(String search) {
        List<Batch> batches=batchPropertyManager.searchBatches(search);
        Long count = batchPropertyManager.getCountOfSearchedBatches(search);
        ListResponse<BatchListResponseDTO> dtos = ListResponse.from(batches, batchListMapper::toListDTO, count);
        return dtos;
    }

    @Override
    public BatchResponseDto getBatchByIdForEventUpdate(Long id) {
        Batch batch = batchManager.findById(id);
        return getBatchDetailsHelper(batch);
    }

    @Override
    public void eventUpdateBody(String encrypted, EventTest param) {
        ExternalMetaData clientIdEntity = externalMetaDataManager.findByKeyAndService("clientId", param.name());
        ExternalMetaData clientSecretEntity = externalMetaDataManager.findByKeyAndService("clientSecret", param.name());
        String clientId = clientIdEntity.getValue();
        String clientSecret = clientSecretEntity.getValue();
        String algo = "AES/CBC/PKCS5PADDING";

        try {
            IvParameterSpec iv = new IvParameterSpec(clientId.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            String decryptedBody = new String(original);
            log.info("Decrypted body from event framework: {{}}", decryptedBody);
            eventBodyDao.create(new EventBody(null, decryptedBody));
        } catch (Exception ex) {
            log.info("Exception at event framework decryption : {{}}", ex.getMessage());
        }
    }

}
