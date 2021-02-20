package de.htw_berlin.f4.service

import org.scalatest.flatspec.AnyFlatSpec

class DistinguishedNameResolverImplSpec extends AnyFlatSpec {

  it should "resolves a distinguished name properly" in {
    val usernameAttribute = "cn"
    val searchBase = "ou=people,dc=wonderland,dc=in"
    val username = "alice"
    val dnResolver: DistinguishedNameResolver = new DistinguishedNameResolverImpl(usernameAttribute, searchBase)
    assertResult(s"$usernameAttribute=$username,$searchBase") {
      dnResolver.resolve(username)
    }
  }
}
