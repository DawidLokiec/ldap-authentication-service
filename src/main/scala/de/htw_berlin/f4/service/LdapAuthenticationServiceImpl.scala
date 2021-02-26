package de.htw_berlin.f4.service

import de.htw_berlin.f4.domain.Credentials
import de.htw_berlin.f4.handler.dip.LdapAuthenticationService

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
 * This class provides functionalities to perform an LDAP authentication.
 *
 * @param ldapServerUrl             the URL of the LDAP server.
 * @param distinguishedNameResolver the distinguished name resolver.
 * @param executionContextExecutor  an implicit execution context.
 */
class LdapAuthenticationServiceImpl(
                                     private val ldapServerUrl: String,
                                     private val distinguishedNameResolver: DistinguishedNameResolver
                                   )(implicit executionContextExecutor: ExecutionContextExecutor)
  extends LdapAuthenticationService {

  import java.util.Properties
  import javax.naming.{AuthenticationException, Context}

  private val ldapEnvironmentProperties = new Properties()
  ldapEnvironmentProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
  ldapEnvironmentProperties.put(Context.PROVIDER_URL, ldapServerUrl)
  ldapEnvironmentProperties.put(Context.SECURITY_AUTHENTICATION, "simple")

  override def authenticate(credentials: Credentials): Future[Boolean] = {
    val password = credentials.password
    if (null == password || password.isBlank) {
      Future.successful(false)
    } else {
      Future {
        ldapEnvironmentProperties.put(
          Context.SECURITY_PRINCIPAL,
          distinguishedNameResolver.resolve(credentials.username)
        )
        ldapEnvironmentProperties.put(Context.SECURITY_CREDENTIALS, password)
        try {
          import javax.naming.directory.InitialDirContext
          val connectionWithLdapServer = new InitialDirContext(ldapEnvironmentProperties)
          connectionWithLdapServer.close()
          true
        } catch {
          case _: AuthenticationException => false
        }
      }
    }
  }
}