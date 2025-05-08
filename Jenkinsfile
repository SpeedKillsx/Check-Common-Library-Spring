pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        GPG_PASSPHRASE = credentials('ringane')
        OSSRH_USERNAME = credentials('g22wyEOk')
        OSSRH_PASSWORD = credentials('JOWq0yFAt3tGRqkvIv1y/ajw/yS/IsHFqDZjXNon1SOQ')
    }

    stages {
        stage('Simple Echo') {
            steps {
                echo 'Testing simple echo'
                sh 'echo "Hello Jenkins"'
            }
        }

        stage('Checkout') {
            steps {
                echo 'Checkout du repository depuis GitHub...'
                checkout scm
                echo 'Repository checkout terminé.'
            }
        }

        stage('Debug Credentials') {
            steps {
                echo "OSSRH_USERNAME: ${OSSRH_USERNAME != null ? 'OK' : 'MISSING'}"
                echo "OSSRH_PASSWORD: ${OSSRH_PASSWORD != null ? 'OK' : 'MISSING'}"
                echo "GPG_PASSPHRASE: ${GPG_PASSPHRASE != null ? 'OK' : 'MISSING'}"
            }
        }

        stage('Validate Git') {
            steps {
                sh 'ls -la'
            }
        }

        stage('Import GPG Keys') {
            steps {
                echo 'Importing GPG keys...'
                sh '''
                    gpg --import /root/.gnupg/private.key || echo "Failed to import private key"
                    gpg --import /root/.gnupg/public.key || echo "Failed to import public key"
                    gpg --list-keys
                '''
            }
        }

        stage('Build & Deploy') {
            steps {
                echo 'Starting build and deployment...'
                sh """
                    mvn clean deploy -P release \
                    -Dossrh.username=${OSSRH_USERNAME} \
                    -Dossrh.password=${OSSRH_PASSWORD} \
                    -Dgpg.passphrase=${GPG_PASSPHRASE}
                """
            }
        }
    }

    post {
        success {
            echo 'Build et déploiement réussis !'
        }
        failure {
            echo 'Le build ou le déploiement a échoué.'
        }
    }
}
