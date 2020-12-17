package de.htw_berlin.service

import org.scalatest.flatspec.AnyFlatSpec

class LdapAuthenticationServiceImplSpec extends AnyFlatSpec {

  it should "return false for a missing password" in {
    val ldapAuthenticationService = new LdapAuthenticationServiceImpl(
      "ldap://host:389",
      new DistinguishedNameResolverImpl("ou=people,dc=example,dc=com")
    )
    val testUsername = "alice"

    assertResult(false) {
      ldapAuthenticationService.authenticate(testUsername, "")
    }

    assertResult(false) {
      ldapAuthenticationService.authenticate(testUsername, null)
    }
  }

  // TODO integrations tests

}
