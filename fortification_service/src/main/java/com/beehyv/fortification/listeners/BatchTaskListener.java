package com.beehyv.fortification.listeners;

import com.beehyv.fortification.config.SpringApplicationContextHolder;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.RoleCategory;
import com.beehyv.fortification.entity.RoleCategoryType;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.manager.BatchManager;
import com.beehyv.fortification.manager.RoleCategoryManager;
import com.beehyv.fortification.manager.StateManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class BatchTaskListener implements org.activiti.engine.delegate.TaskListener {

    public static TaskService getTaskService() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(TaskService.class);
    }

    public static BatchManager getBatchManager() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(BatchManager.class);
    }

    public static StateManager getStateManager() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(StateManager.class);
    }

    public static RoleCategoryManager getRoleCategoryManager() {
        return SpringApplicationContextHolder.getApplicationContext().getBean(RoleCategoryManager.class);
    }

    public void notify(DelegateTask delegateTask) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap map;
        try {
            map = mapper.readValue(delegateTask.getDescription(), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Batch batch = getBatchManager().findById(Long.parseLong(map.getOrDefault("batchId", 0).toString()));
        State state = getStateManager().findByName(delegateTask.getName());
        List<RoleCategory> roleCategories = getRoleCategoryManager().findAllByCategoryIdAndState(
                Long.parseLong(map.getOrDefault("categoryId", 0).toString()), state.getId());
        if (delegateTask.getEventName().equals("create")) {
            String orgId = map.getOrDefault("manufacturerId", "").toString();
            ArrayList<String> assigneeList = new ArrayList<>();
            for(RoleCategory roleCategory: roleCategories) {
                RoleCategoryType type = roleCategory.getRoleCategoryType();
                String assigneeString = roleCategory.getRoleName() + "_" + orgId + "_" + type.name() + "_" +
                        roleCategory.getCategory().getName();
                if (roleCategories.indexOf(roleCategory) != 0) {
                    assigneeList.add(assigneeString);
                } else {
                    assigneeList.add(assigneeString);
                }
            }
            batch.setTaskId(delegateTask.getId());
            batch.setState(state);
            delegateTask.setAssignee(String.join(",", assigneeList));
            delegateTask.setOwner(batch.getId().toString());
            Map<String, Object> variables = new HashMap<>();
            if (delegateTask.getTaskDefinitionKey().equals("batchToDispatch") || delegateTask.getTaskDefinitionKey().equals("rejected")) {
                getTaskService().complete(delegateTask.getId());
            }
            if (delegateTask.getTaskDefinitionKey().equals("batchSampleLabTestDone") || delegateTask.getTaskDefinitionKey().equals("selfTestedBatch")) {
                String sampleTestResult = delegateTask.getVariable("sampleTestResult").toString();
                variables.put(delegateTask.getTaskDefinitionKey() + "_rejected", false);
                variables.put(delegateTask.getTaskDefinitionKey() + "_batchToDispatch", false);
                if (sampleTestResult.equals(SampleTestResult.TEST_FAILED.name())) {
                    variables.put(delegateTask.getTaskDefinitionKey() + "_rejected", true);
                } else {
                    variables.put(delegateTask.getTaskDefinitionKey() + "_batchToDispatch", true);
                }
                getTaskService().complete(batch.getTaskId(), variables);
            }
        }
        getBatchManager().save(batch);
    }
}