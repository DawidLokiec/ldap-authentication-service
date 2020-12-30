package com.github.dawidwalczak.security

import java.security.KeyStore

import javax.net.ssl.{KeyManager, KeyManagerFactory}

object X509KeyManagersFactory {

  def apply(keyStore: KeyStore, keyStorePassword: String): Array[KeyManager] = {
    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm)
    keyManagerFactory.init(keyStore, keyStorePassword.toCharArray)
    keyManagerFactory.getKeyManagers
  }

}
