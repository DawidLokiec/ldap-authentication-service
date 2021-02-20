package de.htw_berlin.f4.config

/**
 * This object contains constants related with the current package config.
 */
object Constants {

  // LDAP related environment variables
  /**
   * The name of the environment variable for the LDAP server's URL: LDAP_SERVER_URL.
   */
  val LDAP_SERVER_URL = "LDAP_SERVER_URL"

  /**
   * The name of the environment variable for the LDAP attribute that contains the username: LDAP_USERNAME_ATTRIBUTE.
   */
  val LDAP_USERNAME_ATTRIBUTE = "LDAP_USERNAME_ATTRIBUTE"

  /**
   * The name of the environment variable for the search base: LDAP_SEARCH_BASE.
   */
  val LDAP_SEARCH_BASE = "LDAP_SEARCH_BASE"

  // TLS/HTTPS related environment variables
  /**
   * The name of the environment variable for the keystore absolute path: KEYSTORE_FULL_NAME.
   */
  val KEYSTORE_FULL_NAME = "KEYSTORE_FULL_NAME"

  /**
   * The name of the environment variable for the keystore password: KEYSTORE_PASSWORD.
   */
  val KEYSTORE_PASSWORD = "KEYSTORE_PASSWORD"

  // Actor system related
  /**
   * The name of the actor system.
   */
  val ActorSystemName = "ldap-authentication-service-actor-system"

}