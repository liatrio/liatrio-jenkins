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
# NO LONGER NECESSARY, using SVC Account and autodetected tokens, but may be needed for other cluster access later.
# Convert minikube Client cert to pkcs12 for import into jenkins credential w/ pass 'secret'
openssl pkcs12 -export -out ~/.minikube/minikube.pfx -inkey ~/.minikube/apiserver.key -in ~/.minikube/apiserver.crt -certfile ~/.minikube/ca.crt -passout pass:secret
cp ~/.minikube/minikube.pfx ~/Documents/minikube.pfx

# Self signed cert
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout tls.key -out tls.crt -subj "/CN=jenkins/O=jenkins"
kubectl create secret generic tls --from-file=tls.crt --from-file=tls.key

```
