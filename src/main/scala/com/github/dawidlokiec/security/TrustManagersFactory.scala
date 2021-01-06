package com.github.dawidlokiec.security

/**
 * This object contains a factory method to create trust managers for a given key store.
 */
object TrustManagersFactory {

  import javax.net.ssl.{KeyManagerFactory, TrustManager, TrustManagerFactory}

  /**
   * Creates trust managers for the passed key store.
   *
   * @param keyStore the key store.
   * @return an array of trust managers.
   */
  def apply(keyStore: java.security.KeyStore): Array[TrustManager] = {
    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm)
    tmf.init(keyStore)
    tmf.getTrustManagers
  }

}