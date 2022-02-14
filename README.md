# Statements Service

### How to build & run

* Clean Project
```
mvn clean
```

* Build Project while skipping Unit Tests
```
mvn install -DskipTests 
```

* Run Unit Tests
```
mvn test
```
**Note:** Unit Test coverage report will be generated inside target/site/jacoco -> **index.html** file.

* Build Project while running Unit Tests
```
mvn install
```

* Run SonarQube Code Analysis
```
mvn sonar:sonar -Dsonar.host.url=http://HOST:PORT -Dsonar.login=THE_GENERATED_TOKEN
```
**Note:** In order to run SonarQube locally, we need to start SonarQube server locally and replace 
HOST: localhost
PORT: 9000
THE_GENERATED_TOKEN: A new generated token via Sonarqube dashboard


* Run Project
```
mvn spring-boot:run
```

## Notes

* Postman Collection
```
Statements Service.postman_collection.json
```

> **Login API:** Authenticate & Authorize users.
>
> **Admin ViewStatements API:** View statements with role Admin after login.
>
> **User ViewStatements API:** View statements with both Admin & User roles after login.
>
> **Logout API:** Logout.

* Unit Test Coverage Report
```
UnitTest_CoverageReport.zip
```

* SonarQube Code Analysis

**Note:** Could not generate a report from SonarQube community edition.

* Session Management via Spring Security Pages

> [Login Page](http://localhost:8099/login)
>
> [Logout Page](http://localhost:8099/logout)

* Actuator Health
> [Health Page](http://localhost:8099/actuator/health)
