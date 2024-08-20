# Technical Architecture

* Utilizing a microservices architecture, the fortification solution offers flexibility in programming language selection to meet specific requirements. Java is utilized for implementing microservices, ensuring robust functionality. Authentication/Authorization services are managed through Keycloak, providing secure access for users across various roles in fortification and lab functionalities.
* In terms of data management, the application’s database relies on SQL, while ImmuDB is employed for auditing and traceability purposes. ImmuDB enables comprehensive traceability by storing data relationships between various lots and workflow details.
* Workflow management, including approvals, is handled using Spring-Activiti, a lightweight BPMN framework. This allows for easy configuration of multiple workflows tailored to specific roles and stages, ensuring efficient processing of tasks such as batch creation, sample request, dispatch, and receipt.
