FROM jenkins/jenkins:lts

COPY jenkins/init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/
ENV JAVA_OPTS="-Dhudson.Main.development=true -Djenkins.install.runSetupWizard=false" 
