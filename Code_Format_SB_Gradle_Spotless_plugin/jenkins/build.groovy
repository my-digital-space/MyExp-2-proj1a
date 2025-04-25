#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Checking out source…'
                checkout scm

                echo 'Running Gradle build (skipping tests)…'
                // Use the Gradle wrapper that comes with Spring Boot projects.
                sh './gradlew clean build -x test'
            }
        }

        stage('Test') {
            steps {
                echo 'Running Gradle tests…'
                sh './gradlew test'
            }
        }
    }

}
