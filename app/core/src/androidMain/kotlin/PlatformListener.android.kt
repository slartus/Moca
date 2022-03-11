import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
}