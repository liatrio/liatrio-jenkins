/**
 * This pipeline will run a Docker image build
 */

def label = "docker-${UUID.randomUUID().toString()}"

podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker
    image: docker:1.11
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
"""
  ) {
  def repo = 'docker.artifactory.liatr.io'
  def repoCredentialId='artifactory'
//  def repo = "liatrio"
//  def repoCredentialId='dockerhub'
  def imagename = "kube-jenkins"
  def image = "$repo/$imagename"
  //def tag = "latest"
  def tag = '0.1.0'

  node(label) {
    stage('Build Docker image') {
      git 'https://github.com/liatrio/liatrio-jenkins.git'
      container('docker') {
        withCredentials([usernamePassword(credentialsId: repoCredentialId, passwordVariable: 'Password', usernameVariable: 'Username')]) {
          sh "docker build -t ${image}:${tag} ."
          sh "docker login -u ${env.Username} -p ${env.Password} ${repo}"
//          sh "docker push ${image}:${tag}"
        }
      }
    }
  }
}
