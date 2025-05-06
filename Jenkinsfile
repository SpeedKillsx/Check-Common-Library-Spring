pipeline {
    agent any

    tools {
        maven 'Maven3'  // Assurez-vous que Maven3 est installé dans Jenkins
    }

    environment {
        // Récupération des credentials Jenkins
        GPG_PASSPHRASE = credentials('ringane')
        OSSRH_USERNAME = credentials('g22wyEOk')
        OSSRH_PASSWORD = credentials('JOWq0yFAt3tGRqkvIv1y/ajw/yS/IsHFqDZjXNon1SOQ') // ID des credentials pour Sonatype OSSRH
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout du repository depuis GitHub...'
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
                echo "Clés GPG importées avec succès."
            }
        }

        stage('Generate Maven settings.xml') {
            steps {
                echo 'Génération du fichier settings.xml personnalisé...'
                script {
                    writeFile file: 'settings.xml', text: """
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>ossrh</id>
      <username>${OSSRH_USERNAME}</username>
      <password>${OSSRH_PASSWORD}</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>gpg</id>
      <properties>
        <gpg.keyname>F7593AF7</gpg.keyname>
        <gpg.passphrase>${GPG_PASSPHRASE}</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>gpg</activeProfile>
  </activeProfiles>
</settings>
                    """
                }
            }
        }

        stage('Build & Deploy') {
            steps {
                echo 'Début du processus de build et de déploiement...'
                script {
                    sh """
                        echo 'Lancement de la commande Maven avec settings.xml personnalisé...'
                        mvn clean deploy -P release -s settings.xml
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
