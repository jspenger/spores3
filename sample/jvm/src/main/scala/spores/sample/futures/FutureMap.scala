package spores.sample

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.concurrent.TrieMap

import upickle.default.{ReadWriter, macroRW, readwriter}

import spores.{Spore, SporeBuilder, Duplicable}
import spores.jvm.Spore
import spores.Duplicable.duplicate


case class Customer(name: String, customerNo: Int)

case class CustomerInfo(customerNo: Int, age: Int, since: Int)

object CustomerInfo {
  given ReadWriter[CustomerInfo] = macroRW
  object CustomerInfoRW extends SporeBuilder[ReadWriter[CustomerInfo]]({ summon })
  given Spore[ReadWriter[CustomerInfo]] = CustomerInfoRW.pack()
}

type CustomerMap = TrieMap[Int, CustomerInfo]

object CustomerMap {
  given [K: ReadWriter, V: ReadWriter]: ReadWriter[TrieMap[K, V]] =
    readwriter[Map[K, V]]
      .bimap[TrieMap[K, V]](
        trieMap => trieMap.toMap,
        map => TrieMap.from(map)
      )
  object CustomerMapRW extends SporeBuilder[ReadWriter[CustomerMap]]({ summon })
  given Spore[ReadWriter[CustomerMap]] = CustomerMapRW.pack()
}

given trieMapDuplicable[K, V]: Duplicable[TrieMap[K, V]] with
  def duplicate(map: TrieMap[K, V]) = map.snapshot()

object FutureMap {
  import CustomerMap.given

  val customerData = TrieMap.empty[Int, CustomerInfo]

  def averageAgeUnsafe(customers: List[Customer]): Future[Float] =
    Future {
      val infos = customers.flatMap { c =>
        customerData.get(c.customerNo) match
          case Some(info) => List(info)
          case None => List()
      }
      val sumAges = infos.foldLeft(0)(_ + _.age).toFloat
      if (infos.nonEmpty) sumAges / infos.size else 0.0f
    }

  def averageAge(customers: List[Customer]): Future[Float] = {
    val spore = Spore.applyWithEnv[CustomerMap, List[Customer] => Float](customerData) { data => cs =>
      val infos = cs.flatMap { c =>
        data.get(c.customerNo) match
          case Some(info) => List(info)
          case None => List()
      }
      val sumAges = infos.foldLeft(0)(_ + _.age).toFloat
      if (infos.nonEmpty) sumAges / infos.size else 0.0f
    }
    val safeSpore = duplicate(spore)
    Future { safeSpore.unwrap()(customers) }
  }

  def main(args: Array[String]): Unit = {
    val cs = List(Customer("Diane", 11), Customer("Hans", 12), Customer("Emily", 13))
    customerData.put(11, CustomerInfo(11, 25, 2018))
    customerData.put(12, CustomerInfo(12, 23, 2017))
    customerData.put(13, CustomerInfo(13, 36, 2019))
    val ageFut = averageAge(cs)
    val age = Await.result(ageFut, Duration(10, "sec"))
    assert(age.toString == "28.0")
    println(age)
  }

}
