
# Food Fortification Quality Management System

## Local Installation Guide

### Prerequisites

Before starting the setup, ensure that the following tools and services are installed and running on your local machine:

- **Java SE 17**  
  Download and install Java SE 17 from the [official website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

- **Maven 3.6 or higher**  
  Download Maven from [here](https://maven.apache.org/download.cgi).

- **MySQL**  
  Install MySQL and ensure it is running. You can download it from [here](https://dev.mysql.com/downloads/installer/).

- **Keycloak**  
  Install and set up Keycloak from [here](https://www.keycloak.org/downloads).

- **Kafka**  
  Download and configure Kafka from [Apache Kafka](https://kafka.apache.org/downloads).

- **Immudb**  
  Download and install Immudb from [here](https://docs.immudb.io/master/running/download.html).

---

### Steps to Set Up the Application Locally

#### 1. Clone the Repository

Start by cloning the repository and switching to the `main` branch:

```bash
git clone https://github.com/Food-Fortification/Food-Fortification-Quality-Management-System.git
cd Food-Fortification-Quality-Management-System
git checkout main
```

#### 2. Build the Parent `pom.xml`

Navigate to the `fortification_parent` directory and build the parent `pom.xml` file:

```bash
cd fortification_parent
```

- Open the `pom.xml` file and make the following changes:
    - Set `<version>` to `0.0.1`.
    - Set `<packaging>` to `pom`.

- Deploy the service:

  ```bash
  mvn deploy
  ```

- Update the `<version>` to `0.0.2` and change the `<packaging>` to `jar`.
- Redeploy the service:

  ```bash
  mvn deploy
  ```

This will store the parent POM and common dependency JARs in your local Maven repository (`.m2/repository`), which will be accessible to all microservices.

#### 3. Start Required Services

Ensure that the following services are running locally:

- **MySQL**  
  Start the MySQL service on your machine.

- **Keycloak**  
  Start Keycloak by running its startup script:

```bash
<keycloak-installation-dir>/bin/standalone.sh
```

- **Kafka**  
  Start both Kafka and Zookeeper services:
```bash
<kafka-installation-dir>/bin/zookeeper-server-start.sh config/zookeeper.properties
<kafka-installation-dir>/bin/kafka-server-start.sh config/server.properties
```

- **Immudb**  
  Run Immudb:
```bash
<immudb-installation-directory>/immudb service install 
```


#### 4. Keycloak Setup

- Create a new **Realm** in Keycloak.
- Set up a **Client** with **Service Account** role-based access in the new realm.

#### 5. Immudb Setup

- Create a new **database** and **user** in Immudb. Ensure that the user has read and write access to the new database.

Example SQL commands to execute in Immudb:

```bash
./immuadmin database create mydatabase;
./immuadmin user create user1 readwrite mydatabase
```

#### 6. Configure Microservice Properties

For each microservice, navigate to the `src/main/resources` directory and create an `application-local.properties` file. Add necessary configurations like database URLs, Keycloak details, Kafka settings, and Immudb credentials. These properties will replace placeholders in the `application.properties` file. For interservice communication add the required microservice urls like `IAM_BASE_URL`,`FORTIFICATION_BASE_URL` in the format `<domain name>:<port>/<context path>`


#### 7. Add BPMN Files for Fortification Microservice

- Add the necessary BPMN (Business Process Model and Notation) files for the fortification service under the `src/main/resources/processes` folder.
- These files will be picked up by Spring Activiti during runtime for workflow management.

#### 8. Run Each Microservice

Run each microservice individually using Maven:

```bash
mvn spring-boot:run -Dspring.profiles.active=local
```

Ensure that all services start successfully and communicate with the required external services (MySQL, Keycloak, Kafka, and Immudb).

#### 9. Access the Application

Once all microservices are running, you can access the OpenAPI documentation for each service using a browser.

For example, to access the Swagger UI for `Iam service`, use:

```
http://localhost:8089/iam/swagger-ui/index.html
```

Repeat this step for other microservices, changing the port and context path according to each microservice's configuration.

#### 10. Run the DSL Setup

After the services are up and running:

- Navigate to the Fortification Service Swagger docs.
- Find the **Update DSL API** under the admin controller.
- Use this API to send the DSL JSON in the request body.

This will create the required product categories and roles based on the DSL (Domain-Specific Language) flow specified in the system.


