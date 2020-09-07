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
import java.io.File
import java.io.FileInputStream

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
        if (file.isDirectory) { // example: /temp/
            val fileList = file.list()
            val piggyNodeList = mutableListOf<PiggyNode>()
            fileList.forEach { fileName ->
                piggyNodeList.add(PiggyNode(fileName, basePath + "${fileName}/"))
            }
            val piggyList = PiggyList(piggyNodeList)
            Response(Status.OK).body(template.apply(piggyList))
        } else if (file.isFile) {
            // todo: error on file transfer
            val inputStream = FileInputStream(file)
            inputStream.use { ips ->
                Response(Status.OK).body(ips)
            }
        }else
            Response(Status.OK).body("error")
    }

    init {
        server = appServer.asServer(Netty(model.port.toInt()))
    }

}