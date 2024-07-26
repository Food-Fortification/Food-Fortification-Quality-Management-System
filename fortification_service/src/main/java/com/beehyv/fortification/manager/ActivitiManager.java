package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.LabConfigCategoryDao;
import com.beehyv.fortification.dto.iam.AddressResponseDto;
import com.beehyv.fortification.dto.responseDto.AddressLocationResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.helper.Constants;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ActivitiManager {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    KeycloakInfo keycloakInfo;

    @Autowired
    LabConfigCategoryDao labConfigCategoryDao;

    private final ObjectMapper mapper = new ObjectMapper();

    public Task startBatchProcess(Batch batch, Map userData, Category category, String labOption) {
        HashMap<String, Object> variables = new HashMap<>();
        HashMap<String, String> config = new HashMap<>();
        config.put("batchId", batch.getId().toString());
        config.put("categoryId", batch.getCategory().getId().toString());
        config.put("manufacturerId", batch.getManufacturerId().toString());
        try {
            variables.put("config", mapper.writeValueAsString(config));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String processKey = getProcessName("batch", category, getStateFromToken(userData, batch.getManufacturerId()),
                labOption);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);

        // normalize XML response
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        try {
            task.setDescription(mapper.writeValueAsString(config));
            task.setOwner(batch.getId().toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        taskService.saveTask(task);
        return task;
    }

    public String getProcessName(String entity, Category category, String state, String labOption) {
        String processName = "";
        List<String> processCombinations = new ArrayList<>();

        if (labOption.isBlank() || labOption.isEmpty()) {
            processCombinations.add(entity + "-" + "default" + "-lab-na");
        } else {
            processCombinations.add(entity + "-" + "default" + "-" + "lab" + "-" + labOption);
        }



        processCombinations.add(entity + "-" + category.getProduct().getName().toLowerCase() + "-" +
                category.getName().toLowerCase() + "-" + state);
        processCombinations.add(entity + "-" + "default" + "-" +
                category.getName().toLowerCase() + "-" + state);
        processCombinations.add(entity + "-" + category.getProduct().getName().toLowerCase() + "-" +
                category.getName().toLowerCase() + "-" + "default");
        processCombinations.add(entity + "-" + "default" + "-" +
                category.getName().toLowerCase() + "-" + "default");
        processCombinations.add(entity + "-" + category.getProduct().getName().toLowerCase() + "-" +
                "default" + "-" + state);
        processCombinations.add(entity + "-" + "default" + "-" +
                "default" + "-" + state);
        processCombinations.add(entity + "-" + category.getProduct().getName().toLowerCase() + "-" +
                "default" + "-" + "default");
        processCombinations.add(entity + "-" + "default" + "-" +
                "default" + "-" + "default");
        for (String combination: processCombinations) {
            if (resourceLoader.getResource("classpath:processes/" + combination + ".bpmn").exists()) {
                processName = combination;
                break;
            }
        }
        return processName;
    }

    public String getStateFromToken(Map userData, Long manufacturerId) {
        String state = "default";
        try {
            AddressLocationResponseDto address = mapper.readValue(userData.getOrDefault("manufacturerAddress", "").toString(),
                    AddressLocationResponseDto.class);
            state = address.getState().getName().toLowerCase();
        } catch (JsonProcessingException e) {
            String url = Constants.IAM_SERVICE_URL + "address/manufacturer/" + manufacturerId;
            AddressResponseDto manufacturerAddress = IamServiceRestHelper.fetchResponse(url, AddressResponseDto.class, keycloakInfo.getAccessToken());
            state = manufacturerAddress.getVillage().getDistrict().getState().getName().toLowerCase();
        }
        return state;
    }

    public Task startLotProcess(Lot lot, Map userData, Category category, String labOption) {
        if(isSuperAdmin())throw new CustomException("Cannot create lot on behalf of another user", HttpStatus.BAD_REQUEST);
        HashMap<String, Object> variables = new HashMap<>();
        HashMap<String, String> config = new HashMap<>();
        config.put("lotId", lot.getId().toString());
        config.put("categoryId", category.getId().toString());
        config.put("manufacturerId", lot.getTargetManufacturerId().toString());
        try {
            variables.put("config", mapper.writeValueAsString(config));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String processKey = getProcessName("lot", category, getStateFromToken(userData, lot.getManufacturerId()),
                labOption);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);

        // normalize XML response
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        try {
            task.setDescription(mapper.writeValueAsString(config));
            task.setOwner(lot.getId().toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        taskService.saveTask(task);
        return task;
    }

    public List<String> getTaskActions(Batch batch, List<RoleCategory> roleCategories, Map userData, List<String> states) {
        return getTaskActions(batch, null, roleCategories, userData, states);
    }

    public List<String> getTaskActions(Lot lot, List<RoleCategory> roleCategories, Map userData, List<String> states) {
        return getTaskActions(null, lot, roleCategories, userData, states);
    }

    public List<String> getTaskActions(Batch batch, Lot lot, List<RoleCategory> roleCategories, Map userData, List<String> states) {
        List actions = new ArrayList();
        String organizationId = userData.getOrDefault("manufacturerId", "").toString();
        String type = "MODULE";
        if (TextUtils.isEmpty(organizationId)) {
            organizationId = userData.getOrDefault("labId", "1").toString();
            type = "LAB";
        }
        String instanceId = batch == null ? lot.getTaskId() : batch.getTaskId();
        String state = batch == null ? lot.getState().getName() : batch.getState().getName();
        Task task = taskService.createTaskQuery().taskId(instanceId).singleResult();
        String finalOrganizationId = organizationId;
        String finalType = type;
        if ((task == null || roleCategories.stream().noneMatch((roleCategory ->
                task.getAssignee().contains(roleCategory.getRoleName() + "_" + finalOrganizationId + "_" + finalType +
                        "_" + roleCategory.getCategory().getName()))))) {
            return actions;
        }
        if (task == null) return actions;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        Collection<FlowElement> flowElements = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId()).getProcess(processInstance.getName()).getFlowElements();
        for (FlowElement element : flowElements) {
            if (element instanceof SequenceFlow && state.toLowerCase().equals(((SequenceFlow) element).getSourceRef().toLowerCase())) {
                if (!states.contains(((SequenceFlow) element).getTargetRef())) {
                    actions.add(((SequenceFlow) element).getTargetRef());
                }
            }
        }
        return actions;
    }

    private boolean isSuperAdmin() {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
    }

    public boolean completeTask(String taskId, Map userData, State state, boolean isBlocked) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String organizationId = userData.getOrDefault("manufacturerId", "").toString();
        String type = "MODULE";
        if (TextUtils.isEmpty(organizationId)) {
            organizationId = userData.getOrDefault("labId", "1").toString();
            type = "LAB";
        }
        boolean isSuperAdmin = this.isSuperAdmin();
        List<String> roles = (List<String>) userData.get("RoleNames");
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        Collection<FlowElement> flowElements = repositoryService
                .getBpmnModel(processInstance.getProcessDefinitionId()).getProcess(processInstance.getName())
                .getFlowElements();
        List<String> actions = new ArrayList<>();
        for (FlowElement element: flowElements) {
            if (element instanceof SequenceFlow && task.getName().equalsIgnoreCase(((SequenceFlow) element).getSourceRef())) {
                actions.add(((SequenceFlow) element).getTargetRef());
            }
        }
        Map<String, Object> variables = actions.stream().collect(Collectors.toMap(d -> task.getTaskDefinitionKey() + "_" + d, d -> state.getName().equalsIgnoreCase(d)));
        boolean hasAccess = false;
            for (String role : roles) {
                if (!isBlocked && (task.getAssignee().contains(role + '_' + organizationId + "_" + type))) {
                    hasAccess = true;
                    break;
                }
            }
        if (hasAccess) {
            log.info("Task Definition key: {{}}", task.getTaskDefinitionKey());
            log.info("Actions Config: {{}}", variables);
            taskService.complete(taskId, variables, true);
            if (List.of("batchSampleRejected","lotSampleRejected").contains(state.getName()))
                removeVariable(task.getExecutionId(), "batchLabId");
            return true;
        }
        return false;
    }

    public  void setVariable(String executionId, String name, String value){
        runtimeService.setVariableLocal(executionId, name, value);
    }
    public String getVariable(String executionId, String name){
        return runtimeService.getVariable(executionId, name).toString();
    }

    public void removeVariable(String executionId, String name){
        runtimeService.removeVariableLocal(executionId,name);

    }
}
