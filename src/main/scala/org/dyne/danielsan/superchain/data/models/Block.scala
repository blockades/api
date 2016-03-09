package org.dyne.danielsan.superchain.data.models

import argonaut._, Argonaut._

/**
  * Created by dan_mi_sun on 27/02/2016.
  */

/*
$ bitcoin-cli getblock 00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048


{
"hash" : "00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048",
"confirmations" : 299191,
"size" : 215,
"height" : 1,
"version" : 1,
"merkleroot" : "0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098",
"tx" : [
"0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098"
],
"time" : 1231469665,
"nonce" : 2573394689,
"bits" : "1d00ffff",
"difficulty" : 1.00000000,
"chainwork" : "0000000000000000000000000000000000000000000000000000000200020002",
"previousblockhash" : "000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f",
"nextblockhash" : "000000006a625f06636b8bb6ac7b960a8d03705d1ace08b1a19da3fdcc99ddbd"
}
*/

object BlockImplicitConversion {

  case class Block(hash: String,
                   confirmations: Int,
                   size: Int,
                   height: Int,
                   version: Int,
                   merkleroot: String,
                   tx: List[String],
                   time: Long,
                   nonce: Long,
                   bits: String,
                   difficulty: Float,
                   chainwork: String,
                   previousblockhash: String,
                   nextblockhash: String)

  implicit val blockCodec: CodecJson[Block] =
    casecodec14(Block.apply, Block.unapply)("hash", "confirmations", "size", "height", "version", "merkleroot", "tx",
      "time", "nonce", "bits", "difficulty", "chainwork", "previousblockhash",
      "nextblockhash")


}