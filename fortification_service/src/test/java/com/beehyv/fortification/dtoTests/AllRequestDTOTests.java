package com.beehyv.fortification.dtoTests;


import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.enums.DashboardExcelReportType;
import com.beehyv.fortification.enums.EntityType;
import com.beehyv.fortification.enums.SampleTestResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllRequestDTOTests {

    @Test
    public void testBatchRequestDto() {
        BatchRequestDto batchRequestDto = new BatchRequestDto();

        Long id = 1L;
        Long categoryId = 1L;
        Long stateId = 1L;
        Date dateOfManufacture = new Date();
        Date dateOfExpiry = new Date();
        String fssaiLicenseId = "fssaiLicenseId";
        String batchNo = "batchNo";
        Long uomId = 1L;
        Double totalQuantity = 1000.0;
        Double remainingQuantity = 500.0;
        String comments = "comments";
        Set<BatchPropertyRequestDto> batchProperties = new HashSet<>();
        Set<BatchDocRequestDto> batchDocs = new HashSet<>();
        Set<LotRequestDto> lots = new HashSet<>();
        Set<MixMappingRequestDto> mixes = new HashSet<>();
        Set<WastageRequestDto> wastes = new HashSet<>();

        batchRequestDto.setId(id);
        batchRequestDto.setCategoryId(categoryId);
        batchRequestDto.setStateId(stateId);
        batchRequestDto.setDateOfManufacture(dateOfManufacture);
        batchRequestDto.setDateOfExpiry(dateOfExpiry);
        batchRequestDto.setFssaiLicenseId(fssaiLicenseId);
        batchRequestDto.setBatchNo(batchNo);
        batchRequestDto.setUomId(uomId);
        batchRequestDto.setTotalQuantity(totalQuantity);
        batchRequestDto.setRemainingQuantity(remainingQuantity);
        batchRequestDto.setComments(comments);
        batchRequestDto.setBatchProperties(batchProperties);
        batchRequestDto.setBatchDocs(batchDocs);
        batchRequestDto.setLots(lots);
        batchRequestDto.setMixes(mixes);
        batchRequestDto.setWastes(wastes);

        assertEquals(id, batchRequestDto.getId());
        assertEquals(categoryId, batchRequestDto.getCategoryId());
        assertEquals(stateId, batchRequestDto.getStateId());
        assertEquals(dateOfManufacture, batchRequestDto.getDateOfManufacture());
        assertEquals(dateOfExpiry, batchRequestDto.getDateOfExpiry());
        assertEquals(fssaiLicenseId, batchRequestDto.getFssaiLicenseId());
        assertEquals(batchNo, batchRequestDto.getBatchNo());
        assertEquals(uomId, batchRequestDto.getUomId());
        assertEquals(totalQuantity, batchRequestDto.getTotalQuantity());
        assertEquals(remainingQuantity, batchRequestDto.getRemainingQuantity());
        assertEquals(comments, batchRequestDto.getComments());
        assertEquals(batchProperties, batchRequestDto.getBatchProperties());
        assertEquals(batchDocs, batchRequestDto.getBatchDocs());
        assertEquals(lots, batchRequestDto.getLots());
        assertEquals(mixes, batchRequestDto.getMixes());
        assertEquals(wastes, batchRequestDto.getWastes());
    }

    @Test
    public void testFirebaseEvent() {
        FirebaseEvent firebaseEvent = new FirebaseEvent();

        Long id = 1L;
        EntityType entity = EntityType.lot;
        Long targetManufacturerId = 1L;
        Long categoryId = 1L;
        String categoryName = "categoryName";
        Long manufacturerId = 1L;
        String labId = "labId";
        Date dateOfAction = new Date();
        LocalDateTime notificationDate = LocalDateTime.now();
        String entityNo = "entityNo";
        Boolean isIndependentBatch = true;
        String currentStateName = "currentStateName";
        String previousStateName = "previousStateName";
        String currentStateDisplayName = "currentStateDisplayName";
        String previousStateDisplayName = "previousStateDisplayName";
        Long labSampleId = 1L;

        firebaseEvent.setId(id);
        firebaseEvent.setEntity(entity);
        firebaseEvent.setTargetManufacturerId(targetManufacturerId);
        firebaseEvent.setCategoryId(categoryId);
        firebaseEvent.setCategoryName(categoryName);
        firebaseEvent.setManufacturerId(manufacturerId);
        firebaseEvent.setLabId(labId);
        firebaseEvent.setDateOfAction(dateOfAction);
        firebaseEvent.setNotificationDate(notificationDate);
        firebaseEvent.setEntityNo(entityNo);
        firebaseEvent.setIsIndependentBatch(isIndependentBatch);
        firebaseEvent.setCurrentStateName(currentStateName);
        firebaseEvent.setPreviousStateName(previousStateName);
        firebaseEvent.setCurrentStateDisplayName(currentStateDisplayName);
        firebaseEvent.setPreviousStateDisplayName(previousStateDisplayName);
        firebaseEvent.setLabSampleId(labSampleId);

        assertEquals(id, firebaseEvent.getId());
        assertEquals(entity, firebaseEvent.getEntity());
        assertEquals(targetManufacturerId, firebaseEvent.getTargetManufacturerId());
        assertEquals(categoryId, firebaseEvent.getCategoryId());
        assertEquals(categoryName, firebaseEvent.getCategoryName());
        assertEquals(manufacturerId, firebaseEvent.getManufacturerId());
        assertEquals(labId, firebaseEvent.getLabId());
        assertEquals(dateOfAction, firebaseEvent.getDateOfAction());
        assertEquals(notificationDate, firebaseEvent.getNotificationDate());
        assertEquals(entityNo, firebaseEvent.getEntityNo());
        assertEquals(isIndependentBatch, firebaseEvent.getIsIndependentBatch());
        assertEquals(currentStateName, firebaseEvent.getCurrentStateName());
        assertEquals(previousStateName, firebaseEvent.getPreviousStateName());
        assertEquals(currentStateDisplayName, firebaseEvent.getCurrentStateDisplayName());
        assertEquals(previousStateDisplayName, firebaseEvent.getPreviousStateDisplayName());
        assertEquals(labSampleId, firebaseEvent.getLabSampleId());
    }

    @Test
    public void testBatchDocRequestDto() {
        BatchDocRequestDto batchDocRequestDto = new BatchDocRequestDto();

        Long id = 1L;
        Long batchId = 1L;
        Long categoryDocId = 1L;
        String name = "name";
        String path = "path";
        Date expiry = new Date();

        batchDocRequestDto.setId(id);
        batchDocRequestDto.setBatchId(batchId);
        batchDocRequestDto.setCategoryDocId(categoryDocId);
        batchDocRequestDto.setName(name);
        batchDocRequestDto.setPath(path);
        batchDocRequestDto.setExpiry(expiry);

        assertEquals(id, batchDocRequestDto.getId());
        assertEquals(batchId, batchDocRequestDto.getBatchId());
        assertEquals(categoryDocId, batchDocRequestDto.getCategoryDocId());
        assertEquals(name, batchDocRequestDto.getName());
        assertEquals(path, batchDocRequestDto.getPath());
        assertEquals(expiry, batchDocRequestDto.getExpiry());
    }

    @Test
    public void testLotStateEvent() {
        LotStateEvent lotStateEvent = new LotStateEvent();

        Long categoryId = 1L;
        Long targetManufacturerId = 1L;
        Long batchManufacturerId = 1L;
        Double quantity = 1000.0;
        String state = "state";
        Date date = new Date();
        SampleTestResult testResult = SampleTestResult.TEST_PASSED;
        String districtGeoId = "districtGeoId";
        String stateGeoId = "stateGeoId";
        String countryGeoId = "countryGeoId";
        Boolean isBatchTested = true;
        Long lotId = 1L;

        lotStateEvent.setCategoryId(categoryId);
        lotStateEvent.setTargetManufacturerId(targetManufacturerId);
        lotStateEvent.setBatchManufacturerId(batchManufacturerId);
        lotStateEvent.setQuantity(quantity);
        lotStateEvent.setState(state);
        lotStateEvent.setDate(date);
        lotStateEvent.setTestResult(testResult);
        lotStateEvent.setDistrictGeoId(districtGeoId);
        lotStateEvent.setStateGeoId(stateGeoId);
        lotStateEvent.setCountryGeoId(countryGeoId);
        lotStateEvent.setIsBatchTested(isBatchTested);
        lotStateEvent.setLotId(lotId);

        assertEquals(categoryId, lotStateEvent.getCategoryId());
        assertEquals(targetManufacturerId, lotStateEvent.getTargetManufacturerId());
        assertEquals(batchManufacturerId, lotStateEvent.getBatchManufacturerId());
        assertEquals(quantity, lotStateEvent.getQuantity());
        assertEquals(state, lotStateEvent.getState());
        assertEquals(date, lotStateEvent.getDate());
        assertEquals(testResult, lotStateEvent.getTestResult());
        assertEquals(districtGeoId, lotStateEvent.getDistrictGeoId());
        assertEquals(stateGeoId, lotStateEvent.getStateGeoId());
        assertEquals(countryGeoId, lotStateEvent.getCountryGeoId());
        assertEquals(isBatchTested, lotStateEvent.getIsBatchTested());
        assertEquals(lotId, lotStateEvent.getLotId());
    }

    @Test
    public void testBatchStateEvent() {
        BatchStateEvent batchStateEvent = new BatchStateEvent();

        Long categoryId = 1L;
        Long manufacturerId = 1L;
        String pincode = "pincode";
        String districtGeoId = "districtGeoId";
        String stateGeoId = "stateGeoId";
        String countryGeoId = "countryGeoId";
        Double quantity = 1000.0;
        String state = "state";
        Date date = new Date();
        SampleTestResult testResult = SampleTestResult.TEST_PASSED;
        Boolean isBatchTested = true;

        batchStateEvent.setCategoryId(categoryId);
        batchStateEvent.setManufacturerId(manufacturerId);
        batchStateEvent.setPincode(pincode);
        batchStateEvent.setDistrictGeoId(districtGeoId);
        batchStateEvent.setStateGeoId(stateGeoId);
        batchStateEvent.setCountryGeoId(countryGeoId);
        batchStateEvent.setQuantity(quantity);
        batchStateEvent.setState(state);
        batchStateEvent.setDate(date);
        batchStateEvent.setTestResult(testResult);
        batchStateEvent.setIsBatchTested(isBatchTested);

        assertEquals(categoryId, batchStateEvent.getCategoryId());
        assertEquals(manufacturerId, batchStateEvent.getManufacturerId());
        assertEquals(pincode, batchStateEvent.getPincode());
        assertEquals(districtGeoId, batchStateEvent.getDistrictGeoId());
        assertEquals(stateGeoId, batchStateEvent.getStateGeoId());
        assertEquals(countryGeoId, batchStateEvent.getCountryGeoId());
        assertEquals(quantity, batchStateEvent.getQuantity());
        assertEquals(state, batchStateEvent.getState());
        assertEquals(date, batchStateEvent.getDate());
        assertEquals(testResult, batchStateEvent.getTestResult());
        assertEquals(isBatchTested, batchStateEvent.getIsBatchTested());
    }

    @Test
    public void testCategoryDslDto() {
        CategoryDslDto categoryDslDto = new CategoryDslDto();

        String name = "name";
        boolean outsidePlatform = true;
        String type = "type";
        List<String> rawMaterials = Arrays.asList("material1", "material2");
        List<TargetDto> target = new ArrayList<>();
        String dispatchLabOption = "dispatchLabOption";

        categoryDslDto.setName(name);
        categoryDslDto.setOutsidePlatform(outsidePlatform);
        categoryDslDto.setType(type);
        categoryDslDto.setRawMaterials(rawMaterials);
        categoryDslDto.setTarget(target);
        categoryDslDto.setDispatchLabOption(dispatchLabOption);

        assertEquals(name, categoryDslDto.getName());
        assertEquals(outsidePlatform, categoryDslDto.isOutsidePlatform());
        assertEquals(type, categoryDslDto.getType());
        assertEquals(rawMaterials, categoryDslDto.getRawMaterials());
        assertEquals(target, categoryDslDto.getTarget());
        assertEquals(dispatchLabOption, categoryDslDto.getDispatchLabOption());
    }

    @Test
    public void testDSLDto() {
        DSLDto dslDto = new DSLDto();

        String product = "product";
        String platformName = "platformName";
        String description = "description";
        List<CategoryDslDto> categories = new ArrayList<>();
        List<String> stages = Arrays.asList("stage1", "stage2");
        List<WorkflowDto> workflow = new ArrayList<>();

        dslDto.setProduct(product);
        dslDto.setPlatformName(platformName);
        dslDto.setDescription(description);
        dslDto.setCategories(categories);
        dslDto.setStages(stages);
        dslDto.setWorkflow(workflow);

        assertEquals(product, dslDto.getProduct());
        assertEquals(platformName, dslDto.getPlatformName());
        assertEquals(description, dslDto.getDescription());
        assertEquals(categories, dslDto.getCategories());
        assertEquals(stages, dslDto.getStages());
        assertEquals(workflow, dslDto.getWorkflow());
    }

    @Test
    public void testTransportQuantityDetailsRequestDto() {
        TransportQuantityDetailsRequestDto transportQuantityDetailsRequestDto = new TransportQuantityDetailsRequestDto();

        Long lotId = 1L;
        String purchaseOrderId = "purchaseOrderId";
        String destinationId = "destinationId";
        Long totalNoOfBags = 10L;
        Double dispatchedQuantity = 1000.0;
        Double grossWeight = 2000.0;
        Double tareWeight = 1000.0;
        Double netWeight = 1000.0;
        List<TransportVehicleDetailsRequestDto> vehicleDetailsRequestDtos = new ArrayList<>();

        transportQuantityDetailsRequestDto.setLotId(lotId);
        transportQuantityDetailsRequestDto.setPurchaseOrderId(purchaseOrderId);
        transportQuantityDetailsRequestDto.setDestinationId(destinationId);
        transportQuantityDetailsRequestDto.setTotalNoOfBags(totalNoOfBags);
        transportQuantityDetailsRequestDto.setDispatchedQuantity(dispatchedQuantity);
        transportQuantityDetailsRequestDto.setGrossWeight(grossWeight);
        transportQuantityDetailsRequestDto.setTareWeight(tareWeight);
        transportQuantityDetailsRequestDto.setNetWeight(netWeight);
        transportQuantityDetailsRequestDto.setVehicleDetailsRequestDtos(vehicleDetailsRequestDtos);

        assertEquals(lotId, transportQuantityDetailsRequestDto.getLotId());
        assertEquals(purchaseOrderId, transportQuantityDetailsRequestDto.getPurchaseOrderId());
        assertEquals(destinationId, transportQuantityDetailsRequestDto.getDestinationId());
        assertEquals(totalNoOfBags, transportQuantityDetailsRequestDto.getTotalNoOfBags());
        assertEquals(dispatchedQuantity, transportQuantityDetailsRequestDto.getDispatchedQuantity());
        assertEquals(grossWeight, transportQuantityDetailsRequestDto.getGrossWeight());
        assertEquals(tareWeight, transportQuantityDetailsRequestDto.getTareWeight());
        assertEquals(netWeight, transportQuantityDetailsRequestDto.getNetWeight());
        assertEquals(vehicleDetailsRequestDtos, transportQuantityDetailsRequestDto.getVehicleDetailsRequestDtos());
    }

    @Test
    public void testTransportVehicleDetailsRequestDto() {
        TransportVehicleDetailsRequestDto transportVehicleDetailsRequestDto = new TransportVehicleDetailsRequestDto();

        Long id = 1L;
        Long lotId = 1L;
        String childPurchaseOrderId = "childPurchaseOrderId";
        String vehicleNo = "vehicleNo";
        String driverName = "driverName";
        String driverLicense = "driverLicense";
        String driverContactNo = "driverContactNo";
        String driverUid = "driverUid";
        Long totalBags = 10L;
        Double totalTruckQuantity = 1000.0;

        transportVehicleDetailsRequestDto.setId(id);
        transportVehicleDetailsRequestDto.setLotId(lotId);
        transportVehicleDetailsRequestDto.setChildPurchaseOrderId(childPurchaseOrderId);
        transportVehicleDetailsRequestDto.setVehicleNo(vehicleNo);
        transportVehicleDetailsRequestDto.setDriverName(driverName);
        transportVehicleDetailsRequestDto.setDriverLicense(driverLicense);
        transportVehicleDetailsRequestDto.setDriverContactNo(driverContactNo);
        transportVehicleDetailsRequestDto.setDriverUid(driverUid);
        transportVehicleDetailsRequestDto.setTotalBags(totalBags);
        transportVehicleDetailsRequestDto.setTotalTruckQuantity(totalTruckQuantity);

        assertEquals(id, transportVehicleDetailsRequestDto.getId());
        assertEquals(lotId, transportVehicleDetailsRequestDto.getLotId());
        assertEquals(childPurchaseOrderId, transportVehicleDetailsRequestDto.getChildPurchaseOrderId());
        assertEquals(vehicleNo, transportVehicleDetailsRequestDto.getVehicleNo());
        assertEquals(driverName, transportVehicleDetailsRequestDto.getDriverName());
        assertEquals(driverLicense, transportVehicleDetailsRequestDto.getDriverLicense());
        assertEquals(driverContactNo, transportVehicleDetailsRequestDto.getDriverContactNo());
        assertEquals(driverUid, transportVehicleDetailsRequestDto.getDriverUid());
        assertEquals(totalBags, transportVehicleDetailsRequestDto.getTotalBags());
        assertEquals(totalTruckQuantity, transportVehicleDetailsRequestDto.getTotalTruckQuantity());
    }

    @Test
    public void testRoleCategoryStateRequestDto() {
        RoleCategoryStateRequestDto roleCategoryStateRequestDto = new RoleCategoryStateRequestDto();

        Long id = 1L;
        Long roleCategoryId = 1L;
        Long categoryId = 1L;
        Long stateId = 1L;

        roleCategoryStateRequestDto.setId(id);
        roleCategoryStateRequestDto.setRoleCategoryId(roleCategoryId);
        roleCategoryStateRequestDto.setCategoryId(categoryId);
        roleCategoryStateRequestDto.setStateId(stateId);

        assertEquals(id, roleCategoryStateRequestDto.getId());
        assertEquals(roleCategoryId, roleCategoryStateRequestDto.getRoleCategoryId());
        assertEquals(categoryId, roleCategoryStateRequestDto.getCategoryId());
        assertEquals(stateId, roleCategoryStateRequestDto.getStateId());
    }

    @Test
    public void testQuantityAggregatesExcelRequestDto() {
        QuantityAggregatesExcelRequestDto quantityAggregatesExcelRequestDto = new QuantityAggregatesExcelRequestDto();

        DashboardRequestDto dashboardRequestDto = new DashboardRequestDto();
        String districtGeoId = "districtGeoId";
        String stateGeoId = "stateGeoId";
        Long sourceManufacturerId = 1L;
        Long targetManufacturerId = 1L;
        DashboardExcelReportType reportType = DashboardExcelReportType.lot;

        quantityAggregatesExcelRequestDto.setDashboardRequestDto(dashboardRequestDto);
        quantityAggregatesExcelRequestDto.setDistrictGeoId(districtGeoId);
        quantityAggregatesExcelRequestDto.setStateGeoId(stateGeoId);
        quantityAggregatesExcelRequestDto.setSourceManufacturerId(sourceManufacturerId);
        quantityAggregatesExcelRequestDto.setTargetManufacturerId(targetManufacturerId);
        quantityAggregatesExcelRequestDto.setReportType(reportType);

        assertEquals(dashboardRequestDto, quantityAggregatesExcelRequestDto.getDashboardRequestDto());
        assertEquals(districtGeoId, quantityAggregatesExcelRequestDto.getDistrictGeoId());
        assertEquals(stateGeoId, quantityAggregatesExcelRequestDto.getStateGeoId());
        assertEquals(sourceManufacturerId, quantityAggregatesExcelRequestDto.getSourceManufacturerId());
        assertEquals(targetManufacturerId, quantityAggregatesExcelRequestDto.getTargetManufacturerId());
        assertEquals(reportType, quantityAggregatesExcelRequestDto.getReportType());
    }

    @Test
    public void testLotPropertyRequestDto() {
        LotPropertyRequestDto lotPropertyRequestDto = new LotPropertyRequestDto();

        Long id = 1L;
        Long lotId = 1L;
        String name = "name";
        String value = "value";

        lotPropertyRequestDto.setId(id);
        lotPropertyRequestDto.setLotId(lotId);
        lotPropertyRequestDto.setName(name);
        lotPropertyRequestDto.setValue(value);

        assertEquals(id, lotPropertyRequestDto.getId());
        assertEquals(lotId, lotPropertyRequestDto.getLotId());
        assertEquals(name, lotPropertyRequestDto.getName());
        assertEquals(value, lotPropertyRequestDto.getValue());
    }

    @Test
    public void testManufacturerEmpanelRequestDto() {
        ManufacturerEmpanelRequestDto manufacturerEmpanelRequestDto = new ManufacturerEmpanelRequestDto();

        String stateGeoId = "stateGeoId";
        Date fromDate = new Date();
        Date toDate = new Date();
        Long sourceCategoryId = 1L;
        Long targetCategoryId = 1L;

        manufacturerEmpanelRequestDto.setStateGeoId(stateGeoId);
        manufacturerEmpanelRequestDto.setFromDate(fromDate);
        manufacturerEmpanelRequestDto.setToDate(toDate);
        manufacturerEmpanelRequestDto.setSourceCategoryId(sourceCategoryId);
        manufacturerEmpanelRequestDto.setTargetCategoryId(targetCategoryId);

        assertEquals(stateGeoId, manufacturerEmpanelRequestDto.getStateGeoId());
        assertEquals(fromDate, manufacturerEmpanelRequestDto.getFromDate());
        assertEquals(toDate, manufacturerEmpanelRequestDto.getToDate());
        assertEquals(sourceCategoryId, manufacturerEmpanelRequestDto.getSourceCategoryId());
        assertEquals(targetCategoryId, manufacturerEmpanelRequestDto.getTargetCategoryId());
    }

    @Test
    public void testTargetDto() {
        TargetDto targetDto = new TargetDto();

        String name = "name";
        String receiveLabOption = "receiveLabOption";

        targetDto.setName(name);
        targetDto.setReceiveLabOption(receiveLabOption);

        assertEquals(name, targetDto.getName());
        assertEquals(receiveLabOption, targetDto.getReceiveLabOption());
    }


}
