package com.github.dawidlokiec.service

import org.scalatest.flatspec.AsyncFlatSpec

//noinspection SpellCheckingInspection
class LdapAuthenticationServiceImplSpec extends AsyncFlatSpec {

  import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

  implicit val globalExecutionContext: ExecutionContextExecutor = ExecutionContext.global
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

  it should "return false for a missing password" in {
    ldapAuthenticationService.authenticate(username, "").map(result => assert(!result))
    ldapAuthenticationService.authenticate(username, " ").map(result => assert(!result))
    ldapAuthenticationService.authenticate(username, "  ").map(result => assert(!result))
    ldapAuthenticationService.authenticate(username, null).map(result => assert(!result))
  }

  it should "return true for valid credentials" in {
    ldapAuthenticationService.authenticate(username, password).map(result => assert(result))
  }

  it should "return false for invalid credentials" in {
    // preparations
    val invalidUsername = appendCurrentSystemTime(username)
    val invalidPassword = appendCurrentSystemTime(password)

    // Test 1: valid invalid username & valid password
    ldapAuthenticationService.authenticate(invalidUsername, password).map(result => assert(!result))
    // Test 2: valid username & invalid password
    ldapAuthenticationService.authenticate(username, invalidPassword).map(result => assert(!result))
    // Test 3: invalid username & password
    ldapAuthenticationService.authenticate(invalidUsername, invalidPassword).map(result => assert(!result))
  }

  private def appendCurrentSystemTime(string: String): String = string + System.currentTimeMillis()
}
