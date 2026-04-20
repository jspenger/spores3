package spores.sample

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

import spores.v03.*
import spores.v03.given


object Futures:

  /**
    * Computes the nth Fibonacci number.
    *
    * Recursively computes the (n-1)th and (n-2)th
    * Fibonacci numbers concurrently using futures.
    */
  def fib(n: Int): Future[Int] =
    if (n <= 2) then
      Future.successful(1)
    else
      val fut1 = fib(n - 1)
      val fut2 = fib(n - 2)

      // captured variables are passed explicitly to
      // `apply` method of `Spore` object
      fut1.flatMap(Spore(fut2) { (res1: Int) =>
        fut2.map(Spore(res1) {
          (res2: Int) => res1 + res2
        })
      })

  def main(args: Array[String]): Unit =
    // computes 8th Fibonacci number = 21
    val res = Await.result(fib(8), Duration(10, "sec"))
    assert(res == 21)
    println(res)
