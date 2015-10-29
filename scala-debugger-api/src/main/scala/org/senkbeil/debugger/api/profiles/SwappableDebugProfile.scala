package org.senkbeil.debugger.api.profiles

import org.senkbeil.debugger.api.lowlevel.JDIArgument
import org.senkbeil.debugger.api.lowlevel.events.EventType.EventType
import org.senkbeil.debugger.api.pipelines.Pipeline
import org.senkbeil.debugger.api.profiles.traits.DebugProfile

import scala.concurrent.Future

/**
 * Represents a debug profile that allows swapping the actual profile
 * implementation underneath.
 */
trait SwappableDebugProfile extends DebugProfile {
  protected val profileManager: ProfileManager

  @volatile private var currentProfileName = ""

  /**
   * Sets the current profile to the one with the provided name.
   *
   * @param name The name of the profile
   *
   * @return The updated profile
   */
  def use(name: String): DebugProfile = {
    currentProfileName = name
    this
  }

  /**
   * Retrieves the current underlying profile.
   *
   * @return The active underlying profile
   */
  def withCurrentProfile: DebugProfile = withProfile(currentProfileName)

  /**
   * Retrieves the profile with the provided name.
   *
   * @param name The name of the profile
   *
   * @throws AssertionError If the profile is not found
   * @return The debug profile
   */
  @throws[AssertionError]
  def withProfile(name: String): DebugProfile = {
    val profile = profileManager.retrieve(name)

    assert(profile.nonEmpty, s"Profile $name does not exist!")

    profile.get
  }

  override def onThreadStartWithData(
    extraArguments: JDIArgument*
  ): Pipeline[ThreadStartEventAndData, ThreadStartEventAndData] = {
    withCurrentProfile.onThreadStartWithData(extraArguments: _*)
  }

  override def stepInWithData(
    extraArguments: JDIArgument*
  ): Future[StepEventAndData] = {
    withCurrentProfile.stepInWithData(extraArguments: _*)
  }

  override def stepOverWithData(
    extraArguments: JDIArgument*
  ): Future[StepEventAndData] = {
    withCurrentProfile.stepOverWithData(extraArguments: _*)
  }

  override def stepOutWithData(
    extraArguments: JDIArgument*
  ): Future[StepEventAndData] = {
    withCurrentProfile.stepOutWithData(extraArguments: _*)
  }

  override def onClassUnloadWithData(
    extraArguments: JDIArgument*
  ): Pipeline[ClassUnloadEventAndData, ClassUnloadEventAndData] = {
    withCurrentProfile.onClassUnloadWithData(extraArguments: _*)
  }

  override def onMonitorContendedEnteredWithData(
    extraArguments: JDIArgument*
  ): Pipeline[MonitorContendedEnteredEventAndData, MonitorContendedEnteredEventAndData] = {
    withCurrentProfile
      .onMonitorContendedEnteredWithData(extraArguments: _*)
  }

  override def onMonitorContendedEnterWithData(
    extraArguments: JDIArgument*
  ): Pipeline[MonitorContendedEnterEventAndData, MonitorContendedEnterEventAndData] = {
    withCurrentProfile
      .onMonitorContendedEnterWithData(extraArguments: _*)
  }

  override def onClassPrepareWithData(
    extraArguments: JDIArgument*
  ): Pipeline[ClassPrepareEventAndData, ClassPrepareEventAndData] = {
    withCurrentProfile.onClassPrepareWithData(extraArguments: _*)
  }

  override def onEventWithData(
    eventType: EventType,
    extraArguments: JDIArgument*
  ): Pipeline[EventAndData, EventAndData] = {
    withCurrentProfile.onEventWithData(eventType, extraArguments: _*)
  }

  override def onMethodExitWithData(
    className: String,
    methodName: String,
    extraArguments: JDIArgument*
  ): Pipeline[MethodExitEventAndData, MethodExitEventAndData] = {
    withCurrentProfile.onMethodExitWithData(
      className,
      methodName,
      extraArguments: _*
    )
  }

  override def onThreadDeathWithData(
    extraArguments: JDIArgument*
  ): Pipeline[ThreadDeathEventAndData, ThreadDeathEventAndData] = {
    withCurrentProfile.onThreadDeathWithData(extraArguments: _*)
  }

  override def onVMDeathWithData(
    extraArguments: JDIArgument*
  ): Pipeline[VMDeathEventAndData, VMDeathEventAndData] = {
    withCurrentProfile.onVMDeathWithData(extraArguments: _*)
  }

  override def onMethodEntryWithData(
    className: String,
    methodName: String,
    extraArguments: JDIArgument*
  ): Pipeline[MethodEntryEventAndData, MethodEntryEventAndData] = {
    withCurrentProfile.onMethodEntryWithData(
      className,
      methodName,
      extraArguments: _*
    )
  }

  override def onExceptionWithData(
    exceptionName: String,
    notifyCaught: Boolean,
    notifyUncaught: Boolean,
    extraArguments: JDIArgument*
  ): Pipeline[ExceptionEventAndData, ExceptionEventAndData] = {
    withCurrentProfile.onExceptionWithData(
      exceptionName,
      notifyCaught,
      notifyUncaught,
      extraArguments: _*
    )
  }

  override def onAllExceptionsWithData(
    notifyCaught: Boolean,
    notifyUncaught: Boolean,
    extraArguments: JDIArgument*
  ): Pipeline[ExceptionEventAndData, ExceptionEventAndData] = {
    withCurrentProfile.onAllExceptionsWithData(
      notifyCaught,
      notifyUncaught,
      extraArguments: _*
    )
  }

  override def onMonitorWaitedWithData(
    extraArguments: JDIArgument*
  ): Pipeline[MonitorWaitedEventAndData, MonitorWaitedEventAndData] = {
    withCurrentProfile.onMonitorWaitedWithData(extraArguments: _*)
  }

  override def onMonitorWaitWithData(
    extraArguments: JDIArgument*
  ): Pipeline[MonitorWaitEventAndData, MonitorWaitEventAndData] = {
    withCurrentProfile.onMonitorWaitWithData(extraArguments: _*)
  }

  override def onBreakpointWithData(
    fileName: String,
    lineNumber: Int,
    extraArguments: JDIArgument*
  ): Pipeline[BreakpointEventAndData, BreakpointEventAndData] = {
    withCurrentProfile.onBreakpointWithData(
      fileName,
      lineNumber,
      extraArguments: _*
    )
  }

  override def onAccessFieldWatchpointWithData(
    className: String,
    fieldName: String,
    extraArguments: JDIArgument*
  ): Pipeline[AccessWatchpointEventAndData, AccessWatchpointEventAndData] = {
    withCurrentProfile.onAccessFieldWatchpointWithData(
      className,
      fieldName,
      extraArguments: _*
    )
  }

  override def onAccessInstanceWatchpointWithData(
    instanceVarName: String,
    extraArguments: JDIArgument*
  ): Pipeline[AccessWatchpointEventAndData, AccessWatchpointEventAndData] = {
    withCurrentProfile.onAccessInstanceWatchpointWithData(
      instanceVarName,
      extraArguments: _*
    )
  }

  override def onModificationFieldWatchpointWithData(
    className: String,
    fieldName: String,
    extraArguments: JDIArgument*
  ): Pipeline[ModificationWatchpointEventAndData, ModificationWatchpointEventAndData] = {
    withCurrentProfile.onModificationFieldWatchpointWithData(
      className,
      fieldName,
      extraArguments: _*
    )
  }

  override def onModificationInstanceWatchpointWithData(
    instanceVarName: String,
    extraArguments: JDIArgument*
  ): Pipeline[ModificationWatchpointEventAndData, ModificationWatchpointEventAndData] = {
    withCurrentProfile.onModificationInstanceWatchpointWithData(
      instanceVarName,
      extraArguments: _*
    )
  }
}
