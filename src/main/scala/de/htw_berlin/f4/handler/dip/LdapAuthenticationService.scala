package de.htw_berlin.f4.handler.dip

import de.htw_berlin.f4.domain.Credentials

/**
 * Defines the functionalities of an LDAP authentication service.
 */
trait LdapAuthenticationService {

  import scala.concurrent.Future

  /**
   * Authenticates asynchronously an user.
   *
   * @param credentials the credentials to check against an LDAP server.
   * @return true if the user was successfully authenticated.
   */
  def authenticate(credentials: Credentials): Future[Boolean]

}