pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        disableConcurrentBuilds()
    }

    environment {
        PROJECT_NAME = 'eco-back'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Clonando rama 'develop' desde GitHub..."
                cleanWs()
                git branch: 'develop',
                    credentialsId: 'git-token-skaotico',
                    url: 'https://github.com/skaotico/eco-back'
            }
        }

      stage('Build & Up with Docker Compose') {
          steps {
              echo 'Construyendo imágenes y levantando contenedores locales...'
              sh '''
                  echo "Eliminando servicios anteriores (si existen)..."
                  docker-compose down -v || true

                  echo "Levantando servicios con build..."
                  docker-compose up --build -d
              '''
          }
      }


        stage('Verify Services') {
            steps {
                echo 'Verificando servicios activos...'
                sh '''
                    echo "Contenedores en ejecución:"
                    docker ps
                '''
            }
        }
    }

    post {
        success {
            echo 'Stack levantado correctamente en Docker local.'
        }
        failure {
            echo 'Error al levantar los contenedores. Revisar logs.'
        }
    }
}
