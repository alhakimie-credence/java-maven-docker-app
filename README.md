# Java 1.8 Maven Docker Application

A complete Java 1.8 Maven development environment running in Docker containers with VSCode integration.

## Features
- ✅ Java 1.8 with Maven
- ✅ Docker containerized development
- ✅ VSCode Remote Development
- ✅ Apache HTTP Client for API requests
- ✅ Jackson JSON processing
- ✅ Apache Commons utilities
- ✅ JUnit testing
- ✅ Persistent data storage

## Git Repository

```bash
# Add remote origin
git remote add origin https://github.com/alhakimie-credence/java-maven-docker-app.git

# Push to GitHub
git push -u origin master
```

## Quick Start
1. Clone this repository
2. Open in VSCode
3. Reopen in Container
4. Run: `mvn clean compile exec:java`

## Dependencies
- OpenJDK 8
- Maven 3.x
- Apache HTTP Client 4.5.13
- Jackson 2.13.4
- Apache Commons Lang3 3.12.0
- JUnit 4.13.2

## Development Commands
```bash
# Build project
mvn clean compile
# Or using Docker exec
docker exec -it java-maven-container bash -c "export JAVA_HOME=/usr/local/openjdk-8 && cd /workspace && mvn clean compile"

# Run application
mvn exec:java -Dexec.mainClass="com.example.App"
# Or using Docker exec
docker exec -it java-maven-container bash -c "export JAVA_HOME=/usr/local/openjdk-8 && cd /workspace && mvn exec:java -Dexec.mainClass=com.example.App"

# Run tests
mvn test
# Or using Docker exec
docker exec -it java-maven-container bash -c "export JAVA_HOME=/usr/local/openjdk-8 && cd /workspace && mvn test"

# Package application
mvn package
# Or using Docker exec
docker exec -it java-maven-container bash -c "export JAVA_HOME=/usr/local/openjdk-8 && cd /workspace && mvn package"
```