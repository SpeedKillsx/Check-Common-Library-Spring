pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        GPG_PASSPHRASE = credentials('ringane') // use ID from Jenkins credentials
        OSSRH_USERNAME = credentials('g22wyEOk')
        OSSRH_PASSWORD = credentials('JOWq0yFAt3tGRqkvIv1y/ajw/yS/IsHFqDZjXNon1SOQ')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Import GPG Key') {
            steps {
                sh '''
                    gpg --import ~/.gnupg/private.key
                    gpg --import ~/.gnupg/public.key
                '''
            }
        }

        stage('Build & Deploy') {
            steps {
                sh '''
                  mvn clean deploy \
                    -P release \
                    -Dgpg.passphrase=$GPG_PASSPHRASE
                '''
            }
        }
    }

    post {
        success {
            echo 'Build and deploy succeeded!'
        }
        failure {
            echo 'Build or deploy failed.'
        }
    }
}
