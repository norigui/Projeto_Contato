pipeline {
    agent any

    environment {
        IMAGE_NAME = 'norigui/contatos-app'
        TAG = 'latest'
        GIT_REPO = 'https://github.com/norigui/Projeto_Contato.git'
        BRANCH = 'main'
        GITHUB_USER = 'norigui'
        GITHUB_TOKEN = credentials('token')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH}",
                    credentialsId: 'github_pat',
                    url: "${GIT_REPO}"
            }
        }

        stage('Build com Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t $IMAGE_NAME:$TAG ."
            }
        }

        stage('Run Container') {
            steps {
                bat 'docker rm -f contatos-container || true'
                bat "docker run -d --name contatos-container -p 8081:8080 $IMAGE_NAME:$TAG"
            }
        }

        stage('Push GitHub') {
            steps {
                script {
                    bat 'echo Build automático em: $(date) > build-info.txt'

                    bat """
                        git config user.name "Jenkins"
                        git config user.email "jenkins@norigui.com"
                        git add build-info.txt
                        git commit -m 'Atualização automática: build-info atualizado'
                        git push https://${GITHUB_USER}:${GITHUB_TOKEN}@github.com/norigui/Projeto_Contato.git ${BRANCH}
                    """
                }
            }
        }

        stage('Push Docker Hub') {
            when {
                expression { return env.DOCKERHUB_USER != null }
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_pat', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]) {
                    bat """
                        echo "$DOCKERHUB_PASS" | docker login -u "$DOCKERHUB_USER" --password-stdin
                        docker push $IMAGE_NAME:$TAG
                    """
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizada.'
        }
    }
}
