package dd.oliver.piggy.controller

import dd.oliver.piggy.server.FileServer
import tornadofx.Controller

class ServerController : Controller() {

    private val server: FileServer by inject()

    internal fun stopServer() {
        server.server.stop()
    }
}