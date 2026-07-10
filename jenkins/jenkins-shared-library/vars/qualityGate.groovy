// Reusable step to enforce the SonarQube quality gate.
def call(Map params = [:]) {
    def timeoutMinutes = params.timeoutMinutes ?: 10

    timeout(time: timeoutMinutes, unit: 'MINUTES') {
        waitForQualityGate abortPipeline: true
    }
}
