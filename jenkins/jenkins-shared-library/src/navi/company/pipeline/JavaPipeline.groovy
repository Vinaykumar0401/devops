package com.company.pipeline

// Declarative pipeline orchestration class for Java projects.
class JavaPipeline implements Serializable {
    def call(Map config) {
        pipeline {
            agent { label config.agentLabel ?: 'java-node' }

            options {
                buildDiscarder(logRotator(
                    numToKeepStr: config.buildsToKeep ?: '3',
                    artifactNumToKeepStr: config.artifactsToKeep ?: '3'
                ))
            }

            tools {
                jdk config.jdk ?: 'JDK17'
                maven config.maven ?: 'maven'
            }

            environment {
                IMAGE_NAME = config.imageName ?: ''
                IMAGE_TAG = config.imageTag ?: "${env.BUILD_NUMBER}"
                LATEST_TAG = config.latestTag ?: 'latest'
            }

            stages {
                stage('Clean Workspace') {
                    steps {
                        cleanWorkspace(message: 'Cleaning workspace before build')
                    }
                }

                stage('Checkout') {
                    steps {
                        checkoutCode(repoUrl: config.repoUrl, branch: config.branch)
                    }
                }

                stage('Fix Docker Compose') {
                    steps {
                        fixDockerCompose(fileName: config.composeFileName ?: 'docker-compose.yml', hostPort: config.hostPort ?: '5433')
                    }
                }

                stage('Build & Test') {
                    steps {
                        buildProject(goals: 'clean verify')
                    }
                    post {
                        always {
                            publishJUnitReports(pattern: '**/target/surefire-reports/*.xml')
                        }
                    }
                }

                stage('SonarQube Analysis') {
                    steps {
                        sonarScan(
                            sonarServer: config.sonarServer,
                            projectKey: config.projectKey,
                            projectName: config.projectName
                        )
                    }
                }

                stage('Quality Gate') {
                    steps {
                        qualityGate(timeoutMinutes: config.qualityGateTimeoutMinutes ?: 10)
                    }
                }

                stage('Deploy Artifact to Nexus') {
                    steps {
                        deployToNexus(skipTests: true)
                    }
                }

                stage('Docker Build') {
                    steps {
                        dockerBuild(
                            imageName: env.IMAGE_NAME,
                            imageTag: env.IMAGE_TAG,
                            latestTag: env.LATEST_TAG
                        )
                    }
                }

                stage('Docker Login') {
                    steps {
                        dockerLogin(credentialsId: config.dockerCredentialId)
                    }
                }

                stage('Push Docker Image') {
                    steps {
                        dockerPush(
                            imageName: env.IMAGE_NAME,
                            imageTag: env.IMAGE_TAG,
                            latestTag: env.LATEST_TAG
                        )
                    }
                }

                stage('Deploy Application') {
                    steps {
                        deployContainer(
                            imageName: env.IMAGE_NAME,
                            latestTag: env.LATEST_TAG,
                            containerName: config.containerName,
                            containerPort: config.containerPort
                        )
                    }
                }
            }

            post {
                success {
                    echo 'Pipeline completed successfully.'
                }

                failure {
                    echo 'Pipeline failed.'
                }

                always {
                    cleanWorkspace(message: 'Cleaning workspace after pipeline execution')
                }
            }
        }
    }
}
