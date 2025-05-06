pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stage('Simple Echo') {
        steps {
            echo 'Testing simple echo'
            sh 'echo "Hello Jenkins"'
        }
    }
    environment {
        GPG_PASSPHRASE = credentials('ringane')
        OSSRH_USERNAME = credentials('g22wyEOk')
        OSSRH_PASSWORD = credentials('JOWq0yFAt3tGRqkvIv1y/ajw/yS/IsHFqDZjXNon1SOQ')
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout du repository depuis GitHub...'
                checkout scm
                echo 'Repository checkout terminé.'
            }
        }

        stage('Debug Credentials') {
            steps {
                script {
                    echo "OSSRH_USERNAME: ${OSSRH_USERNAME != null ? 'OK' : 'MISSING'}"
                    echo "OSSRH_PASSWORD: ${OSSRH_PASSWORD != null ? 'OK' : 'MISSING'}"
                    echo "GPG_PASSPHRASE: ${GPG_PASSPHRASE != null ? 'OK' : 'MISSING'}"
                }
            }
        }


        stage('Validate-git') {
            steps {
                script {
                    sh 'ls -la'
                }
            }
        }

        stage('Import GPG Keys') {
            steps {
                echo 'Importation des clés GPG...'
                script {
                    sh """
                        echo 'Importation des clés privées...'
                        gpg --import ~/.gnupg/private.key
                        echo 'Importation des clés publiques...'
                        gpg --import ~/.gnupg/public.key
                    """
                }
            }
        }

        stage('Build & Deploy') {
            steps {
                echo 'Début du processus de build et de déploiement...'
                script {
                    sh """
                        mvn clean deploy -P release -Dossrh.username=${OSSRH_USERNAME} -Dossrh.password=${OSSRH_PASSWORD} -Dgpg.passphrase=${GPG_PASSPHRASE}
                    """
                }

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
