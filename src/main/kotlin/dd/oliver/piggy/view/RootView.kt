package dd.oliver.piggy.view

import javafx.scene.Parent
import tornadofx.View
import tornadofx.borderpane
import kotlin.system.exitProcess

class RootView : View("RootView") {

    init {
        primaryStage.setOnCloseRequest {
            exitProcess(0)
        }
        primaryStage.minWidth = 400.0
        primaryStage.minHeight = 800.0
        primaryStage.width = 450.0
        primaryStage.height = 900.0
        primaryStage.maxWidth = 500.0
        primaryStage.maxHeight = 1000.0
    }

    private val startView: StartView = find(StartView::class)

    override val root: Parent = borderpane {
        center = startView.root
    }

    override fun onDock() {
        println("$title on dock")
    }

    override fun onUndock() {
        println("$title on undock")
    }
}