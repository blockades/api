package org.dyne.danielsan.superchain.data.bitcoinrpc

import argonaut._, argonaut.Argonaut._
import dispatch._
import org.json4s._
//import org.json4s.native.JsonMethods._
//import org.json4s.jackson.JsonMethods._

/**
  * Created by dan_mi_sun on 23/02/2016.
  */

case class JSONRPCRequest(id: String, method: String, params: Json)
object JSONRPCRequest {
  implicit def JSONRPCRequestJson =
    casecodec3(JSONRPCRequest.apply, JSONRPCRequest.unapply)("id", "method", "params")
}

case class JSONRPCResponse(result: Json, error: Option[String], id: String)
object JSONRPCResponse {
  implicit def JSONRPCResponseJson =
    casecodec3(JSONRPCResponse.apply, JSONRPCResponse.unapply)("result", "error", "id")
}

class JSONRPC(rpcURL: String, username: String, password: String) {
  import dispatch._, Defaults._

  def doRequest[A](req: JSONRPCRequest, responseTransformer: (Json => A)) = {
    println("DO REQUEST: " + req.asJson.toString())
    val dispatchReq = url(rpcURL).addHeader("Content-Type", "application/x-www-form-urlencoded").POST.as(username, password) << req.asJson.toString()
    val resp = Http(dispatchReq OK as.String).either.right.map(_.decodeOption[JSONRPCResponse])

    resp() match {
      case Right(Some(JSONRPCResponse(r, None, _))) => Right(responseTransformer(r))
      case Right(Some(JSONRPCResponse(_, Some(error), _))) => Left(error)
      case Right(_) => Left("No response case class found")
      case Left(t) => Left(t.getMessage)
    }
  }
}

object BitcoinRPC {
  val jsonToString:(Json => String) = (a => a.string.get.toString)

  val emptyjArray = jArray(List())

  def jStringInjArray(s: String) =  jArray(List(jString(s)))

  def getinfo(implicit jsonrpc: JSONRPC) = {
    jsonrpc.doRequest(JSONRPCRequest("t0", "getinfo", emptyjArray), identity[Json])
  }
  def getnewaddress(implicit jsonrpc: JSONRPC) = {
    jsonrpc.doRequest(JSONRPCRequest("t0", "getnewaddress", emptyjArray), jsonToString)
  }

  def validateaddress(address: String)(implicit jsonrpc: JSONRPC) = {
    jsonrpc.doRequest(JSONRPCRequest("t0", "validateaddress",jStringInjArray(address)), identity[Json])
  }

  def addmultisigaddress(addresses: List[String])(implicit jsonrpc: JSONRPC) = {
    val json = jArray(List(jNumber(addresses.size), jArray(addresses map (jString(_)))))
    jsonrpc.doRequest(JSONRPCRequest("t0", "addmultisigaddress", json), jsonToString)
  }

  def sendtoaddress(to: String, amount: Double)(implicit jsonrpc: JSONRPC) = {
    val json = jArray(List(jString(to), jNumber(amount)))
    jsonrpc.doRequest(JSONRPCRequest("t0", "sendtoaddress", json), jsonToString)
  }

  def getrawtransaction(tid: String, verbose: Boolean)(implicit jsonrpc: JSONRPC) = {
    val json = jArray(List(jString(tid), jNumber(if (verbose) 1 else 0)))
    jsonrpc.doRequest(JSONRPCRequest("t0", "getrawtransaction", json), identity[Json])
  }

  def createrawtransaction(txid: String, vout: Int, toAddr: String, amount: Double)(implicit jsonrpc: JSONRPC) = {
    val p1 = jArray(List(Json.obj("txid" -> jString(txid), "vout" -> jNumber(vout))))
    val json = jArray(List(p1, Json.obj(toAddr -> jNumber(amount))))
    jsonrpc.doRequest(JSONRPCRequest("t0", "createrawtransaction", json), jsonToString)
  }

  def signrawtransaction(t: String)(implicit jsonrpc: JSONRPC) = {
    val json = jStringInjArray(t)
    jsonrpc.doRequest(JSONRPCRequest("t0", "signrawtransaction", json), identity[Json])
  }
}

/*
object JSONRPCMain extends App{
  val btcurl="http://127.0.0.1:8332"

  implicit val jsonrpc = new JSONRPC(btcurl, "dave", "suckme")

  /*
    val resp = BitcoinRPC.getnewaddress.right.map(
      a => BitcoinRPC.validateaddress(a)
    )
  */
  //val resp = BitcoinRPC.addmultisigaddress(List("mmjTqkb2F7dRRwfzp12cTmgs7GQTaJTAkd", "n2AvboULvEJmATEmtWs6cYzRcELXQEwX1c"))
  //val resp = BitcoinRPC.sendtoaddress("2N3UMNL8hZ87kS1uRtMVzpRrY4yZyY3NqTa", 0.01)
  val resp = BitcoinRPC.getrawtransaction("0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098", true)
  //val resp = BitcoinRPC.signrawtransaction("0100000001a531d73aa5c71f85e8abf44d2d397c9700eda673ab2c421a341ded6d7242d23101000000da00473044022040a4a2c3a7452d3fd61d989d7619c765744d702157d99ae9e0ad08e31b62c04a02205d066b8a24edc6de92d82d52622475fc88a2c275b62270f1af2b0a8231f588cd01483045022100d84475e0b96912481e0fa18ac829d6a286bd250074f3a8750a65b725d65ebfaf0220375aa0111dd10209e6aeba94a5288bbfc3dec1ac04cddfbc91464c5c3ca467b90147522102bc7b00d7b0a4ec4209e486417e1ef21138ce1910da5457d4e119d14f942a6a8a21033bfbb4a392b385c7271e38308d7a1f8dda33b667c0754c2c8e6e5c989f69769352aeffffffff02302d8900000000001976a914ec0b505e21d114210694154962de2937f8acc0e388ac40420f000000000017a914702cd3719394115f1ce4fa14c4c6eef79a418fc78700000000")
  println(resp)


}
*/

object JSONRPCMain extends App{
  val btcurl="http://127.0.0.1:8332"

  implicit val jsonrpc = new JSONRPC(btcurl, "dave", "suckme")

  val resp = BitcoinRPC.getrawtransaction("0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098", true)

  resp match {
    case Left(error) => println(s"there was an error: $error")
    case Right(json) => println(s"Got the json: $json")
  }
}