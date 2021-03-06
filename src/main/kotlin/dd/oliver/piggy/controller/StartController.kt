package dd.oliver.piggy.controller

import dd.oliver.piggy.model.ViewModel
import dd.oliver.piggy.server.FileServer
import tornadofx.Controller
import java.io.File

class StartController : Controller() {

    private val model: ViewModel by inject()

    private val server: FileServer by inject()

    internal fun checkPath(): Boolean {
        val file = File(model.path)
        return file.exists() && file.isDirectory
    }

    internal fun startServer() {
        server.server.start()
    }

}