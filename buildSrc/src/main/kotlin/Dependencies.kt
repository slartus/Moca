object Dependencies {
    object DI {
        const val kodein = "org.kodein.di:kodein-di-framework-compose:7.9.0"
    }

    object Navigation {
        private const val odyssey = "0.4.0"
        const val odysseyCore = "io.github.alexgladkov:odyssey-core:$odyssey"// For core classes
        const val odysseyCompose =
            "io.github.alexgladkov:odyssey-compose:$odyssey" // For compose extensions
    }

    object Network {
        private const val ktor = "1.6.3"
        const val ktorClientCore = "io.ktor:ktor-client-core:$ktor"
        const val ktorClientSerialization = "io.ktor:ktor-client-serialization:$ktor"
        const val ktorClientCio = "io.ktor:ktor-client-cio:$ktor"
        const val ktorClientLogging = "io.ktor:ktor-client-logging:$ktor"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
    }

    object AndroidCompose {
        const val coil = "io.coil-kt:coil-compose:1.4.0"
        const val activity = "androidx.activity:activity-compose:1.4.0"

        object Accompanist{
            const val systemuicontroller="com.google.accompanist:accompanist-systemuicontroller:0.23.0"
        }
    }
}