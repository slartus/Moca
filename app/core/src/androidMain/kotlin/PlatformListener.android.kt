import android.content.*
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import java.lang.ref.WeakReference


actual class PlatformListener(context: Context) {
    private val contextWeakRef = WeakReference(context)
    private val context get() = contextWeakRef.get()!!
    actual fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context, ex.message ?: ex.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    actual fun openFile(appFile: AppFile) {
        try {
            val fileUri =
                FileProvider.getUriForFile(context, "ru.slartus.moca.provider", appFile.file)

            val intent = Intent(Intent.ACTION_VIEW, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            /// intent.setDataAndType(fileUri, contentResolver.getType(fileUri))
            context.startActivity(intent)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }

    actual fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(context, ClipboardManager::class.java)
        val clip = ClipData.newPlainText("", text)
        clipboardManager?.setPrimaryClip(clip)
    }

    actual fun playUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setDataAndType(Uri.parse(url), "video/mp4")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }
}