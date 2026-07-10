// Reusable step to authenticate with Docker Hub.
def call(Map params = [:]) {
    def credentialsId = params.credentialsId ?: error('credentialsId is required')

    withCredentials([
        usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )
    ]) {
        sh '''
        echo "$DOCKER_PASS" | docker login \
        -u "$DOCKER_USER" \
        --password-stdin
        '''
    }
}
