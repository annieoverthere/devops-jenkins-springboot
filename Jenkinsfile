pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat 'mvnw.cmd clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
            }
        }

        stage('Deploy') {
            steps {
                bat 'java -jar target\\*.jar'
            }
        }
    }
}