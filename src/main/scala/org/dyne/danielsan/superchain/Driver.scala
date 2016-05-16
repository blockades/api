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

        val client = new BitcoinClient

        for (a <- 1 to 1000) {

          val trans = client.decodeRawTransaction(a)
          println("Transaction: " + trans)
          val operationTrans = ChainDatabase.insertTransaction(trans)
          Await.result(operationTrans, 10.seconds)

          val block = client.getBlockForId(a)
          println("Block: " + block)
          val operationBlock = ChainDatabase.insertBlock(block)
          Await.result(operationBlock, 10.seconds)

//          val BTC = client.getTransactionCountFromWithinBlock(a)
//          println("BlockTransactionCounts" + BTC)
//          //need to look at adding info from decodeRawTransaction
//          //might be done through the service
//          //this is the same as updating to two tables at the same time
//          val operationBTC = ChainDatabase.insertBlockTransactionCounts(BTC)
//          Await.result(operationBTC, 10.seconds)


        }

        println("Sample ended")
        System.exit(0)
      }
  }