package de.htw_berlin.f4.service

/**
 * This class implements a method to resolve the distinguished name (DN) of an user from its username.
 *
 * @param usernameAttribute the username attribute, for instance 'cn'
 * @param searchBase        the search base.
 */
class DistinguishedNameResolverImpl(
                                     private val usernameAttribute: String,
                                     private val searchBase: String
                                   ) extends DistinguishedNameResolver {
  /**
   * Returns the distinguished name (DN) of an user.
   * Pattern: s"$usernameAttribute=$username,$searchBase".
   * For instance: ("alice", "cn", "ou=people,dc=wonderland,dc=in") -> "cn=alice,ou=people,dc=wonderland,dc=in".
   *
   * @param username the user's name.
   * @return the distinguished name.
   */
  override def resolve(username: String): String = s"$usernameAttribute=$username,$searchBase"
}