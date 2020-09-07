package dd.oliver.piggy.server

import dd.oliver.piggy.model.ViewModel
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Netty
import org.http4k.server.asServer
import tornadofx.Controller
import tornadofx.JsonBuilder
import java.io.File

private enum class PathSituation{
    DIRECTORY,
    FILE,
    NONSEXIST,
    ERROR,
}

class FileServer: Controller() {

    lateinit var filePath: String

    private val model: ViewModel by inject()

    private val appContexts = routes(
        "/" bind GET to {
            filePath = it.query("path") ?: "/"
            val fullPath = model.path + filePath
            when(testPath(fullPath)){
                PathSituation.DIRECTORY -> {
                    val list = makePiggyList(fullPath, filePath)
                    val builder = JsonBuilder()
                    builder.add("list", list)
                    Response(Status.OK).body(builder.build().toString())
                }
                PathSituation.FILE -> null // transfer file
                PathSituation.NONSEXIST -> null // transfer nonsexist information
                PathSituation.ERROR -> null // transfer error information
            }
            Response(Status.NO_CONTENT)
        },
    )

    private lateinit var server: Http4kServer

    fun start() {
        server = appContexts.asServer(Netty(model.port.toInt()))
        server.start()
    }

    fun stop() {
        server.stop()
    }

    private fun makePiggyList(fullPath: String, filePath: String): MutableList<PiggyObject> {
        val file = File(fullPath)
        val list = file.list()
        val piggyObjectList = mutableListOf<PiggyObject>()
        list.forEach {
            val path = fullPath + it
            val piggyObject: PiggyObject = when(testPath(path)){
                PathSituation.DIRECTORY -> PiggyDirectory(it, filePath + it)
                PathSituation.FILE -> PiggyFile(it, filePath + it)
                PathSituation.NONSEXIST -> throw error("路径不存在")
                PathSituation.ERROR -> throw error("路径不存在")
            }
            piggyObjectList.add(piggyObject)
        }
        return piggyObjectList
    }

    private fun testPath(path: String): PathSituation{
        val file = File(path)
        if(!file.exists())
            return PathSituation.NONSEXIST
        if(file.isDirectory)
            return PathSituation.DIRECTORY
        if(file.isFile)
            return PathSituation.FILE
        return PathSituation.ERROR
    }

}