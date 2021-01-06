package com.github.dawidlokiec.config

import Constants.{ActorSystemName, EnvVarNameKeystoreFullName, EnvVarNameKeystorePassword, EnvVarNameLdapSearchBase, EnvVarNameLdapServerUrl}
import org.scalatest.flatspec.AnyFlatSpec

class ConstantsSpec extends AnyFlatSpec {

  "the environment variable for the LDAP server's URL" should "be LDAP_SERVER_URL" in {
    assert("LDAP_SERVER_URL" == EnvVarNameLdapServerUrl)
  }

  "the environment variable for the LDAP search base" should "be LDAP_SEARCH_BASE" in {
    assert("LDAP_SEARCH_BASE" == EnvVarNameLdapSearchBase)
  }

  "the environment variable for the keystore location" should "be KEYSTORE_FULL_NAME" in {
    assert("KEYSTORE_FULL_NAME" == EnvVarNameKeystoreFullName)
  }

  "the environment variable for the keystore password" should "be KEYSTORE_PASSWORD" in {
    assert("KEYSTORE_PASSWORD" == EnvVarNameKeystorePassword)
  }

  "ActorSystemName" should "be ldap-authentication-service-actor-system" in {
    assert("ldap-authentication-service-actor-system" == ActorSystemName)
  }

}
