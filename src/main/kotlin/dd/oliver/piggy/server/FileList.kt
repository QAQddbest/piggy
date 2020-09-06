package dd.oliver.piggy.server

import org.http4k.template.ViewModel

data class FileList(
    val list: List<FileObject>
) : ViewModel

data class FileObject(
    val fileName: String,
    val filePath: String,
)