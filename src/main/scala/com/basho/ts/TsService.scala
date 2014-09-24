package com.basho.ts

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import com.basho.riak.client._

class TsServiceActor extends Actor with TsService {
  def actorRefFactory = context
  def receive = runRoute(tsRoute)
}

trait TsService extends HttpService {
   val tsRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to my little friend: <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    } ~
    path("ts") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>You found the TS resource, grats</h1>
              </body>
            </html>
          }
        }
      }
    }

  // val tsRoute = {
  //   path("orders") {
  //     authenticate(BasicAuth(realm = "admin area")) { user =>
  //       get {
  //         cache(simpleCache) {
  //           encodeResponse(Deflate) {
  //             complete {
  //               // marshal custom object with in-scope marshaller
  //               getOrdersFromDB
  //             }
  //           }
  //         }
  //       } ~
  //       post {
  //         // decompresses the request with Gzip or Deflate when required
  //         decompressRequest() {
  //           // unmarshal with in-scope unmarshaller
  //           entity(as[Order]) { order =>
  //             // transfer to newly spawned actor
  //             detach() {
  //               complete {
  //                 // ... write order to DB
  //                 "Order received"
  //               }
  //             }
  //           }
  //         }
  //       }
  //     }
  //   } ~
  //   // extract URI path element as Int
  //   pathPrefix("order" / IntNumber) { orderId =>
  //     pathEnd {
  //       // method tunneling via query param
  //       (put | parameter('method ! "put")) {
  //         // form extraction from multipart or www-url-encoded forms
  //         formFields('email, 'total.as[Money]).as(Order) { order =>
  //           complete {
  //             // complete with serialized Future result
  //             (myDbActor ? Update(order)).mapTo[TransactionResult]
  //           }
  //         }
  //       } ~
  //       get {
  //         // JSONP support
  //         jsonpWithParameter("callback") {
  //           // use in-scope marshaller to create completer function
  //           produce(instanceOf[Order]) { completer => ctx =>
  //             processOrderRequest(orderId, completer)
  //           }
  //         }
  //       }
  //     } ~
  //     path("items") {
  //       get {
  //         // parameters to case class extraction
  //         parameters('size.as[Int], 'color ?, 'dangerous ? "no")
  //                 .as(OrderItem) { orderItem =>
  //           // ... route using case class instance created from
  //           // required and optional query parameters
  //         }
  //       }
  //     }
  //   } ~
  //   path("documentation") {
  //     // cache responses to GET requests
  //     cache(simpleCache) {
  //       // optionally compresses the response with Gzip or Deflate
  //       // if the client accepts compressed responses
  //       compressResponse() {
  //         // serve up static content from a JAR resource
  //         getFromResourceDirectory("docs")
  //       }
  //     }
  //   } ~
  //   path("oldApi" / Rest) { pathRest =>
  //     redirect("http://oldapi.example.com/" + pathRest, StatusCodes.MovedPermanently)
  //   }
  // }
}