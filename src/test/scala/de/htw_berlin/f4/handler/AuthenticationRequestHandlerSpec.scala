package de.htw_berlin.f4.handler

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.htw_berlin.f4.server.dip.RequestHandler
import de.htw_berlin.f4.service.{DistinguishedNameResolverImpl, LdapAuthenticationServiceImpl}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

//noinspection SpellCheckingInspection
class AuthenticationRequestHandlerSpec extends AnyFlatSpec with Matchers with ScalatestRouteTest {

  // the integration tests uses a public reachable LDAP requestBody server
  // https://www.forumsys.com/tutorials/integration-how-to/ldap/online-ldap-test-server/
  // Server: ldap.forumsys.com
  // Port: 389
  // Bind DN: cn=read-only-admin,dc=example,dc=com
  // Bind Password: password
  private val serverUrl = "ldap://ldap.forumsys.com:389"
  private val usernameAttribute = "cn"
  private val searchBase = "dc=example,dc=com"
  private val username = "read-only-admin"
  private val password = "password"
  private val ldapAuthenticationService = new LdapAuthenticationServiceImpl(
    serverUrl,
    new DistinguishedNameResolverImpl(usernameAttribute, searchBase)
  )

  private val handler: RequestHandler = new AuthenticationRequestHandler(ldapAuthenticationService)

  it should "return 200 (OK) for valid credentials" in {
    // Stimulation
    Post("/").withEntity(ContentTypes.`application/json`, toJson(username, password)) ~> handler.getRoute ~> check {
      // Test
      status shouldEqual StatusCodes.OK
    }
  }

  it should "return 401 (Unauthorized) for invalid credentials" in {
    val invalidUsername = appendCurrentSystemTime(username)
    val invalidPassword = appendCurrentSystemTime(password)

    // Precondition
    Post("/").withEntity(ContentTypes.`application/json`, toJson(username, password)) ~> handler.getRoute ~> check {
      status shouldEqual StatusCodes.OK
    }

    // Stimulation 1
    Post("/").withEntity(ContentTypes.`application/json`, toJson(invalidUsername, password)) ~> handler.getRoute ~> check {
      // Test 1
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 2
    Post("/").withEntity(ContentTypes.`application/json`, toJson(username, invalidPassword)) ~> handler.getRoute ~> check {
      // Test 2
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 3
    Post("/").withEntity(ContentTypes.`application/json`, toJson(invalidUsername, invalidPassword)) ~> handler.getRoute ~> check {
      // Test 3
      status shouldEqual StatusCodes.Unauthorized
    }
  }

  it should "return 401 (Unauthorized) for missing password" in {
    Seq("", "    ").foreach(x => {
      Post("/").withEntity(ContentTypes.`application/json`, toJson(username, x)) ~> handler.getRoute ~> check {
        status shouldEqual StatusCodes.Unauthorized
      }
    })
  }

  private def appendCurrentSystemTime(string: String): String = string + System.currentTimeMillis()

  private def toJson(username: String, password: String): String = {
    s"""
       |{
       |  "username": "$username",
       |  "password": "$password"
       |}
    """.stripMargin
  }
}