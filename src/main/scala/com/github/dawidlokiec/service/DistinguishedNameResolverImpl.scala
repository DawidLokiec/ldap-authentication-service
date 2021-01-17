package com.github.dawidlokiec.service

/**
 * This class implements a method to resolve the distinguished name (DN) of an user from its username.
 *
 * @param searchBase the search base.
 */
class DistinguishedNameResolverImpl(private val searchBase: String) extends DistinguishedNameResolver {

  /**
   * Returns the distinguished name (DN) of an user.
   * Pattern: s"cn=$username,$searchBase".
   * For instance: ("alice", "ou=people,dc=wonderland,dc=in") -> "cn=alice,ou=people,dc=wonderland,dc=in".
   *
   * @param username the user's name.
   * @return the distinguished name.
   */
  override def resolve(username: String): String = s"cn=$username,$searchBase"
}