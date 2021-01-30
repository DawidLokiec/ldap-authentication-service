package com.github.dawidlokiec.config

import com.github.dawidlokiec.config.Constants.EnvVarNameLdapUsernameAttribute
import org.scalatest.flatspec.AnyFlatSpec

class ConstantsSpec extends AnyFlatSpec {

  import Constants.{
    ActorSystemName,
    EnvVarNameKeystoreFullName,
    EnvVarNameKeystorePassword,
    EnvVarNameLdapSearchBase,
    EnvVarNameLdapServerUrl
  }

  "the environment variable for the LDAP server's URL" should "be LDAP_SERVER_URL" in {
    assert("LDAP_SERVER_URL" == EnvVarNameLdapServerUrl)
  }

  "the environment variable for the LDAP attribute that contains the username" should "be LDAP_USERNAME_ATTRIBUTE" in {
    assert("LDAP_USERNAME_ATTRIBUTE" == EnvVarNameLdapUsernameAttribute)
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
