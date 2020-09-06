package dd.oliver.piggy.server

import dd.oliver.piggy.model.ViewModel
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import tornadofx.Controller

class FileServer(
    val port: Int
) : Controller() {

    private val model: ViewModel by inject()

    val server = routes(
        "/" bind GET to {
            val filePath: String = it.query("path") ?: throw error("filePath not exist in URL")
            // todo: make FileList object on the model.path + filePath
            // todo: is directory: convert FileList to json object; is file: transfer file
            Response(Status.OK)
        },
    )


}