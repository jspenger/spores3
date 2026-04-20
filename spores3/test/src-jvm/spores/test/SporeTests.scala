package spores.test

import utest.*

import spores.v03.*
import spores.v03.given


object SporeTests extends TestSuite {

  val tests = Tests {

    test("testWithoutEnv") {
      val b = Spore() { (x: Int) => x + 2 }
      val res = b(3)
      assert(res == 5)
    }

    test("testWithoutEnv2") {
      def fun(s: Spore1[Nothing, Int, Int]): Unit = {}

      val s = Spore() { (x: Int) => x + 2 }

      fun(s)

      val res = s(3)
      assert(res == 5)
    }

    test("testWithoutEnvWithType") {
      val s: Spore1[Nothing, Int, Int] = Spore() {
        (x: Int) => x + 2
      }
      val res = s(3)
      assert(res == 5)
    }

    // // FIXME: Could not infer type for parameter x of anonymous function
    // test("testWithoutEnvWithType1") {
    //   val s: Spore1[Nothing, Int, Int] = Spore() {
    //     x => x + 2
    //   }
    //   val res = s(3)
    //   assert(res == 5)
    // }

    /* the following does not compile:
  [error] -- [E007] Type Mismatch Error: [...]/BlockTests.scala:37:61
  [error] 37 |    val s: Spore[Int, Int] { type Env = Nothing } = Spore(y) {
  [error]    |                                                    ^
  [error]    |             Found:    com.phaller.blocks.Spore[Int, Int]{Env = Int}
  [error]    |             Required: com.phaller.blocks.Spore[Int, Int]{Env = Nothing}
  [error] 38 |      (x: Int) => x + 2 + y
  [error] 39 |    }
    */
    /*test("testWithoutEnvWithType1") {
      val y = 5
      val s: Spore[Int, Int] { type Env = Nothing } = Spore(y) {
        (x: Int) => x + 2 + y
      }
      val res = s(3)
      assert(res == 5)
    }*/

    test("testWithEnv") {
      val y = 5
      val s = Spore(y) {
        (x: Int) => x + y
      }
      val res = s(10)
      assert(res == 15)
    }

    /*
  [error] -- Error: [...]/BlockTests.scala:83:35
  [error] 83 |      (x: Int) => x + y + z
  [error]    |                           ^
  [error]    |Invalid capture of variable `z`. Add it to the capture list or use `*` to capture all by default.
    */
    /*test("testWithEnvInvalidCapture") {
      val y = 5
      val z = 6
      val s = Spore(y) {
        (x: Int) => x + y + z
      }
      val res = s(10)
      assert(res == 21)
    }*/

    test("testWithEnv2") {
      val str = "anonymous function"
      val s: Spore1[String, Int, Int] = Spore(str) {
        (x: Int) => x + str.length
      }
      val res = s(10)
      assert(res == 28)
    }

    test("testWithEnvTuple") {
      val str = "anonymous function"
      val i = 5

      val s: Spore1[(String, Int), Int, Int] = Spore.apply(str, i) {
        (x: Int) => x + str.length - i
      }

      val res = s(10)
      assert(res == 23)
    }

    test("testWithEnvParamUntupling") {
      val str = "anonymous function"
      val i = 5

      val s = Spore.apply(str, i) {
        (x: Int) => x + str.length - i
      }

      val res = s(10)
      assert(res == 23)
    }

    test("testWithEnvWithType") {
      val y = 5
      val s: Spore1[Int, Int, Int] = Spore(y) {
        (x: Int) => x + y
      }
      val res = s(11)
      assert(res == 16)
    }

    test("testThunk") {
      val x = 5
      val t = Spore(x) { () =>
        x + 7
      }
      val res = t()
      assert(res == 12)
    }

    test("testNestedWithoutEnv") {
      val s = Spore() {
        (x: Int) =>
          val s2 = Spore() { (y: Int) => y - 1 }
          s2(x) + 2
      }
      val res = s(3)
      assert(res == 4)
    }

    test("testNestedWithEnv1") {
      val z = 5

      val s = Spore(z) {
        (x: Int) =>
          val s2 = Spore(z) { (y: Int) => z + y - 1 }
          s2(x) + 2
      }
      val res = s(3)
      assert(res == 9)
    }

    test("testNestedWithEnv2") {
      val z = 5
      val w = 6

      val s = Spore(w, z) {
        (x: Int) =>
          val s2 = Spore(z) { (y: Int) => z + y - 1 }
          s2(x) + 2 - w
      }

      val res = s(3)
      assert(res == 3)
    }

    test("testLocalClasses") {
      val x = 5

      val s = Spore(x) { (y: Int) =>
        class Local2 { def m() = y }
        class Local(p: Int)(using loc: Local2) {
          val fld = x + p
        }

        given l2: Local2 = new Local2
        val l = new Local(y + 1)
        l.fld
      }

      val res = s(3)
      assert(res == 9)
    }
  }

}
