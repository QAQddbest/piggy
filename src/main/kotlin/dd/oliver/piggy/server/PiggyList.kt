package dd.oliver.piggy.server

sealed class PiggyObject(
    open val fileName: String,
    open val filePath: String,
)

data class PiggyDirectory(
    override val fileName: String,
    override val filePath: String,
): PiggyObject(fileName, filePath)

data class PiggyFile(
    override val fileName: String,
    override val filePath: String,
): PiggyObject(fileName, filePath)
