package navi.company.utils

class MavenHelper implements Serializable {
    static String generateVersion(String version) {
        return version?.trim() ?: '1.0.0'
    }

    static String buildGoals(String goals = 'clean verify') {
        return goals
    }
}
