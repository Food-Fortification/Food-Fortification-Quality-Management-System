# How to configure spring activiti

**Spring Activiti** is a lightweight workflow and Business Process Management (BPM) platform designed for managing and automating business processes. Built on top of the Activiti BPMN engine, it integrates seamlessly with the Spring ecosystem, making it easy to embed within Java applications.

Activiti provides support for **BPMN 2.0** (Business Process Model and Notation), which is an open standard for modeling business processes. By using BPMN, developers and business analysts can collaborate on defining workflows in a visual and standard format. The BPMN diagrams can be exported as XML files and directly used within Spring Activiti to define the workflow of tasks, decisions, and other activities.

**How BPMN Files Drive User Workflow**

BPMN files are central to driving user workflows in Activiti. Here's how it works:

1. **BPMN Process Definition**: A BPMN file defines the entire process, including tasks, decision gateways, and sequence flows. It outlines both user-driven tasks (e.g., approvals) and system-driven tasks (e.g., automated notifications or service calls).
2. **Process Deployment**: The BPMN file, typically an XML file, is deployed to the Activiti engine, which reads the definition and instantiates processes as needed.
3. **User Task Assignment**: BPMN files include "User Tasks," which require human intervention. When the workflow reaches these tasks, it waits for a user to complete them before moving to the next step. These tasks can be assigned to specific users or groups.
4. **Event-Driven Execution**: As users interact with the workflow (e.g., completing tasks, making decisions), the Activiti engine evaluates the BPMN definitions and proceeds to the next activity. This can include branching logic (e.g., decision gateways), triggering sub-processes, or sending notifications.

By leveraging BPMN with Spring Activiti, developers can build scalable, maintainable, and easy-to-extend business processes that align with industry-standard process modeling practices.

Below is an example of how we get actions from BPMN files in the FFQMS system

```java
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
```

The method `getTaskActions` returns the list of actions possible at the current stage of the workflow to the end user using different parameters passed to the method.

Below shows the notify method of spring activiti that is responsible for handling the delegation of tasks in a workflow related to `Batch` processing. It processes a task event, parses the task description, and updates the `Batch` entity's state and task assignment based on specific conditions. This method is part of a larger workflow management system, where it interacts with various services like `BatchManager`, `StateManager`, `RoleCategoryManager`, and `TaskService`.

```java
public void notify(DelegateTask delegateTask) {
    ObjectMapper mapper = new ObjectMapper();
    HashMap map;
    
    try {
        map = mapper.readValue(delegateTask.getDescription(), HashMap.class);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    // Retrieve the Batch entity
    Batch batch = getBatchManager().findById(Long.parseLong(map.getOrDefault("batchId", 0).toString()));

    // Retrieve the State entity
    State state = getStateManager().findByName(delegateTask.getName());

    // Retrieve RoleCategories based on categoryId and state
    List<RoleCategory> roleCategories = getRoleCategoryManager().findAllByCategoryIdAndState(
            Long.parseLong(map.getOrDefault("categoryId", 0).toString()), state.getId());

    if (delegateTask.getEventName().equals("create")) {
        // Get organization ID and assign task roles
        String orgId = map.getOrDefault("manufacturerId", "").toString();
        ArrayList<String> assigneeList = new ArrayList<>();

        for(RoleCategory roleCategory : roleCategories) {
            RoleCategoryType type = roleCategory.getRoleCategoryType();
            String assigneeString = roleCategory.getRoleName() + "_" + orgId + "_" + type.name() + "_" +
                    roleCategory.getCategory().getName();
            assigneeList.add(assigneeString);
        }

        // Update Batch entity and assign task
        batch.setTaskId(delegateTask.getId());
        batch.setState(state);
        delegateTask.setAssignee(String.join(",", assigneeList));
        delegateTask.setOwner(batch.getId().toString());

        // Handling specific task definition keys
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

    // Save updated Batch entity
    getBatchManager().save(batch);
}
```

**Key Responsibilities**

1. **Parsing Task Description**: The method reads and parses a JSON description of the task using Jackson's `ObjectMapper`. This JSON contains relevant data like `batchId`, `categoryId`, and `manufacturerId`.
2. **Retrieving Batch and State**: It retrieves the relevant `Batch` entity using the `batchId` from the parsed JSON, and the corresponding `State` of the task using `StateManager`.
3. **Assigning Task Roles**: The method determines the roles responsible for the task based on the `categoryId` and `state`. It assigns the task to specific users based on their roles and updates the task’s assignee list.
4. **Task Completion Based on Conditions**: Depending on the task's definition key, it completes the task, updates variables, and handles scenarios like "batch rejection" or "batch ready for dispatch."

**Key Features**

1. **JSON Parsing with `ObjectMapper`**:
   * The task description is parsed from a JSON string into a `HashMap` using Jackson's `ObjectMapper`. This allows the method to access task-related data, such as `batchId` and `manufacturerId`, to proceed with processing.
2. **Batch and State Retrieval**:
   * The method retrieves the `Batch` entity using `batchId` and the corresponding `State` using `StateManager`. These are essential for updating the state of the batch and assigning the task correctly.
3. **Role-Based Task Assignment**:
   * The method assigns the task to users based on their role categories. It constructs the assignee string using a combination of the role name, organization ID, and role category type. The task is then assigned to these users using the `setAssignee` method.
4. **Conditional Task Completion**:
   * The method checks the task's definition key to decide if it should automatically complete the task. For tasks like "batch dispatch" or "batch rejection," the task is completed immediately.
   * For tasks like "batch sample lab test," it handles the test result and sets additional variables based on whether the sample test passed or failed. The task is completed with these variables.
5. **Updating and Saving Batch**:
   * The method updates the `Batch` entity with the new task ID and state. Once the processing is complete, it saves the updated batch using the `BatchManager`.

**Code Explanation**

**1. Parsing Task Description**

```java
map = mapper.readValue(delegateTask.getDescription(), HashMap.class);
```

* The task's description is a JSON string that contains key details about the task (e.g., `batchId`, `categoryId`). This line parses the description into a `HashMap`, allowing easy access to these values.

**2. Retrieving Batch and State**

```java
Batch batch = getBatchManager().findById(Long.parseLong(map.getOrDefault("batchId", 0).toString()));
State state = getStateManager().findByName(delegateTask.getName());
```

* The method retrieves the `Batch` entity by converting the `batchId` from the JSON map into a `Long` and using the `BatchManager`.
* It also retrieves the `State` entity corresponding to the task's name using the `StateManager`.

**3. Assigning Task to Users**

```java
ArrayList<String> assigneeList = new ArrayList<>();
for (RoleCategory roleCategory : roleCategories) {
    RoleCategoryType type = roleCategory.getRoleCategoryType();
    String assigneeString = roleCategory.getRoleName() + "_" + orgId + "_" + type.name() + "_" +
            roleCategory.getCategory().getName();
    assigneeList.add(assigneeString);
}
delegateTask.setAssignee(String.join(",", assigneeList));
```

* The method constructs a list of assignee strings for the task based on the role category, organization ID, and category name.
* The assignees are then set on the task using the `setAssignee` method.

**4. Completing Task Based on Conditions**

```java
if (delegateTask.getTaskDefinitionKey().equals("batchToDispatch") || delegateTask.getTaskDefinitionKey().equals("rejected")) {
    getTaskService().complete(delegateTask.getId());
}
```

* Depending on the task definition key, the task may be completed immediately, especially for "batch dispatch" or "rejected" tasks.
* For more complex tasks like "batchSampleLabTestDone," additional variables are set based on the sample test result before completing the task.

**Conclusion**

The `notify` method is a crucial part of the workflow engine, managing task assignment and completion based on batch processing states. It interacts with multiple components to ensure tasks are assigned to the right users and completed as per the defined workflow. This method integrates JSON parsing, role-based task assignment, and conditional logic to handle various scenarios in batch processing workflows.
