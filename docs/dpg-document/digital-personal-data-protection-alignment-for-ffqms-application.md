# Digital Personal Data Protection Alignment for FFQMS Application

#### **Digital Personal Data Protection Alignment for FFQMS Application** <a href="#hlqghi1bg4wx" id="hlqghi1bg4wx"></a>

As part of the Digital Personal Data Protection, FFQMS application implements best practices to safeguard individuals' personal data in digital reform.

We collect individual information through:

* **Direct interactions** (e.g., when you register on our website)
* **Automated technologies** (e.g., cookies)

We gather information through both direct interactions and automated technologies. Direct interactions occur when you register on our website, providing us with specific details to personalise and enhance your experience. Additionally, automated technologies, such as cookies, enable us to collect data seamlessly, improving our understanding of user preferences and facilitating a more tailored interaction with our services.

We implement security measures to protect your information as per the Indian IT Act and global security standards, including OWASP.

#### **Key Aspects of Digital Personal Data Protection:** <a href="#id-69hclt67bxte" id="id-69hclt67bxte"></a>

**1. Data Privacy Laws and Regulations:**

* **The Information Technology (Reasonable Security Practices and Procedures and Sensitive Personal Data or Information) Rules, 2011:**
  * These rules were issued under the Information Technology Act, 2000.
  * They define sensitive personal data or information (SPDI) and mandate reasonable security practices for handling such data.
  * Organisations collecting SPDI must obtain consent from the data subjects and provide a privacy policy outlining the purpose of data collection, use, and disclosure.
* **The Personal Data Protection Bill, 2023 (PDP Bill):**
  * The PDP Bill aims to provide comprehensive data protection and privacy regulations in India.
  * It aligns with global data protection standards and addresses issues such as consent, data localization, and the rights of data subjects.
  * The bill proposes the establishment of a Data Protection Authority of India (DPA) to oversee compliance and enforce penalties for violations.
* **The Information Technology Act, 2000 (amended in 2008):**
  * While primarily focused on electronic commerce and cybercrime, the IT Act contains provisions relevant to data protection and privacy.
  * Sections 43A and 72A impose obligations on organisations to protect sensitive personal data and prescribe penalties for negligence in implementing and maintaining reasonable security practices.

**2. Data Encryption:**

* Implemented encryption mechanisms to secure data both in transit and at rest.
* Use of HTTPS (SSL/TLS) for communication between client and server to encrypt data in transit.
* Encrypt sensitive data stored in the database to prevent unauthorised access in case of a breach.

**3. Access Control:**

* Implemented OAuth2 for stateless authentication and session management.
* Enforce proper authorization to ensure that only authenticated users with appropriate permissions can access specific data and functionalities.

**4. Security Measures:**

* Utilised security headers such as Content Security Policy (CSP), X-Content-Type-Options, X-Frame-Options, and X-XSS-Protection to mitigate various types of attacks like content injection, clickjacking, and XSS.
* Implemented input validation and sanitization on both client and server-side to prevent common vulnerabilities such as XSS (Cross-Site Scripting) and SQL injection attacks.
* Validate and sanitise user inputs on the frontend to prevent malicious data from being sent to the backend.
* Implemented server-side validation and sanitization to double-check data integrity and security.

**5. OWASP Practices Adhered to:**

* **Injection Prevention**: Implemented input validation and sanitization to prevent injection flaws such as SQL injections.
* **Sensitive Data Exposure**: Encrypt sensitive data both at rest and in transit using HTTPS (SSL/TLS) to protect against unauthorised access and data breaches.
* **Cross-Site Scripting (XSS) Protection**: Used input validation and sanitization on both client-side and server-side to protect against XSS attacks.
* **Security Misconfigurations**: Implemented proper security headers (CSP, X-Content-Type-Options, X-Frame-Options) to prevent attacks like clickjacking and content injection.
* **Broken Access Control**: Enforced strict access control mechanisms, using OAuth2 to ensure that only authenticated users with appropriate permissions can access specific resources.



**6. Data Breach Response:**

We implement security measures to protect your information as per the Indian IT Act and OWASP standards.

**7. Accountability and Compliance:**

* Ensure that communication is secure and not susceptible to interception or tampering.
* Implemented secure communication protocols and mechanisms for API calls and data transfer.
