// Reusable step to deploy Maven artifacts to Nexus.
def call(Map params = [:]) {
    def skipTests = params.skipTests ?: true
    def extraArgs = skipTests ? '-DskipTests' : ''

    echo 'Deploying Maven artifacts to Nexus'
    sh "mvn deploy ${extraArgs}".trim()
}
