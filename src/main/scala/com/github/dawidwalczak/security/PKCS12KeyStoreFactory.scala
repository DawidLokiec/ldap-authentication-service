package com.github.dawidwalczak.security

import java.io.InputStream
import java.security.KeyStore

object PKCS12KeyStoreFactory {

  def apply(keyStoreAsInputStream: InputStream, keyStorePassword: String): KeyStore = {
    val p12KeyStore = KeyStore.getInstance("PKCS12")
    p12KeyStore.load(keyStoreAsInputStream, keyStorePassword.toCharArray)
    p12KeyStore
  }

}
