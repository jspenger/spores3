package spores.v03

import utest.*

import spores.v03.*
import spores.v03.given
import spores.v03.Spore.`*`

import spores.TestUtils.*


object SporeErrorTests extends TestSuite {

  val tests = Tests {

    test("testInvalidCaptureFreeVar") {
      assert:
        typeCheckErrorMessages:
          """
          val y = 12
          Spore() { (x: Int) => x + y }
          """
        .contains:
          """
          Invalid capture of variable `y`. Add it to the capture list or use `*` to capture all by default.
          """.trim()
    }

    test("testInvalidCaptureListMixesStar") {
      assert:
        typeCheckErrorMessages:
          """
          val y = 12
          val z = 13
          Spore(y, *, z) { (x: Int) => x + y + z }
          """
        .contains:
          """
          Invalid capture list.
          """.trim()
    }

    test("testUnusedCapture") {
      assert:
        typeCheckErrorMessages:
          """
          val y = 12
          val z = 13
          Spore(y, z) { (x: Int) => x + y }
          """
        .contains:
          """
          `z` is not captured by the spore body. Remove it from the capture list. It is a top-level variable or not used in the body.
          """.trim()
    }

    test("testInvalidCaptureThis") {
      assert:
        typeCheckErrorMessages:
          """
          class TestClass {
            val n = 1
            Spore() { (x: Int) => x + this.n }.apply(0)
          }
          (new TestClass())
          """
        .contains:
          """
          Invalid capture of `this` from outer class. Add it to the capture list or use `*` to capture all by default.
          """.trim()
    }
  }
}
