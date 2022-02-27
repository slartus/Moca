import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

actual fun getPlatformName(): String = "Android"

actual fun getHttpClient(): HttpClient = HttpClient(CIO){
    install(JsonFeature){
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
        })
    }
}