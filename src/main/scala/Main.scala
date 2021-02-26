import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.HttpsConnectionContext
import de.htw_berlin.f4.handler.AuthenticationRequestHandler
import de.htw_berlin.f4.handler.dip.LdapAuthenticationService
import de.htw_berlin.f4.helper.HttpsConnectionContextFactory
import de.htw_berlin.f4.server.Server
import de.htw_berlin.f4.service.{DistinguishedNameResolverImpl, LdapAuthenticationServiceImpl}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{Await, ExecutionContextExecutor}

/**
 * Represents the main object with the main method of the LDAP authentication microservice.
 * This object is responsible for constructing the dependencies and starting the service.
 *
 * In order to get this service properly configured, the following environment variables needs to be set:
 * - LDAP_SERVER_URL
 * - LDAP_SEARCH_BASE
 * - LDAP_USERNAME_ATTRIBUTE
 * - KEYSTORE_FULL_NAME
 * - KEYSTORE_PASSWORD
 *
 * @note Read the project's README for more details how to properly setup this service.
 */
object Main {

  /**
   * The logger for logging purposes.
   */
  private val Logger: Logger = LoggerFactory.getLogger(Main.getClass)

  /** Starts the LDAP authentication microservice.
   *
   * @param args command line arguments are ignored. Instead the above mentioned environment variables need to be set.
   */
  def main(args: Array[String]): Unit = {
    Logger.info("Start reading environment variables ...")
    val ldapServerUrl = sys.env("LDAP_SERVER_URL")
    Logger.info("Read LDAP server's URL = {}", ldapServerUrl)
    val ldapUsernameAttribute = sys.env("LDAP_USERNAME_ATTRIBUTE")
    Logger.info("Read LDAP username attribute = {}", ldapUsernameAttribute)
    val ldapSearchBase = sys.env("LDAP_SEARCH_BASE")
    Logger.info("Read LDAP search base = {}", ldapSearchBase)
    implicit val httpsConnectionContext: HttpsConnectionContext = HttpsConnectionContextFactory(
      keyStoreFilename = sys.env("KEYSTORE_FULL_NAME"),
      keyStorePassword = sys.env("KEYSTORE_PASSWORD")
    )
    Logger.info("Finished successfully reading environment variables")

    Logger.info("Start constructing dependencies...")
    implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(
      Behaviors.empty, "ldap-authentication-service-actor-system"
    )
    implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext
    val service: LdapAuthenticationService = new LdapAuthenticationServiceImpl(
      ldapServerUrl,
      new DistinguishedNameResolverImpl(ldapUsernameAttribute, ldapSearchBase)
    )
    Logger.info("Finished successfully constructing dependencies")

    Logger.info("Booting HTTPS server...")
    val httpsServer = new Server(enableCors = true)
    val bindingFuture = httpsServer.bindAndHandleWith(new AuthenticationRequestHandler(service))
    val serverEndpoint = s"https://${httpsServer.host}:${httpsServer.port}/"

    import scala.util.{Failure, Success}
    bindingFuture.onComplete {
      case Success(_) =>
        Logger.info(s"Service online at $serverEndpoint")
      case Failure(e) =>
        Logger.error(s"Failed to bind server at $serverEndpoint: {}", e.getMessage, e)
        actorSystem.terminate()
    }

    import scala.concurrent.duration.Duration
    Await.result(actorSystem.whenTerminated, Duration.Inf)
  }
}
