import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.github.dawidlokiec.config.Constants
import com.github.dawidlokiec.http.{HttpsConnectionContextFactory, Server}
import com.github.dawidlokiec.service.{AuthenticationRequestHandlerImpl, DistinguishedNameResolverImpl, LdapAuthenticationService, LdapAuthenticationServiceImpl}

import scala.concurrent.{Await, ExecutionContextExecutor}

/**
 * Represents the main object with the main method of the LDAP authentication microservice.
 * This object is responsible for constructing the dependencies and starting the service.
 *
 * In order to get this service properly configured, the following environment variables needs to be set:
 * - LDAP_SERVER_URL
 * - LDAP_SEARCH_BASE
 * - KEYSTORE_FULL_NAME
 * - KEYSTORE_PASSWORD
 *
 * @note Read the project's README for more details how to properly setup this service.
 */
object Main {

  /** Starts the LDAP authentication microservice.
   *
   * @param args command line arguments are ignored. Instead the above mentioned environment variables need to be set.
   */
  def main(args: Array[String]): Unit = {
    implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, Constants.ActorSystemName)
    implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext

    val service: LdapAuthenticationService = new LdapAuthenticationServiceImpl(
      sys.env(Constants.EnvVarNameLdapServerUrl),
      new DistinguishedNameResolverImpl(sys.env(Constants.EnvVarNameLdapSearchBase))
    )

    val httpsConnectionContext = HttpsConnectionContextFactory(
      keyStoreFilename = sys.env(Constants.EnvVarNameKeystoreFullName),
      keyStorePassword = sys.env(Constants.EnvVarNameKeystorePassword)
    )

    val httpsServer = new Server(httpsContext = httpsConnectionContext)
    val bindingFuture = httpsServer.bindAndHandleWith(new AuthenticationRequestHandlerImpl(service))
    val serverEndpoint = s"https://${httpsServer.host}:${httpsServer.port}/"

    import scala.util.{Failure, Success}
    bindingFuture.onComplete {
      case Success(_) =>
        println(s"Server online at $serverEndpoint")
      case Failure(exception) =>
        Console.err.println(s"Failed to bind server at $serverEndpoint\nError: ${exception.getMessage}")
        exception.printStackTrace()
        actorSystem.terminate()
    }

    import scala.concurrent.duration.Duration
    Await.result(actorSystem.whenTerminated, Duration.Inf)
  }
}
