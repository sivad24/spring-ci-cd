pipeline {
    agent any

    environment {
        JAVA_HOME = "/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
        PATH = "${JAVA_HOME}/bin:/usr/local/bin:/usr/bin:/bin"
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
                sh './mvnw clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh '/usr/local/bin/docker build -t spring-ci-cd:${BUILD_NUMBER} .'
            }
        }

        stage('Verify Image') {
            steps {
                sh '/usr/local/bin/docker images --format "{{.Repository}}:{{.Tag}}" | grep spring-ci-cd'
            }
        }

        stage('Docker Test') {
            steps {
                sh '/usr/local/bin/docker ps'
            }
        }
    }

    post {
        success {
            echo "✅ Build ${BUILD_NUMBER} successful"
        }
        failure {
            echo "❌ Build failed"
        }
    }
}