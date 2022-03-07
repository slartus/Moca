import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
}