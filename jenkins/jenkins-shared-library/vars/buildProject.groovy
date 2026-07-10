// Reusable step to execute the Maven build and test lifecycle.
def call(Map params = [:]) {
    def goals = params.goals ?: 'clean verify'

    echo "Running Maven build with goals: ${goals}"
    sh "mvn ${goals}"
}
