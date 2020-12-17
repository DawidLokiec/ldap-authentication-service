package de.htw_berlin.http

import de.htw_berlin.http.Constants.{DefaultHttpsPort, DefaultServerIpv4Address}
import org.scalatest.flatspec.AnyFlatSpec

class ConstantsSpec extends AnyFlatSpec {

  "DefaultServerIpv4Address" must "be 0.0.0.0" in {
    assert("0.0.0.0" == DefaultServerIpv4Address)
  }

  "DefaultHttpsPort" must "be 443" in {
    assert(443 == DefaultHttpsPort)
  }

}
