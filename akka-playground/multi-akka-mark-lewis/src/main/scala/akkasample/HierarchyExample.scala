package akkasample

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object HierarchyExample extends App {

  case object CreateChild
  case object SignalChildren
  case object PrintSignal

  class ParentActor extends Actor {
    private var number = 0
    private val children = collection.mutable.Buffer[ActorRef]()

    override def receive: Receive = {
      case CreateChild =>
        children += context.actorOf(Props[ChildActor], "child" + number)
        number += 1
      case SignalChildren =>
        children.foreach( _ ! PrintSignal)
    }
  }

  class ChildActor extends Actor {
    override def receive = {
      case PrintSignal => println(self)
    }
  }

  val system = ActorSystem("HierarchySystem")
  val actor = system.actorOf(Props[ParentActor], "Parent1")

  actor ! CreateChild
  actor ! SignalChildren
  actor ! CreateChild
  actor ! CreateChild
  actor ! SignalChildren



  Thread.sleep(2000)
  system.terminate()
}
