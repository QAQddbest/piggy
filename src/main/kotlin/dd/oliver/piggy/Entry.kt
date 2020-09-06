package dd.oliver.piggy

import dd.oliver.piggy.view.RootView
import tornadofx.App
import tornadofx.addStageIcon
import tornadofx.launch

class Piggy : App(RootView::class) {
    init {
        addStageIcon(resources.image("/img/icon.png"))
    }
}

fun main(args: Array<String>) {
    launch<Piggy>(*args)
}