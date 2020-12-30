package com.github.dawidwalczak.http

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.{Http, HttpsConnectionContext}
import com.github.dawidwalczak.http.dip.RequestHandler

import scala.concurrent.Future

// TODO test.
/** Represents a HTTPS server which can be bind to a host and port.
 * The server needs a request handler for processing incoming requests.
 *
 * @param host         the optional host of the server. The default value is Constants.DefaultServerIpv4Address.
 * @param port         the optional port number of the server. The default value is Constants.DefaultHttpsPort.
 * @param httpsContext the https connection context for the https connection.
 * @param actorSystem  an implicit actor system.
 * @see de.htw_berlin.http.dip.RequestHandler
 */
class Server(val host: String = Constants.DefaultServerIpv4Address,
             val port: Int = Constants.DefaultHttpsPort,
             val httpsContext: HttpsConnectionContext)(implicit val actorSystem: ActorSystem[Nothing]) {

  /** Binds the current server to the endpoint parameters (host and port) and uses the passed
   * handler for processing incoming connections.
   *
   * @param handler the handler to be used for processing incoming requests.
   * @return a server binding future.
   */
  def bindAndHandleWith(handler: RequestHandler): Future[ServerBinding] =
    Http().newServerAt(host, port).enableHttps(httpsContext).bind(handler.getRoute)

}