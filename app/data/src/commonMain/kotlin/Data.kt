import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*


expect fun getHttpClient(): HttpClient
expect class SqlDelightDriverFactory {
    fun createDriver(): SqlDriver
}
