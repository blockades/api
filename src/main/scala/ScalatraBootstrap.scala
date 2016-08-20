import javax.servlet.ServletContext

import org.dyne.danielsan.openblockchain.data.database.ChainDatabase
import org.dyne.danielsan.openblockchain.http.controllers.api.{BlocksController, TransactionsController, VisualizationsController}
import org.dyne.danielsan.openblockchain.http.controllers.{ApiDocsController, OpenBlockchainSwagger}
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpenBlockchainSwagger

  //Cassandra's initialisation code
  implicit val keySpace = ChainDatabase.space
  implicit val session = ChainDatabase.session

  override def init(context: ServletContext) {

//    Await.result(ChainDatabase.autocreate().future(), 10 seconds)

    context.mount(new ApiDocsController, "/api-docs/")
    context.mount(new BlocksController, "/api/blocks")
    context.mount(new TransactionsController, "/api/transactions")
    context.mount(new VisualizationsController, "/api/visualizations")

  }
}