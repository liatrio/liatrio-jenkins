import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;
import org.jenkinsci.plugins.plaincredentials.impl.*
import hudson.util.Secret


Credentials githubCreds = (Credentials) new UsernamePasswordCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    "github-creds,
                    "Github",
                    System.getenv("GITHUB_USER"),
                    System.getenv("GITHUB_PASS"))

Credentials grafanaCreds = (Credentials) new UsernamePasswordCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    "graf-creds,
                    "Grafana",
                    System.getenv("GRAF_USER"),
                    System.getenv("GRAF_PASS"))

Credentials bitbucketToken = (Credentials) new StringCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    "bb-token",
                    "Bitbucket Token",
                    Secret.fromString(System.getenv("BB_TOKEN")))

Credentials jiraCreds = (Credentials) new StringCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    "jira-creds",
                    "JIRA Cred",
                    Secret.fromString(System.getenv("JIRA_CREDS")))

SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), githubCreds)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), grafanaCreds)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), bitbucketToken)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), jiraCreds)
