package dd.oliver.piggy.view

import javafx.scene.Parent
import tornadofx.View
import tornadofx.borderpane
import kotlin.system.exitProcess

class RootView : View("Piggy") {

    init {
        primaryStage.setOnCloseRequest {
            exitProcess(0)
        }
        primaryStage.minWidth = 350.0
        primaryStage.minHeight = 600.0
        primaryStage.width = 400.0
        primaryStage.height = 700.0
        primaryStage.maxWidth = 450.0
        primaryStage.maxHeight = 800.0
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