# liatrio-jenkins

## Job configuration options
 - Add jobs to the configuration as code config in kube/jenkins-configuration.yaml
 - Edit the jobs lists in this repo. (jenkins/jobs.txt, example/jobs.json)
 - Edit the JOBLIST environmental param defined in kube/jenkins-deployment.yaml and point to your own joblist.

## Deploy to kubernetes
```
kubectl apply -f kube/.
```

## To Browse Jenkins
```
minikube service jenkins

minikube service jenkins --url
```

## Minikube dashboard
```
minikube dashboard
```

## Delete from Kubernetes
```
kubectl delete -f kube/.
```

## Manual Start for testing/debugging of init.groovy.d scripts
```
# (Optional) Use docker on minikube
#minikube start
#eval $(minikube docker-env)

docker build . -t kube-jenkins:latest
docker run -it -p 8080:8080 -e JOBLIST=https://raw.githubusercontent.com/liatrio/liatrio-jenkins/master/example/jobs.json kube-jenkins:latest
```
