// Reusable step to build a Docker image.
def call(Map params = [:]) {
    def imageName = params.imageName ?: error('imageName is required')
    def imageTag = params.imageTag ?: error('imageTag is required')
    def latestTag = params.latestTag ?: 'latest'

    sh """
    docker build -t ${imageName}:${imageTag} .
    docker tag ${imageName}:${imageTag} ${imageName}:${latestTag}
    """
}
