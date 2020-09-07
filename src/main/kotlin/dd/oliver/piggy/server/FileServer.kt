package dd.oliver.piggy.server

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import dd.oliver.piggy.model.ViewModel
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import tornadofx.Controller
import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.file.Files

data class ErrorReason(val detail: String)

class FileServer : Controller() {

    private val model: ViewModel by inject()

    private val template = Handlebars(ClassPathTemplateLoader("/web", ".html")).compile("index")

    private val errorTemplate = Handlebars(ClassPathTemplateLoader("/web", ".html")).compile("error")

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
            val randomAccessFile = RandomAccessFile(file, "r")
            randomAccessFile.use { random ->
                val buffer = ByteBuffer.allocate(file.length().toInt())
                Body(buffer).use { body ->
                    random.channel.read(buffer)
                    buffer.flip()
                    var contentType = Files.probeContentType(file.toPath())
                    if (contentType == null) {
                        contentType = "application/octet-stream"
                    }
                    Response(Status.OK).header("Content-type", "${contentType}; charset=utf-8")
                        .header("Content-Disposition", "attachment; filename=\"${file.name}\"")
                        .body(body)
                }

            }
        } else
            Response(Status.OK).body(errorTemplate.apply(ErrorReason("Your request is invalid, which points to ${file.absolutePath}, neither file nor directory.")))
    }

    init {
        server = appServer.asServer(SunHttp(model.port.toInt()))
    }

}