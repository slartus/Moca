import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import ru.slartus.moca.db.MocaDatabase

actual fun getHttpClient() = DataAppResolve.getHttpClient()

actual class SqlDelightDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MocaDatabase.Schema, context, "moca.db")
    }
}

object DataAppResolve {
    fun getHttpClient(): HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true

            })
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.HEADERS
        }
        HttpResponseValidator {
            validateResponse {
//                val error = response.receive<Error>()
//                if (error. != 0) {
//                    throw CustomResponseException(response, "Code: ${error.code}, message: ${error.message}")
//                }
            }
            handleResponseException { exception ->
                Napier.e("Network error", exception)
                throw Exception("Network error", exception)
//                val exceptionResponse = exception.response
//                if (exceptionResponse.status.value !in 200..201) {
//                    val exceptionResponseText = exceptionResponse.readText()
//                    throw MissingPageException(exceptionResponse, exceptionResponseText)
//                }
            }
        }
    }
}
