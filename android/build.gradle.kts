plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
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
}

dependencies {
    implementation(project(":common"))
    implementation(project(":app:core-ui"))
    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.0")


    implementation("io.github.alexgladkov:odyssey-core:0.3.2") // For core classes
    implementation("io.github.alexgladkov:odyssey-compose:0.3.2") // For compose extensions
}