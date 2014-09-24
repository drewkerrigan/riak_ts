package com.basho.ts

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Boot extends App {
  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[TsServiceActor], "ts-service")

  implicit val timeout = Timeout(60.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
