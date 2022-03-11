import java.io.File

actual class AppFile(val file: File) {
    actual val path: String
        get() = file.path

    actual fun appendBytes(bytes: ByteArray) = file.appendBytes(bytes)

    actual fun length(): Long = file.length()

    actual companion object {
        actual fun createTempFile(prefix: String, suffix: String) = AppFile(File("$prefix$suffix"))
    }
}