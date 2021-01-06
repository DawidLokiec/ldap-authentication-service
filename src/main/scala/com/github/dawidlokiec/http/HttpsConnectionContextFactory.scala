package com.github.dawidlokiec.http

import java.security.SecureRandom
import akka.http.scaladsl.{ConnectionContext, HttpsConnectionContext}
import com.github.dawidlokiec.security.{PKCS12KeyStoreFactory, TrustManagersFactory, X509KeyManagersFactory}

import java.io.FileInputStream
import javax.net.ssl.SSLContext

object HttpsConnectionContextFactory {

  // TODO refactor
  def apply(keyStoreFilename: String, keyStorePassword: String): HttpsConnectionContext = {
    val p12KeyStore = PKCS12KeyStoreFactory(new FileInputStream(keyStoreFilename), keyStorePassword)
    val sslContext: SSLContext = SSLContext.getInstance("TLS")
    sslContext.init(X509KeyManagersFactory(p12KeyStore, keyStorePassword), TrustManagersFactory(p12KeyStore), new SecureRandom)
    ConnectionContext.httpsServer(sslContext)
  }

}