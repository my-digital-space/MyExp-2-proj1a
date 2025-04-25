#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo '🔄 Checking out source code…'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔧 Running Gradle build (skipping tests)…'
                // Use the Gradle wrapper that comes with Spring Boot projects
                sh './gradlew clean build -x test'
            }
        }

        stage('Test') {
            steps {
                echo '✅ Running Gradle tests…'
                sh './gradlew test'
            }
            post {
                // Archive only test results immediately after Test stage
                always {
                    echo '📑 Archiving JUnit test reports…'
                    junit '**/build/test-results/**/*.xml'
                }
            }
        }
    }

}
