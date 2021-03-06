import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import ru.slartus.moca.db.MocaDatabase
import java.io.File

actual fun getHttpClient() = DataAppResolve.getHttpClient()

actual class SqlDelightDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:moca.db")
        if (!File("moca.db").exists()) {
            MocaDatabase.Schema.create(driver)
        }
        return driver
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
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        HttpResponseValidator {
            validateResponse { response ->
//                val error = response.receive<Error>()
//                if (error. != 0) {
//                    throw CustomResponseException(response, "Code: ${error.code}, message: ${error.message}")
//                }
            }
            handleResponseException { exception ->
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