package org.senkbeil.debugger.api.lowlevel.steps

import com.sun.jdi.ThreadReference
import com.sun.jdi.request.{EventRequest, EventRequestManager, StepRequest}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, Matchers, OneInstancePerTest}
import org.senkbeil.debugger.api.lowlevel.DummyOperationException
import test.JDIMockHelpers

import scala.util.{Failure, Success}

class DummyStepManagerSpec extends FunSpec with Matchers
  with OneInstancePerTest with MockFactory with JDIMockHelpers
{
  private val TestRequestId = java.util.UUID.randomUUID().toString
  private val stepManager = new DummyStepManager

  describe("DummyStepManager") {
    describe("#stepRequestListById") {
      it("should return an empty list") {
        stepManager.stepRequestListById should be (empty)
      }
    }

    describe("#stepRequestList") {
      it("should return an empty list") {
        stepManager.stepRequestList should be (empty)
      }
    }

    describe("#createStepRequestWithId without removeExistingRequests") {
      it("should return a failure of dummy operation") {
        val testThreadReference = mock[ThreadReference]
        val testSize = 0
        val testDepth = 1

        val result = stepManager.createStepRequestWithId(
          TestRequestId,
          testThreadReference,
          testSize,
          testDepth
        )

        result.isFailure should be (true)
        result.failed.get shouldBe a [DummyOperationException]
      }
    }

    describe("#createStepRequestWithId with removeExistingRequests") {
      it("should return a failure of dummy operation") {
        val testThreadReference = mock[ThreadReference]
        val testRemoveExistingRequests = false
        val testSize = 0
        val testDepth = 1

        val result = stepManager.createStepRequestWithId(
          TestRequestId,
          testRemoveExistingRequests,
          testThreadReference,
          testSize,
          testDepth
        )

        result.isFailure should be (true)
        result.failed.get shouldBe a [DummyOperationException]
      }
    }

    describe("#createStepRequest") {
      it("should return a failure of dummy operation") {
        val testThreadReference = mock[ThreadReference]
        val testSize = 0
        val testDepth = 1

        val result = stepManager.createStepRequest(
          testThreadReference,
          testSize,
          testDepth
        )

        result.isFailure should be (true)
        result.failed.get shouldBe a [DummyOperationException]
      }
    }

    describe("#hasStepRequestWithId") {
      it("should return false") {
        val expected = false

        val actual = stepManager.hasStepRequestWithId(TestRequestId)

        actual should be (expected)
      }
    }

    describe("#hasStepRequest") {
      it("should return false") {
        val expected = false
        val testThreadReference = mock[ThreadReference]

        val actual = stepManager.hasStepRequest(testThreadReference)

        actual should be (expected)
      }
    }

    describe("#getStepRequestWithId") {
      it("should return None") {
        val expected = None

        val actual = stepManager.getStepRequestWithId(TestRequestId)

        actual should be (expected)
      }
    }

    describe("#getStepRequestInfoWithId") {
      it("should return None") {
        val expected = None

        val actual = stepManager.getStepRequestInfoWithId(TestRequestId)

        actual should be (expected)
      }
    }

    describe("#getStepRequest") {
      it("should return None") {
        val expected = None
        val testThreadReference = mock[ThreadReference]

        val actual = stepManager.getStepRequest(testThreadReference)

        actual should be (expected)
      }
    }

    describe("#removeStepRequestWithId") {
      it("should return false") {
        val expected = false

        val actual = stepManager.removeStepRequestWithId(TestRequestId)

        actual should be (expected)
      }
    }

    describe("#removeStepRequest") {
      it("should return false") {
        val expected = false
        val testThreadReference = mock[ThreadReference]

        val actual = stepManager.removeStepRequest(testThreadReference)

        actual should be (expected)
      }
    }
  }
}
