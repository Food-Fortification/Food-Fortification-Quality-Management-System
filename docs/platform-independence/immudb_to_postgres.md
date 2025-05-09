# 🔄 Immudb to PostgreSQL Migration with AES-256 Encryption

## 📋 Overview

This document outlines the major changes required to **migrate from Immudb to PostgreSQL**, while also integrating **AES-256 encryption** for sensitive data fields.

---

## 🛠️ Key Changes Summary

| **Component**      | **Change**                                                                 |
|--------------------|-----------------------------------------------------------------------------|
| Database           | Migrated from **Immudb** to **PostgreSQL**                                 |
| Encryption         | Introduced **AES-256** encryption for sensitive fields                     |
| Connection Layer   | Replaced Immudb client with **JDBC** + **HikariCP**                        |
| Data Integrity     | Replaced Immudb’s cryptographic verification with **hash chaining**        |
| Configuration      | Updated `application.properties` for PostgreSQL and encryption setup       |

---

## 🔐 AES-256 Encryption Implementation

### 🔧 New Component: `EncryptionUtil`

- A utility class that handles:
  - **AES-256 encryption/decryption**
  - Uses a secret key and a salt from application.properties for AES-256 encryption
- Ensures encryption of:
  - `manufacturerName`
  - `manufacturerAddress`
  - `licenseNumber`

![EncryptionUtil](https://github.com/user-attachments/assets/70918b5c-3d8e-4f29-94d6-1fdacc184848)


---

## 🧱 Entity Changes

### 🔄 Field Modifications

- Marked sensitive fields with a **custom `@Encrypt` annotation**
- Introduced both:
  - **Plaintext transient fields** for application use
  - **Encrypted database fields** for secure storage

```java
@Transient
private String manufacturerNamePlain;

@Column(name = "manufacturer_name")
private String manufacturerName; // Encrypted value
```
![BatchEventEntity](https://github.com/user-attachments/assets/56357eda-1b90-4252-b72b-df5f5d54de8a)


---

## 💻 Code Refactoring

### ✅ Removed

- All **`ImmuClient`** and Immudb-specific code

### ➕ Added

- **`JdbcTemplate`**, **HikariCP**, or **Spring Data JPA** for PostgreSQL access
- Encryption handling via `EncryptionUtil`

---
Code Changes Snippet in service and service implentation

![PostgreSqlService](https://github.com/user-attachments/assets/f59d8654-2276-4558-8262-4990eabef6ff)
![PostgreSqlServiceImplementation](https://github.com/user-attachments/assets/d3350604-84f9-4fbe-9520-5e7964db48e7)


## ⚙️ Configuration Changes

### 🔑 `application.properties`


#Postgres database credentials
postgres.url=${POSTGRES_URL}
postgres.username=${POSTGRES_USERNAME}
postgres.password=${POSTGRES_PASSWORD}

#Encryption
encryption.secret.key =${AES_256_KEY}  # 32 chars
encryption.salt=${SECURE_SALT} # 16+ chars

```

### 🧾 `pom.xml` Updates

```xml
	</dependencies>
		<!--Remove the Immudb dependency as it is redundant now-->
		<!-- ===== ESSENTIAL NEW DEPENDENCIES ===== -->
		<!-- Encryption Support -->
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>${commons.codec.version}</version>
		</dependency>

		<!-- BouncyCastle (AES-256 with GCM mode) -->
		<dependency>
			<groupId>org.bouncycastle</groupId>
			<artifactId>bcprov-jdk18on</artifactId> <!-- JDK 17+ compatible -->
			<version>${bouncycastle.version}</version>
		</dependency>

		<!-- Connection Pooling (Critical for Production) -->
		<dependency>
			<groupId>com.zaxxer</groupId>
			<artifactId>HikariCP</artifactId>
			<version>${hikari.version}</version>
		</dependency>

		<!-- Java 17 Crypto Policy Override -->
		<dependency>
			<groupId>org.conscrypt</groupId>
			<artifactId>conscrypt-openjdk-uber</artifactId>
			<version>2.5.2</version>
			<scope>runtime</scope>
		</dependency>

		<!-- ===== OPTIONAL BUT RECOMMENDED ===== -->
		<!-- Faster XML Processing -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.17.0</version>
		</dependency>
	</dependencies>
```

Code Snippet for changes in Pom: 
![Changes_In_Pom](https://github.com/user-attachments/assets/91d1090f-f7b8-46f3-bdbd-f3a3dadfc0a6)
