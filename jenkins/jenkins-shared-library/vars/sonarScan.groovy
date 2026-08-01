// Reusable step to run SonarQube analysis.
def call(Map params = [:]) {
    def sonarServer = params.sonarServer ?: error('sonarServer is required')
    def projectKey = params.projectKey ?: error('projectKey is required')
    def projectName = params.projectName ?: error('projectName is required')

    withSonarQubeEnv(sonarServer) {
        sh """
        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970:sonar \
        -Dsonar.projectKey=${projectKey} \
        -Dsonar.projectName=${projectName}
        """
    }
}
