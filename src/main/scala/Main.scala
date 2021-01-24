import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.github.dawidlokiec.config.Constants
import com.github.dawidlokiec.helper.HttpsConnectionContextFactory
import com.github.dawidlokiec.server.Server
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

    println("Start reading environment variables ...")
    val ldapServerUrl = sys.env(Constants.EnvVarNameLdapServerUrl)
    println("Read LDAP server's URL = " + ldapServerUrl)
    val ldapSearchBase = sys.env(Constants.EnvVarNameLdapSearchBase)
    println("Read LDAP search base = " + ldapSearchBase)

    val service: LdapAuthenticationService = new LdapAuthenticationServiceImpl(
      ldapServerUrl,
      new DistinguishedNameResolverImpl(ldapSearchBase)
    )

    val httpsConnectionContext = HttpsConnectionContextFactory(
      keyStoreFilename = sys.env(Constants.EnvVarNameKeystoreFullName),
      keyStorePassword = sys.env(Constants.EnvVarNameKeystorePassword)
    )
    println("End reading environment variables")
    println("Booting HTTPS server ...")
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
