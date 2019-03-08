import javaposse.jobdsl.plugin.*;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import hudson.util.PersistedList
import org.jenkinsci.plugins.workflow.multibranch.*
import com.cloudbees.hudson.plugins.folder.*
import jenkins.branch.*
import jenkins.plugins.git.*



def list = new File("/usr/share/jenkins/ref/jobs.txt")
def jobs = list.readLines()

jenkins = Jenkins.instance;


def folder = jenkins.createProject(Folder.class, "jobs")

for (int i = 0; i < jobs.size(); i++){

  jobName = jobs[i].split("/")[4].replaceAll(".git","");
  branch = "*/master"



  WorkflowMultiBranchProject job = folder.createProject(WorkflowMultiBranchProject.class, jobName)

  String id = null
  String remote = jobs[i]
  String includes = "*"
  String excludes = ""
  String credentialsId = ""
  boolean ignoreOnPushNotifications = false
  GitSCMSource gitSCMSource = new GitSCMSource(id, remote, credentialsId, includes, excludes, ignoreOnPushNotifications)
  BranchSource branchSource = new BranchSource(gitSCMSource)

  PersistedList sources = job.getSourcesList()
  sources.add(branchSource)


}
jenkins.save()
