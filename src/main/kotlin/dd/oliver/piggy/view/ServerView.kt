package dd.oliver.piggy.view

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
        text("正在共享")
        text(model.path)
        text("于")
        hbox(
            5.0,
            Pos.CENTER,
        ) {
            text(model.ipProperty)
            text(":")
            text(model.portProperty)
        }
        text("你可以在浏览器中输入以下链接以访问服务器")
        textfield {
            whenDocked {
                this.text = """http:\\${model.ip}:${model.port}"""
            }

            isEditable = false
            alignment = Pos.CENTER
            maxWidth = 200.0

        }
        button("结束共享") {
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