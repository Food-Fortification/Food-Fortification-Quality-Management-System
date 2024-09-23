package com.beehyv.fortification.listeners;

import com.beehyv.fortification.config.SpringApplicationContextHolder;
import com.beehyv.fortification.dto.iam.AddressResponseDto;
import com.beehyv.fortification.dto.responseDto.AddressLocationResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.helper.Constants;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class LotTaskListener implements org.activiti.engine.delegate.TaskListener {

    public static TaskService getTaskService() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(TaskService.class);
    }

    public static LotManager getLotManager() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(LotManager.class);
    }

    public static StateManager getStateManager() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(StateManager.class);
    }

    public static RoleCategoryManager getRoleCategoryManager() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(RoleCategoryManager.class);
    }

    public static CategoryManager getCategoryManager() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(CategoryManager.class);
    }

    public static KeycloakInfo getKeycloakInfo() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(KeycloakInfo.class);
    }

    public void notify(DelegateTask delegateTask) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap map;
        try {
            map = mapper.readValue(delegateTask.getDescription(), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Lot lot = getLotManager().findById(Long.parseLong(map.getOrDefault("lotId", 0).toString()));
        String orgId = map.getOrDefault("manufacturerId", "").toString();
        Long stateGeoId = null;
        try {
            AddressLocationResponseDto address = mapper.readValue(getKeycloakInfo().getUserInfo().getOrDefault("manufacturerAddress", "").toString(),
                    AddressLocationResponseDto.class);
            stateGeoId = Long.valueOf(address.getState().getGeoId());
        } catch (JsonProcessingException e) {
            String manufacturerAddressUrl = Constants.IAM_SERVICE_URL + "address/manufacturer/" + orgId;
            AddressResponseDto manufacturerAddress = IamServiceRestHelper.fetchResponse(manufacturerAddressUrl, AddressResponseDto.class, getKeycloakInfo().getAccessToken());
            stateGeoId = Long.valueOf(manufacturerAddress.getVillage().getDistrict().getState().getGeoId());
        }
        State state = getStateManager().findByName(delegateTask.getName());
        List<Category> returnCategories = getCategoryManager().getSourceCategory(lot.getCategory().getId(), stateGeoId);
        List<RoleCategory> roleCategories;
        roleCategories = getRoleCategoryManager().findAllByCategoryIdAndState(
                    Long.parseLong(map.getOrDefault("categoryId", 0).toString()), state.getId());
        if (delegateTask.getEventName().equals("create") && !delegateTask.getName().equals("rejected")) {
            ArrayList<String> assigneeList = new ArrayList<>();
            for (RoleCategory roleCategory : roleCategories) {
                RoleCategoryType type = roleCategory.getRoleCategoryType();
                String assigneeString = roleCategory.getRoleName()
                        + "_" +  ("sentBackRejected".equals(state.getName()) ? lot.getManufacturerId().toString(): orgId)+
                        "_" + type.name() + "_" + roleCategory.getCategory().getName();
                if (roleCategories.indexOf(roleCategory) != 0) {
                    assigneeList.add(assigneeString);
                } else {
                    assigneeList.add(assigneeString);
                }
            }

            lot.setTaskId(delegateTask.getId());
            lot.setState(state);
            delegateTask.setAssignee(String.join(",", assigneeList));
            delegateTask.setOwner(lot.getId().toString());
            if (delegateTask.getTaskDefinitionKey().equals("approved") || delegateTask.getTaskDefinitionKey().equals("rejected")
                    || delegateTask.getTaskDefinitionKey().equals("receivedRejected")) {
                getTaskService().complete(delegateTask.getId());
            }
            if (delegateTask.getTaskDefinitionKey().equals("sentLotSampleToLabTest")) {
                this.completeTaskForExternalTest(delegateTask, lot, "lotSampleInLab", new ArrayList<>());
            }
            if (delegateTask.getTaskDefinitionKey().equals("lotSampleInLab")) {
                this.completeTaskForExternalTest(delegateTask, lot, "lotSampleLabTestDone", List.of("lotSampleRejected"));
            }
            if (delegateTask.getTaskDefinitionKey().equals("lotSampleLabTestDone") || delegateTask.getTaskDefinitionKey().equals("selfTestedLot")) {
                String sampleTestResult = delegateTask.getVariable("sampleTestResult").toString();
                if (sampleTestResult.equals(SampleTestResult.TEST_FAILED.name())) {
                    this.completeTask(delegateTask.getTaskDefinitionKey(), lot.getTaskId(), "toSendBackRejected", List.of("approved"));
                } else {
                    this.completeTask(delegateTask.getTaskDefinitionKey(), lot.getTaskId(), "approved", List.of("toSendBackRejected"));
                }
            }


        }
        getLotManager().save(lot);
    }

    private void completeTaskForExternalTest(DelegateTask delegateTask, Lot lot, String taskToComplete, List<String> tasksToReject) {
        String externalTest;
        try {
            externalTest = delegateTask.getVariable("externalTest").toString();
        } catch (Exception e) {
            externalTest = null;
        }
        if (externalTest != null && externalTest.equals("true")) {
            this.completeTask(delegateTask.getTaskDefinitionKey(), lot.getTaskId(), taskToComplete, tasksToReject);
        }
    }

    private void completeTask(String taskDefinitionKey, String taskId, String taskToComplete, List<String> tasksToReject) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(taskDefinitionKey + "_" + taskToComplete, true);
        tasksToReject.forEach(t -> variables.put(taskDefinitionKey + "_" + t, false));
        getTaskService().complete(taskId, variables);
    }
}