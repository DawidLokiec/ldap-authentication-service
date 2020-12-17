package de.htw_berlin.http

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

class ServerSpec extends AsyncFlatSpec with BeforeAndAfterAll {

  private implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "test-actor-system")
  private val server = new Server(httpsContext = null)

  override def afterAll() {
    system.terminate()
  }

  "the default hostname" should s"be ${Constants.DefaultServerIpv4Address}" in {
    assert(Constants.DefaultServerIpv4Address == server.host)
  }

  "the default port" should s"be ${Constants.DefaultHttpsPort}" in {
    assert(Constants.DefaultHttpsPort == server.port)
  }

}
