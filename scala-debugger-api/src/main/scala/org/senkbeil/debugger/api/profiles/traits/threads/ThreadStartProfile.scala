package org.senkbeil.debugger.api.profiles.traits.threads

import com.sun.jdi.event.ThreadStartEvent
import org.senkbeil.debugger.api.lowlevel.JDIArgument
import org.senkbeil.debugger.api.lowlevel.events.data.JDIEventDataResult
import org.senkbeil.debugger.api.pipelines.Pipeline
import org.senkbeil.debugger.api.pipelines.Pipeline.IdentityPipeline

import scala.util.Try

/**
 * Represents the interface that needs to be implemented to provide
 * thread start functionality for a specific debug profile.
 */
trait ThreadStartProfile {
  /** Represents a thread start event and any associated data. */
  type ThreadStartEventAndData = (ThreadStartEvent, Seq[JDIEventDataResult])

  /**
   * Constructs a stream of thread start events.
   *
   * @param extraArguments The additional JDI arguments to provide
   *
   * @return The stream of thread start events
   */
  def onThreadStart(
    extraArguments: JDIArgument*
  ): Try[IdentityPipeline[ThreadStartEvent]] = {
    onThreadStartWithData(extraArguments: _*).map(_.map(_._1).noop())
  }

  /**
   * Constructs a stream of thread start events.
   *
   * @param extraArguments The additional JDI arguments to provide
   *
   * @return The stream of thread start events and any retrieved data based on
   *         requests from extra arguments
   */
  def onThreadStartWithData(
    extraArguments: JDIArgument*
  ): Try[IdentityPipeline[ThreadStartEventAndData]]

  /**
   * Constructs a stream of thread start events.
   *
   * @param extraArguments The additional JDI arguments to provide
   *
   * @return The stream of thread start events
   */
  def onUnsafeThreadStart(
    extraArguments: JDIArgument*
  ): IdentityPipeline[ThreadStartEvent] = {
    onThreadStart(extraArguments: _*).get
  }

  /**
   * Constructs a stream of thread start events.
   *
   * @param extraArguments The additional JDI arguments to provide
   *
   * @return The stream of thread start events and any retrieved data based on
   *         requests from extra arguments
   */
  def onUnsafeThreadStartWithData(
    extraArguments: JDIArgument*
  ): IdentityPipeline[ThreadStartEventAndData] = {
    onThreadStartWithData(extraArguments: _*).get
  }
}