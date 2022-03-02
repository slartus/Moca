import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.6.10"
}

kotlin {
    android()
    jvm("desktop")

    val ktor_version = "1.6.3"
    val accompanist = "0.24.3-alpha"
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":app:core-ui"))
                implementation(project(":app:data"))
                implementation(project(":app:domain"))
                implementation(project(":app:feature-main"))

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                // Needed only for preview.
                implementation(compose.preview)
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("org.kodein.di:kodein-di-framework-compose:7.9.0")

                implementation("io.github.alexgladkov:odyssey-core:0.3.2") // For core classes
                implementation("io.github.alexgladkov:odyssey-compose:0.3.2") // For compose extensions
            }
        }
        named("androidMain") {
            dependencies {
                api("androidx.appcompat:appcompat:1.4.1")
                api("androidx.core:core-ktx:1.7.0")
            }
        }
        named("desktopMain") {
            dependencies {
            }
        }
    }
}

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
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