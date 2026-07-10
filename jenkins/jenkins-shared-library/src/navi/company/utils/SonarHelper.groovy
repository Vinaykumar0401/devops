package navi.company.utils

class SonarHelper implements Serializable {
    static String sanitizeProjectName(String projectName) {
        return projectName?.replaceAll('[^A-Za-z0-9_.-]', '-')?.trim() ?: 'project'
    }

    static String normalizeProjectKey(String projectKey) {
        return projectKey?.trim() ?: 'project'
    }
}
