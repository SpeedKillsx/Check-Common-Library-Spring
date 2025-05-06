pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        GPG_PASSPHRASE = credentials('ringane') // Utilisation du passphrase GPG
        OSSRH_USERNAME = credentials('g22wyEOk')  // Utilisation de l'username Sonatype
        OSSRH_PASSWORD = credentials('JOWq0yFAt3tGRqkvIv1y/ajw/yS/IsHFqDZjXNon1SOQ')  // Utilisation du password Sonatype
        PRIVATE_KEY = credentials('private.key')  // ID secret pour la clé privée GPG
        PUBLIC_KEY = credentials('public.key')  // ID secret pour la clé publique GPG
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Import GPG Key') {
            steps {
                script {

                    writeFile file: '/tmp/private.key', text: "$PRIVATE_KEY"
                    writeFile file: '/tmp/public.key', text: "$PUBLIC_KEY"


                    sh '''
                        gpg --import /tmp/private.key
                        gpg --import /tmp/public.key
                    '''
                    echo "GPG keys imported"
                }
            }
        }

        stage('Build & Deploy') {
            steps {
                script {
                    sh '''
                        mvn clean deploy -P release -Dgpg.passphrase=$GPG_PASSPHRASE
                    '''
                }
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
