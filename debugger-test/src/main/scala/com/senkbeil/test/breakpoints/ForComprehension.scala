package com.senkbeil.test.breakpoints

/**
 * Provides test of iterating through a while loop.
 *
 * @note Should have a class name of
 *       com.senkbeil.test.breakpoints.ForComprehension
 */
object ForComprehension {
  def main(args: Array[String]) = {
    var count = 0

    for (i <- 1 to 10) {
      count = i // Verify that this is reached via breakpoint 10 times
    }
  }
}
