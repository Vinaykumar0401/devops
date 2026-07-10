// Reusable step to check out source code from a Git repository.
def call(Map params = [:]) {
    def repoUrl = params.repoUrl ?: error('repoUrl is required')
    def branch = params.branch ?: 'main'

    echo "Checking out repository ${repoUrl} on branch ${branch}"
    git branch: branch, url: repoUrl
}
