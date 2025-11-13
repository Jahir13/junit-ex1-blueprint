
[![Maven Test Build](https://github.com//junit-test-sample-01/actions/workflows/mavenBuild.yml/badge.svg)](https://github.com//junit-test-sample-01/actions/workflows/mavenBuild.yml)
[![New Release](https://github.com//junit-test-sample-01/actions/workflows/newRelease.yml/badge.svg)](https://github.com//junit-test-sample-01/actions/workflows/newRelease.yml)

```bash
mvn archetype:generate 
mvn clean install site
```



# junit-test-sample-01
This template can be used to start your own JLegMed application
 
##  Requirements

*   Java 21 (LTS) installed — the project has been updated to target Java 21. Make sure your local JDK and CI runners use Java 21.
*   IDE with maven support 
*   [Optional] Docker or Kubernetes if you want to run your application in a container. See [here](README-GitHub.md) for more information.   
*   [Optional] A locally running [developer stack](deploy/developerStack.yml) providing a Postgres database, ActiveMQ broker, and Swagger-UI 

## Build the Project

*   Checkout the new project in your favorite IDE

*   [Optional] **With** running [developer stack](deploy/developerStack.yml):
    ```shell
    mvn clean install
    
    java -jar "-Djlegmed.config.import=src/test/resources/jlegmed-test.properties" target/junit-test-sample-01-jar-with-dependencies.jar
    ```


## Start Developing your Project

### Set up your project on GitHub  

To continuously build and deploy your application, we recommend using GitHub as described [here](README-GitHub.md).

### Package Structure
To organize our code we recommend the following package structure:

``` 
(com.github.junit-test-sample-01)
    plugins
    |    <technology 1>
    |    ...
    |    <technology n>
    dto  
    |    <use case 1>
    |    ...
    |    <use case n>
    junit-test-sample-01.java
```

### Cleanup Readme

After successfully set up your new project, you should clean up the text of README as described [here](https://www.makeareadme.com)    
