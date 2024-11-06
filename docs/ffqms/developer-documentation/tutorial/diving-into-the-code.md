# Diving into the Code

**Getting started with coding**

* Don't get overwhelmed by the mass of files in the microservices. Jump directly to the subfolder src/main/java/\* where the actually relevant code is located.
* The project consists of six microservices structured into several Modules, each in its own code under src/main/java/ folder.
* Each microservice is responsible for a set of particular roles. Here is a general outline of each microservice and its functions:
  * `IAM Service`: Manages user authentication, authorization, and user roles, including user and manufacturer management.
  * `Fortification Service`: Responsible for managing all fortification processes and related aggregates.
  * `Lab Service`: Handles lab test management, lab and lab categories registration.
  * `Immudb Service`: Manages the traceability and auditing of fortification processes by logging and tracking relevant events.
  * `Fortification Parent`: Contains shared code and dependencies used by the other microservices to maintain consistency and reduce redundancy.
  * `Broadcast Service`: Encrypts and broadcasts batch/lot details to registered subscribers upon specific event triggers, ensuring secure communication.
