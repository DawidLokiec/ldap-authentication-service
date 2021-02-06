package com.github.dawidlokiec.handler.dip

import com.github.dawidlokiec.domain.Credentials

/**
 * Defines the functionalities of a LDAP authentication service.
 */
trait LdapAuthenticationService {

  import scala.concurrent.Future

  /**
   * Authenticates asynchronously an user.
   *
   * @param credentials the credentials to check against an LDAP user.
   * @return true if the user was successfully authenticated.
   */
  def authenticate(credential: Credentials): Future[Boolean]

}