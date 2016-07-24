package akkasample

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object AskPattern extends App {

  case object AskName
  case class NameResponse(name: String)

  class AskActor(val name: String) extends Actor {
    override def receive: Receive = {
      case AskName =>
//        Thread.sleep(10000) // this delay might cause " Messge from Actor was not delivered"
        sender ! NameResponse(name)
    }
    def foo = println("Method foo")
  }

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props(new AskActor("Pat")), "AskActor")

  implicit val timeout = Timeout(1.seconds)
  val answerF = actor ? AskName

  answerF.foreach(println)

  system.terminate()
}
