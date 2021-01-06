package com.github.dawidlokiec.security

/**
 * This object contains a factory method to create a java.security.KeyStore in PKCS12 format.
 */
object PKCS12KeyStoreFactory {

  import java.security.KeyStore

  /**
   * Creates a key store in PKCS12 format.
   *
   * @param keyStoreInputStream the key store as input stream to be loaded.
   * @param keyStorePassword    the key store's password.
   * @return a key store in PKCS12 format.
   */
  def apply(keyStoreInputStream: java.io.InputStream, keyStorePassword: String): KeyStore = {
    val p12KeyStore = KeyStore.getInstance("PKCS12")
    p12KeyStore.load(keyStoreInputStream, keyStorePassword.toCharArray)
    p12KeyStore
  }

}
