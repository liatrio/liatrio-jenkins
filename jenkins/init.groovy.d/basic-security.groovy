import jenkins.model.*
import hudson.security.*
import hudson.security.csrf.DefaultCrumbIssuer

def instance = Jenkins.getInstance()


def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount('admin','liatrioadmin')
instance.setSecurityRealm(hudsonRealm)
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
instance.save()

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

instance.save()

