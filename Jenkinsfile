pipeline {
    agent any

    environment {
        JAVA_HOME  = "/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
        PATH       = "${JAVA_HOME}/bin:/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin"
        IMAGE_NAME = "sivadhanush24/spring-ci-cd"
    }

    stages {

        stage('Check Java') {
            steps {
                sh 'echo JAVA_HOME=$JAVA_HOME'
                sh 'java -version'
            }
        }

        stage('Build App') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh '/usr/local/bin/docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} -t ${IMAGE_NAME}:latest .'
            }
        }

        stage('Login DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-creds',
                        usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh 'echo $PASS | /usr/local/bin/docker login -u $USER --password-stdin'
                }
            }
        }

        stage('Push Image') {
            steps {
                sh '/usr/local/bin/docker push ${IMAGE_NAME}:${BUILD_NUMBER}'
                sh '/usr/local/bin/docker push ${IMAGE_NAME}:latest'
            }
        }
    }

    post {
        always {
            sh '/usr/local/bin/docker rmi ${IMAGE_NAME}:${BUILD_NUMBER} ${IMAGE_NAME}:latest || true'
        }
        success {
            echo "CI passed — build ${BUILD_NUMBER} pushed to DockerHub"
        }
        failure {
            echo "CI failed"
        }
    }
}