package org.senkbeil.debugger.api.virtualmachines

import org.scalamock.scalatest.MockFactory
import org.scalatest.{ParallelTestExecution, Matchers, FunSpec}
import org.senkbeil.debugger.api.lowlevel.breakpoints.{PendingBreakpointSupport, DummyBreakpointManager}
import org.senkbeil.debugger.api.lowlevel.classes.{PendingClassUnloadSupport, DummyClassUnloadManager, PendingClassPrepareSupport, DummyClassPrepareManager}
import org.senkbeil.debugger.api.lowlevel.events.{PendingEventHandlerSupport, DummyEventManager}
import org.senkbeil.debugger.api.lowlevel.exceptions.{PendingExceptionSupport, DummyExceptionManager}
import org.senkbeil.debugger.api.lowlevel.methods.{PendingMethodExitSupport, DummyMethodExitManager, PendingMethodEntrySupport, DummyMethodEntryManager}
import org.senkbeil.debugger.api.lowlevel.monitors._
import org.senkbeil.debugger.api.lowlevel.steps.{PendingStepSupport, DummyStepManager}
import org.senkbeil.debugger.api.lowlevel.threads.{PendingThreadStartSupport, DummyThreadStartManager, PendingThreadDeathSupport, DummyThreadDeathManager}
import org.senkbeil.debugger.api.lowlevel.vm.{PendingVMDeathSupport, DummyVMDeathManager}
import org.senkbeil.debugger.api.lowlevel.watchpoints.{PendingModificationWatchpointSupport, DummyModificationWatchpointManager, PendingAccessWatchpointSupport, DummyAccessWatchpointManager}
import org.senkbeil.debugger.api.profiles.ProfileManager

class DummyScalaVirtualMachineSpec extends FunSpec with Matchers
  with ParallelTestExecution with MockFactory
{
  private val mockProfileManager = mock[ProfileManager]
  private val dummyScalaVirtualMachine = new DummyScalaVirtualMachine(
    mockProfileManager
  )

  describe("DummyScalaVirtualMachine") {
    describe("#initialize") {
      it("should do nothing") {
        dummyScalaVirtualMachine.initialize()
      }
    }

    describe("#isStarted") {
      it("should return false") {
        val expected = false

        val actual = dummyScalaVirtualMachine.isStarted

        actual should be (expected)
      }
    }

    describe("#lowlevel") {
      it("should return a container of dummy managers") {
        val managerContainer = dummyScalaVirtualMachine.lowlevel

        // TODO: Provide a less hard-coded test (this was pulled from manager container spec)
        managerContainer.accessWatchpointManager shouldBe a [DummyAccessWatchpointManager]
        managerContainer.accessWatchpointManager shouldBe a [PendingAccessWatchpointSupport]
        managerContainer.breakpointManager shouldBe a [DummyBreakpointManager]
        managerContainer.breakpointManager shouldBe a [PendingBreakpointSupport]
        managerContainer.classManager should be (null)
        managerContainer.classPrepareManager shouldBe a [DummyClassPrepareManager]
        managerContainer.classPrepareManager shouldBe a [PendingClassPrepareSupport]
        managerContainer.classUnloadManager shouldBe a [DummyClassUnloadManager]
        managerContainer.classUnloadManager shouldBe a [PendingClassUnloadSupport]
        managerContainer.eventManager shouldBe a [DummyEventManager]
        managerContainer.eventManager shouldBe a [PendingEventHandlerSupport]
        managerContainer.exceptionManager shouldBe a [DummyExceptionManager]
        managerContainer.exceptionManager shouldBe a [PendingExceptionSupport]
        managerContainer.methodEntryManager shouldBe a [DummyMethodEntryManager]
        managerContainer.methodEntryManager shouldBe a [PendingMethodEntrySupport]
        managerContainer.methodExitManager shouldBe a [DummyMethodExitManager]
        managerContainer.methodExitManager shouldBe a [PendingMethodExitSupport]
        managerContainer.modificationWatchpointManager shouldBe a [DummyModificationWatchpointManager]
        managerContainer.modificationWatchpointManager shouldBe a [PendingModificationWatchpointSupport]
        managerContainer.monitorContendedEnteredManager shouldBe a [DummyMonitorContendedEnteredManager]
        managerContainer.monitorContendedEnteredManager shouldBe a [PendingMonitorContendedEnteredSupport]
        managerContainer.monitorContendedEnterManager shouldBe a [DummyMonitorContendedEnterManager]
        managerContainer.monitorContendedEnterManager shouldBe a [PendingMonitorContendedEnterSupport]
        managerContainer.monitorWaitedManager shouldBe a [DummyMonitorWaitedManager]
        managerContainer.monitorWaitedManager shouldBe a [PendingMonitorWaitedSupport]
        managerContainer.monitorWaitManager shouldBe a [DummyMonitorWaitManager]
        managerContainer.monitorWaitManager shouldBe a [PendingMonitorWaitSupport]
        managerContainer.requestManager should be (null)
        managerContainer.stepManager shouldBe a [DummyStepManager]
        managerContainer.stepManager shouldBe a [PendingStepSupport]
        managerContainer.threadDeathManager shouldBe a [DummyThreadDeathManager]
        managerContainer.threadDeathManager shouldBe a [PendingThreadDeathSupport]
        managerContainer.threadStartManager shouldBe a [DummyThreadStartManager]
        managerContainer.threadStartManager shouldBe a [PendingThreadStartSupport]
        managerContainer.vmDeathManager shouldBe a [DummyVMDeathManager]
        managerContainer.vmDeathManager shouldBe a [PendingVMDeathSupport]
      }

      it("should return the same container each time") {
        val expected = dummyScalaVirtualMachine.lowlevel

        val actual = dummyScalaVirtualMachine.lowlevel

        actual should be (expected)
      }
    }

    describe("#uniqueId") {
      it("should return a non-empty string") {
        dummyScalaVirtualMachine.uniqueId should not be (empty)
      }

      it("should return the same id each time") {
        val expected = dummyScalaVirtualMachine.uniqueId

        val actual = dummyScalaVirtualMachine.uniqueId

        actual should be (expected)
      }
    }

    describe("#underlyingVirtualMachine") {
      it("should return null") {
        val expected = null

        val actual = dummyScalaVirtualMachine.underlyingVirtualMachine

        actual should be (expected)
      }
    }
  }
}
