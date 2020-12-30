package com.github.dawidwalczak.service

/**
 * This trait defines the functionalities of a LDAP authentication service.
 */
trait LdapAuthenticationService {

  /**
   * Authenticates an user.
   *
   * @param username the username.
   * @param password the user's password.
   * @return true if the user was successfully authenticated, otherwise false.
   */
  def authenticate(username: String, password: String): Boolean

}