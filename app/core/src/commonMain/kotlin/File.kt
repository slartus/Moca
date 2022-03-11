expect class AppFile {
    val path: String
    fun appendBytes(bytes: ByteArray)
    fun length(): Long

    companion object {
        fun createTempFile(prefix: String, suffix: String): AppFile
    }
}