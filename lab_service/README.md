# lab Service



## Installation

### Requirements

Before you can build and run the project, ensure that you have the following installed on your system:

- **Java Development Kit (JDK):** Version 17
- **Apache Maven:** Version 3.6.0 or higher

Follow these steps to set up the project on your local machine:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Rice-Fortification-DPG/Rice-Fortification-DPG.git
   cd Rice-Fortification-DPG/lab_service
   ```

2. **Install dependencies:**

   Use Maven to install the required dependencies:

   ```bash
   mvn clean install
   ```

3. **Compile the project:**

   Compile the project using Maven:

   ```bash
   mvn compile
   ```
4. Add the properties in the [application_dev.properties](src%2Fmain%2Fresources%application_dev.properties) with all the required configurations.

5. **Run the application:**

   ```bash
      mvn spring-boot:run
   ```
