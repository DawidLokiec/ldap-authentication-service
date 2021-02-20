package de.htw_berlin.f4.handler

import de.htw_berlin.f4.handler.dip.LdapAuthenticationService
import de.htw_berlin.f4.server.dip.RequestHandler

/**
 * This class is responsible for handling incoming authentication requests.
 * It handles only HTTP requests of the form: POST / {"username": "here an username", "password": "here a password"}.
 * The passed username and password in the request body are the credentials to check.
 *
 * @param ldapAuthenticationService the authentication service to perform authentication.
 */
class AuthenticationRequestHandler(private val ldapAuthenticationService: LdapAuthenticationService) extends
  RequestHandler {

  import akka.http.scaladsl.server.Route
  import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
  import org.slf4j.LoggerFactory

  /**
   * The logger for logging purposes.
   */
  private val Logger = LoggerFactory.getLogger(getClass)

  /**
   * Returns an instance of Route representing the following route: POST / {"username": "", "password": ""}.
   *
   * @return an instance of Route representing POST / {"username": "", "password": ""}.
   */
  override def getRoute: Route = cors() {
    import akka.http.scaladsl.server.Directives.{as, complete, entity, onComplete, post}
    post {
      import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
      import de.htw_berlin.f4.domain.Credentials
      import io.circe.generic.auto._
      entity(as[Credentials]) { credentials =>
        import scala.util.{Failure, Success}
        import akka.http.scaladsl.model.StatusCodes
        onComplete(ldapAuthenticationService.authenticate(credentials)) {
          case Success(true) => complete(StatusCodes.OK)
          case Success(false) => complete(StatusCodes.Unauthorized)
          case Failure(error) => {
            Logger.error("Error during LDAP authentication {}", error.getMessage, error)
            complete(StatusCodes.InternalServerError, error.getMessage) // Let is crash
          }
        }
      }
    }
  }
}

