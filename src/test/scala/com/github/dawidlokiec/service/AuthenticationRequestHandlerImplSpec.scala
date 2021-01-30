package com.github.dawidlokiec.service

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.dawidlokiec.server.dip.RequestHandler
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AuthenticationRequestHandlerImplSpec extends AnyFlatSpec with Matchers with ScalatestRouteTest {

  // the integration tests uses a public reachable LDAP test server
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

  private val handler: RequestHandler = new AuthenticationRequestHandlerImpl(ldapAuthenticationService)

  "GET /?username=<username>&password=<password>" should "return 200 (OK) for valid credentials" in {
    // Stimulation
    Get(s"/?username=$username&password=$password") ~> handler.getRoute ~> check {
      // Test
      status shouldEqual StatusCodes.OK
    }
  }

  "GET /?username=<username>&password=<password>" should "return 401 (Unauthorized ) for invalid credentials" in {
    // Preperations
    val invalidUsername = appendCurrentSystemTime(username)
    val invalidPassword = appendCurrentSystemTime(password)

    // Precondition
    Get(s"/?username=$username&password=$password") ~> handler.getRoute ~> check {
      status shouldEqual StatusCodes.OK
    }

    // Stimulation 1
    Get(s"/?username=$invalidUsername&password=$password") ~> handler.getRoute ~> check {
      // Test 1
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 2
    Get(s"/?username=$username&password=$invalidPassword") ~> handler.getRoute ~> check {
      // Test 2
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 3
    Get(s"/?username=$invalidUsername&password=$invalidPassword") ~> handler.getRoute ~> check {
      // Test 3
      status shouldEqual StatusCodes.Unauthorized
    }
  }

  "an empty password" should "return status code unauthorized" in {
    // Stimulation 1
    val emptyString = ""
    Get(s"/?username=$username&password=$emptyString") ~> handler.getRoute ~> check {
      // Test 1
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 2
    Get(s"/?username=$username&password=") ~> handler.getRoute ~> check {
      // Test 2
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 3
    Get(s"/?username=$username&password") ~> handler.getRoute ~> check {
      // Test 3
      status shouldEqual StatusCodes.Unauthorized
    }
  }

  private def appendCurrentSystemTime(string: String): String = string + System.currentTimeMillis()
}