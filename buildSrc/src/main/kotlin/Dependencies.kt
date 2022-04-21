object Dependencies {
    object DI {
        const val kodein = "org.kodein.di:kodein-di-framework-compose:7.10.0"
    }

    object Navigation {
        private const val odyssey = "1.0.0-beta05"
        const val odysseyCore = "io.github.alexgladkov:odyssey-core:$odyssey"// For core classes
        const val odysseyCompose =
            "io.github.alexgladkov:odyssey-compose:$odyssey" // For compose extensions
    }

    object Network {
        private const val ktor = "1.6.8"
        const val ktorClientCore = "io.ktor:ktor-client-core:$ktor"
        const val ktorClientSerialization = "io.ktor:ktor-client-serialization:$ktor"
        const val ktorClientCio = "io.ktor:ktor-client-cio:$ktor"
        const val ktorClientLogging = "io.ktor:ktor-client-logging:$ktor"
    }

    object DB {
        object SqlDelight {
            private const val sqlDelight = "1.5.3"

            const val android = "com.squareup.sqldelight:android-driver:$sqlDelight"
            const val sqlite = "com.squareup.sqldelight:sqlite-driver:$sqlDelight"
            const val coroutines= "com.squareup.sqldelight:coroutines-extensions:$sqlDelight"
        }
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
    }

    object Logging {
        const val napier = "io.github.aakira:napier:2.4.0"
    }

    object Tools {
        const val uuid = "com.benasher44:uuid:0.4.0"
    }

    object AndroidCompose {
        const val coil = "io.coil-kt:coil-compose:1.4.0"
        const val activity = "androidx.activity:activity-compose:1.4.0"

        object Accompanist {
            private const val accompanist = "0.23.0"
            const val systemuicontroller =
                "com.google.accompanist:accompanist-systemuicontroller:$accompanist"
            const val insets =
                "com.google.accompanist:accompanist-insets:$accompanist"
            const val insetsUi =
                "com.google.accompanist:accompanist-insets-ui:$accompanist"
        }
    }
}