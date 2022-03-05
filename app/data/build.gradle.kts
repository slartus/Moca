import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":app:domain"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                // Needed only for preview.
                implementation(compose.preview)
                implementation(Dependencies.Network.ktorClientCore)
                implementation(Dependencies.Network.ktorClientSerialization)
                implementation(Dependencies.Network.ktorClientLogging)
                implementation(Dependencies.DI.kodein)
                implementation(Dependencies.Logging.napier)

                implementation(Dependencies.DB.SqlDelight.sqlite)
            }
        }
        named("androidMain") {
            dependencies {
                api(Dependencies.AndroidX.appcompat)
                api(Dependencies.AndroidX.coreKtx)
                implementation(Dependencies.Network.ktorClientCio)
                implementation(Dependencies.DB.SqlDelight.android)
            }
        }
        named("desktopMain") {
            dependencies {
                implementation(Dependencies.Network.ktorClientCio)
                implementation(Dependencies.DB.SqlDelight.sqlite)
            }
        }
    }
}

android {
    compileSdkVersion(Config.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

sqldelight {
    database("MocaDatabase") {
        packageName = "ru.slartus.moca.db"
    }
}