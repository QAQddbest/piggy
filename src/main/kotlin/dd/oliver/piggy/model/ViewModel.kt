package dd.oliver.piggy.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

class ViewModel : ViewModel() {
    val pathProperty = SimpleStringProperty(this, "path", "") // 幕后属性
    var path: String by pathProperty

    val ipProperty = SimpleStringProperty(this, "ip", "") // 幕后属性
    var ip: String by ipProperty

    val portProperty = SimpleStringProperty(this, "port", "2333") // 幕后属性
    var port: String by portProperty
}