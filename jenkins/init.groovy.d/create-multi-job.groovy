import hudson.util.PersistedList
import jenkins.model.Jenkins
import hudson.model.Item
import jenkins.branch.*
import jenkins.plugins.git.*
import hudson.plugins.git.*
import hudson.plugins.git.GitSCM
import hudson.plugins.git.UserRemoteConfig
import org.jenkinsci.plugins.workflow.multibranch.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import com.cloudbees.hudson.plugins.folder.*
import groovy.json.JsonSlurper

Jenkins jenkins = Jenkins.instance

// Bring in some env values
def env = System.getenv()
String jobListUrl = env['JOBLIST']

apiUrl = new URL(jobListUrl)
println jobListUrl
def joblist = new JsonSlurper().parseText(apiUrl.text)

joblist.each { pipeline ->
  println "FOLDER: ${pipeline.folder}"
  println "JOB   : ${pipeline.name}"
  println "TYPE  : ${pipeline.type}"
  println "REPO  : ${pipeline.repo_url}"
  println "CRED  : ${pipeline.credential}"
  println "FILE  : ${pipeline.pipeline_file}"
  println "BRANCH: ${pipeline.branch_includes}"
  // DO MORE WORK HERE
  //TODO: WEBHOOK OR TRIGGER S

  String folderName     = "${pipeline.folder}"
  String jobName        = "${pipeline.name}"
  String gitRepo        = "${pipeline.repo_url}"
  String gitRepoName    = "${pipeline.name}"
  String credentialsId  = "${pipeline.credential}"
  String jobScript      = "${pipeline.pipeline_file}"
  String includes       = "${pipeline.branch_includes}"
  String excludes       = "${pipeline.branch_excludes}"


  // Get the folder where this job should be
  def folder = jenkins.getItem(folderName)
  // Create the folder if it doesn't exist
  if (folder == null) {
    folder = jenkins.createProject(Folder.class, folderName)
  }

  // Multi Branch Job Setup
  if ( "${pipeline.type}" == "multi" ) {
    // Multibranch creation/update
    WorkflowMultiBranchProject mbp
    Item item = folder.getItem(jobName)
    if ( item != null ) {
      // Update case
      mbp = (WorkflowMultiBranchProject) item
    } else {
      // Create case
      mbp = folder.createProject(WorkflowMultiBranchProject.class, jobName)
    }

    // Configure the script this MBP uses
    mbp.getProjectFactory().setScriptPath(jobScript)

    // Add git repo
    String id = null
    String remote = gitRepo
    //String includes = "*"
    //String excludes = ""
    boolean ignoreOnPushNotifications = false
    GitSCMSource gitSCMSource = new GitSCMSource(id, remote, credentialsId, includes, excludes, ignoreOnPushNotifications)
    BranchSource branchSource = new BranchSource(gitSCMSource)

    // Disable triggering build
    NoTriggerBranchProperty noTriggerBranchProperty = new NoTriggerBranchProperty()

    // Can be used later to not trigger/trigger some set of branches
    NamedExceptionsBranchPropertyStrategy.Named nebrs_n = new NamedExceptionsBranchPropertyStrategy.Named("master", noTriggerBranchProperty)

    // Add an example exception
    //BranchProperty defaultBranchProperty = null;
    //NamedExceptionsBranchPropertyStrategy.Named nebrs_n = new NamedExceptionsBranchPropertyStrategy.Named("change-this", defaultBranchProperty)
    //NamedExceptionsBranchPropertyStrategy.Named[] nebpsa = [ nebrs_n ]

    //BranchProperty[] bpa = [noTriggerBranchProperty]
    //NamedExceptionsBranchPropertyStrategy nebps = new NamedExceptionsBranchPropertyStrategy(bpa, nebpsa)

    //branchSource.setStrategy(nebps)

    // Remove and replace?
    PersistedList sources = mbp.getSourcesList()
    sources.clear()
    sources.add(branchSource)

  // End if ( "${job.type}" == "multi" )
  }

  // Single Job Setup
  if ( "${pipeline.type}" == "single" ) {

    // Create the git configuration
    UserRemoteConfig userRemoteConfig = new UserRemoteConfig(gitRepo, gitRepoName, null, credentialsId)
    if (includes) {
      branches = [new BranchSpec(includes)]
    } else {
      branches = [new BranchSpec("*/master")]
    }
    doGenerateSubmoduleConfigurations = false
    submoduleCfg = null
    browser = null
    gitTool = null
    extensions = []
    GitSCM scm = new GitSCM([userRemoteConfig], branches, doGenerateSubmoduleConfigurations, submoduleCfg, browser, gitTool, extensions)

    // Create the workflow
    def definition = new CpsScmFlowDefinition(scm, jobScript)

    // Check if the job already exists
    Object job = null
    job = folder.getItem(jobName)
    if (job == null) {
      oldJob = jenkins.getItem(jobName)
      if (oldJob.getClass() == WorkflowJob.class) {
        // Move any existing job into the folder
        Items.move(oldJob, folder)
      } else {
        // Create it if it doesn't
        job = folder.createProject(WorkflowJob, jobName)
      }
    }
    // Add the workflow to the job
    job.definition = definition
    job.save()
  // End type=single
  }
// End joblist.each
}
