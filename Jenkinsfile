pipeline {
    agent any

    tools {
        maven 'Maven3'  // Assurez-vous que Maven3 est installé dans Jenkins
    }

    environment {
        // Récupération des credentials Jenkins
        GPG_PASSPHRASE = credentials('ringane') // ID de l'ID de credentials pour le passphrase GPG
        OSSRH_USERNAME = credentials('g22wyEOk') // ID des credentials pour le Sonatype OSSRH
        OSSRH_PASSWORD = credentials('JOWq0yFAt3tGRqkvIv1y/ajw/yS/IsHFqDZjXNon1SOQ') // ID des credentials pour Sonatype OSSRH
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout du repository depuis GitHub...'
                // Checkout du repository depuis GitHub
                checkout scm
                echo 'Repository checkout terminé.'
            }
        }
         stage('Validate-git') {
                    steps {
                        script {
                            sh 'ls -la'
                        }
                    }

        stage('Import GPG Keys') {
            steps {
                echo 'Importation des clés GPG...'
                // Import des clés GPG pour signer les artefacts Maven
                script {
                    sh """
                        echo 'Importation des clés privées...'
                        gpg --import ~/.gnupg/private.key
                        echo 'Importation des clés publiques...'
                        gpg --import ~/.gnupg/public.key
                    """
                }
                echo "Clés GPG importées avec succès."
            }
        }

        stage('Build & Deploy') {
            steps {
                echo 'Début du processus de build et de déploiement...'
                // Compilation et déploiement Maven
                script {
                    sh """
                        echo 'Lancement de la commande Maven pour clean et deploy...'
                        mvn clean deploy -P release -Dgpg.passphrase=$GPG_PASSPHRASE
                    """
                }
                echo 'Processus de build et déploiement terminé.'
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
