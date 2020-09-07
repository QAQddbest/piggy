package dd.oliver.piggy.server

import org.http4k.template.ViewModel

//enum class PiggyNodeType {
//    DIRECTORY,
//    FILE,
//}

class PiggyList(
    val lists: MutableList<PiggyNode>
) : ViewModel

class PiggyNode(
    /**
     * file's name of the node. Example: /temp/a.txt, it's a.txt
     */
    val fileName: String,
    /**
     * file's path of the node. Example: /temp/a.txt, it's /temp/
     */
    val filePath: String,
)
