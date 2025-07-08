package spores

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test
import org.junit.Assert.*

import spores.given
import spores.*
import spores.TestUtils.*

object SporeClassBuilderTests:
  class Thunk[T] extends SporeClassBuilder[T => () => T](t => () => t)

  class Predicate extends SporeClassBuilder[Int => Boolean](x => x > 10)

  class FilterWithTypeParam[T] extends SporeClassBuilder[Spore[T => Boolean] => T => Option[T]]({ env => x => if env.unwrap().apply(x) then Some(x) else None })

  class Flatten[T] extends SporeClassBuilder[List[List[T]] => List[T]](x => x.flatten)

  object NestedBuilder:
    class Predicate extends SporeClassBuilder[Int => Boolean](x => x > 10)

@RunWith(classOf[JUnit4])
class SporeClassBuilderTests:
  import SporeClassBuilderTests.*

  @Test
  def testSporeClassBuilderPack(): Unit =
    val packed = new Predicate().pack()
    val predicate = packed.unwrap()
    assertTrue(predicate(11))
    assertFalse(predicate(9))

  @Test
  def testSporeClassBuilderWithEnv(): Unit =
    val packed = new Thunk[Int].pack().withEnv(10)
    val thunk = packed.unwrap()
    assertEquals(10, thunk())

  @Test
  def testSporeClassBuilderWithTypeParam(): Unit =
    val packed = new Flatten[Int].pack()
    val flatten = packed.unwrap()
    val nestedList = List(List(1), List(2), List(3))
    assertEquals(nestedList.flatten, flatten(nestedList))

  @Test
  def testHigherLevelSporeClassBuilder(): Unit =
    val packed = new FilterWithTypeParam[Int].pack()
    val filter = packed.unwrap()
    val predicate = new Predicate().pack()
    assertEquals(Some(11), filter(predicate)(11))
    assertEquals(None, filter(predicate)(9))

  @Test
  def testSporeClassBuilderReadWriter(): Unit =
    val json = """{"$type":"spores.Packed.PackedClass","fun":"spores.SporeClassBuilderTests$Predicate"}"""

    val packed = upickle.default.write(new Predicate().pack())
    assertEquals(json, packed)

    val loaded = upickle.default.read[Spore[Int => Boolean]](json).unwrap()
    assertTrue(loaded(11))
    assertFalse(loaded(9))

  @Test
  def testSporeClassBuilderWithTypeParamReadWriter(): Unit =
    val json = """{"$type":"spores.Packed.PackedClass","fun":"spores.SporeClassBuilderTests$Flatten"}"""

    val packed = upickle.default.write(new Flatten[Int].pack())
    assertEquals(json, packed)

    val loaded = upickle.default.read[Spore[List[List[Int]] => List[Int]]](json).unwrap()
    val nestedList = List(List(1), List(2), List(3))
    assertEquals(nestedList.flatten, loaded(nestedList))

  @Test
  def testSporeClassBuilderWithEnvReadWriter(): Unit =
    val json = """{"$type":"spores.Packed.PackedWithEnv","packed":{"$type":"spores.Packed.PackedClass","fun":"spores.SporeClassBuilderTests$FilterWithTypeParam"},"packedEnv":{"$type":"spores.Packed.PackedEnv","env":"{\"$type\":\"spores.Packed.PackedClass\",\"fun\":\"spores.SporeClassBuilderTests$Predicate\"}","rw":{"$type":"spores.Packed.PackedObject","fun":"spores.ReadWriters$SporeRW$"}}}"""

    val predicate = new Predicate().pack()
    val filter = new FilterWithTypeParam[Int].pack().withEnv(predicate)
    val packed = upickle.default.write(filter)
    assertEquals(json, packed)

    val loaded = upickle.default.read[Spore[Int => Option[Int]]](json).unwrap()
    assertEquals(Some(11), loaded(11))
    assertEquals(None, loaded(9))
