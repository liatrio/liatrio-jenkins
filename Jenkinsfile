pipeline {
//  agent {
//    kubernetes {
//      label 'mypod'
//      defaultContainer 'jnlp'
//      yamlFile 'KubernetesPod.yaml'
//    }
//  }
    agent {
        docker 'docker'
    }
//    agent {
//      label 'docker'
//    }
    environment {
        IMAGE='liatrio/kube-jenkins'
    }
    stages {
        stage('Build_Master') {
            when { not { branch 'master' } }
            environment {
                TAG="${env.BUILD_ID}"
            }
            steps {
                sh "docker build --pull -t ${IMAGE}:${TAG} ."
            }
        }
        stage('Build') {
            when { branch 'master' }
            environment {
                TAG="latest"
            }
            steps {
                sh "docker build --pull -t ${IMAGE}:${TAG} ."
            }
        }
        stage('Push to dockerhub Dev') {
            when { not { branch 'master'} }
            environment { TAG="${env.BUILD_ID}" }
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUsername')]) {
                    sh "docker login -u ${env.dockerUsername} -p ${env.dockerPassword}"
                    sh "docker push ${env.IMAGE}:${TAG}"
                }
            }
        }
        stage('Push to dockerhub') {
            when { branch 'master' }
            environment { TAG="latest" }
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUsername')]) {
                    sh "docker login -u ${env.dockerUsername} -p ${env.dockerPassword}"
                    sh "docker push ${env.IMAGE}:${TAG}"
                }
            }
        }
    }
}
