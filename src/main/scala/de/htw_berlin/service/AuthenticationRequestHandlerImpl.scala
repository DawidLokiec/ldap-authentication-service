package de.htw_berlin.service

import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import de.htw_berlin.http.dip.RequestHandler

/**
 * This class is responsible for handling incoming authentication requests.
 * It handles only HTTP requests of the form: GET /?username=<username>&password=<password>.
 * The passed URL parameters (username and password combination) are the credentials to check.
 *
 * @param ldapAuthenticationService the authentication service to perform authentication.
 */
class AuthenticationRequestHandlerImpl(val ldapAuthenticationService: LdapAuthenticationService) extends RequestHandler {

  import akka.http.scaladsl.server.Directives.{complete, get, parameters}

  /**
   * Returns an instance of Route representing the following route:
   * GET /?username=<username>&password=<password>
   *
   * @return an instance of Route representing GET /?username=<username>&password=<password>.
   */
  override def getRoute: akka.http.scaladsl.server.Route = cors() {
    get {
      parameters(Constants.UrlParameterNameUsername, Constants.UrlParameterNamePassword) { (username, password) =>
        import akka.http.scaladsl.model.StatusCodes
        complete(
          if (ldapAuthenticationService.authenticate(username, password))
            StatusCodes.OK
          else
            StatusCodes.Unauthorized
        )
      }
    }
  }
}