package com.beehyv.fortification.listeners;

import com.beehyv.fortification.config.SpringApplicationContextHolder;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.manager.BatchManager;
import com.beehyv.fortification.manager.RoleCategoryManager;
import com.beehyv.fortification.manager.StateManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchTaskListenerTest {

    @Mock
    SpringApplicationContextHolder springApplicationContextHolder;
    Batch batch = new Batch();
    State state = new State();
    RoleCategory roleCategory1 = new RoleCategory();
    RoleCategory roleCategory2 = new RoleCategory();
    List<RoleCategory> roleCategories = new ArrayList<>();
    Category category = new Category();
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private TaskService taskService;
    @Mock
    private BatchManager batchManager;
    @Mock
    private StateManager stateManager;
    @Mock
    private RoleCategoryManager roleCategoryManager;
    @Mock
    private DelegateTask delegateTask;
    @InjectMocks
    private BatchTaskListener batchTaskListener;
    private MockedStatic<SpringApplicationContextHolder> mockStatic;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(applicationContext.getBean(TaskService.class)).thenReturn(taskService);
        Mockito.when(applicationContext.getBean(BatchManager.class)).thenReturn(batchManager);
        Mockito.when(applicationContext.getBean(StateManager.class)).thenReturn(stateManager);
        Mockito.when(applicationContext.getBean(RoleCategoryManager.class)).thenReturn(roleCategoryManager);
        springApplicationContextHolder.setApplicationContext(applicationContext);


        batch.setId(1L);
        batch.setTaskId("task1");

        state.setId(1L);
        state.setName("state_name");

        category.setId(1L);
        category.setName("category_name");

        roleCategory1.setRoleName("role1");
        roleCategory1.setRoleCategoryType(RoleCategoryType.LAB);
        roleCategory1.setId(1L);
        roleCategory1.setCategory(category);

        roleCategories.add(roleCategory1);


        HashMap<String, Object> taskVariables = new HashMap<>();
        taskVariables.put("batchId", batch.getId().toString());
        taskVariables.put("categoryId", roleCategory1.getId().toString());
        taskVariables.put("manufacturerId", "org1");


        try {
            when(delegateTask.getDescription()).thenReturn(new ObjectMapper().writeValueAsString(taskVariables));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        when(delegateTask.getVariable(any())).thenReturn("a");
        when(delegateTask.getId()).thenReturn("task1");
        when(delegateTask.getTaskDefinitionKey()).thenReturn("batchSampleLabTestDone");
        when(delegateTask.getName()).thenReturn(state.getName());
        when(delegateTask.getEventName()).thenReturn("create");
        when(batchManager.findById(batch.getId())).thenReturn(batch);
        when(stateManager.findByName(state.getName())).thenReturn(state);
        when(roleCategoryManager.findAllByCategoryIdAndState(roleCategory1.getId(), state.getId())).thenReturn(roleCategories);


    }

    @AfterEach
    public void tearDown() {


    }

    @Test
    public void testNotifyCreateEvent() {

        mockStatic = mockStatic(SpringApplicationContextHolder.class);
        when(SpringApplicationContextHolder.getApplicationContext()).thenReturn(applicationContext);
        // Act
        batchTaskListener.notify(delegateTask);

        // Assert
        verify(delegateTask, times(1)).setAssignee(any());
        verify(delegateTask, times(1)).setOwner(any());
        verify(batchManager, times(1)).save(any());
        mockStatic.close();
    }

    @Test
    public void testNotifyBatchSampleLabTestDoneEvent() {
        // Act
        mockStatic = mockStatic(SpringApplicationContextHolder.class);
        when(SpringApplicationContextHolder.getApplicationContext()).thenReturn(applicationContext);
        batchTaskListener.notify(delegateTask);

        // Assert
        verify(taskService, times(1)).complete(any(), any());
        mockStatic.close();
    }

    @Test
    public void testNotifySelfTestedBatchEvent() {
        mockStatic = mockStatic(SpringApplicationContextHolder.class);
        when(SpringApplicationContextHolder.getApplicationContext()).thenReturn(applicationContext);

        when(delegateTask.getTaskDefinitionKey()).thenReturn("selfTestedBatch");
        when(delegateTask.getVariable("sampleTestResult")).thenReturn(SampleTestResult.TEST_FAILED.name());
        when(batchManager.findById(batch.getId())).thenReturn(batch);

        // Act
        batchTaskListener.notify(delegateTask);

        // Assert
        verify(taskService, times(1)).complete(any(), any());
        mockStatic.close();
    }

    @Test
    public void testNotifyOtherEvents() {
        // Arrange
        mockStatic = mockStatic(SpringApplicationContextHolder.class);
        when(SpringApplicationContextHolder.getApplicationContext()).thenReturn(applicationContext);
        when(delegateTask.getTaskDefinitionKey()).thenReturn("otherEvent");

        // Act
        batchTaskListener.notify(delegateTask);

        // Assert
        verifyNoInteractions(taskService);
        mockStatic.close();
    }
}