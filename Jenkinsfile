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
  def repo = "liatrio"
  def imagename = "jnlp-slave"
  def image = "$repo/$imagename"
  def tag ="${env.BUILD_ID}"

  node(label) {
    stage('Build Docker image') {
      git 'https://github.com/liatrio/liatrio-jenkins.git'
      container('docker') {
        withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUsername')]) {
          sh "docker build -t ${image}:${tag} ."
          sh "docker login -u ${env.dockerUsername} -p ${env.dockerPassword}"
          sh "docker push ${image}:${tag}"
        }
      }
    }
  }
}
