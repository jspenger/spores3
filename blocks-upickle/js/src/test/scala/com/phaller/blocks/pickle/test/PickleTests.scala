package com.phaller.blocks
package pickle.test

import scala.scalajs.reflect.Reflect
import scala.scalajs.reflect.annotation.EnableReflectiveInstantiation

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.phaller.blocks.Block

import upickle.default._


@EnableReflectiveInstantiation
object A {
}

@RunWith(classOf[JUnit4])
class PickleTests {

  @Test
  def testReflect(): Unit = {
    val modClass1 = Reflect.lookupLoadableModuleClass("A$")
    val modClass2 = Reflect.lookupLoadableModuleClass("com.phaller.blocks.pickle.test.A$")
    val loaded = modClass2.get.loadModule()
    assert(modClass1.isEmpty)
    assert(modClass2.nonEmpty)
    assert(loaded != null)

    val modClass3 = Reflect.lookupLoadableModuleClass("com.phaller.blocks.pickle.test.MyBlock$")
    assert(modClass3.nonEmpty)
    val loadedMyBlock = modClass3.get.loadModule().asInstanceOf[Block.Builder[Int, Int, Int]]
    assert(loadedMyBlock != null)

    val s = loadedMyBlock(12)
    val res = s(3)
    assert(res == 16)
  }

}
