# liatrio-jenkins

## Job configuration
 - Edit the jobs files in this repo.
```
vi jenkins/jobs.yaml && cat jenkins/jobs.yaml | yaml2json > jenkins/jobs.json 
```
 - Edit the JOBLIST env param in kube/jenkins-deployment.yaml and point to your own joblist.

## Build to run on local minikube docker
```
minikube start
eval $(minikube docker-env)
docker build . -t kube-jenkins:latest
```
## Deploy to kubernetes
```
kubectl apply -f kube/.
```

## Manual Start for testing/debugging of init.groovy.d scripts
```
#eval $(minikube docker-env)
docker build . -t kube-jenkins:latest
docker run -it -p 8080:8080 -e JOBLIST=https://raw.githubusercontent.com/liatrio/liatrio-jenkins/kube/jenkins/jobs.json kube-jenkins:latest
```

## To Browse Jenkins
- 
```
minikube service jenkins --url
or
minikube service jenkins
or 
```
 - http://${MINIKUBE_IP}:30000

## Delete from Kubernetes
```
kubectl delete -f kube/.
```

## Minikube dashboard
```
minikube dashboard
```
