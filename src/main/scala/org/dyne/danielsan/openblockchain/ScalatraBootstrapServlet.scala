package org.dyne.danielsan.openblockchain

import org.slf4j.LoggerFactory

class ScalatraBootstrapServlet extends OpenBlockchainStack {

  val logger = LoggerFactory.getLogger(getClass)
  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say
        <a href="hello-scalate">hello to Scalate</a>
        .
      </body>
    </html>
  }

  get("/hello") {
    logger.info("foo")
    "Hello world!"
  }

}

