package akkasample

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object AskPattern extends App {

  case object AskName
  case class AskNameOf(other: ActorRef)
  case class NameResponse(name: String)

  class AskActor(val name: String) extends Actor {

//    implicit val ec = context.system.dispatcher

    override def receive: Receive = {
      case AskName =>
//        Thread.sleep(10000) // this delay might cause " Message from Actor was not delivered"
        sender ! NameResponse(name)
      case AskNameOf(other) =>
        val f = other ? AskName
        f.onComplete {
          case Success(NameResponse(n)) => println("Name was " + n)
          case Success(s) => println("They didn't tell us thier name " + s)
          case Failure(ex) => println("Asking their name faield")
        }
        val currentSender = sender
        Future {
          currentSender ! "message"
        }
    }
  }

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props(new AskActor("Pat")), "AskActor")
  val actor2 = system.actorOf(Props(new AskActor("Val")), "AskActor2")
//  implicit val ec = system.dispatcher

  implicit val timeout = Timeout(1.seconds)
  val answerF = actor ? AskName
  answerF.foreach(println)

  actor ! AskNameOf(actor2)

  system.terminate()
}
