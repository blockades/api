package org.dyne.danielsan.openblockchain.http.controllers.api.swaggerdocs

import org.dyne.danielsan.openblockchain.data.entity.Block
import org.scalatra.swagger.{StringResponseMessage, SwaggerSupport}

/**
  * Created by dan_mi_sun on 20/08/2016.
  */
trait VisualizationsControllerDocs extends SwaggerSupport {

  val applicationDescription = "OpenBlockchain API"
  val getVisualization =
    (apiOperation[List[Block]]("getBlocks")
      summary "wip"
      notes "wip")

}