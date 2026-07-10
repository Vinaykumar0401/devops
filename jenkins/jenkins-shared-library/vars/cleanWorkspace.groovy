// Reusable step to clean the Jenkins workspace.
def call(Map params = [:]) {
    def message = params.message ?: 'Cleaning workspace...'
    echo message
    cleanWs()
}
