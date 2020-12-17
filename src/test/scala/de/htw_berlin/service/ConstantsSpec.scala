package de.htw_berlin.service

import org.scalatest.flatspec.AnyFlatSpec

class ConstantsSpec extends AnyFlatSpec {

  "the url parameter name for the username" should "be username" in {
    assert("username" == Constants.UrlParameterNameUsername)
  }

  "the url parameter name for the password" should "be password" in {
    assert("password" == Constants.UrlParameterNamePassword)
  }

}
