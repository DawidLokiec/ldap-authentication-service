package com.github.dawidlokiec.http

import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

class ServerSpec extends AsyncFlatSpec with BeforeAndAfterAll {

  import akka.actor.typed.ActorSystem
  import akka.actor.typed.scaladsl.Behaviors

  private implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "test-actor-system")
  private val server = new Server(httpsContext = null)

  override def afterAll() {
    system.terminate()
  }

  "the default server's host" should "be 0.0.0.0" in {
    assert("0.0.0.0" == server.host)
  }

  "the default server's port" should "be 443" in {
    assert(443 == server.port)
  }

}
