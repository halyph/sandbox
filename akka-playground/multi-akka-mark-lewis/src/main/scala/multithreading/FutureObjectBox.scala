package multithreading

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source.fromURL
import io.StdIn._

object FutureObjectBox extends App {

  val page1 = Future {
    "halyph.com: " + fromURL("http://halyph.com").take(100).mkString
  }

  val page2 = Future {
    "halyph.blogspot.com: " + fromURL("http://halyph.blogspot.com/").take(100).mkString
  }

  val page3 = Future {
    "Google: " + fromURL("http://www.google.com/").take(100).mkString
  }

  val pages = List(page1, page2, page3)

//  val firstPage = Future.firstCompletedOf(pages)
//  firstPage.foreach(println)

  val allPages = Future.sequence(pages)
  allPages.foreach(println)

  readLine()
}
