pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        disableConcurrentBuilds()
    }

    environment {
        PROJECT_NAME = 'eco-back'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
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

        stage('Detect Docker Compose') {
            steps {
                script {
                    env.COMPOSE_CMD = sh(
                        script: '''
                        if docker compose version >/dev/null 2>&1; then
                            echo "docker compose"
                        elif docker-compose version >/dev/null 2>&1; then
                            echo "docker-compose"
                        else
                            echo "none"
                        fi
                        ''', returnStdout: true
                    ).trim()

                    if (env.COMPOSE_CMD == 'none') {
                        error("Docker Compose no está instalado en este nodo")
                    } else {
                        echo "Usando comando: ${env.COMPOSE_CMD}"
                    }
                }
            }
        }

        stage('Build and Up with Docker Compose') {
            steps {
                echo "Construyendo imágenes y levantando contenedores locales..."
                sh """
                    echo "Eliminando servicios anteriores si existen..."
                    ${COMPOSE_CMD} down -v || true

                    echo "Levantando servicios con build..."
                    ${COMPOSE_CMD} up --build -d
                """
            }
        }

        stage('Verify Services') {
            steps {
                echo "Verificando contenedores en ejecución..."
                sh """
                    docker ps
                    echo "Últimas 20 líneas de logs de la API Java:"
                    docker logs --tail 20 api-rest-java || true
                """
            }
        }
    }

    post {
        success {
            echo "Stack levantado correctamente en Docker local."
        }
        failure {
            echo "Error al levantar los contenedores. Revisar logs."
        }
    }
}
