package navi.company.utils

class GitHelper implements Serializable {
    static String validateBranchName(String branch) {
        return branch?.trim() ?: 'main'
    }

    static String sanitizeRepoUrl(String repoUrl) {
        return repoUrl?.trim()
    }
}
