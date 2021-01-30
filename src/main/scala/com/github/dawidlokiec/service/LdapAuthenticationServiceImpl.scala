package com.github.dawidlokiec.service

/**
 * This class provides functionalities to perform an LDAP authentication.
 *
 * @param ldapServerUrl             the URL of the LDAP server.
 * @param distinguishedNameResolver the distinguished name resolver.
 */
class LdapAuthenticationServiceImpl(
                                     private val ldapServerUrl: String,
                                     private val distinguishedNameResolver: DistinguishedNameResolver
                                   ) extends LdapAuthenticationService {

  import java.util.Properties
  import javax.naming.{AuthenticationException, Context}

  /** The environment for the LDAP connection. */
  private val ldapEnvironmentProperties = new Properties()
  ldapEnvironmentProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
  ldapEnvironmentProperties.put(Context.PROVIDER_URL, ldapServerUrl)
  ldapEnvironmentProperties.put(Context.SECURITY_AUTHENTICATION, "simple") // TODO increase security

  /**
   * Authenticates an user.
   *
   * @param username the username.
   * @param password the user's password.
   * @return true if the user was successfully authenticated. Returns always false, if the password is blank.
   */
  override def authenticate(username: String, password: String): Boolean = {
    if (null == password || password.isEmpty || password.isBlank) {
      false
    } else {
      ldapEnvironmentProperties.put(Context.SECURITY_PRINCIPAL, distinguishedNameResolver.resolve(username))
      ldapEnvironmentProperties.put(Context.SECURITY_CREDENTIALS, password)
      try {
        import javax.naming.directory.InitialDirContext
        val connectionWithLdapServer = new InitialDirContext(ldapEnvironmentProperties)
        connectionWithLdapServer.close()
        true
      } catch {
        case e: AuthenticationException => false
        // No default case, let it crash
      }
    }
  }
}