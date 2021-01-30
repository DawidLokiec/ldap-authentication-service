package com.github.dawidlokiec.service

import org.scalatest.flatspec.AnyFlatSpec

class LdapAuthenticationServiceImplSpec extends AnyFlatSpec {

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
    Seq(null, "", " ", "    ").foreach(x => assertResult(false) {
      ldapAuthenticationService.authenticate(username, x)
    })
  }

  it should "return true for valid credentials" in {
    assert(ldapAuthenticationService.authenticate(username, password))
  }

  it should "return false for invalid credentials" in {
    // preparations
    val invalidUsername = appendCurrentSystemTime(username)
    val invalidPassword = appendCurrentSystemTime(password)

    // precondition
    assert(ldapAuthenticationService.authenticate(username, password))

    // Test 1: valid invalid username & valid password
    assertResult(false) {
      ldapAuthenticationService.authenticate(invalidUsername, password)
    }

    // Test 2: valid username & invalid password
    assertResult(false) {
      ldapAuthenticationService.authenticate(username, invalidPassword)
    }

    // Test 3: invalid username & password
    assertResult(false) {
      ldapAuthenticationService.authenticate(invalidUsername, invalidPassword)
    }
  }

  private def appendCurrentSystemTime(string: String): String = string + System.currentTimeMillis()
}
