package dd.oliver.piggy.view

import dd.oliver.piggy.controller.StartController
import dd.oliver.piggy.model.ViewModel
import javafx.geometry.Insets
import javafx.geometry.Pos
import tornadofx.*

class StartView : View("StartView") {

    private val model: ViewModel by inject()

    private val controller: StartController by inject()

    override val root = form {

        vbox(
            20.0,
            Pos.CENTER,
        ) {
            imageview(resources.image("/img/logo.png")) {
                alignment = Pos.CENTER
            }
            text("Piggy")
            padding = Insets(20.0, 20.0, 20.0, 20.0)
        }

        fieldset {

            field("路径") {
                textfield(model.pathProperty) {
                    setOnMouseClicked {
                        val pathFile = chooseDirectory("选择共享路径", null, primaryStage)
                        model.path = pathFile?.absolutePath ?: "未选择路径"
                    }
                    isEditable = false
                    validator(ValidationTrigger.OnChange()) {
                        if (controller.checkPath()) {
                            null
                        } else {
                            error("路径不存在或不是路径")
                        }
                    }
                }

                padding = Insets(10.0, 20.0, 10.0, 20.0)
            }

            field("端口") {
                textfield(model.portProperty) {
                    validator(ValidationTrigger.OnChange(250)) {
                        var message: ValidationMessage? = null
                        if (it != null) {
                            message = if (!it.isInt())
                                error("端口号输入不正确")
                            else if (it.toInt() >= 65536)
                                error("端口号不能大于65536")
                            else if (it.toInt() in 0..1024)
                                error("端口号不能为周知端口号")
                            else if (it.toInt() < 0)
                                error("端口号不能为负数")
                            else
                                null
                        }
                        message
                    }
                }
                padding = Insets(10.0, 20.0, 10.0, 20.0)
            }
        }

        button("开始共享") {
            action {
                // Start server
                runAsync {
                    controller.startServer()
                } ui { // Change view
                    replaceWith<ServerView>()
                }
            }
            enableWhen(model.valid)
        }
        alignment = Pos.CENTER
    } // form

    override fun onDock() {
        println("$title on dock")
    }

    override fun onUndock() {
        println("$title on undock")
    }

} // view
