package akkasample

import akka.actor.{Actor, ActorSystem, Props}
import scala.concurrent.duration._

object SchedulerExample extends App {
  case object Count

  class SchedulerActor extends Actor {
    var n = 0
    override def receive: Receive = {
      case Count =>
        n += 1
        println(n)
    }
    def foo = println("Method foo")
  }

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props[SchedulerActor], "SchedulerActor")

  actor ! Count

  implicit val ec = system.dispatcher

  system.scheduler.scheduleOnce(1.second)(actor ! Count)
  val can = system.scheduler.schedule(0.seconds, 100.millis, actor, Count)

  Thread.sleep(2000)
  can.cancel
  system.terminate()
}
