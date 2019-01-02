FROM jenkins/jenkins:lts

COPY jenkins/init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/
ENV JAVA_OPTS="-Dhudson.Main.development=true -Djenkins.install.runSetupWizard=false"

COPY jenkins/plugins.txt /usr/share/jenkins/plugins.txt
COPY jenkins/jobs.txt /var/jenkins_home/jobs.txt
RUN cat /usr/share/jenkins/plugins.txt | /usr/local/bin/install-plugins.sh
