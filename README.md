# Internship-System

## Maven to Gradle Migration

This project has been migrated from Maven to Gradle. The following changes have been made:

1. Created `settings.gradle` to define the multi-module structure
2. Created root `build.gradle` with common dependencies and configurations
3. Created module-specific `build.gradle` files for each submodule
4. Set up Gradle wrapper files (gradlew, gradlew.bat, gradle-wrapper.properties)

### Completing the Migration

To complete the migration in a real-world scenario, you would need to:

1. Install Gradle locally (if not already installed)
2. Run `gradle wrapper` to generate the gradle-wrapper.jar file
3. Test the build with `./gradlew build` (Linux/Mac) or `gradlew.bat build` (Windows)
4. Once the build is confirmed working, remove Maven-specific files:
   - pom.xml files (root and all modules)
   - mvnw and mvnw.cmd scripts

### Module Structure

The project consists of the following modules:

- Api-Gateway
- Application-Service
- Company-Service
- Curator-Service
- Document-Service
- Practice-Service
- Stack-Service
- User-Service

### Dependencies

Common dependencies for all modules:
- Spring Boot (data-jpa, security, web, webflux)
- PostgreSQL
- Lombok
- JWT libraries
- SpringDoc OpenAPI

Each module has its specific dependencies defined in its own build.gradle file.

### Java Version

The project uses Java 17.

### Spring Boot Version

The project uses Spring Boot 3.2.5.