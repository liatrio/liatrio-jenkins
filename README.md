# liatrio-jenkins

## Job configuration options
 - Add jobs to the configuration as code config in kube/jenkins-configuration.yaml
 - Edit the jobs lists in this repo. (jenkins/jobs.txt, example/jobs.json)
 - Edit the JOBLIST environmental param defined in kube/jenkins-deployment.yaml and point to your own joblist.

## Deploy to kubernetes
```
# (Optional) To deploy to a jenkins specific namespace:
#
#➜  kubectl apply -f kube/jenkins-namespace.yaml
#namespace/jenkins created
#
#➜  kubectl config set-context $(kubectl config current-context) --namespace=jenkins
#Context "minikube" modified.

# Not yet needed
#➜  openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout tls.key -out tls.crt -subj "/CN=jenkins/O=jenkins"
#➜  kubectl create secret generic tls --from-file=path/to/tls.crt --from-file=path/to/tls.key
#secret/tls created

➜  kubectl apply -f kube/.
configmap/jenkins-configuration-as-code created
deployment.extensions/jenkins created
namespace/jenkins configured
persistentvolumeclaim/jenkins-master-pvc created
serviceaccount/jenkins created
role.rbac.authorization.k8s.io/jenkins created
rolebinding.rbac.authorization.k8s.io/jenkins created
service/jenkins created
service/jenkins-discovery created
```

## Manual Start for testing/debugging of init.groovy.d scripts
```
# (Optional) Use docker on minikube
#minikube start
#eval $(minikube docker-env)

docker build . -t kube-jenkins:latest
docker run -it -p 8080:8080 -e JOBLIST=https://raw.githubusercontent.com/liatrio/liatrio-jenkins/master/jenkins/jobs.json kube-jenkins:latest
```

## To Browse Jenkins
- 
```
minikube service jenkins --url
or
minikube service jenkins
or 
```
 - http://$(minikube ip):30000

## Delete from Kubernetes
```
kubectl delete -f kube/.
```

## Minikube dashboard
```
minikube dashboard
```
