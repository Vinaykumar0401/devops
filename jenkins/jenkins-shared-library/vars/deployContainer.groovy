// Reusable step to deploy a containerized application.
def call(Map params = [:]) {
    def imageName = params.imageName ?: error('imageName is required')
    def latestTag = params.latestTag ?: 'latest'
    def containerName = params.containerName ?: error('containerName is required')
    def containerPort = params.containerPort ?: '8080'

    sh """
    docker rm -f ${containerName} || true
    docker image prune -f
    docker pull ${imageName}:${latestTag}
    docker run -d \
      --name ${containerName} \
      --restart unless-stopped \
      -p ${containerPort}:8080 \
      ${imageName}:${latestTag}
    """
}
