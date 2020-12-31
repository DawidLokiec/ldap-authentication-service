package com.github.dawidwalczak.http

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.{Http, HttpsConnectionContext}


// TODO test.
/**
 * Represents a HTTPS server which can be bind to a host and port.
 * The server needs a request handler for processing incoming requests.
 *
 * @param host         the optional host of the server. If not present, then the default value is 0.0.0.0.
 * @param port         the optional port of the server. The default value is 443.
 * @param httpsContext the https connection context for the https connection.
 * @param actorSystem  an implicit actor system.
 * @see com.github.dawidwalczak.http.dip.RequestHandler
 */
class Server(val host: String = "0.0.0.0",
             val port: Int = 443,
             val httpsContext: HttpsConnectionContext)(implicit val actorSystem: ActorSystem[Nothing]) {

  import com.github.dawidwalczak.http.dip.RequestHandler
  import scala.concurrent.Future
  import akka.http.scaladsl.Http.ServerBinding

  /**
   * Binds the current server to the host and port and uses the passed handler for processing incoming connections.
   *
   * @param requestHandler the request handler to be used for processing incoming requests.
   * @return a server binding future.
   */
  def bindAndHandleWith(requestHandler: RequestHandler): Future[ServerBinding] =
    Http().newServerAt(host, port).enableHttps(httpsContext).bind(requestHandler.getRoute)
}