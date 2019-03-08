# The minikube quick setup

```
curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.30.0/minikube-darwin-amd64 && chmod +x minikube && sudo cp minikube /usr/local/bin/ && rm minikube
minikube start
```
# To permit an insecure dev/test registry from within minikube, you need to add it on (re)start.

```
MINIKUBE_IP=$(minikube ip)
minikube ip
minikube stop
minikube start --insecure-registry=https://$MINIKUBE_IP:30003
```

```
##########
# DYNAMIC SETTINGS TO FIGURE OUT HOW TO AUTOMATE:
##########

# JENKINS CREDENTIALS TO IMPORT on boot groovy
# Convert minikube Client cert to pkcs12 for import into jenkins credential w/ pass 'secret'
openssl pkcs12 -export -out ~/.minikube/minikube.pfx -inkey ~/.minikube/apiserver.key -in ~/.minikube/apiserver.crt -certfile ~/.minikube/ca.crt -passout pass:secret
cp ~/.minikube/minikube.pfx ~/Documents/minikube.pfx

# Self signed cert
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout tls.key -out tls.crt -subj "/CN=jenkins/O=jenkins"
kubectl create secret generic tls --from-file=tls.crt --from-file=tls.key -n jenkins

##########
# CLOUD CONFIG SETTINGS
##########
# Namespace for slaves
<% From namespace create command above %>

# KUBE URL
https://192.168.99.100:8443

# kubernetes server cert key
cat ~/.minikube/ca.crt

# From Credential above.
<% Certificate Credential ID %>

# Jenkins url
# 192 NO GOOD. Svc ip fails.
# http://192.168.99.100:31024/
# Success: Point jenkins kubernetes plugin config at Private docker IP
http://172.17.0.29:8080

# Jenkins container template:
jenkins/jnlp-slave
```
