package spores
package test

import utest.*

import spores.v03.*
import spores.v03.given
import spores.v03.Duplicable.duplicate


class C {
  var f: Int = 0
}

object C {
  given Duplicable[C] with {
    def duplicate(x: C): C = {
      val y = new C
      y.f = x.f
      y
    }
  }
}

object DuplicableTests extends TestSuite {

  val tests = Tests {
    test("testDuplicateInt") {
      val x = 5
      val dup = summon[Duplicable[Int]]
      assert(5 == dup.duplicate(x))
    }

    test("testDuplicateThunk") {
      val x = 5
      val b = Spore(x) { () =>
        x + 1
      }

      val b2 = duplicate(b)

      val res = b2()
      assert(6 == res)
    }

    test("testDuplicateThunkWithMutableClass") {
      val x = new C
      x.f = 4

      val b = Spore(x) { () =>
        x.f + 1
      }

      val b2 = duplicate(b)

      val res = b2()
      assert(5 == res)
      assert(b.env ne b2.env)
      assert(b.env.f == b2.env.f)
    }

    test("testDuplicatedThunkAccessesNewEnv") {
      val x = new C

      val b = Spore(x) { () =>
        x
      }

      val b2 = duplicate(b)

      val envVal = b2()

      assert(envVal != x)
    }

    test("testDuplicatedThunk1") {
      val x = new C
      x.f = 7

      val b: Spore0[C, C] = Spore(x) { () =>
        x
      }

      val b2 = duplicate(b)

      val envVal = b2()

      assert(7 == envVal.f)
      assert(envVal ne x)
    }

    test("testDuplicatedThunk2") {
      val x = new C
      x.f = 7

      val b: Spore0[C, C] = Spore(x) { () =>
        x
      }

      val b2 = duplicate(b)

      val envVal = b2()

      assert(7 == envVal.f)
      assert(envVal ne x)
    }

    test("testDuplicatedSporeNoCapture") {
      // spore does not capture anything
      val s = Spore() {
        (x: Int) => x + 2
      }
      val s2 = duplicate(s)
      val res = s2(3)
      assert(5 == res)
    }

    test("testDuplicateSporeWithEnv") {
      val x = new C
      x.f = 4

      val b = Spore(x) {
        (y: Int) => x.f + y
      }

      val b2 = duplicate(b)
      val res = b2(3)
      assert(7 == res)
    }

    test("testDuplicateSporeWithEnvGeneric") {
      def duplicateThenApply[E : Duplicable, T, R](spore: Spore1[E, T, R], arg: T): R = {
        val duplicated = duplicate(spore)
        duplicated(arg)
      }

      val x = new C
      x.f = 4

      val b = Spore(x) {
        (y: Int) => x.f + y
      }

      val res = duplicateThenApply(b, 3)
      assert(7 == res)
    }

    test("testPassingSpore") {
      def m2(s: Spore1[C, Int, Int], arg: Int): Int = {
        s(arg)
      }

      def m1(s: Spore1[C, Int, Int]): Int = {
        m2(s, 10) + 20
      }

      val x = new C
      x.f = 4

      val s = Spore(x) {
        (y: Int) => x.f + y
      }

      val res = m1(s)
      assert(34 == res)
    }

    test("testPassingSporeAndDuplicate") {
      def m2[E : Duplicable](spore: Spore1[E, Int, Int], arg: Int): Int = {
        val duplicated = duplicate(spore)
        duplicated(arg)
      }

      def m1[E : Duplicable](spore: Spore1[E, Int, Int]): Int = {
        m2(spore, 10) + 20
      }

      val x = new C
      x.f = 4

      val b = Spore(x) {
        (y: Int) => x.f + y
      }

      val res = m1(b)
      assert(34 == res)
    }

    test("testDuplicateThenApply") {
      def duplicateThenApply[E : Duplicable](s: Spore0[E, C]): C = {
        val duplicated = duplicate(s)
        duplicated()
      }

      val x = new C
      // x is a mutable instance:
      x.f = 7

      // create thunk:
      val b = Spore(x) { () =>
        x
      }

      val y = duplicateThenApply(b)
      assert(7 == y.f)
      // references are not equal:
      assert(y ne x)
    }
  }

}
