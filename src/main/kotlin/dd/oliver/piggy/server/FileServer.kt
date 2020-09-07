package dd.oliver.piggy.server

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import dd.oliver.piggy.model.ViewModel
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Http4kServer
import org.http4k.server.Netty
import org.http4k.server.asServer
import tornadofx.Controller
import tornadofx.JsonBuilder
import java.io.File
import javax.json.Json

class FileServer : Controller() {

    private val model: ViewModel by inject()

    private val template = Handlebars(ClassPathTemplateLoader("/web", ".html")).compile("index")

    val server: Http4kServer

    private val appServer: HttpHandler = {
        // basePath's example: /temp/a.txt, /temp/
        val basePath = it.query("path") ?: "/"
        // absolute path to the file
        val filePath = model.path + basePath
        val file = File(filePath)
        if (file.isDirectory) {
            val fileList = file.list()
            // Fill in JsonArray
            val jBuilder = Json.createArrayBuilder()
            fileList.forEach { fileName ->
                jBuilder.add(PiggyNode(fileName, basePath).toJSON())
            }
            val jArray = jBuilder.build()
            val jsonBuilder = JsonBuilder()
            // Add JsonArray to Json
            jsonBuilder.add("files", jArray)
            val finalJson = jsonBuilder.build()
            println(finalJson)
            Response(Status.OK).body(template.apply(finalJson))
        } else if (file.isFile) {
            Response(Status.OK).body("File")
        }else
            Response(Status.OK).body("error")
    }

    init {
        server = appServer.asServer(Netty(model.port.toInt()))
    }

}