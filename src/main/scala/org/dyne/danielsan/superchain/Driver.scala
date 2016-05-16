package org.dyne.danielsan.superchain

import org.dyne.danielsan.superchain.client.BitcoinClient
import org.dyne.danielsan.superchain.data.database.ChainDatabase
import org.json4s.DefaultFormats

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


/**
  * Created by dan_mi_sun on 13/03/2016.
  *
  */
object Driver {

  def main(args: Array[String]) {

    implicit val formats = DefaultFormats

    implicit val space = ChainDatabase.space
    implicit val session = ChainDatabase.session

    Await.result(ChainDatabase.autocreate().future, 10 seconds)

    //Known limitation here is that the scanner is not dynamic or automatic
    //It is also not fault tolerant with regards to getting to the end of blocks
    //echo `bitcoin-cli getblockcount 2>&1`/`wget -O - http://blockchain.info/q/getblockcount 2>/dev/null`

//    val client = new BitcoinClient
//
//    for (a <- 1 to 1000) {
//
//      val t = client.decodeRawTransaction(a)
//      println("Transaction: " + t)
//      val operationT = ChainDatabase.insertTransaction(t)
//      Await.result(operationT, 10.seconds)
//
//      val b = client.getBlockForId(a)
//      println("Block: " + b)
//      val operationB = ChainDatabase.insertBlock(b)
//      Await.result(operationB, 10.seconds)
//
//    }

//    val cblist = ChainDatabase.listAllBlocks
//    Await.result(cblist, 10.seconds)
//    println("List of Blocks" + cblist)
//
//    val txList = ChainDatabase.listAllTransactions
//    Await.result(txList, 10.seconds)
//    println("List of Transactions" + txList)
    val hash = "000000004da68466ee873c7095c766baf62df93a16df579350e01e7f78911616"
    val blockHash = ChainDatabase.getBlockByHash(hash)
    Await.result(blockHash, 10.seconds)
    println("Block by Hash" + blockHash)



    println("Sample ended")
    System.exit(0)
  }

}
