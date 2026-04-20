package spores.test

import utest.*

import scala.collection.concurrent.TrieMap

import spores.v03.*
import spores.v03.given


case class Customer(name: String, customerNo: Int)
case class CustomerInfo(customerNo: Int, age: Int, since: Int)

object TrieMapTest extends TestSuite {

  type CustomerMap = TrieMap[Int, CustomerInfo]
  given Duplicable[CustomerMap] with
    def duplicate(map: CustomerMap): CustomerMap = map.snapshot()

  val customerData = TrieMap.empty[Int, CustomerInfo]

  val tests = Tests {

    test("test") {
      val data = customerData
      val s = Spore(data) { (cs: List[Customer]) =>
        val infos = cs.flatMap { c =>
          data.get(c.customerNo) match {
            case Some(info) => List(info)
            case None => List()
          }
        }
        val sumAges = infos.foldLeft(0)(_ + _.age).toFloat
        if (infos.size == 0) 0
        else sumAges / infos.size
      }

      val res = s(List())
      assert(res == 0)
    }
  }

}
