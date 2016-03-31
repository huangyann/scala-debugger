package org.scaladebugger.api.profiles.traits.info
//import acyclic.file

import com.sun.jdi.Value

import scala.util.Try

/**
 * Represents information about a value.
 */
trait ValueInfoProfile extends CommonInfoProfile {
  /**
   * Returns the JDI representation this profile instance wraps.
   *
   * @return The JDI instance
   */
  override def toJdiInstance: Value

  /**
   * Returns the type name of this value.
   *
   * @return The type name (typically a fully-qualified class name)
   */
  def typeName: String

  /**
   * Returns the value as a value local to this JVM.
   *
   * @return Success containing the value as a local instance,
   *         otherwise a failure
   */
  def tryToLocalValue: Try[Any] = Try(toLocalValue)

  /**
   * Returns the value as a value local to this JVM.
   *
   * @return The value as a local instance
   */
  def toLocalValue: Any

  /**
   * Returns whether or not this value represents a primitive.
   *
   * @return True if a primitive, otherwise false
   */
  def isPrimitive: Boolean

  /**
   * Returns whether or not this value represents an array.
   *
   * @return True if an array, otherwise false
   */
  def isArray: Boolean

  /**
   * Returns whether or not this value represents an object.
   *
   * @return True if an object, otherwise false
   */
  def isObject: Boolean

  /**
   * Returns whether or not this value represents a string.
   *
   * @return True if a string, otherwise false
   */
  def isString: Boolean

  /**
   * Returns whether or not this value is null.
   *
   * @return True if null, otherwise false
   */
  def isNull: Boolean

  /**
   * Returns the value as an object (profile).
   *
   * @return Success containing the object profile wrapping this value,
   *         otherwise a failure
   */
  def tryToObject: Try[ObjectInfoProfile] = Try(toObject)

  /**
   * Returns the value as an object (profile).
   *
   * @return The object profile wrapping this value
   */
  def toObject: ObjectInfoProfile

  /**
   * Returns the value as an array (profile).
   *
   * @return Success containing the array profile wrapping this value,
   *         otherwise a failure
   */
  def tryToArray: Try[ArrayInfoProfile] = Try(toArray)

  /**
   * Returns the value as an array (profile).
   *
   * @return The array profile wrapping this value
   */
  def toArray: ArrayInfoProfile
}
