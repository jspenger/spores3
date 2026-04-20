package spores.sample

import upickle.default.*

import spores.v03.*
import spores.v03.given


object SporeLocallyExamples {

  def main(args: Array[String]): Unit = {
    locally {
      // Spore1 with Nothing env
      val spore     = Spore() { (x: Int) => x + 1 }
      val className = spore.className
      val spore2    = Spore.fromClassName1[Int, Int](className)
      val spore3    = spore.copy()
      val spore4    = Duplicable.duplicate(spore)
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Int => Int]](written).get()
      assert(className == "spores.sample.SporeLocallyExamples$anon$1")
      assert(spore.hasEnv == false)
      assert(spore(11) == 12)
      assert(spore2(11) == 12)
      assert(spore3(11) == 12)
      assert(unpickled(11) == 12)
      assert(spore4(11) == 12)
      assert(written == """{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$1"}""")
      println(s"Spore class name: $className, env: Nothing -> ${spore(11)}")
    }

    locally {
      // Spore1 with Int env
      val y         = 12
      val spore     = Spore(y) { (x: Int) => x + y }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName1[Int, Int, Int](className, env)
      val spore3    = spore.copy(13)
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Int => Int]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$2")
      assert(env == 12)
      assert(spore.hasEnv == true)
      assert(spore3.env == 13)
      assert(spore(11) == 23)
      assert(spore2(11) == 23)
      assert(spore3(11) == 24)
      assert(unpickled(11) == 23)
      assert(spore4(11) == 23)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$2"},"env":{"tag":"Val","ev":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"},"value":12}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(11)}")
    }

    locally {
      // Spore1 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { (x: Int) => x + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName1[(Int, Int), Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Int => Int]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$3")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(11) == 36)
      assert(spore2(11) == 36)
      assert(spore3(11) == 61)
      assert(unpickled(11) == 36)
      assert(spore4(11) == 36)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$3"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(11)}")
    }

    locally {
      // Spore1 with (Int, Int) env using capture-all mode
      import Spore.`*`
      val y         = 12
      val z         = 13
      val spore     = Spore(*) { (x: Int) => x + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName1[(Int, Int), Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Int => Int]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$4")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(11) == 36)
      assert(spore2(11) == 36)
      assert(spore3(11) == 61)
      assert(unpickled(11) == 36)
      assert(spore4(11) == 36)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$4"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(11)}")
    }

    locally {
      // Spore1 with (Int, )^26 env
      val a=1
      val b=2
      val c=3
      val d=4
      val e=5
      val f=6
      val g=7
      val h=8
      val i=9
      val j=10
      val k=11
      val l=12
      val m=13
      val n=14
      val o=15
      val p=16
      val q=17
      val r=18
      val s=19
      val t=20
      val u=21
      val v=22
      val w=23
      val x=35
      val y=36
      val z=37
      val spore     = Spore(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z) {
        (hello: Int) => hello + a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u + v + w + x + y + z
      }
      type Env5 = (Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int)
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName1[Env5, Int, Int](className, env)
      val newEnv: Env5 = (101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126)
      val spore3    = spore.copy(newEnv)
      val expected3 = 11 + (101 to 126).sum
      assert(className == "spores.sample.SporeLocallyExamples$anon$5")
      assert(spore.env == (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 35, 36, 37))
      assert(spore.hasEnv == true)
      assert(spore3.env == (101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126))
      assert(spore(11)  == 395)
      assert(spore2(11) == 395)
      assert(spore3(11) == expected3)
      println(s"Spore class name: $className, env: $env  -> ${spore(11)}")
    }

    locally {
      // Spore1 with (Int, )^26 env using capture-all mode
      import Spore.`*`
      val a=1
      val b=2
      val c=3
      val d=4
      val e=5
      val f=6
      val g=7
      val h=8
      val i=9
      val j=10
      val k=11
      val l=12
      val m=13
      val n=14
      val o=15
      val p=16
      val q=17
      val r=18
      val s=19
      val t=20
      val u=21
      val v=22
      val w=23
      val x=35
      val y=36
      val z=37
      val spore     = Spore(*) {
        (hello: Int) => hello + a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u + v + w + x + y + z
      }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName1[Tuple, Int, Int](className, env)
      val spore3    = spore.copy(env)
      assert(className == "spores.sample.SporeLocallyExamples$anon$6")
      assert(spore.env == (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 35, 36, 37))
      assert(spore.hasEnv == true)
      assert(spore3.env == (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 35, 36, 37))
      assert(spore(11)  == 395)
      assert(spore2(11) == 395)
      assert(spore3(11) == 395)
      println(s"Spore class name: $className, env: $env  -> ${spore(11)}")
    }

    locally {
      // Spore1 with (Int, )^22 env
      val a=1
      val b=2
      val c=3
      val d=4
      val e=5
      val f=6
      val g=7
      val h=8
      val i=9
      val j=10
      val k=11
      val l=12
      val m=13
      val n=14
      val o=15
      val p=16
      val q=17
      val r=18
      val s=19
      val t=20
      val u=21
      val v=22
      val spore     = Spore(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v) {
        (hello: Int) => hello + a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u + v
      }
      type Env7 = (Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int)
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName1[Env7, Int, Int](className, env)
      val newEnv: Env7 = (101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122)
      val spore3    = spore.copy(newEnv)
      val expected3 = 11 + (101 to 122).sum
      assert(className == "spores.sample.SporeLocallyExamples$anon$7")
      assert(spore.env == (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22))
      assert(spore.hasEnv == true)
      assert(spore3.env == (101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122))
      assert(spore(11)  == 264)
      assert(spore2(11) == 264)
      assert(spore3(11) == expected3)
      println(s"Spore class name: $className, env: $env  -> ${spore(11)}")
    }

    locally {
      // Spore0 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { () => y + z + 1 }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName0[(Int, Int), Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[() => Int]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$8")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore() == 26)
      assert(spore2() == 26)
      assert(spore3() == 51)
      assert(unpickled() == 26)
      assert(spore4() == 26)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$8"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore()}")
    }

    locally {
      // Spore00 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { y + z + 1 }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName00[(Int, Int), Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function00[Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$9")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore.apply == 26)
      assert(spore2.apply == 26)
      assert(spore3.apply == 51)
      assert(unpickled.apply == 26)
      assert(spore4.apply == 26)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$9"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore.apply}")
    }

    locally {
      // Spore2 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { (x1: Int, x2: Int) => x1 + x2 + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName2[(Int, Int), Int, Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function2[Int, Int, Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$10")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(1, 2) == 28)
      assert(spore2(1, 2) == 28)
      assert(spore3(1, 2) == 53)
      assert(unpickled(1, 2) == 28)
      assert(spore4(1, 2) == 28)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$10"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(1, 2)}")
    }

    locally {
      // Spore3 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { (x1: Int, x2: Int, x3: Int) => x1 + x2 + x3 + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName3[(Int, Int), Int, Int, Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function3[Int, Int, Int, Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$11")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(1, 2, 3) == 31)
      assert(spore2(1, 2, 3) == 31)
      assert(spore3(1, 2, 3) == 56)
      assert(unpickled(1, 2, 3) == 31)
      assert(spore4(1, 2, 3) == 31)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$11"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(1, 2, 3)}")
    }

    locally {
      // Spore4 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { (x1: Int, x2: Int, x3: Int, x4: Int) => x1 + x2 + x3 + x4 + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName4[(Int, Int), Int, Int, Int, Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function4[Int, Int, Int, Int, Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$12")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(1, 2, 3, 4) == 35)
      assert(spore2(1, 2, 3, 4) == 35)
      assert(spore3(1, 2, 3, 4) == 60)
      assert(unpickled(1, 2, 3, 4) == 35)
      assert(spore4(1, 2, 3, 4) == 35)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$12"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(1, 2, 3, 4)}")
    }

    locally {
      // Spore5 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { (x1: Int, x2: Int, x3: Int, x4: Int, x5: Int) => x1 + x2 + x3 + x4 + x5 + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName5[(Int, Int), Int, Int, Int, Int, Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function5[Int, Int, Int, Int, Int, Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$13")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(1, 2, 3, 4, 5) == 40)
      assert(spore2(1, 2, 3, 4, 5) == 40)
      assert(spore3(1, 2, 3, 4, 5) == 65)
      assert(unpickled(1, 2, 3, 4, 5) == 40)
      assert(spore4(1, 2, 3, 4, 5) == 40)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$13"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(1, 2, 3, 4, 5)}")
    }

    locally {
      // Spore6 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { (x1: Int, x2: Int, x3: Int, x4: Int, x5: Int, x6: Int) => x1 + x2 + x3 + x4 + x5 + x6 + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName6[(Int, Int), Int, Int, Int, Int, Int, Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function6[Int, Int, Int, Int, Int, Int, Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$14")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(1, 2, 3, 4, 5, 6) == 46)
      assert(spore2(1, 2, 3, 4, 5, 6) == 46)
      assert(spore3(1, 2, 3, 4, 5, 6) == 71)
      assert(unpickled(1, 2, 3, 4, 5, 6) == 46)
      assert(spore4(1, 2, 3, 4, 5, 6) == 46)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$14"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(1, 2, 3, 4, 5, 6)}")
    }

    locally {
      // Spore7 with (Int, Int) env
      val y         = 12
      val z         = 13
      val spore     = Spore(y, z) { (x1: Int, x2: Int, x3: Int, x4: Int, x5: Int, x6: Int, x7: Int) => x1 + x2 + x3 + x4 + x5 + x6 + x7 + y + z }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName7[(Int, Int), Int, Int, Int, Int, Int, Int, Int, Int](className, env)
      val spore3    = spore.copy((20, 30))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function7[Int, Int, Int, Int, Int, Int, Int, Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$15")
      assert(env == (12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (20, 30))
      assert(spore(1, 2, 3, 4, 5, 6, 7) == 53)
      assert(spore2(1, 2, 3, 4, 5, 6, 7) == 53)
      assert(spore3(1, 2, 3, 4, 5, 6, 7) == 78)
      assert(unpickled(1, 2, 3, 4, 5, 6, 7) == 53)
      assert(spore4(1, 2, 3, 4, 5, 6, 7) == 53)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$15"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore(1, 2, 3, 4, 5, 6, 7)}")
    }

    locally {
      // Spore3 with (String, Int, Tuple2[Boolean, Char]) env
      val a = "Hello"
      val b = 13
      val c = Tuple2(true, 'c')
      val spore     = Spore(c, b, a) { (x1: String, x2: Int, x3: Tuple2[Boolean, Char]) => x1 + a + a + (x2 + b).toString() + a + (x3._1 && c._1).toString() + (x3._2 + c._2).toString() }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName3[((Boolean, Char), Int, String), String, Int, (Boolean, Char), String](className, env)
      val spore3    = spore.copy(((false, 'd'), 20, "ollEh"))
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function3[String, Int, (Boolean, Char), String]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$16")
      assert(env == ((true, 'c'), 13, "Hello"))
      assert(spore.hasEnv == true)
      assert(spore3.env == ((false, 'd'), 20, "ollEh"))
      assert(spore("World", 2, Tuple2(false, 'q'))  == "WorldHelloHello15Hellofalse212")
      assert(spore2("World", 2, Tuple2(false, 'q')) == "WorldHelloHello15Hellofalse212")
      assert(spore3("World", 2, Tuple2(false, 'q')) == "WorldollEhollEh22ollEhfalse213")
      assert(unpickled("World", 2, Tuple2(false, 'q')) == "WorldHelloHello15Hellofalse212")
      assert(spore4("World", 2, Tuple2(false, 'q')) == "WorldHelloHello15Hellofalse212")
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$16"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple3RW"},"env":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$BooleanRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$CharRW$"}}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$StringRW$"}},"value":[[true,"c"],13,"Hello"]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore("World", 2, Tuple2(false, 'q'))}")
    }

    locally {
      // Spore3 with Nothing env
      val spore     = Spore() { (x1: String, x2: Int, x3: Tuple2[Boolean, Char]) => x1 +(x2).toString() + (x3._1).toString() + (x3._2).toString() }
      val className = spore.className
      val spore2    = Spore.fromClassName3[String, Int, (Boolean, Char), String](className)
      val spore3    = spore.copy()
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function3[String, Int, (Boolean, Char), String]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$17")
      assert(spore.hasEnv == false)
      assert(spore("World", 2, Tuple2(false, 'q'))  == "World2falseq")
      assert(spore2("World", 2, Tuple2(false, 'q')) == "World2falseq")
      assert(spore3("World", 2, Tuple2(false, 'q')) == "World2falseq")
      assert(unpickled("World", 2, Tuple2(false, 'q')) == "World2falseq")
      assert(spore4("World", 2, Tuple2(false, 'q')) == "World2falseq")
      assert(written == """{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$17"}""")
      println(s"Spore class name: $className, env: Nothing  -> ${spore("World", 2, Tuple2(false, 'q'))}")
    }

    locally {
      // Spore4 from dead code
      object Dead {
        object Code {
          val y     = "Hello"
          val z     = 4.0f
          val spore = Spore(z, y) { (x1: String, x2: Float, x3: Int, x4: Boolean) => if x4 then x1 + y + x2.toString() + z.toString() + x3.toString() else "" }
        }
      }
      val className = "spores.sample.SporeLocallyExamples$anon$18"
      val env       = (4.01f, "olleH")
      val spore2    = Spore.fromClassName4[(Float, String), String, Float, Int, Boolean, String](className, env)
      val spore3    = spore2.copy((5.0f, "ollEh"))
      val pickle    = spore2.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function4[String, Float, Int, Boolean, String]]](written).get()
      val spore4    = Duplicable.duplicate(spore2)
      assert(spore2.env == (4.01f, "olleH"))
      assert(spore2.hasEnv == true)
      assert(spore3.env == (5.0f, "ollEh"))
      assert(spore2("World", 3.0f, 14, true) == "WorldolleH3.04.0114")
      assert(spore3("World", 3.0f, 14, true) == "WorldollEh3.05.014")
      assert(unpickled("World", 3.0f, 14, true) == "WorldolleH3.04.0114")
      assert(spore4("World", 3.0f, 14, true) == "WorldolleH3.04.0114")
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$18"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$FloatRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$StringRW$"}},"value":[4.01,"olleH"]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore2("World", 3.0f, 14, true)}")
    }

    locally {
      val spore     = Spore() { 9 }
      val className = spore.className
      val spore2    = Spore.fromClassName00[Int](className)
      val spore3    = spore.copy()
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function00[Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$19")
      assert(spore.hasEnv == false)
      assert(spore.apply  == 9)
      assert(spore2.apply == 9)
      assert(spore3.apply == 9)
      assert(unpickled.apply == 9)
      assert(spore4.apply == 9)
      assert(written == """{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$19"}""")
      println(s"Spore class name: $className, env: Nothing  -> ${spore.apply}")
    }

    locally {
      val spore     = Spore() { () => 9 }
      val className = spore.className
      val spore2    = Spore.fromClassName0[Int](className)
      val spore3    = spore.copy()
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function0[Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$20")
      assert(spore.hasEnv == false)
      assert(spore()  == 9)
      assert(spore2() == 9)
      assert(spore3() == 9)
      assert(unpickled() == 9)
      assert(spore4() == 9)
      assert(written == """{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$20"}""")
      println(s"Spore class name: $className, env: Nothing  -> ${spore()}")
    }

    locally {
      val y         = 7
      val spore     = Spore(y) { y * 2 }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName00[Int, Int](className, env)
      val spore3    = spore.copy(12)
      val pickle    = spore.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function00[Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore)
      assert(className == "spores.sample.SporeLocallyExamples$anon$21")
      assert(env == 7)
      assert(spore.hasEnv == true)
      assert(spore3.env == 12)
      assert(spore.apply  == 14)
      assert(spore2.apply == 14)
      assert(spore3.apply == 24)
      assert(unpickled.apply == 14)
      assert(spore4.apply == 14)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$21"},"env":{"tag":"Val","ev":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"},"value":7}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore.apply}")
    }

    locally {
      val y = "waii"
      object Dead {
        object Code {
          val spore = Spore(y) { (x: Int) => s"$y-$x" }
        }
      }
      val className = "spores.sample.SporeLocallyExamples$anon$22"
      val spore2    = Spore.fromClassName1[String, Int, String](className, y)
      val env       = spore2.env
      val spore3    = spore2.copy("fff")
      val pickle    = spore2.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function1[Int, String]]](written).get()
      val spore4    = Duplicable.duplicate(spore2)
      assert(spore2.env == "waii")
      assert(spore2.hasEnv == true)
      assert(spore3.env == "fff")
      assert(Dead.Code.spore(5) == "waii-5")
      assert(spore2(5) == "waii-5")
      assert(spore3(5) == "fff-5")
      assert(unpickled(5) == "waii-5")
      assert(spore4(5) == "waii-5")
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$22"},"env":{"tag":"Val","ev":{"tag":"Body","kind":0,"className":"spores.ReadWriters$StringRW$"},"value":"waii"}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore2(5)}")
    }

    locally {
      object Outer {
        val m = 15
        object Inner {
          val y     = "party"
          val spore = Spore(m, y) { (x: Int) => s"$y:${x + m}" }
        }
      }
      val className = "spores.sample.SporeLocallyExamples$anon$23"
      val spore2    = Spore.fromClassName1[(Int, String), Int, String](className, (15, "party"))
      val env       = spore2.env
      val spore3    = spore2.copy((4, "two"))
      val pickle    = spore2.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function1[Int, String]]](written).get()
      val spore4    = Duplicable.duplicate(spore2)
      assert(spore2.env == (15, "party"))
      assert(spore2.hasEnv == true)
      assert(spore3.env == (4, "two"))
      assert(Outer.Inner.spore(1) == "party:16")
      assert(spore2(1) == "party:16")
      assert(spore3(1) == "two:5")
      assert(unpickled(1) == "party:16")
      assert(spore4(1) == "party:16")
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$23"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$StringRW$"}},"value":[15,"party"]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore2(1)}")
    }

    locally {
      val y         = 10
      val spore     = Spore(y) { (x: Int) => x + y }
      val base      = spore : Spore[Int, Function1[Int, Int]]
      val spore2    = Spore.narrow(base)
      val className = spore2.className
      val spore3    = Spore.fromClassName1[Int, Int, Int](className, spore2.env)
      val pickle    = spore2.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function1[Int, Int]]](written).get()
      val spore4    = Duplicable.duplicate(spore2)
      assert(className == "spores.sample.SporeLocallyExamples$anon$24")
      assert(spore2.env == 10)
      assert(spore2.hasEnv == true)
      assert(spore2(5) == 15)
      assert(spore3(5) == 15)
      assert(unpickled(5) == 15)
      assert(spore4(5) == 15)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$24"},"env":{"tag":"Val","ev":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"},"value":10}}""")
      println(s"Spore class name: $className, env: ${spore2.env}  -> ${spore2(5)}")
    }

    locally {
      val y = 12
      val z = 13
      val w = 14
      val spore     = Spore(w, y, z) { (x: Int) => if x == 0 then Spore(w, x, y) { (a: Int) => a + w + y + x } else Spore(w, x, z) { (a: Int) => a + w + z + x } }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName1[(Int, Int, Int), Int, Spore1[(Int, Int, Int), Int, Int]](className, env)
      val spore3    = spore2.copy(env)
      val pickle    = spore2.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function1[Int, Spore1[(Int, Int, Int), Int, Int]]]](written).get()
      val spore4    = Duplicable.duplicate(spore2)
      assert(spore.env == (14, 12, 13))
      assert(spore.hasEnv == true)
      assert(spore3.env == (14, 12, 13))
      assert(spore2(0)(11) == 37)
      assert(spore2(1)(11) == 39)
      assert(unpickled(0)(11) == 37)
      assert(unpickled(1)(11) == 39)
      assert(spore4(0)(11) == 37)
      assert(spore4(1)(11) == 39)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$25"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple3RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[14,12,13]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore2(0)(11)}")
    }

    locally {
      val p = 1
      val q = 2
      val spore = Spore(p, q) { (a: Int, b: Int) => 
                    Spore(a, b, p, q) { (c: Int) =>
                      val w = 123 + a + b + c + p + q
                      Spore(w) { (x: Int) =>
                        x + w                            
                      }
                    }
                  }
      val className = spore.className
      val env       = spore.env
      val spore2    = Spore.fromClassName2[(Int, Int), Int, Int, Spore1[(Int, Int, Int, Int), Int, Spore1[Int, Int, Int]]](className, env)
      val spore3    = spore2.copy(env)
      val pickle    = spore2.pickle
      val written   = write(pickle)
      val unpickled = read[Pickle[Function2[Int, Int, Spore1[(Int, Int, Int, Int), Int, Spore1[Int, Int, Int]]]]](written).get()
      val spore4    = Duplicable.duplicate(spore2)
      assert(spore.env == (1, 2))
      assert(spore.hasEnv == true)
      assert(spore3.env == (1, 2))
      assert(spore2(10, 20)(30)(40) == 226)
      assert(spore3(10, 20)(30)(40) == 226)
      assert(unpickled(10, 20)(30)(40) == 226)
      assert(spore4(10, 20)(30)(40) == 226)
      assert(written == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$28"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[1,2]}}""")
      println(s"Spore class name: $className, env: $env  -> ${spore2(10, 20)(30)(40)}")
    }

    locally {
      object Dead {
        object Code {
          val p = 1
          val q = 2
          val spore = Spore(p, q) { (a: Int, b: Int) =>
                        Spore(a, b, p, q) { (c: Int) =>
                          val w = 123 + a + b + c + p + q
                          Spore(w) { (x: Int) =>
                            x + w
                          }
                        }
                      }
        }
      }
      val outerClassName     = "spores.sample.SporeLocallyExamples$anon$31"
      val middleClassName    = "spores.sample.SporeLocallyExamples$anon$32"
      val innermostClassName = "spores.sample.SporeLocallyExamples$anon$33"
      val outer     = Spore.fromClassName2[(Int, Int), Int, Int, Spore1[(Int, Int, Int, Int), Int, Spore1[Int, Int, Int]]](outerClassName, (1, 2))
      val middle    = Spore.fromClassName1[(Int, Int, Int, Int), Int, Spore1[Int, Int, Int]](middleClassName, (10, 20, 1, 2))
      val innermost = Spore.fromClassName1[Int, Int, Int](innermostClassName, 186)
      val outerPickle     = outer.pickle
      val middlePickle    = middle.pickle
      val innermostPickle = innermost.pickle
      val outerWritten    = write(outerPickle)
      val middleWritten   = write(middlePickle)
      val innermostWritten= write(innermostPickle)
      val outerUnpickled     = read[Pickle[Function2[Int, Int, Spore1[(Int, Int, Int, Int), Int, Spore1[Int, Int, Int]]]]](outerWritten).get()
      val middleUnpickled    = read[Pickle[Function1[Int, Spore1[Int, Int, Int]]]](middleWritten).get()
      val innermostUnpickled = read[Pickle[Function1[Int, Int]]](innermostWritten).get()
      assert(outer.env == (1, 2))
      assert(middle.env == (10, 20, 1, 2))
      assert(innermost.env == 186)
      assert(outer.hasEnv == true)
      assert(middle.hasEnv == true)
      assert(innermost.hasEnv == true)
      assert(outer(10, 20)(30)(40) == 226)
      assert(middle(30)(40) == 226)
      assert(innermost(40) == 226)
      assert(outerUnpickled(10, 20)(30)(40) == 226)
      assert(middleUnpickled(30)(40) == 226)
      assert(innermostUnpickled(40) == 226)
      assert(outerWritten == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$31"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple2RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[1,2]}}""")
      assert(middleWritten == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$32"},"env":{"tag":"Val","ev":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"WithEnv","fun":{"tag":"Body","kind":1,"className":"spores.ReadWriters$Tuple4RW"},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"env":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"}},"value":[10,20,1,2]}}""")
      assert(innermostWritten == """{"tag":"WithEnv","fun":{"tag":"Body","kind":2,"className":"spores.sample.SporeLocallyExamples$anon$33"},"env":{"tag":"Val","ev":{"tag":"Body","kind":0,"className":"spores.ReadWriters$IntRW$"},"value":186}}""")
      println(s"Outer     class name: $outerClassName, env: ${outer.env}")
      println(s"Middle    class name: $middleClassName, env: ${middle.env}")
      println(s"Innermost class name: $innermostClassName, env: ${innermost.env}")
    }
  }
}
