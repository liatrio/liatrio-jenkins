FROM jenkins/jenkins:2.166-alpine

ENV JAVA_OPTS="-Dhudson.Main.development=true -Djenkins.install.runSetupWizard=false"

# Additional plugins
COPY jenkins/plugins.txt /usr/share/jenkins/plugins.txt
RUN cat /usr/share/jenkins/plugins.txt | /usr/local/bin/install-plugins.sh

# Additional jenkins config template
COPY jenkins/ /usr/share/jenkins/ref/

## install Maven
#USER root
#RUN apt-get update && apt-get install -y maven
USER jenkins
