# Sample QA Acceptance Test with Gradle + JUnit 5 + Rest Assured

This repository is to show sample of running QA Acceptance test with Gradle, Junit 5 and Rest Assured.

Development environment: 

    https://docker-http-app-development.harishkannarao.com

## Github Actions Build status
[![Build Status](https://github.com/harishkannarao/gradle-qa-acceptance-tests/workflows/CI-master-development/badge.svg)](https://github.com/harishkannarao/gradle-qa-acceptance-tests/actions?query=workflow%3ACI-master-development)

## Required Softwares, Tools and Version
* Java JDK Version: 11 Adopt OpenJDK (`java -version`)
* Git Client: Any latest version (`git --version`)
* Integrated Development Environment: Any version of IntelliJ Idea or Eclipse

## Run tests against development

    TEST_ENVIRONMENT='development' ./gradlew clean build
    
## Run tests against local (default)

    ./gradlew clean build
    
## Run tests against development in Github Actions CI

    export GITHUB_PERSONAL_ACCESS_TOKEN=<<your_personal_token>>

    curl -v -H "Accept: application/vnd.github.everest-preview+json" \
        -H "Authorization: token $GITHUB_PERSONAL_ACCESS_TOKEN" \
        --request POST \
        --data '{"event_type": "do-master-development-ci"}' \
        'https://api.github.com/repos/harishkannarao/gradle-qa-acceptance-tests/dispatches'