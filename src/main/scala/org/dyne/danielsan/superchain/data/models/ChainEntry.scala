package org.dyne.danielsan.superchain.data.models

/**
  * Created by dan_mi_sun on 06/02/2016.
  */

case class ChainEntry(id: String, txCount: Int, blockHeader: String) //, address: String

/*
Here need to figure out what a ChainEntry is. What is it comprised of?
I suspect that a ChainEntry is the Transaction address? Or reference?

The route of commands that we go through is:
$ bitcoin-cli getblockhash 1 // it is worth noting this starts from 1 _not_ 0. This is by design.

00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048

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

$ bitcoin-cli getrawtransaction 0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098 1

{
    "hex" : "01000000010000000000000000000000000000000000000000000000000000000000000000ffffffff0704ffff001d0104ffffffff0100f2052a0100000043410496b538e853519c726a2c91e61ec11600ae1390813a627c66fb8be7947be63c52da7589379515d4e0a604f8141781e62294721166bf621e73a82cbf2342c858eeac00000000",
    "txid" : "0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098",
    "version" : 1,
    "locktime" : 0,
    "vin" : [
        {
            "coinbase" : "04ffff001d0104",
            "sequence" : 4294967295
        }
    ],
    "vout" : [
        {
            "value" : 50.00000000,
            "n" : 0,
            "scriptPubKey" : {
                "asm" : "0496b538e853519c726a2c91e61ec11600ae1390813a627c66fb8be7947be63c52da7589379515d4e0a604f8141781e62294721166bf621e73a82cbf2342c858ee OP_CHECKSIG",
                "hex" : "410496b538e853519c726a2c91e61ec11600ae1390813a627c66fb8be7947be63c52da7589379515d4e0a604f8141781e62294721166bf621e73a82cbf2342c858eeac",
                "reqSigs" : 1,
                "type" : "pubkey",
                "addresses" : [
                    "12c6DSiU4Rq3P4ZxziKxzrL5LmMBrzjrJX"
                ]
            }
        }
    ],
    "blockhash" : "00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048",
    "confirmations" : 299485,
    "time" : 1231469665,
    "blocktime" : 1231469665
}
*/

