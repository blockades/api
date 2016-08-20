package org.dyne.danielsan.openblockchain.http.controllers.api

import org.dyne.danielsan.openblockchain.OpenBlockchainStack
import org.dyne.danielsan.openblockchain.data.database.ChainDatabase
import org.dyne.danielsan.openblockchain.http.controllers.api.swaggerdocs.VisualizationsControllerDocs
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.FutureSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.swagger.Swagger

/**
  * Created by dan_mi_sun on 20/08/2016.
  */
class VisualizationsController(implicit val swagger: Swagger) extends OpenBlockchainStack
  with FutureSupport with JacksonJsonSupport with VisualizationsControllerDocs {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  val executor = scala.concurrent.ExecutionContext.global

  before() {
    contentType = formats("json")
  }

  get("/:id/:period/:unit", operation(getVisualization)) {
    val id = params("id")
    val period = params("period")
    val unit = params("unit")
    ChainDatabase.getVisualization(id, period, unit)
  }

}