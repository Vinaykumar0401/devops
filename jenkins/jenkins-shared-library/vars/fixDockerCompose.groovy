// Reusable step to update the Docker Compose host port mapping.
def call(Map params = [:]) {
    def fileName = params.fileName ?: 'docker-compose.yml'
    def hostPort = params.hostPort ?: '5433'

    echo "Updating ${fileName} to use host port ${hostPort}"
    sh """
    if [ -f ${fileName} ]; then
        sed -i 's/\"5432:5432\"/\"${hostPort}:5432\"/g' ${fileName}
    fi
    """
}
