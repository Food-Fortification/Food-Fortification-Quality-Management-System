package org.path.fortification.manager;

import org.path.fortification.dao.LabConfigCategoryDao;
import org.path.fortification.dto.iam.*;
import org.path.fortification.dto.responseDto.LabNameAddressResponseDto;
import org.path.fortification.entity.*;
import org.path.fortification.helper.IamServiceRestHelper;
import org.path.fortification.helper.LabServiceManagementHelper;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ActivitiManagerTest {


    Map<Long, LabNameAddressResponseDto> labMap = new HashMap<>();
    Batch batch = new Batch();
    Category category = new Category(1L, "null", new Product(1L, "null", null), null, null, true, null);
    Map<String, Object> userData = new HashMap<>();
    String labOption = "option1";
    ProcessInstance processInstance = new MockProcessInstance();
    ArrayList<Task> tasks = new ArrayList<>();
    Task mockTask = mock(Task.class);
    TaskQuery mockTaskQuery = mock(TaskQuery.class);
    Batch mockBatch = mock(Batch.class);
    Lot entity = new Lot();
    @Mock
    private RuntimeService runtimeService;
    @Mock
    private TaskService taskService;
    @Mock
    private RepositoryService repositoryService;
    @Mock
    private ResourceLoader resourceLoader;
    @Mock
    private KeycloakInfo keycloakInfo;
    @Mock
    private LabConfigCategoryDao labConfigCategoryDao;
    @InjectMocks
    private ActivitiManager activitiManager;
    @Mock
    private IamServiceRestHelper iamServiceRestHelper;
    @Mock
    private UriComponentsBuilder uriComponentsBuilder;
    @Mock
    private UriComponents uriComponents;
    private final ObjectMapper mapper = new ObjectMapper();
    private MockedStatic<UriComponentsBuilder> uriComponentsBuilderMockedStatic;
    private MockedStatic<IamServiceRestHelper> mockStatic;
    private MockedStatic<LabServiceManagementHelper> labServiceManagementHelperMockedStatic;

    @BeforeEach
    void setUp() throws JsonProcessingException {

        entity.setId(1L);
        entity.setTotalQuantity(50.0);
        entity.setUom(new UOM());
        entity.setState(new State());
        entity.setTargetManufacturerId(1L);
        entity.setLastActionDate(new Date());
        batch.setId(1L);
        batch.setManufacturerId(1L);
        batch.setCategory(category);


        // Mock ResourceLoader
        when(resourceLoader.getResource(anyString())).thenReturn(new MockResource());

        // Mock KeycloakInfo
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", Set.of("ROLE_USER"));
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(keycloakInfo.getAccessToken()).thenReturn("mock_access_token");

        when(runtimeService.startProcessInstanceByKey(any(), (Map<String, Object>) any())).thenReturn(processInstance);
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(keycloakInfo.getAccessToken()).thenReturn("a");

        mockStatic = mockStatic(IamServiceRestHelper.class);
        labServiceManagementHelperMockedStatic = mockStatic(LabServiceManagementHelper.class);
        uriComponentsBuilderMockedStatic = Mockito.mockStatic(UriComponentsBuilder.class);

        UriComponentsBuilder uriComponentsBuilderSpy = Mockito.spy(UriComponentsBuilder.class);
        when(UriComponentsBuilder.fromHttpUrl(Mockito.anyString())).thenReturn(uriComponentsBuilderSpy);
        when(UriComponentsBuilder.fromHttpUrl(any(String.class))).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.queryParam(any(), anyCollection())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.queryParam(any(), (Object) any())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        when(uriComponents.toUriString()).thenReturn("https://example.com/api/endpoint");
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(new AddressResponseDto(1L, "null", "null", "null", new VillageResponseDto(1L, "a", new DistrictResponseDto(1L, "null", "null", "", "", new StateResponseDto(1L, "null", "null", new CountryResponseDto(1L, "a", "null")))), 10.0, 10.0));


        when(mockBatch.getTaskId()).thenReturn("1");
        when(taskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskQuery.taskId(any())).thenReturn(mockTaskQuery);
        tasks.add(mockTask);
        when(mockTaskQuery.processInstanceId(any())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.list()).thenReturn(tasks);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTask.getAssignee()).thenReturn("assignee");
    }

    @AfterEach
    public void tearDown() {
        mockStatic.close();
        labServiceManagementHelperMockedStatic.close();
        uriComponentsBuilderMockedStatic.close();
    }

    @Test
    void testStartBatchProcess() {


        // Call the method under test
        Task result = activitiManager.startBatchProcess(batch, userData, category, labOption);

        // Verify the result
        assertNotNull(result);
    }

    @Test
    void testGetProcessName() {
        // Prepare test data
        String entity = "batch";
        String state = "state1";
        String labOption = "option1";

        // Call the method under test
        String result = activitiManager.getProcessName(entity, category, state, labOption);

        // Verify the result
        assertNotNull(result);
        // Add more assertions based on the expected behavior
    }

    @Test
    void testGetStateFromToken() {
        // Prepare test data
        Map<String, Object> userData = new HashMap<>();
        Long manufacturerId = 1L;

        // Call the method under test
        String result = activitiManager.getStateFromToken(userData, manufacturerId);

        // Verify the result
        assertNotNull(result);
        // Add more assertions based on the expected behavior
    }

    @Test
    void testStartLotProcess() {
        // Prepare test data

        Map<String, Object> userData = new HashMap<>();
        String labOption = "option1";

        // Call the method under test
        Task result = activitiManager.startLotProcess(entity, userData, category, labOption);

        // Verify the result
        assertNotNull(result);
    }


    @Test
    void testGetTaskActionsLot() {
        // Prepare test data

        List<RoleCategory> roleCategories = new ArrayList<>();
        Map<String, Object> userData = new HashMap<>();
        List<String> states = new ArrayList<>();

        // Call the method under test
        List<String> result = activitiManager.getTaskActions(entity, roleCategories, userData, states);

        // Verify the result
        assertNotNull(result);
        // Add more assertions based on the expected behavior
    }


    @Test
    void testSetVariable() {
        // Prepare test data
        String executionId = "execution_id";
        String name = "variable_name";
        String value = "variable_value";

        // Call the method under test
        activitiManager.setVariable(executionId, name, value);

        verify(runtimeService, times(1)).setVariableLocal(any(), any(), any());
    }


    @Test
    void testRemoveVariable() {
        // Prepare test data
        String executionId = "execution_id";
        String name = "variable_name";

        // Call the method under test
        activitiManager.removeVariable(executionId, name);

        // Verify the result
        // Add assertions based on the expected behavior
    }

    @Test
    void testCompleteTask() {
        // Arrange
        String taskId = "1";
        Map<String, Object> userData = new HashMap<>();
        userData.put("manufacturerId", "1");
        userData.put("RoleNames", Arrays.asList("role1", "role2"));
        State state = new State();
        state.setName("batchSampleRejected");
        boolean isBlocked = false;

        Task mockTask = mock(Task.class);
        when(mockTask.getProcessInstanceId()).thenReturn("1");
        when(mockTask.getTaskDefinitionKey()).thenReturn("taskKey");
        when(mockTask.getAssignee()).thenReturn("role1_1_MODULE");
        when(mockTask.getExecutionId()).thenReturn("1");

        TaskQuery mockTaskQuery = mock(TaskQuery.class);
        when(mockTaskQuery.taskId(taskId)).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTask.getName()).thenReturn("taskName");

        when(taskService.createTaskQuery()).thenReturn(mockTaskQuery);

        ProcessInstance mockProcessInstance = mock(ProcessInstance.class);
        when(mockProcessInstance.getProcessDefinitionId()).thenReturn("1");
        when(mockProcessInstance.getName()).thenReturn("processName");

        when(runtimeService.createProcessInstanceQuery()).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId("1")).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId("1").singleResult()).thenReturn(mockProcessInstance);

        SequenceFlow mockSequenceFlow = mock(SequenceFlow.class);
        when(mockSequenceFlow.getSourceRef()).thenReturn("taskName");
        when(mockSequenceFlow.getTargetRef()).thenReturn("targetRef");

        Collection<FlowElement> flowElements = new ArrayList<>();
        flowElements.add(mockSequenceFlow);

        when(repositoryService.getBpmnModel("1")).thenReturn(mock(BpmnModel.class));
        when(repositoryService.getBpmnModel("1").getProcess("processName")).thenReturn(mock(Process.class));
        when(repositoryService.getBpmnModel("1").getProcess("processName").getFlowElements()).thenReturn(flowElements);

        // Act
        boolean result = activitiManager.completeTask(taskId, userData, state, isBlocked);

        // Assert
        assertTrue(result);
    }

}

class MockProcessInstance implements ProcessInstance {
    @Override
    public String getProcessDefinitionId() {
        return "";
    }

    @Override
    public String getProcessDefinitionName() {
        return "";
    }

    @Override
    public String getProcessDefinitionKey() {
        return "";
    }

    @Override
    public Integer getProcessDefinitionVersion() {
        return 0;
    }

    @Override
    public String getDeploymentId() {
        return "";
    }

    @Override
    public String getBusinessKey() {
        return "";
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public boolean isEnded() {
        return false;
    }

    @Override
    public String getActivityId() {
        return "";
    }

    @Override
    public String getProcessInstanceId() {
        return "";
    }

    @Override
    public String getParentId() {
        return "";
    }

    @Override
    public String getSuperExecutionId() {
        return "";
    }

    @Override
    public String getRootProcessInstanceId() {
        return "";
    }

    @Override
    public String getParentProcessInstanceId() {
        return "";
    }

    @Override
    public Map<String, Object> getProcessVariables() {
        return Map.of();
    }

    @Override
    public String getTenantId() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getLocalizedName() {
        return "";
    }

    @Override
    public String getLocalizedDescription() {
        return "";
    }

    @Override
    public Date getStartTime() {
        return null;
    }

    @Override
    public String getStartUserId() {
        return "";
    }
    // Implement required methods
}

class MockResource implements org.springframework.core.io.Resource {
    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public URL getURL() throws IOException {
        return null;
    }

    @Override
    public URI getURI() throws IOException {
        return null;
    }

    @Override
    public File getFile() throws IOException {
        return null;
    }

    @Override
    public long contentLength() throws IOException {
        return 0;
    }

    @Override
    public long lastModified() throws IOException {
        return 0;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }
    // Implement required methods
}
