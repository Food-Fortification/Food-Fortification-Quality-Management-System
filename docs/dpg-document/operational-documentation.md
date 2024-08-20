# Operational Documentation

### **Administrator Guides** <a href="#toc2126907398" id="toc2126907398"></a>

* Once all the services are up and running run the dsl Api in fortification service to setup the application
  * **POST** {FORTIFICATION\_URL}/admin/dsl with JSON generated in the above step
    1. Creates Categories and Actions for Category-Specific Roles
    2. Defines and establishes categories and their respective actions.
    3. Creates USER, APPROVER, ADMIN Roles in key cloak with Appropriate Category Actions
    4. Sets up roles with permissions tailored to the actions relevant to each role.
* Create laboratories and assign them to related categories

### **Maintenance** <a href="#toc902566246" id="toc902566246"></a>

* Once the DSL is executed, the platform is configured for the stages with the defined roles and actions.
* Additional configurations can be made to the existing APIs by adding the given service as a dependency and modifying the APIs as required.

###

