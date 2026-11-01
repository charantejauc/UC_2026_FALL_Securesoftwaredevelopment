**Secure Database Access Using Spring Boot and HTTPS**

1. Project Overview

This project demonstrates secure access to an employee database through a Spring Boot REST API using HTTPS. It addresses common application security risks, including SQL injection and unauthorized access.

The application uses Spring Security for authentication and role-based authorization, Spring Data JPA for parameterized database access, and a Java KeyStore to enable encrypted HTTPS communication.

2. Technologies Used

- Java 17
- Spring Boot 3.5.7
- Spring Security
- Spring Data JPA and Hibernate
- H2 In-Memory Database
- Apache Maven 3.9.5
- Java KeyStore (PKCS12)
- IntelliJ IDEA

 3. Security Features

**HTTPS Communication:** The application uses a self-signed TLS certificate stored in a PKCS12 Java KeyStore and runs on HTTPS port 8443.

**Authentication:** Spring Security authenticates API requests using HTTP Basic authentication. Two demonstration accounts are configured: `admin` and `employee`.

**Role-Based Authorization:** Employee information retrieval is restricted to the `ADMIN` role using `@PreAuthorize("hasRole('ADMIN')")`.

**SQL Injection Prevention:** Spring Data JPA uses parameter binding rather than SQL string concatenation. Employee identifiers are also validated using the pattern `EMP[0-9]{3,6}`.

**Password Protection:** Demonstration passwords are BCrypt-encoded in memory and supplied through environment variables.

 4. Application Architecture

```text
HTTPS Client
     |
     v
Spring Security
(Authentication and Authorization)
     |
     v
EmployeeAccountController
     |
     v
EmployeeAccountService
(@PreAuthorize)
     |
     v
EmployeeAccountRepository
(Spring Data JPA)
     |
     v
H2 Database
```

 5. REST API

**Endpoint:**

```http
GET /api/employees/{employeeId}
```

Example:

```text
https://localhost:8443/api/employees/EMP101
```

A successful authorized request returns employee information in JSON format.

Example response:

```json
{
  "id": 1,
  "employeeId": "EMP101",
  "employeeName": "Sample Employee One",
  "department": "IT",
  "email": "employee1@example.com"
}
```

The application uses fictional employee records for testing.

6. Running the Application

Prerequisites

- Java 17
- Apache Maven
- Java `keytool`
- A terminal such as PowerShell

Generate a Development KeyStore

The KeyStore is excluded from GitHub because it contains a private key.

From the project directory, execute:

```powershell
mkdir certs
keytool -genkeypair -alias localhost -keyalg RSA -keysize 3072 -validity 365 -storetype PKCS12 -keystore certs/localhost-keystore.p12 -dname "CN=localhost, OU=Development, O=Course Project, L=Local, ST=NA, C=US" -ext "SAN=dns:localhost,ip:127.0.0.1"
```

Choose a password when prompted.

### Configure Environment Variables

The following environment variables are required:

| Variable | Purpose |
|---|---|
| `SSL_KEYSTORE_PASSWORD` | Password protecting the KeyStore |
| `APP_ADMIN_PASSWORD` | Password for the administrator account |
| `APP_USER_PASSWORD` | Password for the employee account |

In PowerShell, set them securely:

```powershell
$secure = Read-Host "KeyStore password" -AsSecureString
$env:SSL_KEYSTORE_PASSWORD = [System.Net.NetworkCredential]::new("", $secure).Password
Remove-Variable secure

$secure = Read-Host "Admin password" -AsSecureString
$env:APP_ADMIN_PASSWORD = [System.Net.NetworkCredential]::new("", $secure).Password
Remove-Variable secure

$secure = Read-Host "Employee password" -AsSecureString
$env:APP_USER_PASSWORD = [System.Net.NetworkCredential]::new("", $secure).Password
Remove-Variable secure
```

### Start Spring Boot

```powershell
mvn clean compile
mvn spring-boot:run
```

The application should be accessible at:

```text
https://localhost:8443
```
7. Security Testing

The following scenarios were verified during development:

| Test scenario | HTTP result |
|---|---|
| Request without credentials | 401 Unauthorized |
| Authenticated USER requesting an ADMIN-protected resource | 403 Forbidden |
| Authorized ADMIN requesting employee information | 200 OK |
| Request for a nonexistent employee | 404 Not Found |

Test Commands

Unauthenticated request:

```bash
curl -k -i https://localhost:8443/api/employees/EMP101
```

Authenticated employee request:

```bash
curl -k -i -u employee https://localhost:8443/api/employees/EMP101
```

Authenticated administrator request:

```bash
curl -k -i -u admin https://localhost:8443/api/employees/EMP101
```

Enter the relevant password when prompted.

The `-k` flag bypasses certificate verification for the self-signed development certificate. It should not be used for production connections.

SQL injection-style input was also tested. Spring Data JPA parameter binding prevents the supplied identifier from being incorporated into SQL syntax. The malformed-input test returned a server error during testing, so input-validation error handling requires further improvement.

8. Project Limitations

This application was developed for educational purposes and is not production-ready.

- The H2 database stores data in memory.
- Demonstration accounts are maintained in memory.
- The HTTPS certificate is self-signed.
- Phishing-resistant multifactor authentication is not implemented.
- HTTPS testing was performed locally on `localhost`.
- Additional exception handling, logging, and production security controls would be needed for deployment.

9. Conclusion

The project demonstrates secure database retrieval through a Spring Boot REST API using HTTPS, Spring Security, role-based authorization, parameter binding, and input validation.

Together, these mechanisms illustrate a layered approach to application security and database access protection.
