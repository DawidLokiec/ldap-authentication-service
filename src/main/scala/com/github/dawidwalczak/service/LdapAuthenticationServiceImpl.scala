package com.github.dawidwalczak.service

import java.util.Properties
import javax.naming.{AuthenticationException, Context}
import javax.naming.directory.InitialDirContext

/** This class provides functionalities to perform an authentication against a LDAP directory.
 *
 * @param ldapServerUrl             the URL of the LDAP server.
 * @param distinguishedNameResolver the DN resolver.
 */
class LdapAuthenticationServiceImpl(val ldapServerUrl: String,
                                    val distinguishedNameResolver: DistinguishedNameResolver
                                   ) extends LdapAuthenticationService {
  /** The the environment for the LDAP connection. */
  private val ldapEnvironmentProperties = new Properties()
  ldapEnvironmentProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
  ldapEnvironmentProperties.put(Context.PROVIDER_URL, ldapServerUrl)
  ldapEnvironmentProperties.put(Context.SECURITY_AUTHENTICATION, "simple") // TODO increase security

  /**
   * Authenticates an user.
   *
   * @param username the username.
   * @param password the user's plain password.
   * @return true if the user was successfully authenticated, otherwise false. Returns always false, if the password is
   *         blank.
   */
  override def authenticate(username: String, password: String): Boolean = {
    if (null == password || password.isEmpty) {
      false
    } else {
      ldapEnvironmentProperties.put(Context.SECURITY_PRINCIPAL, distinguishedNameResolver.resolve(username))
      ldapEnvironmentProperties.put(Context.SECURITY_CREDENTIALS, password)
      try {
        val connectionWithLdapServer = new InitialDirContext(ldapEnvironmentProperties)
        connectionWithLdapServer.close()
        true
      } catch {
        case _: AuthenticationException => false
      }
    }
  }
}