expect class PlatformListener {
    fun openUrl(url: String)
    fun openFile(appFile: AppFile)
    fun copyToClipboard(text: String)
}