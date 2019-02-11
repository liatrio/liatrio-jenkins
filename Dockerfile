FROM jenkins/jenkins:lts
ARG github_user="admin"
ARG github_pass="password"
ARG graf_user="admin"
ARG graf_pass="password"
ARG bb_token
ARG jira_creds

COPY jenkins/init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/
ENV JAVA_OPTS="-Dhudson.Main.development=true -Djenkins.install.runSetupWizard=false"
ENV GITHUB_USER=$github_user
ENV GITHUB_PASS=$github_pass
ENV GRAF_USER=$graf_user
ENV GRAF_PASS=$graf_pass
ENV BB_TOKEN=$bb_token
ENV JIRA_CREDS=$jira_creds

COPY jenkins/plugins.txt /usr/share/jenkins/plugins.txt
COPY jenkins/jobs.txt /var/jenkins_home/jobs.txt
RUN cat /usr/share/jenkins/plugins.txt | /usr/local/bin/install-plugins.sh
