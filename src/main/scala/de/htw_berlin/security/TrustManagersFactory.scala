package de.htw_berlin.security

import java.security.KeyStore
import javax.net.ssl.{KeyManagerFactory, TrustManager, TrustManagerFactory}

object TrustManagersFactory {

  def apply(keyStore: KeyStore): Array[TrustManager] = {
    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm)
    tmf.init(keyStore)
    tmf.getTrustManagers
  }

}
