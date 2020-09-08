package dd.oliver.piggy.view

import dd.oliver.piggy.UserLocale
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
            paddingAll = 20.0
        }

        fieldset {

            field(UserLocale("StartView_Path")) {
                textfield(model.pathProperty) {
                    setOnMouseClicked {
                        val pathFile =
                            chooseDirectory(UserLocale("StartView_DirectoryChooserTitle"), null, primaryStage)
                        model.path = pathFile?.absolutePath ?: UserLocale("StartView_PathNotChooseMessage")
                    }
                    isEditable = false
                    validator(ValidationTrigger.OnChange()) {
                        if (controller.checkPath()) {
                            null
                        } else {
                            error(UserLocale("StartView_PathNotClearPopUp"))
                        }
                    }
                }

                padding = Insets(10.0, 20.0, 10.0, 20.0)
            }

            field(UserLocale("StartView_Port")) {
                textfield(model.portProperty) {
                    validator(ValidationTrigger.OnChange(250)) {
                        var message: ValidationMessage? = null
                        if (it != null) {
                            message = if (!it.isInt())
                                error(UserLocale("StartView_PortNotRight"))
                            else if (it.toInt() >= 65536)
                                error(UserLocale("StartView_PortCantGreaterTo"))
                            else if (it.toInt() in 0..1024)
                                error(UserLocale("StartView_PortCantBeWellKnown"))
                            else if (it.toInt() < 0)
                                error(UserLocale("StartView_PortCantLessTo"))
                            else
                                null
                        }
                        message
                    }
                }
                padding = Insets(10.0, 20.0, 10.0, 20.0)
            }
        }

        button(UserLocale("StartView_StartSharing")) {
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
