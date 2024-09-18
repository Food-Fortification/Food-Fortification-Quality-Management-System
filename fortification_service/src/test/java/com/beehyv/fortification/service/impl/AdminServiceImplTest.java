package com.beehyv.fortification.service.impl;


import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.LabNameAddressResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.helper.LabServiceManagementHelper;
import com.beehyv.fortification.manager.*;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
public class AdminServiceImplTest {

    @Mock
    SourceCategoryMappingManager sourceCategoryMappingManager;
    @Mock
    LabConfigCategoryManager labConfigCategoryManager;
    @Mock
    KeycloakInfo keycloakInfo;
    Category category = new Category();
    MockedStatic<IamServiceRestHelper> mockedStaticIam;
    MockedStatic<LabServiceManagementHelper> mockedStaticLab;
    @Mock
    private BatchManager batchManager;
    @Mock
    private RoleCategoryManager roleCategoryManager;
    @Mock
    private RoleCategoryStateManager roleCategoryStateManager;
    @Mock
    private LotManager lotManager;
    @Mock
    private StateManager stateManager;
    @Mock
    private CategoryManager categoryManager;
    @Mock
    private ProductManager productManager;
    @Mock
    private CategoryManager manager;
    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        category.setName("category1");
        Category category2 = new Category();
        category2.setName("category1");
        when(manager.create(any())).thenReturn(category2);
        when(manager.findCategoryByName(any())).thenReturn(category);
        when(manager.findCategoryListByName(any())).thenReturn(Collections.singletonList(category));
        when(roleCategoryManager.findAllByCategoryAndRoleNames(any())).thenReturn(List.of(new RoleCategory()));
        when(roleCategoryManager.create(any())).thenReturn(new RoleCategory());
        when(roleCategoryStateManager.findByIdState(any(), any(), any())).thenReturn(new RoleCategoryState());
        when(roleCategoryStateManager.create(any())).thenReturn(new RoleCategoryState());
        when(stateManager.findByName(any())).thenReturn(new State());

        mockedStaticIam = org.mockito.Mockito.mockStatic(IamServiceRestHelper.class);
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(List.of(1));

        Map<Long, LabNameAddressResponseDto> res = new HashMap<>();
        res.put(1L, new LabNameAddressResponseDto());
        mockedStaticLab = org.mockito.Mockito.mockStatic(LabServiceManagementHelper.class);
        when(LabServiceManagementHelper.fetchLabNameAddressByLotIds(any(), any(), any())).thenReturn(res);

        when(keycloakInfo.getAccessToken()).thenReturn("accessToken");
    }

    @AfterEach
    public void tearDown() {
        mockedStaticIam.close();
        mockedStaticLab.close();
    }

    @Test
    public void testGetAllBatches() {

        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest searchRequest = new SearchListRequest();

        ListResponse result = adminService.getAllBatches(pageNumber, pageSize, searchRequest);
        assert result != null;
    }

    @Test
    public void testUpdateDSL() {
        // Arrange
        DSLDto dto = new DSLDto();
        dto.setProduct("product1");
        dto.setDescription("description1");

        CategoryDslDto categoryDslDto = new CategoryDslDto();
        categoryDslDto.setName("category1");
        categoryDslDto.setOutsidePlatform(false);
        categoryDslDto.setType("creation");
        categoryDslDto.setDispatchLabOption("OPTIONAL");
        categoryDslDto.setRawMaterials(Arrays.asList("raw1", "raw2"));

        TargetDto targetDto = new TargetDto();
        targetDto.setName("target1");
        targetDto.setReceiveLabOption("OPTIONAL");
        categoryDslDto.setTarget(List.of(targetDto));

        dto.setCategories(List.of(categoryDslDto));
        dto.setStages(List.of("stage1"));

        WorkflowDto workflowDto = new WorkflowDto();
        workflowDto.setName("stage1");
        workflowDto.setCategories(List.of(categoryDslDto));
        dto.setWorkflow(List.of(workflowDto));

        Product product = new Product();
        product.setName("product1");
        product.setDescription("description1");

        when(productManager.findByName("product1")).thenReturn(Optional.of(product));
        when(productManager.create(any(Product.class))).thenReturn(product);


        dto.setCategories(List.of(categoryDslDto));


        // Act
        String result = adminService.updateDSL(dto);
        assertEquals("dsl executed successfully", result);


        //Act with different type
        categoryDslDto.setType("dispatch");
        workflowDto.setCategories(List.of(categoryDslDto));
        dto.setCategories(List.of(categoryDslDto));

        result = adminService.updateDSL(dto);
        assertEquals("dsl executed successfully", result);


        //Act with different no DispatchLabOption
        categoryDslDto.setDispatchLabOption("random");

        result = adminService.updateDSL(dto);
        assertEquals("dsl executed successfully", result);
    }

}