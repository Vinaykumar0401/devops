// Reusable step to publish JUnit XML reports.
def call(Map params = [:]) {
    def pattern = params.pattern ?: '**/target/surefire-reports/*.xml'
    junit pattern
}
