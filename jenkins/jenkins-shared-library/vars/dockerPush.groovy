// Reusable step to push Docker images to a registry.
def call(Map params = [:]) {
    def imageName = params.imageName ?: error('imageName is required')
    def imageTag = params.imageTag ?: error('imageTag is required')
    def latestTag = params.latestTag ?: 'latest'

    sh """
    docker push ${imageName}:${imageTag}
    docker push ${imageName}:${latestTag}
    """
}
