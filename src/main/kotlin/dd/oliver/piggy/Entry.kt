package dd.oliver.piggy

import dd.oliver.piggy.view.RootView
import tornadofx.App
import tornadofx.addStageIcon
import tornadofx.launch
import java.util.*

object UserLocale {
    lateinit var locale: Locale
    lateinit var rb: ResourceBundle
    operator fun invoke(key: String): String {
        return rb.getString(key)
    }
}

class Piggy : App(RootView::class) {
    init {
        addStageIcon(resources.image("/img/icon.png"))
    }
}

fun main(args: Array<String>) {
    UserLocale.locale = Locale.getDefault()
    UserLocale.rb = ResourceBundle.getBundle("message", UserLocale.locale)
    launch<Piggy>(*args)
}