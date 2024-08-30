package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.iam.AddressResponseDto;
import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.EntityType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.helper.*;
import com.beehyv.fortification.manager.*;
import com.beehyv.fortification.mapper.*;
import com.beehyv.fortification.service.LotService;
import com.beehyv.fortification.service.MixMappingService;
import com.beehyv.fortification.service.WastageService;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.fileUpload.service.StorageClient;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class LotServiceImpl implements LotService {
    private final BaseMapper<LotResponseDto, LotRequestDto, Lot> mapper = BaseMapper.getForClass(LotMapper.class);
    private final BaseMapper<StateResponseDto, StateRequestDto, State> stateMapper = BaseMapper.getForClass(StateMapper.class);
    private final BaseMapper<LotListResponseDTO, LotRequestDto, Lot> lotListMapper = BaseMapper.getForListClass(LotMapper.class);
    private final BaseMapper<SizeUnitResponseDto, SizeUnitRequestDto, SizeUnit> sizeUnitMapper = BaseMapper.getForClass(SizeUnitMapper.class);
    private LotManager manager;
    @Autowired
    private KeycloakInfo keycloakInfo;
    @Autowired
    private ActivitiManager activitiManager;
    @Autowired
    private CategoryManager categoryManager;
    @Autowired
    private LabConfigCategoryManager labConfigCategoryManager;
    @Autowired
    private StateManager stateManager;
    @Autowired
    private RoleCategoryManager roleCategoryManager;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FunctionUtils functionUtils;

    @Autowired
    private MessageManager messageManager;
    private final UOMManager uomManager;
    private final MixMappingService mixMappingService;
    private final BatchManager batchManager;
    private final FunctionUtils utils;
    private final ExternalMetaDataManager externalMetaDataManager;
    @Autowired
    private StorageClient storageClient;
    @Autowired
    private WastageService wastageService;

    public static final Map<String, String> stateMap;

    static {
        stateMap = new HashMap<>();
        stateMap.put("dispatched", "source");
        stateMap.put("lotReceived", "target");
        stateMap.put("selfTestedLot", "target");
        stateMap.put("sentLotSampleToLabTest", "target");
        stateMap.put("lotSampleInLab", "target");
        stateMap.put("lotSampleLabTestDone", "target");
        stateMap.put("approved", "target");
        stateMap.put("toSendBackRejected", "target");
        stateMap.put("sentBackRejected", "target");
        stateMap.put("receivedRejected", "source");
        stateMap.put("lotSampleRejected", "target");
    }

    private final String[] batchDetailsColumnNames = {"SL. NO", "Batch No", "Manufacturer Batch Number", "Manufacturing Date", "Expiry Date", "Quantity Used", "Stage"};

    private final String[] inventoryDetailsColumnNames = {"SL. NO", "Lot Number", "Manufacturing Date", "Expiry Date", "Total Quantity(KG)", "Remaining Quantity(KG)", "Stage"};

    @Override
    public Long createLot(Long categoryId, LotRequestDto dto) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        if (dto.getExternalTargetManufacturerId() != null && !dto.getExternalTargetManufacturerId().isEmpty()) {
            log.info("Request received for creating lot: {}", dto);
            String url = Constants.IAM_SERVICE_URL + "manufacturer/externalManufacturerId/" + dto.getExternalTargetManufacturerId();
            manufacturerId = IamServiceRestHelper.fetchResponse(url, Long.class, keycloakInfo.getAccessToken());
            dto.setTargetManufacturerId(manufacturerId);
        }
        if (dto.getExternalTargetManufacturerId() != null && !dto.getExternalTargetManufacturerId().isEmpty()) {
            log.info("Request received for creating lot: {}", dto);
            String url = Constants.IAM_SERVICE_URL + "manufacturer/externalManufacturerId/" + dto.getExternalTargetManufacturerId();
            manufacturerId = IamServiceRestHelper.fetchResponse(url, Long.class, keycloakInfo.getAccessToken());
            dto.setTargetManufacturerId(manufacturerId);
        }
        Lot entity = mapper.toEntity(dto);
        entity.setDistrictGeoId(IamServiceRestHelper.getDistrictGeoIdByManufacturerId(dto.getTargetManufacturerId(), keycloakInfo.getAccessToken()));
        Long batchId = dto.getBatchId();
        Batch batch = batchManager.findById(batchId);
        entity.setSourceBatchMapping(new HashSet<>());
        entity.setBatch(batch, entity);
        entity.setPrefetchedInstructions(batch.getPrefetchedInstructions());
        if (dto.getTargetManufacturerId() != null) {
            log.info("Requested targetManufactureId: {}", dto.getTargetManufacturerId());
            entity.setAction(functionUtils.converStringToEnum(dto.getTargetManufacturerId(), categoryId, keycloakInfo.getAccessToken()));
        }
        entity.setManufacturerId(batch.getManufacturerId());
        entity.setCategory(new Category(categoryId));
        if ((!Objects.equals(batch.getManufacturerId(), manufacturerId))) {
            throw new ValidationException("Batch doesn't belongs to your organisation");
        }
        if (batch.getCategory().isIndependentBatch()) {
            throw new ValidationException("Cannot create lot from a Raw Material");
        }
        this.validateLot(dto, entity, batch);

        AddressResponseDto sourceManufacturerAddress = IamServiceRestHelper.getManufacturerAddress(entity.getManufacturerId(), keycloakInfo.getAccessToken());
        if (sourceManufacturerAddress != null && sourceManufacturerAddress.getVillage() != null && sourceManufacturerAddress.getVillage().getDistrict() != null) {
            entity.setSourceDistrictGeoId(sourceManufacturerAddress.getVillage().getDistrict().getGeoId());
            if (sourceManufacturerAddress.getVillage().getDistrict().getState() != null) {
                entity.setSourceStateGeoId(sourceManufacturerAddress.getVillage().getDistrict().getState().getGeoId());
            }
        }

        AddressResponseDto targetManufacturerAddress = IamServiceRestHelper.getManufacturerAddress(entity.getTargetManufacturerId(), keycloakInfo.getAccessToken());
        if (targetManufacturerAddress != null && targetManufacturerAddress.getVillage() != null && targetManufacturerAddress.getVillage().getDistrict() != null) {
            entity.setTargetDistrictGeoId(targetManufacturerAddress.getVillage().getDistrict().getGeoId());
            if (targetManufacturerAddress.getVillage().getDistrict().getState() != null) {
                entity.setTargetStateGeoId(targetManufacturerAddress.getVillage().getDistrict().getState().getGeoId());
            }
        }

        if (Objects.equals(batch.getState().getName(), "batchToDispatch")
                || Objects.equals(batch.getState().getName(), "partiallyDispatched")) {
            Double lotTotalQuantityInKg = entity.getTotalQuantity() * uomManager.findById(entity.getUom().getId()).getConversionFactorKg();
            if (dto.getDateOfDispatch().before(batch.getLastActionDate())) {
                throw new ValidationException("Lot dispatch date cannot be before batch ready to be dispatch date");
            }
            if (batch.getRemainingQuantity() - lotTotalQuantityInKg >= 0) {
                Double remainingQuantity = batch.getRemainingQuantity() - lotTotalQuantityInKg;
                batch.setRemainingQuantity(remainingQuantity);
            } else {
                throw new ValidationException("Batch with number " + batch.getBatchNo() + " doesn't contain enough quantity");
            }
            State state = batch.getRemainingQuantity() > 0 ? stateManager.findByName("partiallyDispatched") : stateManager.findByName("fullyDispatched");
            batch.setState(state);
            Integer lotSequence = Optional.ofNullable(batch.getLotSequence()).orElse(0);
            entity.setLotNo(String.format("%s_L%02d", batch.getBatchNo(), lotSequence));

            ExternalMetaData externalMetaData = externalMetaDataManager.findByKeyAndService("lotDispatch", "TCS");
            if (dto.getTransportQuantityDetailsRequestDto() != null && !dto.getTransportQuantityDetailsRequestDto().getVehicleDetailsRequestDtos().isEmpty() && externalMetaData != null) {
                String purchaseOrderUrl = Constants.IAM_SERVICE_URL + "purchase-order/dispatchable-quantity";
                TransportVehicleDetailsRequestDto transportVehicleDetailsRequestDto = dto.getTransportQuantityDetailsRequestDto().getVehicleDetailsRequestDtos().stream().toList().get(0);
                String driverUid = transportVehicleDetailsRequestDto.getDriverUid();
                UriComponents purchaseOrderUri = UriComponentsBuilder.fromHttpUrl(purchaseOrderUrl)
                        .queryParam("purchaseOrderId", dto.getTransportQuantityDetailsRequestDto().getPurchaseOrderId())
                        .queryParam("childPurchaseOrderId", transportVehicleDetailsRequestDto.getChildPurchaseOrderId())
                        .build();
                Double dispatchableQuantity = IamServiceRestHelper.fetchResponse(purchaseOrderUri.toString(), Double.class, keycloakInfo.getAccessToken());
                if (entity.getTotalQuantity() > dispatchableQuantity) {
                    throw new CustomException("Lot quantity exceeds max dispatchable quantity by " + (entity.getTotalQuantity() - dispatchableQuantity) + "kg", HttpStatus.BAD_REQUEST);
                }
            }

            Lot savedLot = manager.create(entity);
            Lot lot = manager.findById(savedLot.getId());
            Map<String, Object> userInfo = keycloakInfo.getUserInfo();
            Long targetCategoryId = dto.getTargetCategoryId();
            LabConfigCategory labConfigCategory =
                    labConfigCategoryManager.findByCategoryIds(categoryId, targetCategoryId);
            String labOption = "";
            if (labConfigCategory == null) {
                labOption = "optional";
            } else {
                labOption = labConfigCategory.getLabOption().toLowerCase();
            }
            if (labOption.isBlank()) {
                labOption = "na";
            }
            Task task = activitiManager.startLotProcess(lot, userInfo, batch.getCategory(), labOption);
            lot.setState(stateManager.findByName(task.getTaskDefinitionKey()));
            lot.setLastActionDate(dto.getDateOfDispatch());
            manager.update(lot);

            if (dto.getTransportQuantityDetailsRequestDto() != null && !dto.getTransportQuantityDetailsRequestDto().getVehicleDetailsRequestDtos().isEmpty()) {
                try {
                    String purchaseOrderUrl = Constants.IAM_SERVICE_URL + "purchase-order/dispatchable-quantity";
                    String childPurchaseOrderId = dto.getTransportQuantityDetailsRequestDto().getVehicleDetailsRequestDtos().stream().toList().get(0).getChildPurchaseOrderId();
                    UriComponents purchaseOrderUri = UriComponentsBuilder.fromHttpUrl(purchaseOrderUrl)
                            .queryParam("purchaseOrderId", dto.getTransportQuantityDetailsRequestDto().getPurchaseOrderId())
                            .queryParam("childPurchaseOrderId", childPurchaseOrderId)
                            .queryParam("quantity", entity.getTotalQuantity())
                            .build();
                    IamServiceRestHelper.updateDispatchedQuantity(purchaseOrderUri.toString(), keycloakInfo.getAccessToken());
                } catch (Exception e) {
                    log.info("Error occurred while updating dispatched quantity " + e.getMessage());
                }
            }

            batch.setLotSequence(lotSequence + 1);
            batchManager.update(batch);
            String categoryName = batch.getCategory().getName().toLowerCase();
            if (categoryName.split(" ").length > 1) {
                categoryName = String.join("-", categoryName.split(" "));
            }
            String qrCodeText = String.format("%s%s/%s/lot/code/%s", Constants.QRCODE_UI_URL, categoryName, lot.getCategory().getId(), lot.getUuid());
            storageClient.save(qrCodeText, Constants.FORTIFICATION_LOT_QR_CODE);
            EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
            entityStateRequestDTO.setLotId(lot.getId());
            entityStateRequestDTO.setComments(lot.getComments());
            entityStateRequestDTO.setDateOfAction(lot.getDateOfDispatch());
            messageManager.statusChangeHandler(
                    entityStateRequestDTO,
                    lot.getState(),
                    userInfo,
                    stateMap.get(lot.getState().getName()).equals("source") ? lot.getManufacturerId() : lot.getTargetManufacturerId(),
                    keycloakInfo.getAccessToken(),
                    false
            );
            messageManager.sendLotUpdate(
                    lot.getTargetManufacturerId(),
                    batch.getCategory().getId(),
                    batch.getManufacturerId(),
                    lot.getTotalQuantity() * lot.getUom().getConversionFactorKg(),
                    lot.getState().getName(),
                    lot.getDateOfDispatch(),
                    null,
                    keycloakInfo.getAccessToken(),
                    lot.getBatch().getIsLabTested(),
                    lot.getId()
            );

            notificationBuilder(lot.getBatch().getCategory(), lot, new State(), lot.getState(), null, null);
            return lot.getId();
        } else if (Objects.equals(batch.getState().getName(), "fullyDispatched")) {
            throw new ValidationException("Batch is fully dispatched");
        } else {
            throw new ValidationException("Batch is not approved");
        }
    }

    @Override
    public List<Long> createTargetLotFromSourceLots(TargetLotRequestDto dto, Long categoryId, Boolean externalDispatch) {
        if (dto.getExternalTargetManufacturerId() != null && !dto.getExternalTargetManufacturerId().isEmpty()) {
            String url = Constants.IAM_SERVICE_URL + "manufacturer/externalManufacturerId/" + dto.getExternalTargetManufacturerId();
            Long manufacturerId = IamServiceRestHelper.fetchResponse(url, Long.class, keycloakInfo.getAccessToken());
            dto.setTargetManufacturerId(manufacturerId);
            Long uomId = uomManager.findByName("Kg").getId();
            dto.getMixes().forEach(m -> {
                List<Lot> lotList = manager.findByLotNo(m.getSourceLotNo());
                Lot lot = lotList.stream().filter(l -> l.getTargetBatchMapping().isEmpty()).toList().get(0);
                m.setSourceLotId(lot.getId());
                m.setUomId(uomId);
            });
        }

        List<UOM> uomList = uomManager.findAllByIds(dto.getMixes().stream().map(MixMappingRequestDto::getUomId).collect(Collectors.toList()));
        List<Lot> lotList = manager.findAllByIds(dto.getMixes().stream().map(MixMappingRequestDto::getSourceLotId).collect(Collectors.toList()));
        Map<String, Object> userInfo = keycloakInfo.getUserInfo();
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        UOM uomKg = uomManager.findByName("Kg");

        return dto.getMixes().stream().map(m -> {
            UOM uom = uomList.stream().filter(u -> Objects.equals(u.getId(), m.getUomId())).findFirst().get();
            Lot sourceLot = lotList.stream().filter(l -> Objects.equals(l.getId(), m.getSourceLotId())).findFirst().get();
            Double quantityUsed = m.getQuantityUsed();
            if (quantityUsed * uom.getConversionFactorKg() > sourceLot.getRemainingQuantity() * sourceLot.getUom().getConversionFactorKg()) {
                throw new CustomException("Quantity is greater than remaining quantity for lot no. " + sourceLot.getLotNo(), HttpStatus.BAD_REQUEST);
            }
            if (!Objects.equals(categoryId, sourceLot.getCategory().getId())) {
                throw new CustomException("Category Id is not matching for lot no. " + sourceLot.getLotNo(), HttpStatus.BAD_REQUEST);
            }
            if (!Objects.equals(sourceLot.getTargetManufacturerId(), manufacturerId)) {
                throw new CustomException("Manufacturer Id is not matching for lot no. " + sourceLot.getLotNo(), HttpStatus.BAD_REQUEST);
            }
            if (!sourceLot.getState().getName().equals("approved")) {
                throw new CustomException("State should be approved for lot no. " + sourceLot.getLotNo(), HttpStatus.BAD_REQUEST);
            }
            if (dto.getDateOfDispatch().before(sourceLot.getDateOfAcceptance())) {
                throw new CustomException("Date of acceptance should be less than date of dispatch for lot no. " + sourceLot.getLotNo(), HttpStatus.BAD_REQUEST);
            }


            Lot lot = new Lot();
            lot.setLotNo(String.valueOf(sourceLot.getLotNo()));
            lot.setTotalQuantity(m.getQuantityUsed() * uom.getConversionFactorKg());
            lot.setRemainingQuantity(m.getQuantityUsed() * uom.getConversionFactorKg());
            lot.setUom(uomKg);
            lot.setDateOfDispatch(dto.getDateOfDispatch());
            if (dto.getTargetManufacturerId() != null) {
                log.info("Requested targetManufactureId: {}", dto.getTargetManufacturerId());
                lot.setTargetManufacturerId(dto.getTargetManufacturerId());
                lot.setAction(functionUtils.converStringToEnum(dto.getTargetManufacturerId(), categoryId, keycloakInfo.getAccessToken()));
                AddressResponseDto targetManufacturerAddress = IamServiceRestHelper.getManufacturerAddress(lot.getTargetManufacturerId(), keycloakInfo.getAccessToken());
                if (targetManufacturerAddress != null && targetManufacturerAddress.getVillage() != null && targetManufacturerAddress.getVillage().getDistrict() != null) {
                    lot.setTargetDistrictGeoId(targetManufacturerAddress.getVillage().getDistrict().getGeoId());
                    if (targetManufacturerAddress.getVillage().getDistrict().getState() != null) {
                        lot.setTargetStateGeoId(targetManufacturerAddress.getVillage().getDistrict().getState().getGeoId());
                    }
                }
            }
            lot.setComments(dto.getComments());
            lot.setIsBlocked(false);
            lot.setIsLabTested(false);
            lot.setPrefetchedInstructions(sourceLot.getPrefetchedInstructions());
            lot.setLots(new HashSet<>(Collections.singletonList(sourceLot)));
            lot.setLastActionDate(dto.getDateOfDispatch());
            lot.setCategory(categoryManager.findById(categoryId));
            lot.setManufacturerId(lotList.stream().map(Lot::getTargetManufacturerId).findFirst()
                    .orElseThrow(() -> new CustomException("Target Manufacturer Not Found for the given mixes", HttpStatus.BAD_REQUEST)));
            AddressResponseDto sourceManufacturerAddress = IamServiceRestHelper.getManufacturerAddress(lot.getManufacturerId(), keycloakInfo.getAccessToken());
            if (sourceManufacturerAddress != null && sourceManufacturerAddress.getVillage() != null && sourceManufacturerAddress.getVillage().getDistrict() != null) {
                lot.setSourceDistrictGeoId(sourceManufacturerAddress.getVillage().getDistrict().getGeoId());
                if (sourceManufacturerAddress.getVillage().getDistrict().getState() != null) {
                    lot.setSourceStateGeoId(sourceManufacturerAddress.getVillage().getDistrict().getState().getGeoId());
                }
            }
            lot = manager.create(lot);

            sourceLot.setRemainingQuantity(sourceLot.getRemainingQuantity() * sourceLot.getUom().getConversionFactorKg() - quantityUsed * uom.getConversionFactorKg());
            sourceLot.setUom(uomKg);
            if (externalDispatch != null && externalDispatch) {
                lot.setState(stateManager.findByName("dispatched"));
            } else {
                LabConfigCategory labConfigCategory =
                        labConfigCategoryManager.findByCategoryIds(categoryId, dto.getTargetManufacturerId());
                String labOption = "";
                if (labConfigCategory == null) {
                    labOption = "optional";
                } else {
                    labOption = labConfigCategory.getLabOption().toLowerCase();
                }
                if (labOption.isBlank()) {
                    labOption = "na";
                }
                Task task = activitiManager.startLotProcess(lot, userInfo, lot.getCategory(), labOption);
                lot.setState(stateManager.findByName(task.getTaskDefinitionKey()));
                lot.setTaskId(task.getId());
            }
            String qrCodeText = String.format("%s%s/%s/lot/code/%s", Constants.QRCODE_UI_URL, lot.getCategory().getName(), lot.getCategory().getId(), lot.getUuid());
            storageClient.save(qrCodeText, Constants.FORTIFICATION_LOT_QR_CODE);
            manager.update(lot);
            if (externalDispatch == null || !externalDispatch) {
                EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
                entityStateRequestDTO.setLotId(lot.getId());
                entityStateRequestDTO.setComments(lot.getComments());
                entityStateRequestDTO.setDateOfAction(lot.getDateOfDispatch());
                messageManager.statusChangeHandler(
                        entityStateRequestDTO,
                        lot.getState(),
                        userInfo,
                        stateMap.get(lot.getState().getName()).equals("source") ? lot.getManufacturerId() : lot.getTargetManufacturerId(),
                        keycloakInfo.getAccessToken(),
                        false
                );
                messageManager.sendLotUpdate(
                        lot.getTargetManufacturerId(),
                        lot.getCategory().getId(),
                        lot.getManufacturerId(),
                        lot.getTotalQuantity() * lot.getUom().getConversionFactorKg(),
                        lot.getState().getName(),
                        lot.getDateOfDispatch(),
                        null,
                        keycloakInfo.getAccessToken(),
                        lot.getIsLabTested(),
                        lot.getId()
                );
            }
            notificationBuilder(lot.getCategory(), lot, new State(), lot.getState(), null, null);
            return lot.getId();
        }).toList();
    }

    private boolean isSuperAdmin() {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
    }

    private boolean isInspectionUser() {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("INSPECTION"));
    }

    public static Integer getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public void validateLot(LotRequestDto dto, Lot entity, Batch batch) {
        if (dto.getDateOfReceiving() != null && dto.getDateOfDispatch() != null && dto.getDateOfReceiving().before(dto.getDateOfDispatch())) {
            throw new ValidationException("Receiving Date Cannot be before Dispatch Date");
        }
        if (dto.getDateOfAcceptance() != null && dto.getDateOfReceiving() != null && dto.getDateOfAcceptance().before(dto.getDateOfReceiving())) {
            throw new ValidationException("Acceptance Date Cannot be before Receiving Date");
        }
        if (!dto.getSizeUnits().isEmpty()) {
            Set<SizeUnit> batchSizeUnitList = batch.getSizeUnits();
            Set<SizeUnit> batchSizeUnits = batchSizeUnitList.stream().filter(bs -> !bs.getIsDispatched()).collect(Collectors.toSet());
            Set<SizeUnitRequestDto> lotSizeUnits = dto.getSizeUnits();
            if (batchSizeUnits.isEmpty())
                throw new CustomException("Batch must be packaged first", HttpStatus.BAD_REQUEST);
            boolean noMatch = lotSizeUnits.stream().anyMatch(s -> batchSizeUnits.stream()
                    .noneMatch(su -> Objects.equals(su.getSize(), s.getSize()) && Objects.equals(su.getUom().getId(), s.getUomId())));
            if (noMatch) throw new CustomException("Size validation Error", HttpStatus.BAD_REQUEST);
            Map<Long, Double> batchSizeQuantityMap = batchSizeUnits.stream()
                    .collect(Collectors.groupingBy(SizeUnit::getSize, Collectors.summingDouble(SizeUnit::getQuantity)));
            Map<Long, Double> lotSizeQuantityMap = lotSizeUnits.stream()
                    .collect(Collectors.groupingBy(SizeUnitRequestDto::getSize, Collectors.summingDouble(SizeUnitRequestDto::getQuantity)));
            lotSizeUnits.stream().peek(s -> {
                if (batchSizeQuantityMap.get(s.getSize()) < lotSizeQuantityMap.get(s.getSize()))
                    throw new CustomException("Size unit validation error", HttpStatus.BAD_REQUEST);
            }).toList();
            List<SizeUnit> batchSizeUnitsList = batchSizeUnits.stream().toList();
            for (SizeUnitRequestDto sizeUnit : lotSizeUnits) {
                double quantity = sizeUnit.getQuantity();
                for (SizeUnit unit : batchSizeUnitsList) {
                    if (!(Objects.equals(unit.getSize(), sizeUnit.getSize())
                            && Objects.equals(unit.getUom().getId(), sizeUnit.getUomId())) || quantity == 0) continue;
                    double remainingQuantity = unit.getQuantity() - quantity;
                    if (remainingQuantity > 0) {
                        unit.setQuantity(remainingQuantity);
                        quantity = 0;
                    } else if (remainingQuantity == 0) {
                        unit.setQuantity(0d);
                        quantity = 0;
                    } else {
                        quantity = quantity - unit.getQuantity();
                        unit.setQuantity(0d);
                    }
                }
            }
            List<Long> uomIds = dto.getSizeUnits().stream().map(SizeUnitRequestDto::getUomId).collect(Collectors.toList());
            List<UOM> uoms = uomManager.findAllByIds(uomIds);
            Double totalQuantity = lotSizeUnits.stream()
                    .mapToDouble(element -> element.getSize() * element.getQuantity() * uoms.stream()
                            .filter(uom -> Objects.equals(uom.getId(), element.getUomId()))
                            .findFirst().get().getConversionFactorKg()).sum();
            entity.setTotalQuantity(totalQuantity);
            entity.setRemainingQuantity(totalQuantity);
            entity.setUom(uomManager.findByConversionFactor(1L));
            batchSizeUnitsList.stream().filter(s -> s.getQuantity() > 0).map(su -> batch.getSizeUnits().remove(su)).close();
            batch.getSizeUnits().addAll(new HashSet<>(batchSizeUnitsList.stream().filter(s -> s.getQuantity() > 0).toList()));
            batch.getSizeUnits().removeAll(new HashSet<>(batchSizeUnitsList.stream().filter(s -> s.getQuantity() == 0).toList()));
            List<SizeUnit> dispatchedSizeUnits = batchSizeUnitList.stream().filter(SizeUnit::getIsDispatched).toList();
            dto.getSizeUnits().forEach(su -> {
                List<SizeUnit> sizeUnits = dispatchedSizeUnits.stream().filter(sizeUnit -> sizeUnit.getSize().equals(su.getSize()) && sizeUnit.getUom().getId().equals(su.getUomId())).toList();
                SizeUnit sizeUnit;
                if (!sizeUnits.isEmpty()) {
                    sizeUnit = sizeUnits.get(0);
                    sizeUnit.setQuantity(su.getQuantity() + sizeUnits.get(0).getQuantity());
                } else {
                    sizeUnit = sizeUnitMapper.toEntity(su);
                    sizeUnit.setIsDispatched(true);
                }
                sizeUnit.setBatch(batch);
                batch.getSizeUnits().add(sizeUnit);
            });
        } else {
            entity.setTotalQuantity(dto.getTotalQuantity());
            entity.setRemainingQuantity(dto.getTotalQuantity());
            entity.setUom(new UOM(dto.getUomId()));
        }
    }

    @Override
    public List<StateResponseDto> getLotActions(Long categoryId, Long lotId, ActionType actionType, String sampleState) {
        Lot lot = manager.findById(lotId);
        return getLotActions(categoryId, lot, actionType, sampleState);
    }

    public List<StateResponseDto> getLotActions(Long categoryId, Lot lot, ActionType actionType, String sampleState) {
        if (sampleState != null && !sampleState.isEmpty()) return getLotActionsForInspection(sampleState);
        if (lot.getTaskId() == null || lot.getIsBlocked().equals(true)) return new ArrayList<>();
        Map<String, Object> userInfo = keycloakInfo.getUserInfo();
        Set<String> roles = (Set<String>) userInfo.get("roles");
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).filter(rc -> rc.length == 3).toList();
        List<RoleCategory> roleCategories = roleCategoryManager.findAllByCategoryAndRoleNames(roleSplitList);
        List<String> roleStates = roleCategories.stream().filter(s ->
                s.getCategory().getId().equals(lot.getCategory().getId())).flatMap(
                d -> d.getTargetStates().stream()).map(s -> s.getState().getName()).collect(Collectors.toList());
        List<String> actions = activitiManager.getTaskActions(lot, roleCategories, userInfo, roleStates);
        List<State> states = stateManager.findByNames(actions);
        return states.stream().map(stateMapper::toDto).filter(c -> c.getActionType().equals(actionType)).collect(Collectors.toList());

    }

    private List<StateResponseDto> getLotActionsForInspection(String sampleState) {
        List<String> actions = Constants.sampleStateMap.getOrDefault(sampleState, new ArrayList<>());
        if (actions.isEmpty()) return new ArrayList<>();
        List<State> states = stateManager.findByActionNames(actions, StateType.LOT);
        return states.stream().map(stateMapper::toDto).filter(s -> s.getActionType().equals(ActionType.lab)).toList();
    }

    @Override
    public boolean receiveLot(LotReceiveRequestDto dto, Long categoryId, List<Long> ids) {
        log.info("External Receive lot dto : {{}}", dto);
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        List<Lot> lotList = new ArrayList<>();
        if (ids.isEmpty()) {
            Lot lot = manager.findByLotNo(dto.getLotNo()).stream().filter(l -> l.getTargetBatchMapping().isEmpty()).toList().get(0);
            lotList = Collections.singletonList(lot);
        } else {
            lotList = manager.findAllByIds(ids);
        }

        for (Lot lot : lotList) {
            Long manufacturerId = null;
            if (dto.getExternalTargetManufacturerId() != null && dto.getExternalTargetManufacturerId().isEmpty()) {
                String url = Constants.IAM_SERVICE_URL + "manufacturer/externalManufacturerId/" + dto.getExternalTargetManufacturerId();
                manufacturerId = IamServiceRestHelper.fetchResponse(url, Long.class, keycloakInfo.getAccessToken());
            }
            long tokenManufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
            if (tokenManufacturerId != 0L && !lot.getTargetManufacturerId().equals(tokenManufacturerId)) {
                throw new CustomException(lot.getLotNo() + " does not belongs to this manufacturer", HttpStatus.BAD_REQUEST);
            } else if (tokenManufacturerId == 0L && Objects.equals(manufacturerId, lot.getTargetManufacturerId())) {
                throw new CustomException(lot.getLotNo() + " does not belongs to this manufacturer", HttpStatus.BAD_REQUEST);
            }
            entityStateRequestDTO.setLotId(lot.getId());
            State state = stateManager.findByName("lotReceived");
            entityStateRequestDTO.setStateId(state.getId());
            entityStateRequestDTO.setComments(dto.getComments());
            entityStateRequestDTO.setDateOfAction(dto.getDateOfAction());
            if (updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, false)) {
                if (dto.getAcknowledgedQuantity() != null && dto.getAcknowledgedQuantity() != 0D && (lot.getRemainingQuantity() - dto.getAcknowledgedQuantity()) > 0D) {
                    WastageRequestDto wastageRequestDto = new WastageRequestDto();
                    wastageRequestDto.setLotId(lot.getId());
                    wastageRequestDto.setUomId(uomManager.findByName("Kg").getId());
                    wastageRequestDto.setWastageQuantity(lot.getRemainingQuantity() - dto.getAcknowledgedQuantity());
                    wastageRequestDto.setReportedDate(dto.getDateOfAction());
                    wastageRequestDto.setComments("Reported acknowledged quantity is " + dto.getAcknowledgedQuantity() + "Kg");
                    wastageService.createLotWastage(wastageRequestDto, lot.getId());
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean acceptLot(LotReceiveRequestDto dto, Long categoryId, List<Long> ids) {
        log.info("External Receive lot dto : {{}}", dto);
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        List<Lot> lotList = new ArrayList<>();
        if (ids.isEmpty()) {
            Lot lot = manager.findByLotNo(dto.getLotNo()).stream().filter(l -> l.getTargetBatchMapping().isEmpty()).toList().get(0);
            lotList = Collections.singletonList(lot);
        } else {
            lotList = manager.findAllByIds(ids);
        }

        for (Lot lot : lotList) {
            Long manufacturerId = null;
            if (dto.getExternalTargetManufacturerId() != null && dto.getExternalTargetManufacturerId().isEmpty()) {
                String url = Constants.IAM_SERVICE_URL + "manufacturer/externalManufacturerId/" + dto.getExternalTargetManufacturerId();
                manufacturerId = IamServiceRestHelper.fetchResponse(url, Long.class, keycloakInfo.getAccessToken());
            }
            long tokenManufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
            if (tokenManufacturerId != 0L && !lot.getTargetManufacturerId().equals(tokenManufacturerId)) {
                throw new CustomException(lot.getLotNo() + " does not belongs to this manufacturer", HttpStatus.BAD_REQUEST);
            } else if (tokenManufacturerId == 0L && Objects.equals(manufacturerId, lot.getTargetManufacturerId())) {
                throw new CustomException(lot.getLotNo() + " does not belongs to this manufacturer", HttpStatus.BAD_REQUEST);
            }
            entityStateRequestDTO.setLotId(lot.getId());
            State state = stateManager.findByName("approved");
            entityStateRequestDTO.setStateId(state.getId());
            entityStateRequestDTO.setComments(dto.getComments());
            entityStateRequestDTO.setDateOfAction(dto.getDateOfAction());
            if (!updateLotState(categoryId, entityStateRequestDTO, ActionType.module, null, false)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateLotState(Long categoryId, EntityStateRequestDTO entityStateRequestDTO, ActionType actionType,
                                  SampleTestResult sampleTestResult, Boolean isExternalTest) {
        Map userInfo = keycloakInfo.getUserInfo();
        Lot lot = manager.findById(entityStateRequestDTO.getLotId());
        State prevState = lot.getState();
        if (lot.getLastActionDate().after(entityStateRequestDTO.getDateOfAction())) {
            throw new ValidationException("Date of action cannot be before last action date");
        }
        List<String> roleCategoryNames = this.roleCategoryNames();
        State state = stateManager.findById(entityStateRequestDTO.getStateId());
        List<StateResponseDto> stateResponseDtos = getLotActions(categoryId, lot, actionType, null);
        if (stateResponseDtos.stream().noneMatch(s -> s.getId().equals(entityStateRequestDTO.getStateId())) &&
                !(entityStateRequestDTO.getIsInspectionSample() != null && entityStateRequestDTO.getIsInspectionSample() && isExternalTest)) {
            return false;
        }
        if (state.getName().equals("lotReceived") && lot.getDateOfDispatch().after(entityStateRequestDTO.getDateOfAction())) {
            throw new ValidationException(" Receiving Date Date cannot be before Dispatch Date");
        }
        if (state.getName().equals("approved") && lot.getDateOfReceiving().after(entityStateRequestDTO.getDateOfAction())) {
            throw new ValidationException(" Acceptance Date Date cannot be before Receiving Date");
        }
        if (entityStateRequestDTO.getIsInspectionSample() != null && entityStateRequestDTO.getIsInspectionSample()) {
            if (isExternalTest) {
                if (prevState.getName().equals("lotReceived") && (state.getName().equals("lotSampleLabTestDone")
                        || state.getName().equals("selfTestedLot"))) {
                    this.assignActivitiVariable(lot, roleCategoryNames, List.of("externalTest", "sampleTestResult"), Map.of(
                            "externalTest", isExternalTest.toString(),
                            "sampleTestResult", sampleTestResult.name()
                    ), isExternalTest);
                    Category category = lot.getCategory();
                    state = stateManager.findByName("sentLotSampleToLabTest");
                    Task task = taskService.createTaskQuery().taskId(lot.getTaskId()).singleResult();
                    List<String> newAssignees = new ArrayList<>();
                    newAssignees.add(String.format("%s_%s_%s_%s", "USER", entityStateRequestDTO.getLabId().toString(), RoleCategoryType.LAB.name(), category.getName()));
                    newAssignees.add(String.format("%s_%s_%s_%s", "ADMIN", entityStateRequestDTO.getLabId().toString(), RoleCategoryType.LAB.name(), category.getName()));
                    task.setAssignee(String.join(",", newAssignees));
                    taskService.saveTask(task);

                }
            } else {
                return true;
            }
        }
        userInfo.put("RoleNames", roleCategoryNames);
        if (Arrays.asList("selfTestedLot", "lotSampleLabTestDone").contains(state.getName())) {
            this.assignActivitiVariable(lot, roleCategoryNames, List.of("sampleTestResult"), Map.of("sampleTestResult", sampleTestResult.name()), isExternalTest);
        }
        userInfo.put("RoleNames", roleCategoryNames);
        Long labSampleId = null;
        if (activitiManager.completeTask(lot.getTaskId(), userInfo, state, lot.getIsBlocked())) {
            Lot savedLot = manager.findById(lot.getId());
            String taskId = savedLot.getTaskId();
            State savedState = savedLot.getState();
            lot.setTaskId(taskId);
            lot.setState(savedState);
            String newStateName = savedState.getName();
            List<String> labStatus = List.of("sentlotsampletolabtest", "lotsampleinlab");
            LabSampleCreateResponseDto labSampleDto = null;
            lot.setLastActionDate(entityStateRequestDTO.getDateOfAction());
            if (Arrays.asList("selfTestedLot", "lotSampleLabTestDone").contains(state.getName())) {
                lot.setIsLabTested(true);
            }
            String labId;
            if (labStatus.contains(savedState.getName().toLowerCase())) {
                String token = keycloakInfo.getAccessToken();
                Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
                if (newStateName.toLowerCase().equals(labStatus.get(0))) {
                    if (entityStateRequestDTO.getLabId() != null && entityStateRequestDTO.getLabId() != 0L) {
                        labSampleDto = LabServiceManagementHelper.createSample(lot, token, entityStateRequestDTO.getDateOfAction(), entityStateRequestDTO.getLabId());
                    } else {
                        labSampleDto = LabServiceManagementHelper.createSample(lot, token, entityStateRequestDTO.getDateOfAction(), null);
                    }
                    labSampleId = labSampleDto.getId();
                    labId = labSampleDto.getLabId().toString();
                    activitiManager.setVariable(task.getExecutionId(), "lotLabId", labId);
                } else {
                    labId = activitiManager.getVariable(task.getExecutionId(), "lotLabId");
                }
                String[] assigneesList = task.getAssignee().split(",");
                List<String> newAssignees = new ArrayList<>();
                Arrays.stream(assigneesList).forEach(d -> {
                    String[] assignees = d.split("_");
                    assignees[1] = labId;
                    newAssignees.add(String.join("_", assignees) + ",");
                });
                task.setAssignee(String.join(",", newAssignees));
                taskService.saveTask(task);
                manager.update(lot);
            } else {
                labId = null;
                if (newStateName.equals("lotReceived") || newStateName.equals("selfTestedLot")) {
                    lot.setDateOfReceiving(entityStateRequestDTO.getDateOfAction());
                    lot.setIsReceivedAtTarget(true);
                    manager.update(lot);
                } else if (newStateName.equals("approved")) {
                    lot.setDateOfAcceptance(entityStateRequestDTO.getDateOfAction());
                    lot.setIsTargetAccepted(true);
                    manager.update(lot);
                } else {
                    manager.update(lot);
                }
            }
            try {
                if (entityStateRequestDTO.getIsInspectionSample() != null && entityStateRequestDTO.getIsInspectionSample()
                        && prevState.getName().equals("lotReceived")) {
                    List<String> stateNames = List.of("sentLotSampleToLabTest", "lotSampleInLab", "lotSampleLabTestDone");
                    List<State> skippedStates = stateManager.findByNames(stateNames);
                    skippedStates.add(savedState);
                    messageManager.sendMessage(skippedStates, lot, sampleTestResult, entityStateRequestDTO, userInfo, keycloakInfo.getAccessToken());
                } else if (state.getName().equals("selfTestedLot") || state.getName().equals("lotSampleLabTestDone")) {
                    messageManager.sendLotUpdate(
                            lot.getTargetManufacturerId(),
                            lot.getCategory().getId(),
                            lot.getManufacturerId(),
                            lot.getTotalQuantity() * lot.getUom().getConversionFactorKg(),
                            state.getName(),
                            lot.getDateOfDispatch(),
                            sampleTestResult,
                            keycloakInfo.getAccessToken(),
                            lot.getIsLabTested(),
                            lot.getId()
                    );
                    Map<State, Boolean> stateMap = new LinkedHashMap<>();
                    stateMap.put(state, false);
                    stateMap.put(savedState, true);
                    messageManager.sendStatusHandlerMessage(stateMap, entityStateRequestDTO, userInfo, lot, keycloakInfo.getAccessToken());
                    notificationBuilder(lot.getCategory(), lot, prevState, state, labId, labSampleId);
                    notificationBuilder(lot.getCategory(), lot, state, savedState, labId, labSampleId);
                } else {
                    messageManager.statusChangeHandler(
                            entityStateRequestDTO,
                            savedState,
                            userInfo,
                            stateMap.get(state.getName()).equals("source") ? lot.getManufacturerId() : lot.getTargetManufacturerId(),
                            keycloakInfo.getAccessToken(),
                            false
                    );
                    notificationBuilder(lot.getCategory(), lot, prevState, state, labId, labSampleId);
                }
                messageManager.sendLotUpdate(
                        lot.getTargetManufacturerId(),
                        lot.getCategory().getId(),
                        lot.getManufacturerId(),
                        lot.getTotalQuantity() * lot.getUom().getConversionFactorKg(),
                        lot.getState().getName(),
                        lot.getDateOfDispatch(),
                        null,
                        keycloakInfo.getAccessToken(),
                        lot.getIsLabTested(),
                        lot.getId()
                );

            } catch (Exception e) {
                if (labSampleDto != null)
                    LabServiceManagementHelper.deleteSample(labSampleDto, keycloakInfo.getAccessToken());
                throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
            }

        }
        return true;
    }


    private void assignActivitiVariable(Lot lot, List<String> roleCategoryNames, List<String> variableNames, Map<String, String> variableValuesMap, Boolean externalTest) {
        Task task = taskService.createTaskQuery().taskId(lot.getTaskId()).singleResult();
        long labId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("labId", 0).toString());
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        boolean hasLabAccess = false;
        boolean hasModuleAccess = false;
        for (String role : roleCategoryNames) {
            if (!lot.getIsBlocked() && (task.getAssignee().contains(role + '_' + labId + "_" + "LAB"))) {
                hasLabAccess = true;
                break;
            }
        }
        for (String role : roleCategoryNames) {
            if (!lot.getIsBlocked() && (task.getAssignee().contains(role + '_' + manufacturerId + "_" + "MODULE"))) {
                hasModuleAccess = true;
                break;
            }
        }
        if (hasLabAccess || hasModuleAccess || (externalTest != null && externalTest))
            variableNames.forEach(v -> activitiManager.setVariable(task.getExecutionId(), v, variableValuesMap.get(v)));
    }

    private void notificationBuilder(Category category, Lot lot, State state, State savedState, String labId, Long labSampleId) {
        messageManager.sendNotification(
                FirebaseEvent
                        .builder()
                        .id(lot.getId())
                        .entity(EntityType.lot)
                        .targetManufacturerId(lot.getTargetManufacturerId())
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .manufacturerId(lot.getManufacturerId())
                        .labId(labId)
                        .notificationDate(LocalDateTime.now())
                        .entityNo(lot.getLotNo())
                        .isIndependentBatch(category.isIndependentBatch())
                        .currentStateName(savedState.getName())
                        .previousStateName(state.getName())
                        .currentStateDisplayName(savedState.getDisplayName())
                        .previousStateDisplayName(state.getDisplayName())
                        .labSampleId(labSampleId)
                        .build()
        );
    }

    private List<String> roleCategoryNames() {
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).filter(rc -> rc.length == 3).toList();
        List<RoleCategory> roleCategories = roleCategoryManager.findAllByCategoryAndRoleNames(roleSplitList);
        return roleCategories.stream()
                .map(RoleCategory::getRoleName).collect(Collectors.toList());
    }

    @Override
    public LotResponseDto getLotById(Long id, Long categoryId) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        Lot entity = null;
        if (categoryManager.isCategorySuperAdmin(categoryId, RoleCategoryType.MODULE) || categoryManager.isCategoryInspectionUser(categoryId, RoleCategoryType.MODULE) || roles.stream().anyMatch(r -> r.contains("MONITOR"))) {
            entity = manager.findById(id);
        } else {
            entity = manager.findByIdAndManufacturerId(id, manufacturerId);
        }
        LotResponseDto lotResponseDto = mapper.toDto(entity);
        List<MixMappingResponseDto> mixes = mixMappingService.getAllMixMappingsBySourceLot(id, null, null)
                .getData().stream()
                .peek(m -> {
                    m.setQuantityUsed(m.getQuantityUsed() * m.getUom().getConversionFactorKg());
                    m.setUom(BaseMapper.uomMapper.toDto(uomManager.findByConversionFactor(1L)));
                }).toList();
        if (Objects.equals(entity.getTargetManufacturerId(), manufacturerId) || this.isSuperAdmin() || this.isInspectionUser())
            lotResponseDto.setUsage(mixes);
        return lotResponseDto;
    }

    @Override
    public ListResponse<LotResponseDto> getAllLotsByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        List<Lot> entities = manager.findAllByBatchId(batchId, pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), batchId, pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateLot(Long categoryId, LotRequestDto dto) {
        Lot existingLot = manager.findById(dto.getId());
        double prevQuantity = existingLot.getTotalQuantity() * existingLot.getUom().getConversionFactorKg();
        Lot updatedLot = manager.update(existingLot);
        Double quantityDiff = updatedLot.getTotalQuantity() * updatedLot.getUom().getConversionFactorKg() - prevQuantity;
        messageManager.sendLotUpdate(
                updatedLot.getTargetManufacturerId(),
                updatedLot.getCategory().getId(),
                updatedLot.getManufacturerId(),
                quantityDiff,
                updatedLot.getState().getName(),
                updatedLot.getDateOfDispatch(),
                null,
                keycloakInfo.getAccessToken(),
                updatedLot.getIsLabTested(),
                updatedLot.getId()
        );
    }

    @Override
    public void deleteLot(Long id) {
        manager.delete(id);
    }

    @Override
    public ListResponse<LotListResponseDTO> getAllLots(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<Lot> entities = manager.findAllByCategoryId(manufacturerId, categoryId, searchListRequest, pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), manufacturerId, categoryId, pageNumber, pageSize, searchListRequest);

        ListResponse<LotListResponseDTO> dtos = ListResponse.from(entities, lotListMapper::toListDTO, count);
        List<Long> lotIds = dtos.getData().stream().map(LotListResponseDTO::getId).toList();
        Map<Long, LabNameAddressResponseDto> labNameAddressResponseDtoMap = LabServiceManagementHelper.fetchLabNameAddressByLotIds(keycloakInfo.getAccessToken(), lotIds, EntityType.lot);
        dtos.getData()
                .forEach(dto -> {
                    LabNameAddressResponseDto labNameAddressResponseDto = labNameAddressResponseDtoMap.get(dto.getId());
                    if(labNameAddressResponseDto != null){
                        dto.setLabName(labNameAddressResponseDto.getName());
                        dto.setLabId(labNameAddressResponseDto.getId());
                        dto.setLabCertificateNumber(labNameAddressResponseDto.getLabCertificateNumber());
                    }
                    else if(dto.getLotProperties().stream().anyMatch(lotPropertyResponseDto -> lotPropertyResponseDto.getName().equals("testedAtLab"))){
                        dto.setLabName(dto.getLotProperties().stream().filter(lotPropertyResponseDto -> lotPropertyResponseDto.getName().equals("testedAtLab")).findFirst().get().getValue());
                        dto.setLabCertificateNumber(dto.getLotProperties().stream().filter(lotPropertyResponseDto -> lotPropertyResponseDto.getName().equals("testedLabTCNumber")).findFirst().get().getValue());
                        dto.setLabId(Long.valueOf(dto.getLotProperties().stream().filter(lotPropertyResponseDto -> lotPropertyResponseDto.getName().equals("testedLabId")).findFirst().get().getValue()));

                    }
                });
        return dtos;
    }

    @Override
    public ListResponse<LotListResponseDTO> getLotInventory(Long categoryId, Integer pageNumber, Integer pageSize, Boolean approvedSourceLots, SearchListRequest searchRequest) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<Lot> entities = manager.getLotInventory(manufacturerId, categoryId, searchRequest, pageNumber, pageSize, approvedSourceLots);
        Long count = manager.getLotInventoryCount(entities.size(), manufacturerId, categoryId, pageNumber, pageSize, searchRequest, approvedSourceLots);
        return ListResponse.from(entities, lotListMapper::toListDTO, count);
    }

    @Override
    public ListResponse<LotResponseDto> getAllLotsBySourceCategoryId(Long categoryId, Long manufacturerId, String search, Integer pageNumber, Integer pageSize) {
        Long tokenManufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<Lot> lots;
        if (isSuperAdmin()) {
            lots = manager.getAllLotsBySourceCategoryId(manufacturerId, categoryId, search, pageNumber, pageSize);
            return ListResponse.from(lots, mapper::toDto, manager.getCount(lots.size(), manufacturerId, categoryId, search, pageNumber, pageSize));
        } else {
            lots = manager.getAllLotsBySourceCategoryId(tokenManufacturerId, categoryId, search, pageNumber, pageSize);
            return ListResponse.from(lots, mapper::toDto, manager.getCount(lots.size(), tokenManufacturerId, categoryId, search, pageNumber, pageSize));
        }
    }

    @Override
    public LotResponseDto getDetailsForUUID(String uuid) {
        Lot entity = manager.findByUUID(uuid);
        LotResponseDto lotResponseDto = mapper.toDto(entity);
        List<MixMappingResponseDto> mixes = mixMappingService.getAllMixMappingsBySourceLot(entity.getId(), null, null)
                .getData();
        lotResponseDto.setUsage(mixes);
        return lotResponseDto;
    }

    @Override
    public Boolean checkLabAccess(Long lotId) {
        Lot lot = manager.findById(lotId);
        if (categoryManager.isCategorySuperAdmin(lot.getCategory().getId(), RoleCategoryType.LAB))
            return true;
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        if (roles.stream().anyMatch(r -> r.contains("MONITOR"))) return true;
        if (categoryManager.isCategorySuperAdmin(lot.getCategory().getId(), RoleCategoryType.LAB)) return true;
        if (categoryManager.isCategoryInspectionUser(lot.getCategory().getId(), RoleCategoryType.LAB)) return true;
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        return Objects.equals(lot.getManufacturerId(), manufacturerId) || Objects.equals(lot.getTargetManufacturerId(), manufacturerId)
                || lot.getTargetBatchMapping().stream()
                .map(BatchLotMapping::getSourceLot)
                .filter(Objects::nonNull)
                .anyMatch(s -> (Objects.equals(s.getManufacturerId(), manufacturerId) || Objects.equals(s.getTargetManufacturerId(), manufacturerId)))
                || lot.getSourceBatchMapping().stream()
                .map(BatchLotMapping::getTargetLot)
                .filter(Objects::nonNull)
                .anyMatch(t -> (Objects.equals(t.getManufacturerId(), manufacturerId) || Objects.equals(t.getTargetManufacturerId(), manufacturerId)));
    }

    @Override
    public void updateBatchInspectionStatus(Long lotId, Boolean isBlocked) {
        Lot lot = manager.findById(lotId);
        lot.setIsBlocked(isBlocked);
        manager.update(lot);
    }

    @Override
    public LotHistoryResponseDto getHistoryForLot(Long lotId) {
        Lot lot = manager.findById(lotId);
        if (!checkLabAccess(lotId)) throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        return utils.checkLotHistory(lot);
    }

    @Override
    public List<LotResponseDto> getSourceLotsByTargetLotId(Long id) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Lot targetLot = manager.findById(id);
        return targetLot.getSourceBatchMapping().stream().map(blm -> {
            Lot lot = blm.getSourceLot();
            LotResponseDto lotResponseDto = mapper.toDto(lot);
            lotResponseDto.setTotalQuantity(blm.getQuantity());
            lotResponseDto.setRemainingQuantity(blm.getQuantity());
            List<MixMappingResponseDto> mixes = mixMappingService.getAllMixMappingsBySourceLot(id, null, null)
                    .getData();
            if (Objects.equals(lot.getTargetManufacturerId(), manufacturerId))
                lotResponseDto.setUsage(mixes);
            return lotResponseDto;
        }).toList();
    }

    @Override
    public LotResponseDto getLotByIdForEventUpdate(Long id) {
        Lot lot = manager.findById(id);
        LotResponseDto dto = mapper.toDto(lot);
        List<MixMappingResponseDto> mixes = mixMappingService.getAllMixMappingsBySourceLot(id, null, null)
                .getData().stream()
                .peek(m -> {
                    m.setQuantityUsed(m.getQuantityUsed() * m.getUom().getConversionFactorKg());
                    m.setUom(BaseMapper.uomMapper.toDto(uomManager.findByConversionFactor(1L)));
                }).toList();
        dto.setUsage(mixes);
        return dto;
    }


}
