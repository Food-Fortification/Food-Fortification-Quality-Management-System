package org.path.fortification.dtoTests;

import org.path.fortification.dto.responseDto.*;
import org.path.fortification.entity.DocType;
import org.path.fortification.enums.VendorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllResponseDtoTests {

    private BatchListResponseDTO batchListResponseDTO;
    private BatchDocResponseDto batchDocResponseDto;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testAllFieldsAddressLocationResponseDto() {
        AddressLocationResponseDto addressLocationResponseDto = new AddressLocationResponseDto();
        String laneOne = "laneOne";
        String laneTwo = "laneTwo";
        LocationResponseDto country = new LocationResponseDto();
        LocationResponseDto state = new LocationResponseDto();
        LocationResponseDto district = new LocationResponseDto();
        LocationResponseDto village = new LocationResponseDto();
        String pinCode = "123456";
        Double latitude = 12.345678;
        Double longitude = 98.765432;

        addressLocationResponseDto.setLaneOne(laneOne);
        addressLocationResponseDto.setLaneTwo(laneTwo);
        addressLocationResponseDto.setCountry(country);
        addressLocationResponseDto.setState(state);
        addressLocationResponseDto.setDistrict(district);
        addressLocationResponseDto.setVillage(village);
        addressLocationResponseDto.setPinCode(pinCode);
        addressLocationResponseDto.setLatitude(latitude);
        addressLocationResponseDto.setLongitude(longitude);

        assertEquals(laneOne, addressLocationResponseDto.getLaneOne());
        assertEquals(laneTwo, addressLocationResponseDto.getLaneTwo());
        assertEquals(country, addressLocationResponseDto.getCountry());
        assertEquals(state, addressLocationResponseDto.getState());
        assertEquals(district, addressLocationResponseDto.getDistrict());
        assertEquals(village, addressLocationResponseDto.getVillage());
        assertEquals(pinCode, addressLocationResponseDto.getPinCode());
        assertEquals(latitude, addressLocationResponseDto.getLatitude());
        assertEquals(longitude, addressLocationResponseDto.getLongitude());
    }

    @Test
    public void testAllFieldsAggregatedTestingResponseDto() {
        AggregatedTestingResponseDto aggregatedTestingResponseDto = new AggregatedTestingResponseDto();
        Double totalTestedQuantity = 100.0;
        Double underTestingQuantity = 50.0;
        Double approvedQuantity = 80.0;
        Double rejectedQuantity = 20.0;
        Double batchTestApprovedQuantity = 60.0;
        Double batchTestRejectedQuantity = 40.0;
        Double lotTestApprovedQuantity = 30.0;
        Double lotTestRejectedQuantity = 70.0;
        Double totalLotTestedQuantity = 100.0;

        aggregatedTestingResponseDto.setTotalTestedQuantity(totalTestedQuantity);
        aggregatedTestingResponseDto.setUnderTestingQuantity(underTestingQuantity);
        aggregatedTestingResponseDto.setApprovedQuantity(approvedQuantity);
        aggregatedTestingResponseDto.setRejectedQuantity(rejectedQuantity);
        aggregatedTestingResponseDto.setBatchTestApprovedQuantity(batchTestApprovedQuantity);
        aggregatedTestingResponseDto.setBatchTestRejectedQuantity(batchTestRejectedQuantity);
        aggregatedTestingResponseDto.setLotTestApprovedQuantity(lotTestApprovedQuantity);
        aggregatedTestingResponseDto.setLotTestRejectedQuantity(lotTestRejectedQuantity);
        aggregatedTestingResponseDto.setTotalLotTestedQuantity(totalLotTestedQuantity);

        assertEquals(totalTestedQuantity, aggregatedTestingResponseDto.getTotalTestedQuantity());
        assertEquals(underTestingQuantity, aggregatedTestingResponseDto.getUnderTestingQuantity());
        assertEquals(approvedQuantity, aggregatedTestingResponseDto.getApprovedQuantity());
        assertEquals(rejectedQuantity, aggregatedTestingResponseDto.getRejectedQuantity());
        assertEquals(batchTestApprovedQuantity, aggregatedTestingResponseDto.getBatchTestApprovedQuantity());
        assertEquals(batchTestRejectedQuantity, aggregatedTestingResponseDto.getBatchTestRejectedQuantity());
        assertEquals(lotTestApprovedQuantity, aggregatedTestingResponseDto.getLotTestApprovedQuantity());
        assertEquals(lotTestRejectedQuantity, aggregatedTestingResponseDto.getLotTestRejectedQuantity());
        assertEquals(totalLotTestedQuantity, aggregatedTestingResponseDto.getTotalLotTestedQuantity());
    }

    @Test
    public void testAllFieldsBatchListResponseDTO() {
        BatchListResponseDTO batchListResponseDTO = new BatchListResponseDTO();
        Long id = 1L;
        String uuid = "uuid";
        String batchNo = "batchNo";
        String name = "name";
        String manufacturerBatchNumber = "manufacturerBatchNumber";
        CategoryResponseDto category = new CategoryResponseDto();
        Date dateOfManufacture = new Date();
        Date dateOfExpiry = new Date();
        Double totalQuantity = 100.0;
        Double remainingQuantity = 50.0;
        String uom = "uom";
        String state = "state";
        String comments = "comments";
        String prefetchedInstructions = "prefetchedInstructions";
        Long manufacturerId = 2L;
        String labName = "labName";
        Long labId = 3L;
        String createdBy = "createdBy";

        batchListResponseDTO.setId(id);
        batchListResponseDTO.setUuid(uuid);
        batchListResponseDTO.setBatchNo(batchNo);
        batchListResponseDTO.setName(name);
        batchListResponseDTO.setManufacturerBatchNumber(manufacturerBatchNumber);
        batchListResponseDTO.setCategory(category);
        batchListResponseDTO.setDateOfManufacture(dateOfManufacture);
        batchListResponseDTO.setDateOfExpiry(dateOfExpiry);
        batchListResponseDTO.setTotalQuantity(totalQuantity);
        batchListResponseDTO.setRemainingQuantity(remainingQuantity);
        batchListResponseDTO.setUom(uom);
        batchListResponseDTO.setState(state);
        batchListResponseDTO.setComments(comments);
        batchListResponseDTO.setPrefetchedInstructions(prefetchedInstructions);
        batchListResponseDTO.setManufacturerId(manufacturerId);
        batchListResponseDTO.setLabName(labName);
        batchListResponseDTO.setLabId(labId);
        batchListResponseDTO.setCreatedBy(createdBy);

        assertEquals(id, batchListResponseDTO.getId());
        assertEquals(uuid, batchListResponseDTO.getUuid());
        assertEquals(batchNo, batchListResponseDTO.getBatchNo());
        assertEquals(name, batchListResponseDTO.getName());
        assertEquals(manufacturerBatchNumber, batchListResponseDTO.getManufacturerBatchNumber());
        assertEquals(category, batchListResponseDTO.getCategory());
        assertEquals(dateOfManufacture, batchListResponseDTO.getDateOfManufacture());
        assertEquals(dateOfExpiry, batchListResponseDTO.getDateOfExpiry());
        assertEquals(totalQuantity, batchListResponseDTO.getTotalQuantity());
        assertEquals(remainingQuantity, batchListResponseDTO.getRemainingQuantity());
        assertEquals(uom, batchListResponseDTO.getUom());
        assertEquals(state, batchListResponseDTO.getState());
        assertEquals(comments, batchListResponseDTO.getComments());
        assertEquals(prefetchedInstructions, batchListResponseDTO.getPrefetchedInstructions());
        assertEquals(manufacturerId, batchListResponseDTO.getManufacturerId());
        assertEquals(labName, batchListResponseDTO.getLabName());
        assertEquals(labId, batchListResponseDTO.getLabId());
        assertEquals(createdBy, batchListResponseDTO.getCreatedBy());
    }

    @Test
    public void testAllFieldsAggregatedProductionResponseDto() {
        AggregatedProductionResponseDto aggregatedProductionResponseDto = new AggregatedProductionResponseDto();
        Double producedQuantity = 100.0;
        Double inProductionQuantity = 50.0;

        aggregatedProductionResponseDto.setProducedQuantity(producedQuantity);
        aggregatedProductionResponseDto.setInProductionQuantity(inProductionQuantity);

        assertEquals(producedQuantity, aggregatedProductionResponseDto.getProducedQuantity());
        assertEquals(inProductionQuantity, aggregatedProductionResponseDto.getInProductionQuantity());
    }

    @Test
    public void testBatchListResponseDTO() {
        batchListResponseDTO = new BatchListResponseDTO();

        Long id = 1L;
        String uuid = "uuid";
        String batchNo = "batchNo";
        String name = "name";
        String manufacturerBatchNumber = "manufacturerBatchNumber";
        CategoryResponseDto category = new CategoryResponseDto();
        Date dateOfManufacture = new Date();
        Date dateOfExpiry = new Date();
        Double totalQuantity = 100.0;
        Double remainingQuantity = 50.0;
        String uom = "uom";
        String state = "state";
        String comments = "comments";
        String prefetchedInstructions = "prefetchedInstructions";
        Long manufacturerId = 2L;
        String labName = "labName";
        Long labId = 3L;
        String createdBy = "createdBy";

        batchListResponseDTO.setId(id);
        batchListResponseDTO.setUuid(uuid);
        batchListResponseDTO.setBatchNo(batchNo);
        batchListResponseDTO.setName(name);
        batchListResponseDTO.setManufacturerBatchNumber(manufacturerBatchNumber);
        batchListResponseDTO.setCategory(category);
        batchListResponseDTO.setDateOfManufacture(dateOfManufacture);
        batchListResponseDTO.setDateOfExpiry(dateOfExpiry);
        batchListResponseDTO.setTotalQuantity(totalQuantity);
        batchListResponseDTO.setRemainingQuantity(remainingQuantity);
        batchListResponseDTO.setUom(uom);
        batchListResponseDTO.setState(state);
        batchListResponseDTO.setComments(comments);
        batchListResponseDTO.setPrefetchedInstructions(prefetchedInstructions);
        batchListResponseDTO.setManufacturerId(manufacturerId);
        batchListResponseDTO.setLabName(labName);
        batchListResponseDTO.setLabId(labId);
        batchListResponseDTO.setCreatedBy(createdBy);

        assertEquals(id, batchListResponseDTO.getId());
        assertEquals(uuid, batchListResponseDTO.getUuid());
        assertEquals(batchNo, batchListResponseDTO.getBatchNo());
        assertEquals(name, batchListResponseDTO.getName());
        assertEquals(manufacturerBatchNumber, batchListResponseDTO.getManufacturerBatchNumber());
        assertEquals(category, batchListResponseDTO.getCategory());
        assertEquals(dateOfManufacture, batchListResponseDTO.getDateOfManufacture());
        assertEquals(dateOfExpiry, batchListResponseDTO.getDateOfExpiry());
        assertEquals(totalQuantity, batchListResponseDTO.getTotalQuantity());
        assertEquals(remainingQuantity, batchListResponseDTO.getRemainingQuantity());
        assertEquals(uom, batchListResponseDTO.getUom());
        assertEquals(state, batchListResponseDTO.getState());
        assertEquals(comments, batchListResponseDTO.getComments());
        assertEquals(prefetchedInstructions, batchListResponseDTO.getPrefetchedInstructions());
        assertEquals(manufacturerId, batchListResponseDTO.getManufacturerId());
        assertEquals(labName, batchListResponseDTO.getLabName());
        assertEquals(labId, batchListResponseDTO.getLabId());
        assertEquals(createdBy, batchListResponseDTO.getCreatedBy());
    }

    @Test
    public void testBatchDocResponseDto() {
        batchDocResponseDto = new BatchDocResponseDto();

        Long id = 1L;
        String uuid = "uuid";
        CategoryDocResponseDto categoryDoc = new CategoryDocResponseDto();
        String name = "name";
        String path = "path";
        Date expiry = new Date();

        batchDocResponseDto.setId(id);
        batchDocResponseDto.setUuid(uuid);
        batchDocResponseDto.setCategoryDoc(categoryDoc);
        batchDocResponseDto.setName(name);
        batchDocResponseDto.setPath(path);
        batchDocResponseDto.setExpiry(expiry);

        assertEquals(id, batchDocResponseDto.getId());
        assertEquals(uuid, batchDocResponseDto.getUuid());
        assertEquals(categoryDoc, batchDocResponseDto.getCategoryDoc());
        assertEquals(name, batchDocResponseDto.getName());
        assertEquals(path, batchDocResponseDto.getPath());
        assertEquals(expiry, batchDocResponseDto.getExpiry());
    }

    @Test
    public void testBatchMonitorResponseDto() {
        BatchMonitorResponseDto batchMonitorResponseDto = new BatchMonitorResponseDto();
        BatchListResponseDTO batchListResponseDTO = new BatchListResponseDTO();
        batchMonitorResponseDto.setBatchProperties(batchListResponseDTO);
        CategoryResponseDto category = new CategoryResponseDto();
        batchMonitorResponseDto.setCategory(category);
        Set<MixMappingMonitorResponseDto> mixes = new HashSet<>();
        MixMappingMonitorResponseDto mix = new MixMappingMonitorResponseDto();
        mixes.add(mix);
        batchMonitorResponseDto.setMixes(mixes);
        assertEquals(category, batchMonitorResponseDto.getCategory());
        assertEquals(mixes, batchMonitorResponseDto.getMixes());
    }

    @Test
    public void testBatchPropertyResponseDto() {
        BatchPropertyResponseDto batchPropertyResponseDto = new BatchPropertyResponseDto();

        Long id = 1L;
        String name = "name";
        String value = "value";

        batchPropertyResponseDto.setId(id);
        batchPropertyResponseDto.setName(name);
        batchPropertyResponseDto.setValue(value);

        assertEquals(id, batchPropertyResponseDto.getId());
        assertEquals(name, batchPropertyResponseDto.getName());
        assertEquals(value, batchPropertyResponseDto.getValue());
    }

    @Test
    public void testBatchResponseDto() {
        BatchResponseDto batchResponseDto = new BatchResponseDto();

        Long id = 1L;
        Long manufacturerId = 2L;
        Date dateOfManufacture = new Date();
        Date dateOfExpiry = new Date();
        String licenseId = "licenseId";
        String batchNo = "batchNo";
        Double totalQuantity = 100.0;
        Double remainingQuantity = 50.0;
        String comments = "comments";
        String prefetchedInstructions = "prefetchedInstructions";
        String qrcode = "qrcode";
        String uuid = "uuid";
        String batchName = "batchName";
        Date lastActionDate = new Date();
        UOMResponseDto uom = new UOMResponseDto();
        CategoryResponseDto category = new CategoryResponseDto();
        StateResponseDto state = new StateResponseDto();
        Set<BatchPropertyResponseDto> batchProperties = new HashSet<>();
        Set<BatchDocResponseDto> batchDocs = new HashSet<>();
        Set<SizeUnitResponseDto> sizeUnits = new HashSet<>();
        Set<LotListResponseDTO> lots = new HashSet<>();
        Set<MixMappingResponseDto> mixes = new HashSet<>();
        Set<WastageResponseDto> wastes = new HashSet<>();

        batchResponseDto.setId(id);
        batchResponseDto.setManufacturerId(manufacturerId);
        batchResponseDto.setDateOfManufacture(dateOfManufacture);
        batchResponseDto.setDateOfExpiry(dateOfExpiry);
        batchResponseDto.setLicenseId(licenseId);
        batchResponseDto.setBatchNo(batchNo);
        batchResponseDto.setTotalQuantity(totalQuantity);
        batchResponseDto.setRemainingQuantity(remainingQuantity);
        batchResponseDto.setComments(comments);
        batchResponseDto.setPrefetchedInstructions(prefetchedInstructions);
        batchResponseDto.setQrcode(qrcode);
        batchResponseDto.setUuid(uuid);
        batchResponseDto.setBatchName(batchName);
        batchResponseDto.setLastActionDate(lastActionDate);
        batchResponseDto.setUom(uom);
        batchResponseDto.setCategory(category);
        batchResponseDto.setState(state);

        batchResponseDto.setBatchProperties(batchProperties);
        batchResponseDto.setBatchDocs(batchDocs);
        batchResponseDto.setSizeUnits(sizeUnits);
        batchResponseDto.setLots(lots);
        batchResponseDto.setMixes(mixes);
        batchResponseDto.setWastes(wastes);

        assertEquals(id, batchResponseDto.getId());
        assertEquals(manufacturerId, batchResponseDto.getManufacturerId());
        assertEquals(dateOfManufacture, batchResponseDto.getDateOfManufacture());
        assertEquals(dateOfExpiry, batchResponseDto.getDateOfExpiry());
        assertEquals(licenseId, batchResponseDto.getLicenseId());
        assertEquals(batchNo, batchResponseDto.getBatchNo());
        assertEquals(totalQuantity, batchResponseDto.getTotalQuantity());
        assertEquals(remainingQuantity, batchResponseDto.getRemainingQuantity());
        assertEquals(comments, batchResponseDto.getComments());
        assertEquals(prefetchedInstructions, batchResponseDto.getPrefetchedInstructions());
        assertEquals(qrcode, batchResponseDto.getQrcode());
        assertEquals(uuid, batchResponseDto.getUuid());
        assertEquals(batchName, batchResponseDto.getBatchName());
        assertEquals(lastActionDate, batchResponseDto.getLastActionDate());
        assertEquals(uom, batchResponseDto.getUom());
        assertEquals(category, batchResponseDto.getCategory());
        assertEquals(state, batchResponseDto.getState());
        assertEquals(batchProperties, batchResponseDto.getBatchProperties());
        assertEquals(batchDocs, batchResponseDto.getBatchDocs());
        assertEquals(sizeUnits, batchResponseDto.getSizeUnits());
        assertEquals(lots, batchResponseDto.getLots());
        assertEquals(mixes, batchResponseDto.getMixes());
        assertEquals(wastes, batchResponseDto.getWastes());
    }

    @Test
    public void testCategoryDocResponseDto() {
        CategoryDocResponseDto categoryDocResponseDto = new CategoryDocResponseDto();

        Long id = 1L;
        Long categoryId = 2L;
        DocType docType = new DocType();
        Boolean isMandatory = true;
        Boolean isEnabled = true;

        categoryDocResponseDto.setId(id);
        categoryDocResponseDto.setCategoryId(categoryId);
        categoryDocResponseDto.setDocType(docType);
        categoryDocResponseDto.setIsMandatory(isMandatory);
        categoryDocResponseDto.setIsEnabled(isEnabled);

        assertEquals(id, categoryDocResponseDto.getId());
        assertEquals(categoryId, categoryDocResponseDto.getCategoryId());
        assertEquals(docType, categoryDocResponseDto.getDocType());
        assertEquals(isMandatory, categoryDocResponseDto.getIsMandatory());
        assertEquals(isEnabled, categoryDocResponseDto.getIsEnabled());
    }

    @Test
    public void testCategoryResponseDto() {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        Long id = 1L;
        String name = "name";
        Boolean independentBatch = true;
        Integer sequence = 1;
        ProductResponseDto product = new ProductResponseDto();
        Set<CategoryResponseDto> sourceCategories = new HashSet<>();
        Set<CategoryDocResponseDto> documents = new HashSet<>();

        categoryResponseDto.setId(id);
        categoryResponseDto.setName(name);
        categoryResponseDto.setIndependentBatch(independentBatch);
        categoryResponseDto.setSequence(sequence);
        categoryResponseDto.setProduct(product);
        categoryResponseDto.setSourceCategories(sourceCategories);
        categoryResponseDto.setDocuments(documents);

        assertEquals(id, categoryResponseDto.getId());
        assertEquals(name, categoryResponseDto.getName());
        assertEquals(independentBatch, categoryResponseDto.getIndependentBatch());
        assertEquals(sequence, categoryResponseDto.getSequence());
        assertEquals(product, categoryResponseDto.getProduct());
        assertEquals(sourceCategories, categoryResponseDto.getSourceCategories());
        assertEquals(documents, categoryResponseDto.getDocuments());
    }

    @Test
    public void testCategoryRoleResponseDto() {
        CategoryRoleResponseDto categoryRoleResponseDto = new CategoryRoleResponseDto();

        Long id = 1L;
        List<String> roles = Arrays.asList("role1", "role2", "role3");

        categoryRoleResponseDto.setId(id);
        categoryRoleResponseDto.setRoles(roles);

        assertEquals(id, categoryRoleResponseDto.getId());
        assertEquals(roles, categoryRoleResponseDto.getRoles());
    }

    @Test
    public void testDashboardProductionQuantityResponseDto() {
        DashboardProductionQuantityResponseDto dashboardProductionQuantityResponseDto = new DashboardProductionQuantityResponseDto();

        Double data = 100.0;
        String manufacturerName = "manufacturerName";
        Long manufacturerId = 1L;
        String districtGeoId = "districtGeoId";
        String stateGeoId = "stateGeoId";
        String districtName = "districtName";
        String stateName = "stateName";

        dashboardProductionQuantityResponseDto.setData(data);
        dashboardProductionQuantityResponseDto.setManufacturerName(manufacturerName);
        dashboardProductionQuantityResponseDto.setManufacturerId(manufacturerId);
        dashboardProductionQuantityResponseDto.setDistrictGeoId(districtGeoId);
        dashboardProductionQuantityResponseDto.setStateGeoId(stateGeoId);
        dashboardProductionQuantityResponseDto.setDistrictName(districtName);
        dashboardProductionQuantityResponseDto.setStateName(stateName);

        assertEquals(data, dashboardProductionQuantityResponseDto.getData());
        assertEquals(manufacturerName, dashboardProductionQuantityResponseDto.getManufacturerName());
        assertEquals(manufacturerId, dashboardProductionQuantityResponseDto.getManufacturerId());
        assertEquals(districtGeoId, dashboardProductionQuantityResponseDto.getDistrictGeoId());
        assertEquals(stateGeoId, dashboardProductionQuantityResponseDto.getStateGeoId());
        assertEquals(districtName, dashboardProductionQuantityResponseDto.getDistrictName());
        assertEquals(stateName, dashboardProductionQuantityResponseDto.getStateName());
    }

    @Test
    public void testDashboardProductionResponseDto() {
        DashboardProductionResponseDto dashboardProductionResponseDto = new DashboardProductionResponseDto();

        Double totalProduction = 1000.0;
        Double approvedQuantity = 800.0;
        Double availableTested = 700.0;
        Double availableNotTested = 100.0;
        Long categoryId = 1L;
        String categoryName = "categoryName";
        String id = "id";

        dashboardProductionResponseDto.setTotalProduction(totalProduction);
        dashboardProductionResponseDto.setApprovedQuantity(approvedQuantity);
        dashboardProductionResponseDto.setAvailableTested(availableTested);
        dashboardProductionResponseDto.setAvailableNotTested(availableNotTested);
        dashboardProductionResponseDto.setCategoryId(categoryId);
        dashboardProductionResponseDto.setCategoryName(categoryName);
        dashboardProductionResponseDto.setId(id);

        assertEquals(totalProduction, dashboardProductionResponseDto.getTotalProduction());
        assertEquals(approvedQuantity, dashboardProductionResponseDto.getApprovedQuantity());
        assertEquals(availableTested, dashboardProductionResponseDto.getAvailableTested());
        assertEquals(availableNotTested, dashboardProductionResponseDto.getAvailableNotTested());
        assertEquals(categoryId, dashboardProductionResponseDto.getCategoryId());
        assertEquals(categoryName, dashboardProductionResponseDto.getCategoryName());
        assertEquals(id, dashboardProductionResponseDto.getId());
    }

    @Test
    public void testDashboardQuantityAgencyResponseDto() {
        DashboardQuantityAgencyResponseDto dashboardQuantityAgencyResponseDto = new DashboardQuantityAgencyResponseDto();

        String agencyName = "agencyName";
        Long manufacturerId = 1L;
        Double totalProduction = 1000.0;
        Double batchTestApproved = 800.0;
        Double batchTestRejected = 200.0;
        Double notTestedQuantity = 100.0;
        Double totalDemand = 1200.0;
        Double totalSupply = 800.0;
        Double lotApprovedQuantity = 700.0;
        Double lotRejectedQuantity = 100.0;
        Double lotApprovalPending = 200.0;
        Double availableTested = 600.0;
        String districtName = "districtName";
        String stateName = "stateName";
        String licenseNumber = "licenseNumber";

        dashboardQuantityAgencyResponseDto.setAgencyName(agencyName);
        dashboardQuantityAgencyResponseDto.setManufacturerId(manufacturerId);
        dashboardQuantityAgencyResponseDto.setTotalProduction(totalProduction);
        dashboardQuantityAgencyResponseDto.setBatchTestApproved(batchTestApproved);
        dashboardQuantityAgencyResponseDto.setBatchTestRejected(batchTestRejected);
        dashboardQuantityAgencyResponseDto.setNotTestedQuantity(notTestedQuantity);
//        dashboardQuantityAgencyResponseDto.setTotalDemand(totalDemand);
//        dashboardQuantityAgencyResponseDto.setTotalSupply(totalSupply);
        dashboardQuantityAgencyResponseDto.setLotApprovedQuantity(lotApprovedQuantity);
        dashboardQuantityAgencyResponseDto.setLotRejectedQuantity(lotRejectedQuantity);
        dashboardQuantityAgencyResponseDto.setLotApprovalPending(lotApprovalPending);
        dashboardQuantityAgencyResponseDto.setAvailableTested(availableTested);
        dashboardQuantityAgencyResponseDto.setDistrictName(districtName);
        dashboardQuantityAgencyResponseDto.setStateName(stateName);
        dashboardQuantityAgencyResponseDto.setLicenseNumber(licenseNumber);

        assertEquals(agencyName, dashboardQuantityAgencyResponseDto.getAgencyName());
        assertEquals(manufacturerId, dashboardQuantityAgencyResponseDto.getManufacturerId());
        assertEquals(totalProduction, dashboardQuantityAgencyResponseDto.getTotalProduction());
        assertEquals(batchTestApproved, dashboardQuantityAgencyResponseDto.getBatchTestApproved());
        assertEquals(batchTestRejected, dashboardQuantityAgencyResponseDto.getBatchTestRejected());
        assertEquals(notTestedQuantity, dashboardQuantityAgencyResponseDto.getNotTestedQuantity());
//        assertEquals(totalDemand, dashboardQuantityAgencyResponseDto.getTotalDemand());
//        assertEquals(totalSupply, dashboardQuantityAgencyResponseDto.getTotalSupply());
        assertEquals(lotApprovedQuantity, dashboardQuantityAgencyResponseDto.getLotApprovedQuantity());
        assertEquals(lotRejectedQuantity, dashboardQuantityAgencyResponseDto.getLotRejectedQuantity());
        assertEquals(lotApprovalPending, dashboardQuantityAgencyResponseDto.getLotApprovalPending());
        assertEquals(availableTested, dashboardQuantityAgencyResponseDto.getAvailableTested());
        assertEquals(districtName, dashboardQuantityAgencyResponseDto.getDistrictName());
        assertEquals(stateName, dashboardQuantityAgencyResponseDto.getStateName());
        assertEquals(licenseNumber, dashboardQuantityAgencyResponseDto.getLicenseNumber());
    }

    @Test
    public void testDashboardQuantityDistrictResponseDto() {
        DashboardQuantityDistrictResponseDto dashboardQuantityDistrictResponseDto = new DashboardQuantityDistrictResponseDto();

        String agencyName = "agencyName";
        Long manufacturerId = 1L;
        String districtName = "districtName";
        String stateName = "stateName";
        String districtGeoId = "districtGeoId";
        Double totalDemand = 1200.0;
        Double totalSupply = 800.0;
        Double lotApprovedQuantity = 700.0;
        Double lotRejectedQuantity = 100.0;
        Double lotApprovalPending = 200.0;
        Double totalAvailable = 600.0;
        Double totalDispatched = 200.0;
        String licenseNumber = "licenseNumber";
        List<DashboardQuantityDistrictResponseDto> agencyList = new ArrayList<>();

        dashboardQuantityDistrictResponseDto.setAgencyName(agencyName);
        dashboardQuantityDistrictResponseDto.setManufacturerId(manufacturerId);
        dashboardQuantityDistrictResponseDto.setDistrictName(districtName);
        dashboardQuantityDistrictResponseDto.setStateName(stateName);
        dashboardQuantityDistrictResponseDto.setDistrictGeoId(districtGeoId);
//        dashboardQuantityDistrictResponseDto.setTotalDemand(totalDemand);
//        dashboardQuantityDistrictResponseDto.setTotalSupply(totalSupply);
        dashboardQuantityDistrictResponseDto.setLotApprovedQuantity(lotApprovedQuantity);
        dashboardQuantityDistrictResponseDto.setLotRejectedQuantity(lotRejectedQuantity);
        dashboardQuantityDistrictResponseDto.setLotApprovalPending(lotApprovalPending);
        dashboardQuantityDistrictResponseDto.setTotalAvailable(totalAvailable);
        dashboardQuantityDistrictResponseDto.setTotalDispatched(totalDispatched);
        dashboardQuantityDistrictResponseDto.setLicenseNumber(licenseNumber);
        dashboardQuantityDistrictResponseDto.setAgencyList(agencyList);

        assertEquals(agencyName, dashboardQuantityDistrictResponseDto.getAgencyName());
        assertEquals(manufacturerId, dashboardQuantityDistrictResponseDto.getManufacturerId());
        assertEquals(districtName, dashboardQuantityDistrictResponseDto.getDistrictName());
        assertEquals(stateName, dashboardQuantityDistrictResponseDto.getStateName());
        assertEquals(districtGeoId, dashboardQuantityDistrictResponseDto.getDistrictGeoId());
//        assertEquals(totalDemand, dashboardQuantityDistrictResponseDto.getTotalDemand());
//        assertEquals(totalSupply, dashboardQuantityDistrictResponseDto.getTotalSupply());
        assertEquals(lotApprovedQuantity, dashboardQuantityDistrictResponseDto.getLotApprovedQuantity());
        assertEquals(lotRejectedQuantity, dashboardQuantityDistrictResponseDto.getLotRejectedQuantity());
        assertEquals(lotApprovalPending, dashboardQuantityDistrictResponseDto.getLotApprovalPending());
        assertEquals(totalAvailable, dashboardQuantityDistrictResponseDto.getTotalAvailable());
        assertEquals(totalDispatched, dashboardQuantityDistrictResponseDto.getTotalDispatched());
        assertEquals(licenseNumber, dashboardQuantityDistrictResponseDto.getLicenseNumber());
        assertEquals(agencyList, dashboardQuantityDistrictResponseDto.getAgencyList());
    }

    @Test
    public void testDashboardQuantityWarehouseResponseDto() {
        DashboardQuantityWarehouseResponseDto dashboardQuantityWarehouseResponseDto = new DashboardQuantityWarehouseResponseDto();

        String agencyName = "agencyName";
        Long manufacturerId = 1L;
        Double lotApprovedQuantity = 700.0;
        Double lotRejectedQuantity = 100.0;
        Double lotApprovalPending = 200.0;
        Double totalAvailable = 600.0;
        Double totalDispatched = 200.0;

        dashboardQuantityWarehouseResponseDto.setAgencyName(agencyName);
        dashboardQuantityWarehouseResponseDto.setManufacturerId(manufacturerId);
        dashboardQuantityWarehouseResponseDto.setLotApprovedQuantity(lotApprovedQuantity);
        dashboardQuantityWarehouseResponseDto.setLotRejectedQuantity(lotRejectedQuantity);
        dashboardQuantityWarehouseResponseDto.setLotApprovalPending(lotApprovalPending);
        dashboardQuantityWarehouseResponseDto.setTotalAvailable(totalAvailable);
        dashboardQuantityWarehouseResponseDto.setTotalDispatched(totalDispatched);

        assertEquals(agencyName, dashboardQuantityWarehouseResponseDto.getAgencyName());
        assertEquals(manufacturerId, dashboardQuantityWarehouseResponseDto.getManufacturerId());
        assertEquals(lotApprovedQuantity, dashboardQuantityWarehouseResponseDto.getLotApprovedQuantity());
        assertEquals(lotRejectedQuantity, dashboardQuantityWarehouseResponseDto.getLotRejectedQuantity());
        assertEquals(lotApprovalPending, dashboardQuantityWarehouseResponseDto.getLotApprovalPending());
        assertEquals(totalAvailable, dashboardQuantityWarehouseResponseDto.getTotalAvailable());
        assertEquals(totalDispatched, dashboardQuantityWarehouseResponseDto.getTotalDispatched());
    }

    @Test
    public void testDashboardResponseDto() {
        DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();

        String agencyName = "agencyName";
        Long manufacturerId = 1L;
        String districtName = "districtName";
        String districtGeoId = "districtGeoId";
        Double totalProduction = 1000.0;
        Double batchTestApproved = 800.0;
        Double batchTestRejected = 200.0;
        Double notTestedQuantity = 100.0;
        Double totalDemand = 1200.0;
        Double totalSupply = 800.0;
        Double lotApprovedQuantity = 700.0;
        Double lotRejectedQuantity = 100.0;
        Double lotApprovalPending = 200.0;
        Double totalAvailable = 600.0;

        dashboardResponseDto.setAgencyName(agencyName);
        dashboardResponseDto.setManufacturerId(manufacturerId);
        dashboardResponseDto.setDistrictName(districtName);
        dashboardResponseDto.setDistrictGeoId(districtGeoId);
        dashboardResponseDto.setTotalProduction(totalProduction);
        dashboardResponseDto.setBatchTestApproved(batchTestApproved);
        dashboardResponseDto.setBatchTestRejected(batchTestRejected);
        dashboardResponseDto.setNotTestedQuantity(notTestedQuantity);
        dashboardResponseDto.setTotalDemand(totalDemand);
        dashboardResponseDto.setTotalSupply(totalSupply);
        dashboardResponseDto.setLotApprovedQuantity(lotApprovedQuantity);
        dashboardResponseDto.setLotRejectedQuantity(lotRejectedQuantity);
        dashboardResponseDto.setLotApprovalPending(lotApprovalPending);
        dashboardResponseDto.setTotalAvailable(totalAvailable);

        assertEquals(agencyName, dashboardResponseDto.getAgencyName());
        assertEquals(manufacturerId, dashboardResponseDto.getManufacturerId());
        assertEquals(districtName, dashboardResponseDto.getDistrictName());
        assertEquals(districtGeoId, dashboardResponseDto.getDistrictGeoId());
        assertEquals(totalProduction, dashboardResponseDto.getTotalProduction());
        assertEquals(batchTestApproved, dashboardResponseDto.getBatchTestApproved());
        assertEquals(batchTestRejected, dashboardResponseDto.getBatchTestRejected());
        assertEquals(notTestedQuantity, dashboardResponseDto.getNotTestedQuantity());
        assertEquals(totalDemand, dashboardResponseDto.getTotalDemand());
        assertEquals(totalSupply, dashboardResponseDto.getTotalSupply());
        assertEquals(lotApprovedQuantity, dashboardResponseDto.getLotApprovedQuantity());
        assertEquals(lotRejectedQuantity, dashboardResponseDto.getLotRejectedQuantity());
        assertEquals(lotApprovalPending, dashboardResponseDto.getLotApprovalPending());
        assertEquals(totalAvailable, dashboardResponseDto.getTotalAvailable());
    }

    @Test
    public void testDashboardTestingResponseDto() {
        DashboardTestingResponseDto dashboardTestingResponseDto = new DashboardTestingResponseDto();

        Double batchTested = 1000.0;
        Double lotApproved = 800.0;
        Double lotRejected = 200.0;
        Double lotInTransit = 100.0;
        Double availableTested = 700.0;
        Double batchRejected = 200.0;
        Double batchApproved = 800.0;
        Double totalDispatched = 1000.0;
        Long categoryId = 1L;
        String categoryName = "categoryName";
        String id = "id";

        dashboardTestingResponseDto.setBatchTested(batchTested);
        dashboardTestingResponseDto.setLotApproved(lotApproved);
        dashboardTestingResponseDto.setLotRejected(lotRejected);
        dashboardTestingResponseDto.setLotInTransit(lotInTransit);
        dashboardTestingResponseDto.setAvailableTested(availableTested);
        dashboardTestingResponseDto.setBatchRejected(batchRejected);
        dashboardTestingResponseDto.setBatchApproved(batchApproved);
        dashboardTestingResponseDto.setTotalDispatched(totalDispatched);
        dashboardTestingResponseDto.setCategoryId(categoryId);
        dashboardTestingResponseDto.setCategoryName(categoryName);
        dashboardTestingResponseDto.setId(id);

        assertEquals(batchTested, dashboardTestingResponseDto.getBatchTested());
        assertEquals(lotApproved, dashboardTestingResponseDto.getLotApproved());
        assertEquals(lotRejected, dashboardTestingResponseDto.getLotRejected());
        assertEquals(lotInTransit, dashboardTestingResponseDto.getLotInTransit());
        assertEquals(availableTested, dashboardTestingResponseDto.getAvailableTested());
        assertEquals(batchRejected, dashboardTestingResponseDto.getBatchRejected());
        assertEquals(batchApproved, dashboardTestingResponseDto.getBatchApproved());
        assertEquals(totalDispatched, dashboardTestingResponseDto.getTotalDispatched());
        assertEquals(categoryId, dashboardTestingResponseDto.getCategoryId());
        assertEquals(categoryName, dashboardTestingResponseDto.getCategoryName());
        assertEquals(id, dashboardTestingResponseDto.getId());
    }

    @Test
    public void testDashboardWarehouseResponseDto() {
        DashboardWarehouseResponseDto dashboardWarehouseResponseDto = new DashboardWarehouseResponseDto();

        Double accepted = 1000.0;
        Double rejected = 200.0;
        Double dispatched = 800.0;
        Double available = 600.0;
        String id = "id";

        dashboardWarehouseResponseDto.setAccepted(accepted);
        dashboardWarehouseResponseDto.setRejected(rejected);
        dashboardWarehouseResponseDto.setDispatched(dispatched);
        dashboardWarehouseResponseDto.setAvailable(available);
        dashboardWarehouseResponseDto.setId(id);

        assertEquals(accepted, dashboardWarehouseResponseDto.getAccepted());
        assertEquals(rejected, dashboardWarehouseResponseDto.getRejected());
        assertEquals(dispatched, dashboardWarehouseResponseDto.getDispatched());
        assertEquals(available, dashboardWarehouseResponseDto.getAvailable());
        assertEquals(id, dashboardWarehouseResponseDto.getId());
    }

    @Test
    public void testEmpanelledAggregatesResponseDto() {
        EmpanelledAggregatesResponseDto empanelledAggregatesResponseDto = new EmpanelledAggregatesResponseDto();

        Double approved = 1000.0;
        Double rejected = 200.0;
        Double transit = 800.0;
        Double dispatched = 600.0;
        Double available = 400.0;
        Long categoryId = 1L;
        String categoryName = "categoryName";
        String geoId = "geoId";
        Double receivedLots = 1200.0;

        empanelledAggregatesResponseDto.setApproved(approved);
        empanelledAggregatesResponseDto.setRejected(rejected);
        empanelledAggregatesResponseDto.setTransit(transit);
        empanelledAggregatesResponseDto.setDispatched(dispatched);
        empanelledAggregatesResponseDto.setAvailable(available);
        empanelledAggregatesResponseDto.setCategoryId(categoryId);
        empanelledAggregatesResponseDto.setCategoryName(categoryName);
        empanelledAggregatesResponseDto.setGeoId(geoId);
        empanelledAggregatesResponseDto.setReceivedLots(receivedLots);

        assertEquals(approved, empanelledAggregatesResponseDto.getApproved());
        assertEquals(rejected, empanelledAggregatesResponseDto.getRejected());
        assertEquals(transit, empanelledAggregatesResponseDto.getTransit());
        assertEquals(dispatched, empanelledAggregatesResponseDto.getDispatched());
        assertEquals(available, empanelledAggregatesResponseDto.getAvailable());
        assertEquals(categoryId, empanelledAggregatesResponseDto.getCategoryId());
        assertEquals(categoryName, empanelledAggregatesResponseDto.getCategoryName());
        assertEquals(geoId, empanelledAggregatesResponseDto.getGeoId());
        assertEquals(receivedLots, empanelledAggregatesResponseDto.getReceivedLots());
    }

    @Test
    public void testLocationResponseDto() {
        LocationResponseDto locationResponseDto = new LocationResponseDto();

        Long id = 1L;
        String name = "name";
        String code = "code";
        String geoId = "geoId";
        String stateName = "stateName";

        locationResponseDto.setId(id);
        locationResponseDto.setName(name);
        locationResponseDto.setCode(code);
        locationResponseDto.setGeoId(geoId);
        locationResponseDto.setStateName(stateName);

        assertEquals(id, locationResponseDto.getId());
        assertEquals(name, locationResponseDto.getName());
        assertEquals(code, locationResponseDto.getCode());
        assertEquals(geoId, locationResponseDto.getGeoId());
        assertEquals(stateName, locationResponseDto.getStateName());
    }

    @Test
    public void testLotListResponseDTO() {
        LotListResponseDTO lotListResponseDTO = new LotListResponseDTO();

        Long id = 1L;
        String lotNo = "lotNo";
        Long targetManufacturerId = 1L;
        Boolean isReceivedAtTarget = true;
        Boolean isTargetAcknowledgedReport = true;
        Boolean isTargetAccepted = true;
        Date dateOfManufacture = new Date();
        Date dateOfExpiry = new Date();
        Date dateOfReceiving = new Date();
        Date dateOfAcceptance = new Date();
        Date dateOfDispatch = new Date();
        String batchName = "batchName";
        String labName = "labName";
        Long labId = 1L;
        String prefetchedInstructions = "prefetchedInstructions";
        String manufacturerBatchNumber = "manufacturerBatchNumber";
        String licenseNumber = "licenseNumber";
        String manufacturerLotNumber = "manufacturerLotNumber";
        Double totalQuantity = 1000.0;
        Double remainingQuantity = 800.0;
        String state = "state";
        String uuid = "uuid";
        BatchResponseDto batch = new BatchResponseDto();
        String uom = "uom";
        String comments = "comments";
        String manufacturerName = "manufacturerName";
        String manufacturerAddress = "manufacturerAddress";
        CategoryResponseDto category = new CategoryResponseDto();
        Set<LotPropertyResponseDto> lotProperties = new HashSet<>();
        String createdBy = "createdBy";

        lotListResponseDTO.setId(id);
        lotListResponseDTO.setLotNo(lotNo);
        lotListResponseDTO.setTargetManufacturerId(targetManufacturerId);
        lotListResponseDTO.setIsReceivedAtTarget(isReceivedAtTarget);
        lotListResponseDTO.setIsTargetAcknowledgedReport(isTargetAcknowledgedReport);
        lotListResponseDTO.setIsTargetAccepted(isTargetAccepted);
        lotListResponseDTO.setDateOfManufacture(dateOfManufacture);
        lotListResponseDTO.setDateOfExpiry(dateOfExpiry);
        lotListResponseDTO.setDateOfReceiving(dateOfReceiving);
        lotListResponseDTO.setDateOfAcceptance(dateOfAcceptance);
        lotListResponseDTO.setDateOfDispatch(dateOfDispatch);
        lotListResponseDTO.setBatchName(batchName);
        lotListResponseDTO.setLabName(labName);
        lotListResponseDTO.setLabId(labId);
        lotListResponseDTO.setPrefetchedInstructions(prefetchedInstructions);
        lotListResponseDTO.setManufacturerBatchNumber(manufacturerBatchNumber);
        lotListResponseDTO.setLicenseNumber(licenseNumber);
        lotListResponseDTO.setManufacturerLotNumber(manufacturerLotNumber);
        lotListResponseDTO.setTotalQuantity(totalQuantity);
        lotListResponseDTO.setRemainingQuantity(remainingQuantity);
        lotListResponseDTO.setState(state);
        lotListResponseDTO.setUuid(uuid);
        lotListResponseDTO.setBatch(batch);
        lotListResponseDTO.setUom(uom);
        lotListResponseDTO.setComments(comments);
        lotListResponseDTO.setManufacturerName(manufacturerName);
        lotListResponseDTO.setManufacturerAddress(manufacturerAddress);
        lotListResponseDTO.setCategory(category);
        lotListResponseDTO.setLotProperties(lotProperties);
        lotListResponseDTO.setCreatedBy(createdBy);

        assertEquals(id, lotListResponseDTO.getId());
        assertEquals(lotNo, lotListResponseDTO.getLotNo());
        assertEquals(targetManufacturerId, lotListResponseDTO.getTargetManufacturerId());
        assertEquals(isReceivedAtTarget, lotListResponseDTO.getIsReceivedAtTarget());
        assertEquals(isTargetAcknowledgedReport, lotListResponseDTO.getIsTargetAcknowledgedReport());
        assertEquals(isTargetAccepted, lotListResponseDTO.getIsTargetAccepted());
        assertEquals(dateOfManufacture, lotListResponseDTO.getDateOfManufacture());
        assertEquals(dateOfExpiry, lotListResponseDTO.getDateOfExpiry());
        assertEquals(dateOfReceiving, lotListResponseDTO.getDateOfReceiving());
        assertEquals(dateOfAcceptance, lotListResponseDTO.getDateOfAcceptance());
        assertEquals(dateOfDispatch, lotListResponseDTO.getDateOfDispatch());
        assertEquals(batchName, lotListResponseDTO.getBatchName());
        assertEquals(labName, lotListResponseDTO.getLabName());
        assertEquals(labId, lotListResponseDTO.getLabId());
        assertEquals(prefetchedInstructions, lotListResponseDTO.getPrefetchedInstructions());
        assertEquals(manufacturerBatchNumber, lotListResponseDTO.getManufacturerBatchNumber());
        assertEquals(licenseNumber, lotListResponseDTO.getLicenseNumber());
        assertEquals(manufacturerLotNumber, lotListResponseDTO.getManufacturerLotNumber());
        assertEquals(totalQuantity, lotListResponseDTO.getTotalQuantity());
        assertEquals(remainingQuantity, lotListResponseDTO.getRemainingQuantity());
        assertEquals(state, lotListResponseDTO.getState());
        assertEquals(uuid, lotListResponseDTO.getUuid());
        assertEquals(batch, lotListResponseDTO.getBatch());
        assertEquals(uom, lotListResponseDTO.getUom());
        assertEquals(comments, lotListResponseDTO.getComments());
        assertEquals(manufacturerName, lotListResponseDTO.getManufacturerName());
        assertEquals(manufacturerAddress, lotListResponseDTO.getManufacturerAddress());
        assertEquals(category, lotListResponseDTO.getCategory());
        assertEquals(lotProperties, lotListResponseDTO.getLotProperties());
        assertEquals(createdBy, lotListResponseDTO.getCreatedBy());
    }

    @Test
    public void testLotResponseDto() {
        LotResponseDto lotResponseDto = new LotResponseDto();

        Long id = 1L;
        String lotNo = "lotNo";
        String qrcode = "qrcode";
        Long manufacturerId = 1L;
        Long targetManufacturerId = 1L;
        Date dateOfManufacture = new Date();
        Date dateOfExpiry = new Date();
        Boolean isReceivedAtTarget = true;
        Date dateOfReceiving = new Date();
        Boolean isTargetAcknowledgedReport = true;
        Boolean isTargetAccepted = true;
        Date dateOfAcceptance = new Date();
        Date dateOfDispatch = new Date();
        Double totalQuantity = 1000.0;
        Double remainingQuantity = 800.0;
        StateResponseDto state = new StateResponseDto();
        Date lastActionDate = new Date();
        String manufacturerLotNumber = "manufacturerLotNumber";
        TransportQuantityDetailsResponseDto transportQuantityDetailsResponseDto = new TransportQuantityDetailsResponseDto();
        CategoryResponseDto category = new CategoryResponseDto();
        List<MixMappingResponseDto> usage = new ArrayList<>();
        BatchResponseDto batch = new BatchResponseDto();
        UOMResponseDto uom = new UOMResponseDto();
        Set<SizeUnitResponseDto> sizeUnits = new HashSet<>();
        String comments = "comments";
        Set<WastageResponseDto> wastes = new HashSet<>();
        String prefetchedInstructions = "prefetchedInstructions";
        Set<LotResponseDto> sourceLots = new HashSet<>();
        Set<LotResponseDto> targetLots = new HashSet<>();
        Set<LotPropertyResponseDto> lotProperties = new HashSet<>();

        lotResponseDto.setId(id);
        lotResponseDto.setLotNo(lotNo);
        lotResponseDto.setQrcode(qrcode);
        lotResponseDto.setManufacturerId(manufacturerId);
        lotResponseDto.setTargetManufacturerId(targetManufacturerId);
        lotResponseDto.setDateOfManufacture(dateOfManufacture);
        lotResponseDto.setDateOfExpiry(dateOfExpiry);
        lotResponseDto.setIsReceivedAtTarget(isReceivedAtTarget);
        lotResponseDto.setDateOfReceiving(dateOfReceiving);
        lotResponseDto.setIsTargetAcknowledgedReport(isTargetAcknowledgedReport);
        lotResponseDto.setIsTargetAccepted(isTargetAccepted);
        lotResponseDto.setDateOfAcceptance(dateOfAcceptance);
        lotResponseDto.setDateOfDispatch(dateOfDispatch);
        lotResponseDto.setTotalQuantity(totalQuantity);
        lotResponseDto.setRemainingQuantity(remainingQuantity);
        lotResponseDto.setState(state);
        lotResponseDto.setLastActionDate(lastActionDate);
        lotResponseDto.setManufacturerLotNumber(manufacturerLotNumber);
        lotResponseDto.setTransportQuantityDetailsResponseDto(transportQuantityDetailsResponseDto);
        lotResponseDto.setCategory(category);
        lotResponseDto.setUsage(usage);
        lotResponseDto.setBatch(batch);
        lotResponseDto.setUom(uom);
        lotResponseDto.setSizeUnits(sizeUnits);
        lotResponseDto.setComments(comments);
        lotResponseDto.setWastes(wastes);
        lotResponseDto.setPrefetchedInstructions(prefetchedInstructions);
        lotResponseDto.setSourceLots(sourceLots);
        lotResponseDto.setTargetLots(targetLots);
        lotResponseDto.setLotProperties(lotProperties);

        assertEquals(id, lotResponseDto.getId());
        assertEquals(lotNo, lotResponseDto.getLotNo());
        assertEquals(qrcode, lotResponseDto.getQrcode());
        assertEquals(manufacturerId, lotResponseDto.getManufacturerId());
        assertEquals(targetManufacturerId, lotResponseDto.getTargetManufacturerId());
        assertEquals(dateOfManufacture, lotResponseDto.getDateOfManufacture());
        assertEquals(dateOfExpiry, lotResponseDto.getDateOfExpiry());
        assertEquals(isReceivedAtTarget, lotResponseDto.getIsReceivedAtTarget());
        assertEquals(dateOfReceiving, lotResponseDto.getDateOfReceiving());
        assertEquals(isTargetAcknowledgedReport, lotResponseDto.getIsTargetAcknowledgedReport());
        assertEquals(isTargetAccepted, lotResponseDto.getIsTargetAccepted());
        assertEquals(dateOfAcceptance, lotResponseDto.getDateOfAcceptance());
        assertEquals(dateOfDispatch, lotResponseDto.getDateOfDispatch());
        assertEquals(totalQuantity, lotResponseDto.getTotalQuantity());
        assertEquals(remainingQuantity, lotResponseDto.getRemainingQuantity());
        assertEquals(state, lotResponseDto.getState());
        assertEquals(lastActionDate, lotResponseDto.getLastActionDate());
        assertEquals(manufacturerLotNumber, lotResponseDto.getManufacturerLotNumber());
        assertEquals(transportQuantityDetailsResponseDto, lotResponseDto.getTransportQuantityDetailsResponseDto());
        assertEquals(category, lotResponseDto.getCategory());
        assertEquals(usage, lotResponseDto.getUsage());
        assertEquals(batch, lotResponseDto.getBatch());
        assertEquals(uom, lotResponseDto.getUom());
        assertEquals(sizeUnits, lotResponseDto.getSizeUnits());
        assertEquals(comments, lotResponseDto.getComments());
        assertEquals(wastes, lotResponseDto.getWastes());
        assertEquals(prefetchedInstructions, lotResponseDto.getPrefetchedInstructions());
        assertEquals(sourceLots, lotResponseDto.getSourceLots());
        assertEquals(targetLots, lotResponseDto.getTargetLots());
        assertEquals(lotProperties, lotResponseDto.getLotProperties());
    }

    @Test
    public void testLotStateGeoResponseDto() {
        LotStateGeoResponseDto lotStateGeoResponseDto = new LotStateGeoResponseDto();

        Long id = 1L;
        CategoryResponseDto category = new CategoryResponseDto();
        String pincode = "pincode";
        String districtGeoId = "districtGeoId";
        String stateGeoId = "stateGeoId";
        String countryGeoId = "countryGeoId";
        Double totalQuantity = 1000.0;
        Double inTransitQuantity = 800.0;
        Double approvedQuantity = 600.0;
        Double rejectedQuantity = 400.0;
        Double sentToLabTestQuantity = 200.0;
        Double testInProgressQuantity = 100.0;
        Double testedQuantity = 50.0;

        lotStateGeoResponseDto.setId(id);
        lotStateGeoResponseDto.setCategory(category);
        lotStateGeoResponseDto.setPincode(pincode);
        lotStateGeoResponseDto.setDistrictGeoId(districtGeoId);
        lotStateGeoResponseDto.setStateGeoId(stateGeoId);
        lotStateGeoResponseDto.setCountryGeoId(countryGeoId);
        lotStateGeoResponseDto.setTotalQuantity(totalQuantity);
        lotStateGeoResponseDto.setInTransitQuantity(inTransitQuantity);
        lotStateGeoResponseDto.setApprovedQuantity(approvedQuantity);
        lotStateGeoResponseDto.setRejectedQuantity(rejectedQuantity);
        lotStateGeoResponseDto.setSentToLabTestQuantity(sentToLabTestQuantity);
        lotStateGeoResponseDto.setTestInProgressQuantity(testInProgressQuantity);
        lotStateGeoResponseDto.setTestedQuantity(testedQuantity);

        assertEquals(id, lotStateGeoResponseDto.getId());
        assertEquals(category, lotStateGeoResponseDto.getCategory());
        assertEquals(pincode, lotStateGeoResponseDto.getPincode());
        assertEquals(districtGeoId, lotStateGeoResponseDto.getDistrictGeoId());
        assertEquals(stateGeoId, lotStateGeoResponseDto.getStateGeoId());
        assertEquals(countryGeoId, lotStateGeoResponseDto.getCountryGeoId());
        assertEquals(totalQuantity, lotStateGeoResponseDto.getTotalQuantity());
        assertEquals(inTransitQuantity, lotStateGeoResponseDto.getInTransitQuantity());
        assertEquals(approvedQuantity, lotStateGeoResponseDto.getApprovedQuantity());
        assertEquals(rejectedQuantity, lotStateGeoResponseDto.getRejectedQuantity());
        assertEquals(sentToLabTestQuantity, lotStateGeoResponseDto.getSentToLabTestQuantity());
        assertEquals(testInProgressQuantity, lotStateGeoResponseDto.getTestInProgressQuantity());
        assertEquals(testedQuantity, lotStateGeoResponseDto.getTestedQuantity());
    }

    @Test
    public void testManufacturerListDashboardResponseDto() {
        ManufacturerListDashboardResponseDto manufacturerListDashboardResponseDto = new ManufacturerListDashboardResponseDto();

        Long id = 1L;
        String name = "name";
        String completeAddress = "completeAddress";
        String type = "type";
        String vendorType = "vendorType";
        Boolean accreditedByAgency = true;
        String agencyName = "agencyName";
        String licenseNumber = "licenseNumber";
        String laneOne = "laneOne";
        String laneTwo = "laneTwo";
        String villageName = "villageName";
        String districtName = "districtName";
        String districtGeoId = "districtGeoId";
        String stateName = "stateName";
        String countryName = "countryName";
        String pinCode = "pinCode";
        String latitude = "latitude";
        String longitude = "longitude";

        manufacturerListDashboardResponseDto.setId(id);
        manufacturerListDashboardResponseDto.setName(name);
        manufacturerListDashboardResponseDto.setCompleteAddress(completeAddress);
        manufacturerListDashboardResponseDto.setType(type);
        manufacturerListDashboardResponseDto.setVendorType(vendorType);
        manufacturerListDashboardResponseDto.setAccreditedByAgency(accreditedByAgency);
        manufacturerListDashboardResponseDto.setAgencyName(agencyName);
        manufacturerListDashboardResponseDto.setLicenseNumber(licenseNumber);
        manufacturerListDashboardResponseDto.setLaneOne(laneOne);
        manufacturerListDashboardResponseDto.setLaneTwo(laneTwo);
        manufacturerListDashboardResponseDto.setVillageName(villageName);
        manufacturerListDashboardResponseDto.setDistrictName(districtName);
        manufacturerListDashboardResponseDto.setDistrictGeoId(districtGeoId);
        manufacturerListDashboardResponseDto.setStateName(stateName);
        manufacturerListDashboardResponseDto.setCountryName(countryName);
        manufacturerListDashboardResponseDto.setPinCode(pinCode);
        manufacturerListDashboardResponseDto.setLatitude(latitude);
        manufacturerListDashboardResponseDto.setLongitude(longitude);

        assertEquals(id, manufacturerListDashboardResponseDto.getId());
        assertEquals(name, manufacturerListDashboardResponseDto.getName());
        assertEquals(completeAddress, manufacturerListDashboardResponseDto.getCompleteAddress());
        assertEquals(type, manufacturerListDashboardResponseDto.getType());
        assertEquals(vendorType, manufacturerListDashboardResponseDto.getVendorType());
        assertEquals(accreditedByAgency, manufacturerListDashboardResponseDto.getAccreditedByAgency());
        assertEquals(agencyName, manufacturerListDashboardResponseDto.getAgencyName());
        assertEquals(licenseNumber, manufacturerListDashboardResponseDto.getLicenseNumber());
        assertEquals(laneOne, manufacturerListDashboardResponseDto.getLaneOne());
        assertEquals(laneTwo, manufacturerListDashboardResponseDto.getLaneTwo());
        assertEquals(villageName, manufacturerListDashboardResponseDto.getVillageName());
        assertEquals(districtName, manufacturerListDashboardResponseDto.getDistrictName());
        assertEquals(districtGeoId, manufacturerListDashboardResponseDto.getDistrictGeoId());
        assertEquals(stateName, manufacturerListDashboardResponseDto.getStateName());
        assertEquals(countryName, manufacturerListDashboardResponseDto.getCountryName());
        assertEquals(pinCode, manufacturerListDashboardResponseDto.getPinCode());
        assertEquals(latitude, manufacturerListDashboardResponseDto.getLatitude());
        assertEquals(longitude, manufacturerListDashboardResponseDto.getLongitude());
    }

    @Test
    public void testManufacturerResponseDto() {
        ManufacturerResponseDto manufacturerResponseDto = new ManufacturerResponseDto();

        Long id = 1L;
        String name = "name";
        String completeAddress = "completeAddress";
        String type = "type";
        Boolean accreditedByAgency = true;
        VendorType vendorType = VendorType.Manufacturer;
        String agencyName = "agencyName";
        String licenseNumber = "licenseNumber";
        Double totalScore = 100.0;
        String externalManufacturerId = "externalManufacturerId";
        Set<ManufacturerResponseDto> targetManufacturers = new HashSet<>();

        manufacturerResponseDto.setId(id);
        manufacturerResponseDto.setName(name);
        manufacturerResponseDto.setCompleteAddress(completeAddress);
        manufacturerResponseDto.setType(type);
        manufacturerResponseDto.setAccreditedByAgency(accreditedByAgency);
        manufacturerResponseDto.setVendorType(vendorType);
        manufacturerResponseDto.setAgencyName(agencyName);
        manufacturerResponseDto.setLicenseNumber(licenseNumber);
        manufacturerResponseDto.setTotalScore(totalScore);
        manufacturerResponseDto.setExternalManufacturerId(externalManufacturerId);
        manufacturerResponseDto.setTargetManufacturers(targetManufacturers);

        assertEquals(id, manufacturerResponseDto.getId());
        assertEquals(name, manufacturerResponseDto.getName());
        assertEquals(completeAddress, manufacturerResponseDto.getCompleteAddress());
        assertEquals(type, manufacturerResponseDto.getType());
        assertEquals(accreditedByAgency, manufacturerResponseDto.getAccreditedByAgency());
        assertEquals(vendorType, manufacturerResponseDto.getVendorType());
        assertEquals(agencyName, manufacturerResponseDto.getAgencyName());
        assertEquals(licenseNumber, manufacturerResponseDto.getLicenseNumber());
        assertEquals(totalScore, manufacturerResponseDto.getTotalScore());
        assertEquals(externalManufacturerId, manufacturerResponseDto.getExternalManufacturerId());
        assertEquals(targetManufacturers, manufacturerResponseDto.getTargetManufacturers());
    }

    @Test
    public void testMixMappingResponseDto() {
        MixMappingResponseDto mixMappingResponseDto = new MixMappingResponseDto();

        Long id = 1L;
        CategoryResponseDto sourceCategory = new CategoryResponseDto();
        LotListResponseDTO sourceLot = new LotListResponseDTO();
        BatchListResponseDTO targetBatch = new BatchListResponseDTO();
        Double quantityUsed = 1000.0;
        UOMResponseDto uom = new UOMResponseDto();

        mixMappingResponseDto.setId(id);
        mixMappingResponseDto.setSourceCategory(sourceCategory);
        mixMappingResponseDto.setSourceLot(sourceLot);
        mixMappingResponseDto.setTargetBatch(targetBatch);
        mixMappingResponseDto.setQuantityUsed(quantityUsed);
        mixMappingResponseDto.setUom(uom);

        assertEquals(id, mixMappingResponseDto.getId());
        assertEquals(sourceCategory, mixMappingResponseDto.getSourceCategory());
        assertEquals(sourceLot, mixMappingResponseDto.getSourceLot());
        assertEquals(targetBatch, mixMappingResponseDto.getTargetBatch());
        assertEquals(quantityUsed, mixMappingResponseDto.getQuantityUsed());
        assertEquals(uom, mixMappingResponseDto.getUom());
    }

    @Test
    public void testStateGeoDto() {
        StateGeoDto stateGeoDto = new StateGeoDto();

        String type = "type";
        Long categoryId = 1L;
        String categoryName = "categoryName";
        Boolean isIndependentBatch = true;
        String pincode = "pincode";
        String districtGeoId = "districtGeoId";
        String stateGeoId = "stateGeoId";
        String countryGeoId = "countryGeoId";
        Double totalQuantity = 1000.0;
        Double inProductionQuantity = 800.0;
        Double producedQuantity = 600.0;
        Double inTransitQuantity = 400.0;
        Double receivedQuantity = 200.0;
        Double approvedQuantity = 100.0;
        Double rejectedQuantity = 50.0;
        Double batchSampleInTransitQuantity = 25.0;
        Double batchSampleTestInProgressQuantity = 12.5;
        Double batchTestedQuantity = 6.25;
        Double lotSampleInTransitQuantity = 3.125;
        Double lotSampleTestInProgressQuantity = 1.5625;
        Double lotTestedQuantity = 0.78125;
        Double lotRejected = 0.390625;
        Double rejectedInTransitQuantity = 0.1953125;
        Double receivedRejectedQuantity = 0.09765625;
        Double sampleInTransitQuantity = 0.048828125;
        Double testInProgressQuantity = 0.0244140625;
        Double testedQuantity = 0.01220703125;
        Double remainingQuantity = 0.006103515625;
        Double usedQuantity = 0.0030517578125;

        stateGeoDto.setType(type);
        stateGeoDto.setCategoryId(categoryId);
        stateGeoDto.setCategoryName(categoryName);
        stateGeoDto.setIsIndependentBatch(isIndependentBatch);
        stateGeoDto.setPincode(pincode);
        stateGeoDto.setDistrictGeoId(districtGeoId);
        stateGeoDto.setStateGeoId(stateGeoId);
        stateGeoDto.setCountryGeoId(countryGeoId);
        stateGeoDto.setTotalQuantity(totalQuantity);
        stateGeoDto.setInProductionQuantity(inProductionQuantity);
        stateGeoDto.setProducedQuantity(producedQuantity);
        stateGeoDto.setInTransitQuantity(inTransitQuantity);
        stateGeoDto.setReceivedQuantity(receivedQuantity);
        stateGeoDto.setApprovedQuantity(approvedQuantity);
        stateGeoDto.setRejectedQuantity(rejectedQuantity);
        stateGeoDto.setBatchSampleInTransitQuantity(batchSampleInTransitQuantity);
        stateGeoDto.setBatchSampleTestInProgressQuantity(batchSampleTestInProgressQuantity);
        stateGeoDto.setBatchTestedQuantity(batchTestedQuantity);
        stateGeoDto.setLotSampleInTransitQuantity(lotSampleInTransitQuantity);
        stateGeoDto.setLotSampleTestInProgressQuantity(lotSampleTestInProgressQuantity);
        stateGeoDto.setLotTestedQuantity(lotTestedQuantity);
        stateGeoDto.setLotRejected(lotRejected);
        stateGeoDto.setRejectedInTransitQuantity(rejectedInTransitQuantity);
        stateGeoDto.setReceivedRejectedQuantity(receivedRejectedQuantity);
        stateGeoDto.setSampleInTransitQuantity(sampleInTransitQuantity);
        stateGeoDto.setTestInProgressQuantity(testInProgressQuantity);
        stateGeoDto.setTestedQuantity(testedQuantity);
        stateGeoDto.setRemainingQuantity(remainingQuantity);
        stateGeoDto.setUsedQuantity(usedQuantity);

        assertEquals(type, stateGeoDto.getType());
        assertEquals(categoryId, stateGeoDto.getCategoryId());
        assertEquals(categoryName, stateGeoDto.getCategoryName());
        assertEquals(isIndependentBatch, stateGeoDto.getIsIndependentBatch());
        assertEquals(pincode, stateGeoDto.getPincode());
        assertEquals(districtGeoId, stateGeoDto.getDistrictGeoId());
        assertEquals(stateGeoId, stateGeoDto.getStateGeoId());
        assertEquals(countryGeoId, stateGeoDto.getCountryGeoId());
        assertEquals(totalQuantity, stateGeoDto.getTotalQuantity());
        assertEquals(inProductionQuantity, stateGeoDto.getInProductionQuantity());
        assertEquals(producedQuantity, stateGeoDto.getProducedQuantity());
        assertEquals(inTransitQuantity, stateGeoDto.getInTransitQuantity());
        assertEquals(receivedQuantity, stateGeoDto.getReceivedQuantity());
        assertEquals(approvedQuantity, stateGeoDto.getApprovedQuantity());
        assertEquals(rejectedQuantity, stateGeoDto.getRejectedQuantity());
        assertEquals(batchSampleInTransitQuantity, stateGeoDto.getBatchSampleInTransitQuantity());
        assertEquals(batchSampleTestInProgressQuantity, stateGeoDto.getBatchSampleTestInProgressQuantity());
        assertEquals(batchTestedQuantity, stateGeoDto.getBatchTestedQuantity());
        assertEquals(lotSampleInTransitQuantity, stateGeoDto.getLotSampleInTransitQuantity());
        assertEquals(lotSampleTestInProgressQuantity, stateGeoDto.getLotSampleTestInProgressQuantity());
        assertEquals(lotTestedQuantity, stateGeoDto.getLotTestedQuantity());
        assertEquals(lotRejected, stateGeoDto.getLotRejected());
        assertEquals(rejectedInTransitQuantity, stateGeoDto.getRejectedInTransitQuantity());
        assertEquals(receivedRejectedQuantity, stateGeoDto.getReceivedRejectedQuantity());
        assertEquals(sampleInTransitQuantity, stateGeoDto.getSampleInTransitQuantity());
        assertEquals(testInProgressQuantity, stateGeoDto.getTestInProgressQuantity());
        assertEquals(testedQuantity, stateGeoDto.getTestedQuantity());
        assertEquals(remainingQuantity, stateGeoDto.getRemainingQuantity());
        assertEquals(usedQuantity, stateGeoDto.getUsedQuantity());
    }

    @Test
    public void testTestingResponseDto() {
        TestingResponseDto testingResponseDto = new TestingResponseDto();

        String id = "id";
        Double totalTestedQuantity = 1000.0;
        Double underTestingQuantity = 800.0;
        Double batchTestApprovedQuantity = 600.0;
        Double batchTestRejectedQuantity = 400.0;
        Double approvedQuantity = 200.0;
        Double rejectedQuantity = 100.0;
        Long categoryId = 1L;
        Double lotTestApprovedQuantity = 50.0;
        Double lotTestRejectedQuantity = 25.0;
        Double totalLotTestedQuantity = 12.5;

        testingResponseDto.setId(id);
        testingResponseDto.setTotalTestedQuantity(totalTestedQuantity);
        testingResponseDto.setUnderTestingQuantity(underTestingQuantity);
        testingResponseDto.setBatchTestApprovedQuantity(batchTestApprovedQuantity);
        testingResponseDto.setBatchTestRejectedQuantity(batchTestRejectedQuantity);
        testingResponseDto.setApprovedQuantity(approvedQuantity);
        testingResponseDto.setRejectedQuantity(rejectedQuantity);
        testingResponseDto.setCategoryId(categoryId);
        testingResponseDto.setLotTestApprovedQuantity(lotTestApprovedQuantity);
        testingResponseDto.setLotTestRejectedQuantity(lotTestRejectedQuantity);
        testingResponseDto.setTotalLotTestedQuantity(totalLotTestedQuantity);

        assertEquals(id, testingResponseDto.getId());
        assertEquals(totalTestedQuantity, testingResponseDto.getTotalTestedQuantity());
        assertEquals(underTestingQuantity, testingResponseDto.getUnderTestingQuantity());
        assertEquals(batchTestApprovedQuantity, testingResponseDto.getBatchTestApprovedQuantity());
        assertEquals(batchTestRejectedQuantity, testingResponseDto.getBatchTestRejectedQuantity());
        assertEquals(approvedQuantity, testingResponseDto.getApprovedQuantity());
        assertEquals(rejectedQuantity, testingResponseDto.getRejectedQuantity());
        assertEquals(categoryId, testingResponseDto.getCategoryId());
        assertEquals(lotTestApprovedQuantity, testingResponseDto.getLotTestApprovedQuantity());
        assertEquals(lotTestRejectedQuantity, testingResponseDto.getLotTestRejectedQuantity());
        assertEquals(totalLotTestedQuantity, testingResponseDto.getTotalLotTestedQuantity());
    }

    @Test
    public void testTransportQuantityDetailsResponseDto() {
        TransportQuantityDetailsResponseDto transportQuantityDetailsResponseDto = new TransportQuantityDetailsResponseDto();

        Long id = 1L;
        Long lotId = 1L;
        String purchaseOrderId = "purchaseOrderId";
        Long totalNoOfBags = 100L;
        Double dispatchedQuantity = 1000.0;
        Double grossWeight = 2000.0;
        Double tareWeight = 1000.0;
        Double netWeight = 1000.0;
        List<TransportVehicleDetailsResponseDto> vehicleDetailsResponseDtos = new ArrayList<>();

        transportQuantityDetailsResponseDto.setId(id);
        transportQuantityDetailsResponseDto.setLotId(lotId);
        transportQuantityDetailsResponseDto.setPurchaseOrderId(purchaseOrderId);
        transportQuantityDetailsResponseDto.setTotalNoOfBags(totalNoOfBags);
        transportQuantityDetailsResponseDto.setDispatchedQuantity(dispatchedQuantity);
        transportQuantityDetailsResponseDto.setGrossWeight(grossWeight);
        transportQuantityDetailsResponseDto.setTareWeight(tareWeight);
        transportQuantityDetailsResponseDto.setNetWeight(netWeight);
        transportQuantityDetailsResponseDto.setVehicleDetailsResponseDtos(vehicleDetailsResponseDtos);

        assertEquals(id, transportQuantityDetailsResponseDto.getId());
        assertEquals(lotId, transportQuantityDetailsResponseDto.getLotId());
        assertEquals(purchaseOrderId, transportQuantityDetailsResponseDto.getPurchaseOrderId());
        assertEquals(totalNoOfBags, transportQuantityDetailsResponseDto.getTotalNoOfBags());
        assertEquals(dispatchedQuantity, transportQuantityDetailsResponseDto.getDispatchedQuantity());
        assertEquals(grossWeight, transportQuantityDetailsResponseDto.getGrossWeight());
        assertEquals(tareWeight, transportQuantityDetailsResponseDto.getTareWeight());
        assertEquals(netWeight, transportQuantityDetailsResponseDto.getNetWeight());
        assertEquals(vehicleDetailsResponseDtos, transportQuantityDetailsResponseDto.getVehicleDetailsResponseDtos());
    }

    @Test
    public void testTransportVehicleDetailsResponseDto() {
        TransportVehicleDetailsResponseDto transportVehicleDetailsResponseDto = new TransportVehicleDetailsResponseDto();

        Long id = 1L;
        Long lotId = 1L;
        String childPurchaseOrderId = "childPurchaseOrderId";
        String vehicleNo = "vehicleNo";
        String driverName = "driverName";
        String driverLicense = "driverLicense";
        String driverContactNo = "driverContactNo";
        Long totalBags = 100L;
        Double totalTruckQuantity = 1000.0;

        transportVehicleDetailsResponseDto.setId(id);
        transportVehicleDetailsResponseDto.setLotId(lotId);
        transportVehicleDetailsResponseDto.setChildPurchaseOrderId(childPurchaseOrderId);
        transportVehicleDetailsResponseDto.setVehicleNo(vehicleNo);
        transportVehicleDetailsResponseDto.setDriverName(driverName);
        transportVehicleDetailsResponseDto.setDriverLicense(driverLicense);
        transportVehicleDetailsResponseDto.setDriverContactNo(driverContactNo);
        transportVehicleDetailsResponseDto.setTotalBags(totalBags);
        transportVehicleDetailsResponseDto.setTotalTruckQuantity(totalTruckQuantity);

        assertEquals(id, transportVehicleDetailsResponseDto.getId());
        assertEquals(lotId, transportVehicleDetailsResponseDto.getLotId());
        assertEquals(childPurchaseOrderId, transportVehicleDetailsResponseDto.getChildPurchaseOrderId());
        assertEquals(vehicleNo, transportVehicleDetailsResponseDto.getVehicleNo());
        assertEquals(driverName, transportVehicleDetailsResponseDto.getDriverName());
        assertEquals(driverLicense, transportVehicleDetailsResponseDto.getDriverLicense());
        assertEquals(driverContactNo, transportVehicleDetailsResponseDto.getDriverContactNo());
        assertEquals(totalBags, transportVehicleDetailsResponseDto.getTotalBags());
        assertEquals(totalTruckQuantity, transportVehicleDetailsResponseDto.getTotalTruckQuantity());
    }


}