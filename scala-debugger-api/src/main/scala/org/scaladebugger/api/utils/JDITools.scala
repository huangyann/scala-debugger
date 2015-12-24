package org.scaladebugger.api.utils

import java.io.File
import java.net.URLClassLoader

/**
 * Exposes utility methods related to the Java Debugger Interface.
 */
object JDITools extends JDITools

/**
 * Contains utility methods related to the Java Debugger Interface.
 */
class JDITools private[utils] extends JDILoader with Logging {
  /**
   * Converts a class string to a file string.
   *
   * @example org.senkbeil.MyClass becomes org/senkbeil/MyClass.scala
   *
   * @param classString The class string to convert
   *
   * @return The resulting file string
   */
  def scalaClassStringToFileString(classString: String) =
    classString.replace('.', java.io.File.separatorChar) + ".scala"

  /**
   * Retrieves a JVM classpath string that contains the current classpath.
   *
   * @return The classpath as a string
   */
  def jvmClassPath: String = getSystemClassLoader match {
    case u: URLClassLoader =>
      u.getURLs.map(_.getPath).map(new File(_)).mkString(getPathSeparator)
    case _ => getJavaClassPath
  }

  /**
   * Retrieves the system classloader.
   *
   * @return The system classloader instance
   */
  override protected def getSystemClassLoader: ClassLoader =
    ClassLoader.getSystemClassLoader

  /**
   * Retrieves the system property for path.separator.
   *
   * @return The string representing the 'path.separator' system property
   */
  protected def getPathSeparator: String = System.getProperty("path.separator")

  /**
   * Retrieves the system property for java.class.path.
   *
   * @return The string representing the 'java.class.path' system property
   */
  protected def getJavaClassPath: String = System.getProperty("java.class.path")

  /**
   * Spawns a new Scala process using the provided class name as the entrypoint.
   *
   * @note Assumes that Scala is available on the path!
   *
   * @param className The name of the class to use as the entrypoint for the
   *                  Scala process
   * @param port The port to use for the Scala process to listen on
   * @param hostname Optional hostname to use for the Scala process to listen on
   * @param server Whether or not to launch the process as a server waiting for
   *               a debugger connection or a client connecting to a listening
   *               debugger
   * @param suspend Whether or not to start the process suspended until a
   *                debugger attaches to it or it attaches to a debugger
   * @param args The collection of arguments to pass to the Scala process
   *
   * @return The created Scala process
   */
  def spawn(
    className: String,
    port: Int,
    hostname: String = "",
    server: Boolean = true,
    suspend: Boolean = false,
    args: Seq[String] = Nil
  ): Process = {
    val jdwpString = generateJdwpString(
      port = port,
      hostname = hostname,
      suspend = suspend,
      server = server
    )

    val jdiProcess = newJDIProcess()
    jdiProcess.setJdwpString(jdwpString)
    jdiProcess.setClassPath(jvmClassPath)
    jdiProcess.setClassName(className)
    jdiProcess.setDirectory(getUserDir)

    jdiProcess.start()
  }

  /**
   * Creates a new JDI process instance.
   *
   * @return The new JDI process instance
   */
  protected def newJDIProcess(): JDIProcess = new JDIProcess

  /**
   * Retrieves the system property for user.dir.
   *
   * @return The string representing the 'user.dir' system property
   */
  protected def getUserDir: String = System.getProperty("user.dir")

  /**
   * Generates a JDWP string for use when launching JVMs.
   *
   * @param port The port used to connect to a debugger or listen for debugger
   *             connections
   * @param transport The means of communication (defaults to dt_socket)
   * @param server If true, indicates that the target JVM should run as a
   *               server listening on the provided port for debugger
   *               connections; if false, indicates that the target JVM should
   *               connect to a debugger using the provided port
   * @param suspend If true, indicates that the target JVM should start up
   *                suspended until a connection with a debugger has been
   *                established
   * @param hostname If provided, used as the hostname to connect or bind
   *                 to depending on the server flag
   *
   * @return The string representing the JDWP settings
   */
  def generateJdwpString(
    port: Int,
    transport: String = "dt_socket",
    server: Boolean = true,
    suspend: Boolean = false,
    hostname: String = ""
  ): String = {
    val serverString = if (server) "y" else "n"
    val suspendString = if (suspend) "y" else "n"
    val hostnameString = if (hostname.nonEmpty) hostname + ":" else ""
    val addressString = hostnameString + port.toString

    "-agentlib:jdwp=" + Seq(
      Seq("transport", transport).mkString("="),
      Seq("server", serverString).mkString("="),
      Seq("suspend", suspendString).mkString("="),
      Seq("address", addressString).mkString("=")
    ).mkString(",")
  }
}