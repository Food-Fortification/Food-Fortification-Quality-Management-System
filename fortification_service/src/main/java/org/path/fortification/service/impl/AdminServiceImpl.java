package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.*;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.entity.*;
import org.path.fortification.manager.*;
import org.path.fortification.mapper.*;
import org.path.fortification.enums.EntityType;
import org.path.fortification.enums.ManufacturerCategoryAction;
import org.path.fortification.helper.Constants;
import org.path.fortification.helper.IamServiceRestHelper;
import org.path.fortification.helper.LabServiceManagementHelper;
import org.path.fortification.service.AdminService;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {
    private final BaseMapper<BatchListResponseDTO, BatchRequestDto, Batch> batchListMapper = BaseMapper.getForListClass(BatchMapper.class);
    private final BaseMapper<CategoryResponseDto, CategoryRequestDto, Category> categoryMapper = BaseMapper.getForClass(CategoryMapper.class);
    @Autowired
    private BatchManager batchManager;

    @Autowired
    private LotManager lotManager;

    @Autowired
    private KeycloakInfo keycloakInfo;

    private CategoryManager manager;

    private final BaseMapper<RoleCategoryResponseDto, RoleCategoryRequestDto, RoleCategory> roleCategoryBaseMapper =
            BaseMapper.getForClass(RoleCategoryMapper.class);

    @Autowired
    private RoleCategoryManager roleCategoryManager;

    @Autowired
    private RoleCategoryStateManager roleCategoryStateManager;

    @Autowired
    private StateManager stateManager;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private LabConfigCategoryManager labConfigCategoryManager;

    @Autowired
    private SourceCategoryMappingManager sourceCategoryMappingManager;



    private final BaseMapper<LotListResponseDTO, LotRequestDto, Lot> lotListMapper = BaseMapper.getForListClass(LotMapper.class);

    private Set<String> roles = new HashSet<>();

    @Override
    public ListResponse getAllBatches(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        return this.getAllBatches(pageNumber, pageSize, searchRequest, false);
    }


    public ListResponse getAllBatches(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, Boolean remQuantity) {
        List<Long> testManufacturerIds = this.getTestManufacturerIds();
        List<Batch> batches = batchManager.findAllBatches(pageNumber, pageSize, searchRequest, remQuantity, testManufacturerIds);
        Long count = ((Integer) batches.size()).longValue();
        ListResponse<BatchListResponseDTO> dtos = ListResponse.from(batches, batchListMapper::toListDTO, count);
        List<Long> batchIds = dtos.getData().stream().map(BatchListResponseDTO::getId).toList();
        Map<Long, LabNameAddressResponseDto> labNameAddressResponseDtoMap = LabServiceManagementHelper.fetchLabNameAddressByLotIds(keycloakInfo.getAccessToken(), batchIds, EntityType.batch);
        dtos.getData()
                .forEach(dto -> {
                    LabNameAddressResponseDto labNameAddressResponseDto = labNameAddressResponseDtoMap.get(dto.getId());
                    if(labNameAddressResponseDto != null){
                        dto.setLabName(labNameAddressResponseDto.getName());
                        dto.setLabId(labNameAddressResponseDto.getId());
                        dto.setLabCertificateNumber(labNameAddressResponseDto.getLabCertificateNumber());
                    }
                });
        return dtos;
    }

    @Override
    public ListResponse<LotListResponseDTO> getAllLots(Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest) {
        List<Long> testManufacturerIds = this.getTestManufacturerIds();
        List<Lot> entities = lotManager.findAllByCategoryIds(searchListRequest, pageNumber, pageSize, testManufacturerIds);
        Long count = lotManager.getCount(entities.size(), pageNumber, pageSize, searchListRequest, testManufacturerIds);
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
                });
        return dtos;
    }

    @Override
    public List<CategoryResponseDto> getAllCategoriesForAdmin(Integer pageNumber, Integer pageSize) {
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).toList();
        List<String> categoryNames = roleSplitList.stream().map(r -> r[0]).toList();
        List<Category> categories = manager.findAllByNames(categoryNames);
        return categories.stream().map(categoryMapper::toDto).toList();
    }

    private List<Long> getTestManufacturerIds() {
        String url = Constants.IAM_SERVICE_URL + "/manufacturer/test-manufacturers";
        List<Integer> testManufacturerIds = (List<Integer>) IamServiceRestHelper.fetchResponse(url, List.class, keycloakInfo.getAccessToken());
        return testManufacturerIds.stream().mapToLong(Integer::longValue).boxed().toList();
    }

    public String updateDSL(DSLDto dto) {
        Product productEntity;
        Optional<Product> product = productManager.findByName(dto.getProduct());
        if (product.isPresent()) {
            productEntity = product.get();
        } else {
            product = Optional.of(new Product());
            product.get().setName(dto.getProduct());
            product.get().setDescription(dto.getDescription());
            productEntity = productManager.create(product.get());
        }

        dto.getCategories().forEach(categoryDslDto -> {
            Category category = checkAndSaveCategory
                    (categoryDslDto.getName(), productEntity, categoryDslDto.isOutsidePlatform());
        });
        dto.getStages().forEach(stage -> {
            List<WorkflowDto> workflowDtos = dto.getWorkflow().stream().filter(workflowDto -> workflowDto.getName().contentEquals(stage)).collect(Collectors.toList());
            if (workflowDtos.size() == 1) {
                WorkflowDto workflowDto = workflowDtos.get(0);
                List<CategoryDslDto> categoryDslDtos = workflowDto.getCategories();
                Category stageCategory;
                List<Category> stageCategories = manager.findCategoryListByName(stage);
                if (stageCategories.isEmpty()) {
                    stageCategory = null;
                } else {
                    stageCategory = stageCategories.get(0);
                }
                if (!categoryDslDtos.isEmpty()) {
                    categoryDslDtos.forEach(categoryDslDto -> {
                        Category category = checkAndSaveCategory
                                (categoryDslDto.getName(), productEntity, categoryDslDto.isOutsidePlatform());
                        List<String> rawMaterials = categoryDslDto.getRawMaterials();
                        if (!rawMaterials.isEmpty()) {
                            rawMaterials.forEach(raw -> {
                                Category source = checkAndSaveCategory(raw, productEntity, categoryDslDto.isOutsidePlatform());
                                List<CategoryDslDto> dtos = dto.getCategories().stream()
                                        .filter(categoryDslDto1 -> categoryDslDto1.getName().contentEquals(source.getName())).toList();
                                if (!dtos.isEmpty()) {
                                    if (!dtos.get(0).isOutsidePlatform()) {
                                        List<String> usersStateList = new ArrayList<>();
                                        usersStateList.add("approved");
                                        checkAndSaveRoleCategoryAndState(
                                                category,
                                                source,
                                                "USER",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateList);

                                        List<String> usersStateListAdmin = new ArrayList<>();
                                        usersStateListAdmin.add("approved");
                                        checkAndSaveRoleCategoryAndState(
                                                category,
                                                source,
                                                "ADMIN",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateListAdmin);
                                    }
                                }

                                updateSourceCategories(category, source);
                            });
                        }


                        if (categoryDslDto.getType().contentEquals("creation")) {
                            checkAndSaveLabConfig(category, category, categoryDslDto.getDispatchLabOption().toLowerCase());
                            //                         batch state
                            if (categoryDslDto.getDispatchLabOption().contentEquals("OPTIONAL") ||
                                    categoryDslDto.getDispatchLabOption().contentEquals("MANDATE")) {
                                List<String> usersStateList = new ArrayList<>();
                                usersStateList.add("created");
                                usersStateList.add("batchSampleLabTestDone");
                                usersStateList.add("rejected");
                                usersStateList.add("batchToDispatch");
                                usersStateList.add("partiallyDispatched");
                                usersStateList.add("fullyDispatched");
                                usersStateList.add("batchSampleRejected");

                                // lot
                                usersStateList.add("sentBackRejected");
                                usersStateList.add("receivedRejected");

                                checkAndSaveRoleCategoryAndState(
                                        Objects.requireNonNullElse(stageCategory, category),
                                        category,
                                        "USER",
                                        RoleCategoryType.MODULE,
                                        categoryDslDto.getTarget(),
                                        usersStateList);


                                List<String> labStateList = new ArrayList<>();
                                labStateList.add("sentBatchSampleToLabTest");
                                labStateList.add("batchSampleInLab");

                                //lot
                                labStateList.add("sentLotSampleToLabTest");
                                labStateList.add("lotSampleInLab");

                                checkAndSaveRoleCategoryAndState(
                                        Objects.requireNonNullElse(stageCategory, category),
                                        category,
                                        "USER",
                                        RoleCategoryType.LAB,
                                        categoryDslDto.getTarget(),
                                        labStateList);

                                List<String> usersStateListAdmin = new ArrayList<>();
                                usersStateListAdmin.add("created");
                                usersStateListAdmin.add("batchSampleLabTestDone");
                                usersStateListAdmin.add("rejected");
                                usersStateListAdmin.add("batchToDispatch");
                                usersStateListAdmin.add("partiallyDispatched");
                                usersStateListAdmin.add("fullyDispatched");
                                usersStateListAdmin.add("batchSampleRejected");

                                // lot
                                usersStateListAdmin.add("sentBackRejected");
                                usersStateListAdmin.add("receivedRejected");
                                checkAndSaveRoleCategoryAndState(
                                        Objects.requireNonNullElse(stageCategory, category),
                                        category,
                                        "ADMIN",
                                        RoleCategoryType.MODULE,
                                        categoryDslDto.getTarget(),
                                        usersStateListAdmin);

                                List<String> labStateListAdmin = new ArrayList<>();
                                labStateListAdmin.add("sentBatchSampleToLabTest");
                                labStateListAdmin.add("batchSampleInLab");

                                //lot
                                labStateListAdmin.add("sentLotSampleToLabTest");
                                labStateListAdmin.add("lotSampleInLab");
                                checkAndSaveRoleCategoryAndState(
                                        Objects.requireNonNullElse(stageCategory, category),
                                        category,
                                        "ADMIN",
                                        RoleCategoryType.LAB,
                                        categoryDslDto.getTarget(),
                                        labStateListAdmin);

                            } else {
                                List<String> usersStateList = new ArrayList<>();
                                usersStateList.add("created");
                                usersStateList.add("batchToDispatch");
                                usersStateList.add("partiallyDispatched");
                                usersStateList.add("fullyDispatched");
                                checkAndSaveRoleCategoryAndState(
                                        Objects.requireNonNullElse(stageCategory, category),
                                        category,
                                        "USER",
                                        RoleCategoryType.MODULE,
                                        categoryDslDto.getTarget(),
                                        usersStateList);

                                List<String> usersStateListAdmin = new ArrayList<>();
                                usersStateListAdmin.add("created");
                                usersStateListAdmin.add("batchToDispatch");
                                usersStateListAdmin.add("partiallyDispatched");
                                usersStateListAdmin.add("fullyDispatched");
                                checkAndSaveRoleCategoryAndState(
                                        Objects.requireNonNullElse(stageCategory, category),
                                        category,
                                        "ADMIN",
                                        RoleCategoryType.MODULE,
                                        categoryDslDto.getTarget(),
                                        usersStateListAdmin);

                            }
                        }

                        List<TargetDto> targetDtos = categoryDslDto.getTarget();

                        if (!targetDtos.isEmpty()) {
                            targetDtos.forEach(targetDto -> {
                                Category target = checkAndSaveCategory(targetDto.getName(), productEntity, categoryDslDto.isOutsidePlatform());

                                if (categoryDslDto.getType().contentEquals("creation")) {
                                    checkAndSaveSourceMappings(
                                            Objects.requireNonNullElse(stageCategory, category),
                                            Objects.requireNonNullElse(stageCategory, category),
                                            target,
                                            ManufacturerCategoryAction.CREATION
                                    );

                                } else if (categoryDslDto.getType().contentEquals("dispatch")) {
                                    Category base = checkAndSaveCategory(workflowDto.getName(), productEntity, categoryDslDto.isOutsidePlatform());
                                    checkAndSaveSourceMappings(
                                            Objects.requireNonNullElse(stageCategory, category),
                                            Objects.requireNonNullElse(stageCategory, category),
                                            target,
                                            ManufacturerCategoryAction.LOT_TO_LOT_DISPATCH
                                    );

                                    // if dispatched start setting states for user
                                    if (categoryDslDto.getDispatchLabOption().contentEquals("OPTIONAL") ||
                                            categoryDslDto.getDispatchLabOption().contentEquals("MANDATE")) {
                                        List<String> usersStateList = new ArrayList<>();
                                        usersStateList.add("approved");
                                        usersStateList.add("sentBackRejected");
                                        usersStateList.add("receivedRejected");
                                        usersStateList.add("lotSampleRejected");

                                        checkAndSaveRoleCategoryAndState(
                                                target,
                                                base,
                                                "USER",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateList);
                                        checkAndSaveRoleCategoryAndState(
                                                Objects.requireNonNullElse(stageCategory, category),
                                                category,
                                                "USER",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateList);

                                        List<String> labStateList = new ArrayList<>();

                                        labStateList.add("sentLotSampleToLabTest");
                                        labStateList.add("lotSampleInLab");

                                        checkAndSaveRoleCategoryAndState(
                                                target,
                                                base,
                                                "USER",
                                                RoleCategoryType.LAB,
                                                categoryDslDto.getTarget(),
                                                labStateList);

                                        List<String> usersStateListAdmin = new ArrayList<>();
                                        usersStateListAdmin.add("approved");
                                        usersStateListAdmin.add("sentBackRejected");
                                        usersStateListAdmin.add("receivedRejected");
                                        checkAndSaveRoleCategoryAndState(
                                                category,
                                                category,
                                                "ADMIN",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateListAdmin);
                                        checkAndSaveRoleCategoryAndState(
                                                Objects.requireNonNullElse(stageCategory, category),
                                                category,
                                                "ADMIN",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateListAdmin);
                                        List<String> labStateListAdmin = new ArrayList<>();
                                        labStateListAdmin.add("approved");
                                        labStateListAdmin.add("sentLotSampleToLabTest");
                                        labStateListAdmin.add("lotSampleInLab");
                                        checkAndSaveRoleCategoryAndState(
                                                target,
                                                category,
                                                "ADMIN",
                                                RoleCategoryType.LAB,
                                                categoryDslDto.getTarget(),
                                                labStateListAdmin);
                                    } else {
                                        List<String> usersStateList = new ArrayList<>();
                                        usersStateList.add("approved");
                                        usersStateList.add("sentBackRejected");
                                        usersStateList.add("receivedRejected");
                                        usersStateList.add("lotSampleRejected");
                                        checkAndSaveRoleCategoryAndState(
                                                target,
                                                category,
                                                "USER",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateList);
                                        checkAndSaveRoleCategoryAndState(
                                                Objects.requireNonNullElse(stageCategory, category),
                                                category,
                                                "USER",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateList);
                                        List<String> usersStateListAdmin = new ArrayList<>();
                                        usersStateListAdmin.add("approved");
                                        usersStateListAdmin.add("sentBackRejected");
                                        usersStateListAdmin.add("receivedRejected");
                                        checkAndSaveRoleCategoryAndState(
                                                Objects.requireNonNullElse(stageCategory, category),
                                                category,
                                                "ADMIN",
                                                RoleCategoryType.MODULE,
                                                categoryDslDto.getTarget(),
                                                usersStateListAdmin);
                                    }

                                }


                                // setting states for approver
                                List<String> usersStateList = new ArrayList<>();
                                usersStateList.add("dispatched");
                                usersStateList.add("lotReceived");
                                usersStateList.add("lotSampleRejected");
                                usersStateList.add("toSendBackRejected");
                                usersStateList.add("approved");
                                usersStateList.add("lotSampleLabTestDone");

                                checkAndSaveRoleCategoryAndState(
                                        target,
                                        category,
                                        "APPROVER",
                                        RoleCategoryType.MODULE,
                                        categoryDslDto.getTarget(),
                                        usersStateList);

                                List<String> usersStateListAdmin = new ArrayList<>();
                                usersStateListAdmin.add("dispatched");
                                usersStateListAdmin.add("lotReceived");
                                usersStateListAdmin.add("lotSampleRejected");
                                usersStateListAdmin.add("toSendBackRejected");
                                usersStateListAdmin.add("approved");
                                usersStateList.add("lotSampleLabTestDone");
//                                usersStateListAdmin.add("rejected");
                                checkAndSaveRoleCategoryAndState(
                                        manager.findCategoryByName(targetDto.getName()),
                                        category,
                                        "ADMIN",
                                        RoleCategoryType.MODULE,
                                        categoryDslDto.getTarget(),
                                        usersStateListAdmin);

                                if (targetDto.getReceiveLabOption().contentEquals("OPTIONAL") ||
                                        targetDto.getReceiveLabOption().contentEquals("MANDATE")) {
                                    List<String> usersStateListLab = new ArrayList<>();
                                    usersStateListLab.add("lotSampleLabTestDone");
                                    usersStateListLab.add("lotSampleRejected");
                                    usersStateListLab.add("toSendBackRejected");
                                    usersStateListLab.add("approved");
                                    usersStateList.add("lotSampleLabTestDone");
//                                    usersStateListLab.add("rejected");
                                    checkAndSaveRoleCategoryAndState(
                                            manager.findCategoryByName(targetDto.getName()),
                                            category,
                                            "APPROVER",
                                            RoleCategoryType.LAB,
                                            categoryDslDto.getTarget(),
                                            usersStateListLab);

                                    List<String> usersStateListAdminLab = new ArrayList<>();
                                    usersStateListAdminLab.add("lotSampleLabTestDone");
//                                    usersStateListAdminLab.add("lotSampleRejected");
//                                    usersStateListAdminLab.add("toSendBackRejected");
//                                    usersStateListAdminLab.add("approved");
//                                    usersStateList.add("lotSampleLabTestDone");
//                                    usersStateListAdminLab.add("rejected");
                                    checkAndSaveRoleCategoryAndState(
                                            manager.findCategoryByName(targetDto.getName()),
                                            category,
                                            "ADMIN",
                                            RoleCategoryType.LAB,
                                            categoryDslDto.getTarget(),
                                            usersStateListAdminLab);

                                }
                                checkAndSaveLabConfig(category, target, targetDto.getReceiveLabOption().toLowerCase());
                            });
                        }

                    });
                }
            }
        });


        IamServiceRestHelper.addKeycloakRoles(roles, keycloakInfo.getAccessToken());
        return "dsl executed successfully";
    }

    private void checkAndSaveSourceMappings(Category returnCategory, Category source, Category target, ManufacturerCategoryAction categoryAction) {
        SourceCategoryMapping sourceCategoryMapping =
                sourceCategoryMappingManager.findByIds(returnCategory.getId(), source.getId(), target.getId());
        if (sourceCategoryMapping == null) {
            sourceCategoryMapping = new SourceCategoryMapping();
            sourceCategoryMapping.setSourceCategory(source);
            sourceCategoryMapping.setTargetCategory(target);
            sourceCategoryMapping.setReturnCategory(returnCategory);
            sourceCategoryMapping.setCategoryAction(categoryAction);
            sourceCategoryMappingManager.create(sourceCategoryMapping);
        }
    }

    private void checkAndSaveLabConfig(Category source, Category category, String dispatchLabOption) {
        LabConfigCategory labConfigCategory = labConfigCategoryManager.findByCategoryIds(source.getId(), category.getId());
        if (labConfigCategory == null) {
            labConfigCategory = new LabConfigCategory();
            labConfigCategory.setLabOption(dispatchLabOption);
            labConfigCategory.setSourceCategory(source);
            labConfigCategory.setTargetCategory(category);
            labConfigCategoryManager.create(labConfigCategory);
        } else {
            if (!labConfigCategory.getLabOption().contentEquals(dispatchLabOption)) {
                labConfigCategory.setLabOption(dispatchLabOption);
                labConfigCategoryManager.update(labConfigCategory);
            }

        }

    }

    private void updateSourceCategories(Category category, Category source) {
        Set<Category> sourceCategories = category.getSourceCategories();
        if (sourceCategories != null) {
            if (!sourceCategories.isEmpty()) {
                if (sourceCategories.stream().noneMatch(category1 -> Objects.equals(category1.getId(), source.getId()))) {
                    sourceCategories.add(source);
                    category.setSourceCategories(sourceCategories);
                    manager.update(category);
                }
            } else {
                sourceCategories.add(source);
                category.setSourceCategories(sourceCategories);
                manager.update(category);
            }
        } else {
            sourceCategories = new HashSet<>();
            sourceCategories.add(source);
            category.setSourceCategories(sourceCategories);
            manager.update(category);
        }
    }

    private void checkAndSaveRoleCategoryAndState(Category category, Category baseCategory, String role, RoleCategoryType type, List<TargetDto> target, List<String> stateList) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(category.getName().toUpperCase() + "_" + role + "_" + type);
        RoleCategory roleCategory = roleCategoryManager
                .findByCategoryAndRoleNames(category.getName(), role, type);
        if (roleCategory == null) {
            roleCategory = new RoleCategory();
            roleCategory.setRoleName(role);
            roleCategory.setRoleCategoryType(type);
            roleCategory.setCategory(category);
            roleCategory = roleCategoryManager.create(roleCategory);
        }


        final Set<RoleCategoryState> roleCategoryStates = new HashSet<>();
        if (roleCategory.getRoleCategoryStates() != null) {
            roleCategoryStates.addAll(roleCategory.getRoleCategoryStates());
        }
        RoleCategory finalRoleCategory = roleCategory;
        stateList.forEach(state -> {
            RoleCategoryState roleCategoryState =
                    checkAndSaveRoleCategoryState(baseCategory, finalRoleCategory, stateManager.findByName(state));
            if (roleCategoryStates.stream().noneMatch(state1 -> Objects.equals(state1.getId(), roleCategoryState.getId()))) {
                roleCategoryStates.add(roleCategoryState);

            }

        });

        if (roleCategory.getRoleCategoryStates() != null && roleCategoryStates.size() != roleCategory.getRoleCategoryStates().size()) {
            roleCategory.setRoleCategoryStates(roleCategoryStates);
            roleCategoryManager.update(roleCategory);
        }

    }

    private RoleCategoryState checkAndSaveRoleCategoryState(Category baseCategory, RoleCategory roleCategory, State state) {
        RoleCategoryState roleCategoryState = roleCategoryStateManager
                .findByIdState(baseCategory.getId(), roleCategory.getId(), state.getId());

        if (roleCategoryState == null) {
            roleCategoryState = new RoleCategoryState();
            roleCategoryState.setCategory(baseCategory);
            roleCategoryState.setRoleCategory(roleCategory);
            roleCategoryState.setState(state);
            roleCategoryState = roleCategoryStateManager.create(roleCategoryState);
        }
        return roleCategoryState;
    }

    private Category checkAndSaveCategory(String name, Product productEntity, boolean outsidePlatform) {
        List<Category> categories = manager.findCategoryListByName(name);
        Category category = new Category();
        if (categories.isEmpty()) {
            category.setName(name);
            category.setProduct(productEntity);
            Integer sequence = manager.findSequence();
            if (sequence == null) {
                sequence = 1;
            } else {
                sequence += 1;
            }
            category.setSequence(sequence);
            category.setIndependentBatch(outsidePlatform);
            category = manager.create(category);
        } else {
            category = categories.get(0);
        }
        return category;
    }


}