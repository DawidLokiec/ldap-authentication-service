package com.github.dawidwalczak.http.dip

/**
 * This trait defines the methods of a http request handler.
 */
trait RequestHandler {

  /** Returns an instance of Route.
   *
   * @return an instance of Route.
   */
  def getRoute: akka.http.scaladsl.server.Route

}