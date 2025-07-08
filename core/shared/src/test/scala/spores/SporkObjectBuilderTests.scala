package spores

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test
import org.junit.Assert.*

import spores.given
import spores.*
import spores.TestUtils.*

object SporeBuilderTests:
  object Thunk extends SporeBuilder[() => Int](() => 10)

  object Predicate extends SporeBuilder[Int => Boolean](x => x > 10)

  object HigherLevelFilter extends SporeBuilder[Spore[Int => Boolean] => Int => Option[Int]]({ env => x => if env.unwrap().apply(x) then Some(x) else None })

  object PredicateCtx extends SporeBuilder[Int ?=> Boolean](summon[Int] > 10)

  object OptionMapper extends SporeBuilder[Option[Int] => Int](x => x.getOrElse(0))

  object ListReducer extends SporeBuilder[List[Int] => Int](x => x.sum)

  object NestedBuilder:
    object Predicate extends SporeBuilder[Int => Boolean](x => x > 10)

@RunWith(classOf[JUnit4])
class SporeBuilderTests:
  import SporeBuilderTests.*

  @Test
  def testSporeBuilderPack(): Unit =
    val packed = Predicate.pack()
    val predicate = packed.unwrap()
    assertTrue(predicate(11))
    assertFalse(predicate(9))

  @Test
  def testNestedSporeBuilderPack(): Unit =
    val packed = NestedBuilder.Predicate.pack()
    val predicate = packed.unwrap()
    assertTrue(predicate(11))
    assertFalse(predicate(9))

  @Test
  def testSporeBuilderThunk(): Unit =
    val packed = Thunk.pack()
    val thunk = packed.unwrap()
    assertEquals(10, thunk())

  @Test
  def testWithEnv(): Unit =
    val packed9 = Predicate.pack().withEnv(9)
    val packed11 = Predicate.pack().withEnv(11)
    assertFalse(packed9.unwrap())
    assertTrue(packed11.unwrap())

  @Test
  def testWithCtx(): Unit =
    val packed9 = PredicateCtx.pack().withCtx(9)
    val packed11 = PredicateCtx.pack().withCtx(11)
    assertFalse(packed9.unwrap())
    assertTrue(packed11.unwrap())

  @Test
  def testPackBuildHigherOrderSporeBuilder(): Unit =
    val predicate = Predicate.pack()
    val filter = HigherLevelFilter.pack().withEnv(predicate).unwrap()
    assertEquals(Some(11), filter(11))
    assertEquals(None, filter(9))

  @Test
  def testSporeReadWriter(): Unit =
    val json = """{"$type":"spores.Packed.PackedObject","fun":"spores.SporeBuilderTests$Predicate$"}"""

    val packed = upickle.default.write(Predicate.pack())
    assertEquals(json, packed)

    val loaded = upickle.default.read[Spore[Int => Boolean]](json).unwrap()
    assertTrue(loaded(11))
    assertFalse(loaded(9))

  @Test
  def testNestedSporeReadWriter(): Unit =
    val json = """{"$type":"spores.Packed.PackedObject","fun":"spores.SporeBuilderTests$NestedBuilder$Predicate$"}"""

    val packed = upickle.default.write(NestedBuilder.Predicate.pack())
    assertEquals(json, packed)

    val loaded = upickle.default.read[Spore[Int => Boolean]](json).unwrap()
    assertTrue(loaded(11))
    assertFalse(loaded(9))

  @Test
  def testSporeReadWriterWithEnv(): Unit =
    val json = """{"$type":"spores.Packed.PackedWithEnv","packed":{"$type":"spores.Packed.PackedObject","fun":"spores.SporeBuilderTests$HigherLevelFilter$"},"packedEnv":{"$type":"spores.Packed.PackedEnv","env":"{\"$type\":\"spores.Packed.PackedObject\",\"fun\":\"spores.SporeBuilderTests$Predicate$\"}","rw":{"$type":"spores.Packed.PackedObject","fun":"spores.ReadWriters$SporeRW$"}}}"""

    val predicate = Predicate.pack()
    val filter = HigherLevelFilter.pack().withEnv(predicate)
    val packed = upickle.default.write(filter)
    assertEquals(json, packed)

    val loaded = upickle.default.read[Spore[Int => Option[Int]]](json).unwrap()
    assertEquals(Some(11), loaded(11))
    assertEquals(None, loaded(9))

  @Test
  def testOptionEnvironment(): Unit =
    val packed = OptionMapper.pack().withEnv(Some(11))
    val fun = packed.unwrap()
    assertEquals(11, fun)

    val packed2 = OptionMapper.pack().withEnv(Some(11))
    val fun2 = packed2.unwrap()
    assertEquals(11, fun2)

  @Test
  def testListEnvironment(): Unit =
    val packed = ListReducer.pack().withEnv(List(1, 2, 3))
    val fun = packed.unwrap()
    assertEquals(6, fun)

    val packed2 = ListReducer.pack().withEnv(List(1, 2, 3))
    val fun2 = packed2.unwrap()
    assertEquals(6, fun2)
