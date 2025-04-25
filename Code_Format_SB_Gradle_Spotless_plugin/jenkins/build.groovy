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

        stage('Code Format Check') {
            steps {
                echo '🔄 Code Format Check…'

                def status = sh (
                        script: '''
                                mkdir -p build/reports/spotless && \
                                gradle spotlessCheck > build/reports/spotless-report.txt 2>&1
                                ''',
                        returnStatus: true
                )
                if (status !=0) {
                    archiveArtifacts artifacts: 'build/reports/spotless/*.txt', allowEmptyArchive: true
                    error 'Spotless code format failed! Report is here: ' +
                            'click Jenkins failed build number -> Status -> Build Artifacts'
                }
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
