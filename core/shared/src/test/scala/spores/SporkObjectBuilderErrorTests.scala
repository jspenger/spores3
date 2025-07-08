package spores

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test
import org.junit.Assert.*

import spores.given
import spores.*
import spores.TestUtils.*

object SporeBuilderErrorTests:
  class NotObjObj extends SporeBuilder[Int => Int](x => x)

  class SomeClass:
    object NotTopLevel extends SporeBuilder[Int => Int](x => x)

  def someMethod: SporeBuilder[Int => Int] = {
    object NotTopLevel extends SporeBuilder[Int => Int](x => x)
    NotTopLevel
  }

@RunWith(classOf[JUnit4])
class SporeBuilderErrorTests:
  import SporeBuilderErrorTests.*

  @Test
  def testClassSporeBuilderError(): Unit =
    assertTrue:
      typeCheckErrors:
        """
        new NotObjObj().pack()
        """
      .contains:
        """
        The provided SporeBuilder `spores.SporeBuilderErrorTests$.NotObjObj` is not an object.
        """.trim()

    assertTrue:
      typeCheckErrors:
        """
        val notObjObj = new NotObjObj()
        notObjObj.pack()
        """
      .contains:
        """
        The provided SporeBuilder `spores.SporeBuilderErrorTests$.NotObjObj` is not an object.
        """.trim()

  @Test
  def testNotTopLevelError(): Unit =
    assertTrue:
      typeCheckErrors:
        """
        val notTopLevel = new SomeClass().NotTopLevel
        notTopLevel.pack()
        """
      .contains:
        """
        The provided SporeBuilder `spores.SporeBuilderErrorTests$.SomeClass.NotTopLevel$` is not a top-level object; its owner `SomeClass` is not a top-level object nor a package.
        """.trim()

    assertTrue:
      typeCheckErrors:
        """
        val notObject = someMethod
        notObject.pack()
        """
      .contains:
        """
        The provided SporeBuilder `spores.SporeBuilder` is not an object.
        """.trim()

    assertTrue:
      typeCheckErrors:
        """
        object Builder extends SporeBuilder[Int => String](x => x.toString.reverse)
        Builder.pack()
        """
      .exists:
        _.matches:
          raw"""
          The provided SporeBuilder `.*Builder\$$` is not a top-level object; its owner `.*` is not a top-level object nor a package.
          """.trim()
