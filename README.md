For accessing the bitcoin server ensure that bitcoin.conf has the following options enabled.

# Listen for RPC connections on this TCP port:
rpcport=8332

# You can use Bitcoin or bitcoind to send commands to Bitcoin/bitcoind
# running on another host using this option:
rpcconnect=127.0.0.1

To have this running you need to ensure there is both a running version of cassandra and also a running version of the
bitcoin daemon. Bitcoin sever does not automatically create the full transaction index, so you will need to ensure that
you have the flags enabled.

> bitcoind -daemon -reindex -txindex

Which has an index of the transactions which you are querying.


---

High Level Steps:
    $ bitcoin-cli getblockhash <INTEGER>
- get blockhash (how to figure out which is the latest?)
- insert the blockhash into the DB
- Loop through all INTEGERS till all blockhashes have been inserted into the DB
(another way to do this is from the JSON from the block itself as it has 'next' and 'previous')

    $ bitcoin-cli getblock <BLOCKHASH>
- Loop through all blockhashes in the DB and get the block JSON and insert into the DB

    NEED TO WRITE LOOP for each block JSON
- Loop through each block and extract into the DB a list of all tx from within the block (these are the rawTransactions)
- This means looping through each tx array to extract each tx code within the array for each block

    $ bitcoin-cli getrawtransaction <RAW-TX-ID>
- Loop through all RawTransactions
- Insert JSON response into Transaction within DB

    ---

NEXT STEPS:

    - Write model for blockhash
    - Insert the blockhash into the DB (using fixed value)
    - Write a loop to go through all integers to get all blockhashes and insert each into DB

    - Write model for block
    - Insert a block JSON into the DB using a hardcoded value
    - Write loop to go through all blockhashes to extract all block JSON and insert into DB

    - Write model for RAW-TX-ID
    - Write a function which can extract the RAW-TX-ID from the block JSON (using fixed block JSON to begin with)
    - Write a function which can insert RAW-TX-IDs into DB
    - Write a loop which can go through all blocks, extract RAW-TX-ID and insert into DB

    - Write a function which can insert Transaction into DB (using fixed value)
    - Write a function which loops through all the RAW-TX-ID and then inserts the Transaction JSON into DB

    - Write a function which can go through all the Transactions and note if they have OP_RETURN or not
    - **Question: Does ^ step require a new Model?
    - **It might be enough to use Angular to pull that right out of the DB and stick it up on a chart

    ---


The following are the primary commands of the bitcoin server which we need to issue to be able to build up a 'full'
version of the bitcoin blockchain.

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