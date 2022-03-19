import java.io.File

actual class AppFile(private val file: File) {
    actual val path: String
        get() = file.path

    actual fun appendBytes(bytes: ByteArray) = file.appendBytes(bytes)

    actual fun length(): Long = file.length()

    actual companion object {
        actual fun createTempFile(prefix: String, suffix: String): AppFile {
            var fileName = "$prefix$suffix"
            var c = 1;
            while (File(fileName).exists()) {
                fileName = "${prefix} (${c++})$suffix"
            }
            val file = File(fileName)

            return AppFile(file)
        }
    }
}