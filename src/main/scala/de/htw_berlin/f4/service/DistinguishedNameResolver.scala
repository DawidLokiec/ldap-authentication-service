package de.htw_berlin.f4.service

/**
 * Defines a method to resolve the distinguished name (DN) of an user from its username.
 * Example: Supposed the username is alice, then the method should return the following example distinguished name:
 * uid=alice,ou=people,dc=wonderland,dc=in.
 */
trait DistinguishedNameResolver {

  /**
   * Returns the distinguished name (DN) of an user.
   * For instance: "alice" -> "uid=alice,ou=people,dc=wonderland,dc=in".
   *
   * @param username the user's name.
   * @return the distinguished name.
   */
  def resolve(username: String): String
}