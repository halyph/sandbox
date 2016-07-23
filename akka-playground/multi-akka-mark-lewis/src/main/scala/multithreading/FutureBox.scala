package multithreading

import io.StdIn._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FutureBox extends App {

  println("This is 1st")
  val f = Future {
    println("Print in Future ")
  }
  Thread.sleep(1)
  println("This is Last")


  val f2 = Future {
    for(i <- 1 to 30 ) yield ParallelCollect.fib(i)
  }

  f2.onComplete {
    case Success(n) => println(n)
    case Failure(ex) => println("something went wrong " + ex)
  }


  readLine()
}
