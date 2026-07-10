// Declarative pipeline entrypoint for the shared library.
def call(Map config = [:]) {
    def effectiveConfig = [
        agentLabel: 'java-node',
        jdk: 'JDK17',
        maven: 'maven',
        repoUrl: '',
        branch: 'main',
        imageName: '',
        imageTag: '',
        latestTag: 'latest',
        containerName: 'petclinic',
        containerPort: '8080',
        sonarServer: 'SonarQube',
        projectKey: 'petclinic',
        projectName: 'petclinic',
        dockerCredentialId: '',
        buildsToKeep: '3',
        artifactsToKeep: '3',
        composeFileName: 'docker-compose.yml',
        hostPort: '5433',
        qualityGateTimeoutMinutes: 10
    ] + (config ?: [:])

    pipeline {
        agent { label effectiveConfig.agentLabel }

        options {
            buildDiscarder(logRotator(
                numToKeepStr: effectiveConfig.buildsToKeep,
                artifactNumToKeepStr: effectiveConfig.artifactsToKeep
            ))
        }

        tools {
            jdk effectiveConfig.jdk
            maven effectiveConfig.maven
        }

        stages {
            stage('Clean Workspace') {
                steps {
                    cleanWorkspace(message: 'Cleaning workspace before build')
                }
            }

            stage('Checkout') {
                steps {
                    checkoutCode(repoUrl: effectiveConfig.repoUrl, branch: effectiveConfig.branch)
                }
            }

            stage('Fix Docker Compose') {
                steps {
                    fixDockerCompose(fileName: effectiveConfig.composeFileName, hostPort: effectiveConfig.hostPort)
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
                        sonarServer: effectiveConfig.sonarServer,
                        projectKey: effectiveConfig.projectKey,
                        projectName: effectiveConfig.projectName
                    )
                }
            }

            stage('Quality Gate') {
                steps {
                    qualityGate(timeoutMinutes: effectiveConfig.qualityGateTimeoutMinutes)
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
                        imageName: effectiveConfig.IMAGE_NAME,
                        imageTag: effectiveConfig.IMAGE_TAG,
                        latestTag: effectiveConfig.LATEST_TAG
                    )
                }
            }

            stage('Docker Login') {
                steps {
                    dockerLogin(credentialsId: effectiveConfig.dockerCredentialId)
                }
            }

            stage('Push Docker Image') {
                steps {
                    dockerPush(
                        imageName: effectiveConfig.IMAGE_NAME,
                        imageTag: effectiveConfig.IMAGE_TAG,
                        latestTag: effectiveConfig.LATEST_TAG
                    )
                }
            }

            stage('Deploy Application') {
                steps {
                    deployContainer(
                        imageName: effectiveConfig.IMAGE_NAME,
                        latestTag: effectiveConfig.LATEST_TAG,
                        containerName: effectiveConfig.containerName,
                        containerPort: effectiveConfig.containerPort
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
