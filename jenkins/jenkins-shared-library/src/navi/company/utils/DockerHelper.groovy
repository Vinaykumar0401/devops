package navi.company.utils

class DockerHelper implements Serializable {
    static String buildDockerTag(String imageName, String imageTag) {
        return "${imageName}:${imageTag}"
    }

    static String formatImageName(String imageName) {
        return imageName.toLowerCase().trim()
    }
}
