package com.github.dawidlokiec.handler

import com.github.dawidlokiec.server.dip.RequestHandler
import com.github.dawidlokiec.service.LdapAuthenticationService

/**
 * This class is responsible for handling incoming authentication requests.
 * It handles only HTTP requests of the form: GET /?username=<username>&password=<password>.
 * The passed URL parameters (username and password combination) are the credentials to check.
 *
 * @param ldapAuthenticationService the authentication service to perform authentication.
 */
class AuthenticationRequestHandler(private val ldapAuthenticationService: LdapAuthenticationService) extends
  RequestHandler {

  import akka.http.scaladsl.server.Route
  import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors

  /**
   * Returns an instance of Route representing the following route:
   * GET /?username=<username>&password=<password>
   *
   * @return an instance of Route representing GET /?username=<username>&password=<password>.
   */
  override def getRoute: Route = cors() {
    import akka.http.scaladsl.server.Directives.{complete, get, parameters}
    get {
      parameters("username", "password") { (username, password) =>
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