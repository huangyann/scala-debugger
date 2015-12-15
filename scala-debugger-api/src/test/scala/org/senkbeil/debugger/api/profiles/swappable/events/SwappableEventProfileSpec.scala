package org.senkbeil.debugger.api.profiles.swappable.events

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, Matchers, ParallelTestExecution}
import org.senkbeil.debugger.api.lowlevel.JDIArgument
import org.senkbeil.debugger.api.lowlevel.events.EventType.EventType
import org.senkbeil.debugger.api.profiles.ProfileManager
import org.senkbeil.debugger.api.profiles.swappable.SwappableDebugProfile
import org.senkbeil.debugger.api.profiles.traits.DebugProfile

class SwappableEventProfileSpec extends FunSpec with Matchers
  with ParallelTestExecution with MockFactory
{
  private val mockDebugProfile = mock[DebugProfile]
  private val mockProfileManager = mock[ProfileManager]

  private val swappableDebugProfile = new Object with SwappableDebugProfile {
    override protected val profileManager: ProfileManager = mockProfileManager
  }

  describe("SwappableEventProfile") {
    describe("#onEventWithData") {
      // TODO: ScalaMock is causing a stack overflow exception
      ignore("should invoke the method on the underlying profile") {
        val eventType = mock[EventType]
        val arguments = Seq(mock[JDIArgument])

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).once()

        (mockDebugProfile.onEventWithData _)
          .expects(eventType, arguments).once()

        swappableDebugProfile.onEventWithData(eventType, arguments: _*)
      }

      it("should throw an exception if there is no underlying profile") {
        val eventType = mock[EventType]
        val arguments = Seq(mock[JDIArgument])

        (mockProfileManager.retrieve _).expects(*).returning(None).once()

        intercept[AssertionError] {
          swappableDebugProfile.onEventWithData(eventType, arguments: _*)
        }
      }
    }
  }
}
