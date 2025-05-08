pipeline {
	agent any

    tools {
		maven 'Maven3'
    }

    environment {
		GPG_PASSPHRASE = credentials('gpg-creeds')
        OSSRH_CREDS = credentials('ossrh-creds')
    }

    stages {
		stage('Simple Echo') {
			steps {
				echo 'Testing simple echo'
                sh 'chmod 777 mvnw && echo "Hello Jenkins"'
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
				echo "OSSRH_USERNAME: ${OSSRH_CREDS_USR != null ? 'OK' : 'MISSING'}"
                echo "OSSRH_PASSWORD: ${OSSRH_CREDS_PSW != null ? 'OK' : 'MISSING'}"
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
				echo '🔑 Importing GPG keys...'
				sh '''
					gpg --batch --yes --pinentry-mode loopback --import /var/jenkins_home/.gnupg/private.gpg || echo "❗ Failed to import private key"
					gpg --batch --yes --pinentry-mode loopback --import /var/jenkins_home/.gnupg/public.gpg || echo "❗ Failed to import public key"
					gpg --list-secret-keys
				'''
    	}
	}
        stage('Test GPG Signing') {
			steps {
				echo '🧪 Testing GPG signing...'
                sh '''
                    echo "Test file" > test.txt
                    gpg --batch --yes --pinentry-mode loopback --passphrase "${GPG_PASSPHRASE}" -ab test.txt
                    ls -la test.txt*
                '''
             }
        }

        stage('Build & Deploy') {
			steps {
				echo 'Starting build and deployment...'
                sh """
                    mvn clean deploy -P release \
                    -Dossrh.username=${OSSRH_CREDS_USR} \
                    -Dossrh.password=${OSSRH_CREDS_PSW} \
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
