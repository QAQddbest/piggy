package dd.oliver.piggy.view

import dd.oliver.piggy.UserLocale
import dd.oliver.piggy.controller.ServerController
import dd.oliver.piggy.model.ViewModel
import javafx.geometry.Pos
import javafx.scene.Parent
import tornadofx.*
import java.net.InetAddress


class ServerView : View("ServerView") {

    private val model: ViewModel by inject()

    private val controller: ServerController by inject()

    override val root: Parent = vbox(
        20.0,
        Pos.CENTER,
    ) {
        text(UserLocale("ServerView_NowSharing"))
        text(model.path)
        text(UserLocale("ServerView_At"))
        hbox(
            5.0,
            Pos.CENTER,
        ) {
            text(model.ipProperty)
            text(":")
            text(model.portProperty)
        }
        text(UserLocale("ServerView_BrowserMessage"))
        textfield {
            whenDocked {
                this.text = """http://${model.ip}:${model.port}"""
            }

            isEditable = false
            alignment = Pos.CENTER
            maxWidth = 200.0

        }
        button(UserLocale("ServerView_StopSharing")) {
            action {
                runAsync {
                    controller.stopServer()
                } ui {
                    replaceWith<StartView>()
                }
            }
        }
    }

    override fun onDock() {
        println("$title on dock")
        val addr = InetAddress.getLocalHost()
        model.ip = addr.hostAddress
    }

    override fun onUndock() {
        println("$title on undock")
    }

}