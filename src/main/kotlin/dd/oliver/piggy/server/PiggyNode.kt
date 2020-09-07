package dd.oliver.piggy.server

import tornadofx.JsonBuilder
import tornadofx.JsonModel
import javax.json.JsonObject

//enum class PiggyNodeType {
//    DIRECTORY,
//    FILE,
//}

class PiggyNode(
    /**
     * file's name of the node. Example: /temp/a.txt, it's a.txt
     */
    val fileName: String,
    /**
     * file's path of the node. Example: /temp/a.txt, it's /temp/
     */
    val filePath: String,
): JsonModel {
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("fileName", fileName)
            add("filePath", filePath)
        }
    }
}
