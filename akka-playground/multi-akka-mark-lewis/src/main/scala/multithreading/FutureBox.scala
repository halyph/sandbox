package multithreading

import io.StdIn._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureBox extends App{

  println("This is 1st")
  val f = Future {
    println("Print in Future ")
  }
  Thread.sleep(1)
  println("This is Last")

  readLine()
}
