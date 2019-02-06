import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;
import org.jenkinsci.plugins.plaincredentials.impl.*
import hudson.util.Secret

Credentials grafanaCreds = (Credentials) new UsernamePasswordCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    java.util.UUID.randomUUID().toString(),
                    "Grafana",
                    System.getenv("GRAF_USER"),
                    System.getenv("GRAF_PASS"))

Credentials bitbucketToken = (Credentials) new StringCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    java.util.UUID.randomUUID().toString(),
                    "Bitbucket Token",
                    Secret.fromString(System.getenv("BB_TOKEN")))

Credentials jiraCreds = (Credentials) new StringCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    java.util.UUID.randomUUID().toString(),
                    "JIRA Cred",
                    Secret.fromString(System.getenv("JIRA_CREDS")))

SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), grafanaCreds)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), bitbucketToken)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), jiraCreds)
