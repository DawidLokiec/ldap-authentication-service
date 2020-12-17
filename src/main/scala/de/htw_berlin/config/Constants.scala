package de.htw_berlin.config

/** This object contains constants related with the current module config. */
object Constants {

  // LDAP related environment variables
  /** The name of the environment variable for the LDAP server's URL: LDAP_SERVER_URL. */
  val EnvVarNameLdapServerUrl = "LDAP_SERVER_URL"
  /** The name of the environment variable for the search base: LDAP_SEARCH_BASE. */
  val EnvVarNameLdapSearchBase = "LDAP_SEARCH_BASE"

  // TLS/HTTPS related environment variables
  /** The name of the environment variable for the keystore absolute path: KEYSTORE_FULL_NAME. */
  val EnvVarNameKeystoreFullName = "KEYSTORE_FULL_NAME"
  /** The name of the environment variable for the keystore password: KEYSTORE_PASSWORD. */
  val EnvVarNameKeystorePassword = "KEYSTORE_PASSWORD"

  // Actor system related
  /** The name of the actor system. */
  val ActorSystemName = "ldap-authentication-service-actor-system"

}