pipeline {
    agent any
    stages{
        stage("printing triangle patten") {
            steps {
                script {
                    def rows = 5
                    for (int i = 1; i <= rows; i++) {
                        def line = ''
                        for (int j = 1; j <= i; j++) {
                            line += '* '
                        }
                        echo line
                    }
                }
            }
        }
        stage("printing reverse triangle patten") {
            steps {
                script {
                    def rows = 5
                    for (int i = rows; i >= 1; i--) {
                        def line = ''
                        for (int j = 1; j <= i; j++) {
                            line += '* '
                        }
                        echo line
                    }
                }
            }
        }
    }
}