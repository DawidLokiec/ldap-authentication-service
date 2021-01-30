package com.github.dawidlokiec.service

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.dawidlokiec.server.dip.RequestHandler
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
/*
class AuthenticationRequestHandlerImplSpec extends AnyFlatSpec with Matchers with ScalatestRouteTest {

  private val ldapAuthenticationService: LdapAuthenticationService = new LdapAuthenticationServiceImpl(
    "ldap://host:389", new DistinguishedNameResolverImpl("cn", "ou=people,dc=example,dc=com")
  )
  private val handler: RequestHandler = new AuthenticationRequestHandlerImpl(ldapAuthenticationService)

  "GET /?username=<username>&password=<password>" should "trigger authentication" in {
    // Preparations
    val testUsername = "testUsername" + System.currentTimeMillis()
    val testPassword = "testPassword" + System.currentTimeMillis()

    // Stimulation
    Get(s"/?username=$testUsername&password=$testPassword") ~> handler.getRoute ~> check {
      // Test
      status shouldEqual StatusCodes.InternalServerError
    }
  }

  "an empty password" should "return status code unauthorized" in {
    // Preparations
    val testUsername = "testUsername" + System.currentTimeMillis()
    val testPassword = ""

    // Stimulation 1
    Get(s"/?username=$testUsername&password=$testPassword") ~> handler.getRoute ~> check {
      // Test 1
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 2
    Get(s"/?username=$testUsername&password=") ~> handler.getRoute ~> check {
      // Test 2
      status shouldEqual StatusCodes.Unauthorized
    }

    // Stimulation 3
    Get(s"/?username=$testUsername&password") ~> handler.getRoute ~> check {
      // Test 3
      status shouldEqual StatusCodes.Unauthorized
    }
  }

}
*/