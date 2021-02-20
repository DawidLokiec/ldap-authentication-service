package de.htw_berlin.f4.config

import Constants.LDAP_USERNAME_ATTRIBUTE
import org.scalatest.flatspec.AnyFlatSpec

class ConstantsSpec extends AnyFlatSpec {

  import Constants.{
    ActorSystemName,
    KEYSTORE_FULL_NAME,
    KEYSTORE_PASSWORD,
    LDAP_SEARCH_BASE,
    LDAP_SERVER_URL
  }

  "the environment variable for the LDAP server's URL" should "be LDAP_SERVER_URL" in {
    assert("LDAP_SERVER_URL" == LDAP_SERVER_URL)
  }

  "the environment variable for the LDAP attribute that contains the username" should "be LDAP_USERNAME_ATTRIBUTE" in {
    assert("LDAP_USERNAME_ATTRIBUTE" == LDAP_USERNAME_ATTRIBUTE)
  }

  "the environment variable for the LDAP search base" should "be LDAP_SEARCH_BASE" in {
    assert("LDAP_SEARCH_BASE" == LDAP_SEARCH_BASE)
  }

  "the environment variable for the keystore location" should "be KEYSTORE_FULL_NAME" in {
    assert("KEYSTORE_FULL_NAME" == KEYSTORE_FULL_NAME)
  }

  "the environment variable for the keystore password" should "be KEYSTORE_PASSWORD" in {
    assert("KEYSTORE_PASSWORD" == KEYSTORE_PASSWORD)
  }

  "ActorSystemName" should "be ldap-authentication-service-actor-system" in {
    assert("ldap-authentication-service-actor-system" == ActorSystemName)
  }

}