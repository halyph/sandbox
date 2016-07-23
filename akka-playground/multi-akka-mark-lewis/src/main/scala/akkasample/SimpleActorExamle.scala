package akkasample

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.Actor.Receive

object SimpleActorExamle extends App {

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case s:String => println("String: " + s)
      case i:Int => println("Int: " + i)
    }
    def foo = println("Method foo")
  }

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props[SimpleActor], "SimpleActor")

  actor ! "Hello"
  actor ! "Second MSG"
  actor ! 'S'

}
