package com.beehyv.fortification.listeners;

import com.beehyv.fortification.config.SpringApplicationContextHolder;
import com.beehyv.fortification.dto.iam.*;
import com.beehyv.fortification.dto.responseDto.AddressLocationResponseDto;
import com.beehyv.fortification.dto.responseDto.LocationResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.manager.CategoryManager;
import com.beehyv.fortification.manager.LotManager;
import com.beehyv.fortification.manager.RoleCategoryManager;
import com.beehyv.fortification.manager.StateManager;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LotTaskListenerTest {

    AddressResponseDto addressResponseDto = new AddressResponseDto(1L, "null", "null", "null", new VillageResponseDto(1L, "a", new DistrictResponseDto(1L, "null", "null", "", "", new StateResponseDto(1L, "null", "null", new CountryResponseDto(1L, "a", "null")))), 10.0, 10.0);
    AddressLocationResponseDto addressLocationResponseDto = new AddressLocationResponseDto("1", "1", new LocationResponseDto(), new LocationResponseDto(), new LocationResponseDto(), new LocationResponseDto(), "1", 10.0, 10.0);
    HashMap<String, Object> userInfo = new HashMap<>();
    Category category = new Category(1L, "name", null, null, null, true, 1);
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private TaskService taskService;
    @Mock
    private LotManager lotManager;
    @Mock
    private StateManager stateManager;
    @Mock
    private RoleCategoryManager roleCategoryManager;
    @Mock
    private CategoryManager categoryManager;
    @Mock
    private KeycloakInfo keycloakInfo;
    @Mock
    private DelegateTask delegateTask;
    @InjectMocks
    private LotTaskListener lotTaskListener;
    private Lot lot;
    private State state;
    private RoleCategory roleCategory1;
    private RoleCategory roleCategory2;
    private List<RoleCategory> roleCategories;
    private HashMap<String, Object> taskVariables;
    private MockedStatic<SpringApplicationContextHolder> mockStatic;
    private MockedStatic<IamServiceRestHelper> mockStaticIamServiceRestHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userInfo.put("manufacturerAddress", addressLocationResponseDto);
        Mockito.when(applicationContext.getBean(TaskService.class)).thenReturn(taskService);
        Mockito.when(applicationContext.getBean(LotManager.class)).thenReturn(lotManager);
        Mockito.when(applicationContext.getBean(StateManager.class)).thenReturn(stateManager);
        Mockito.when(applicationContext.getBean(RoleCategoryManager.class)).thenReturn(roleCategoryManager);
        Mockito.when(applicationContext.getBean(CategoryManager.class)).thenReturn(categoryManager);
        Mockito.when(applicationContext.getBean(KeycloakInfo.class)).thenReturn(keycloakInfo);
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(keycloakInfo.getAccessToken()).thenReturn("token");
        when(categoryManager.findAllByNames(any())).thenReturn(Collections.singletonList(category));

        lot = new Lot();
        lot.setId(1L);
        lot.setCategory(category);

        state = new State();
        state.setId(1L);
        state.setName("state_name");

        roleCategory1 = new RoleCategory();
        roleCategory1.setRoleName("role1");
        roleCategory1.setRoleCategoryType(RoleCategoryType.MODULE);
        roleCategory1.setId(1L);
        roleCategory1.setCategory(category);

        roleCategory2 = new RoleCategory();
        roleCategory2.setRoleName("role2");
        roleCategory2.setRoleCategoryType(RoleCategoryType.LAB);
        roleCategory2.setId(1L);
        roleCategory2.setCategory(category);

        roleCategories = new ArrayList<>();
        roleCategories.add(roleCategory1);
        roleCategories.add(roleCategory2);

        taskVariables = new HashMap<>();
        taskVariables.put("lotId", lot.getId().toString());
        taskVariables.put("categoryId", roleCategory1.getId().toString());
        taskVariables.put("manufacturerId", "org1");
    }

    @Test
    public void testNotifyCreateEvent() throws JsonProcessingException {
        mockStatic = mockStatic(SpringApplicationContextHolder.class);
        mockStaticIamServiceRestHelper = mockStatic(IamServiceRestHelper.class);
        when(SpringApplicationContextHolder.getApplicationContext()).thenReturn(applicationContext);
        addressResponseDto = new AddressResponseDto(1L, "null", "null", "null", new VillageResponseDto(1L, "a", new DistrictResponseDto(1L, "", "", "", "", new StateResponseDto(1L, "null", "1", new CountryResponseDto(1L, "a", "null")))), 10.0, 10.0);
        when(IamServiceRestHelper.fetchResponse(any(), eq(AddressResponseDto.class), any())).thenReturn(addressResponseDto);
        when(IamServiceRestHelper.fetchResponse(any(), eq(Boolean.class), any())).thenReturn(true);


        when(delegateTask.getTaskDefinitionKey()).thenReturn("approved");
        when(delegateTask.getDescription()).thenReturn(new ObjectMapper().writeValueAsString(taskVariables));
        when(delegateTask.getName()).thenReturn(state.getName());
        when(delegateTask.getEventName()).thenReturn("create");
        when(lotManager.findById(lot.getId())).thenReturn(lot);
        when(stateManager.findByName(state.getName())).thenReturn(state);
        when(roleCategoryManager.findAllByCategoryIdAndState(roleCategory1.getId(), state.getId())).thenReturn(roleCategories);
        when(categoryManager.getSourceCategory(any(), any())).thenReturn(Collections.singletonList(category));
        lotTaskListener.notify(delegateTask);

        verify(delegateTask, times(1)).setAssignee(any());
        verify(delegateTask, times(1)).setOwner(lot.getId().toString());
        verify(lotManager, times(1)).save(lot);

        mockStatic.close();
        mockStaticIamServiceRestHelper.close();
    }


    private void setupMocksForExternalTest(boolean isExternalTest) {
        when(delegateTask.getVariable("externalTest")).thenReturn(isExternalTest ? "true" : "false");
    }
}