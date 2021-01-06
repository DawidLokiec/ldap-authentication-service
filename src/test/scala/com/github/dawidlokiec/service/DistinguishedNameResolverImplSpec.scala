package com.github.dawidlokiec.service

import org.scalatest.flatspec.AnyFlatSpec

class DistinguishedNameResolverImplSpec extends AnyFlatSpec {

  it should "resolves a distinguished name properly" in {
    val searchBase = "ou=people,dc=wonderland,dc=in"
    val username = "alice"
    val dnResolver: DistinguishedNameResolver = new DistinguishedNameResolverImpl(searchBase)
    assertResult(s"cn=$username,$searchBase") {
      dnResolver.resolve(username)
    }
  }

}
