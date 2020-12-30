package com.github.dawidwalczak.security

/**
 * This object contains a factory method to create key managers for a given keystore.
 */
object X509KeyManagersFactory {

  import javax.net.ssl.{KeyManager, KeyManagerFactory}

  /**
   * Creates key managers for the passed key store.
   *
   * @param keyStore         the key store.
   * @param keyStorePassword the key store's password.
   * @return an array of key managers.
   */
  def apply(keyStore: java.security.KeyStore, keyStorePassword: String): Array[KeyManager] = {
    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm)
    keyManagerFactory.init(keyStore, keyStorePassword.toCharArray)
    keyManagerFactory.getKeyManagers
  }

}