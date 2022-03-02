plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
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
}

dependencies {
    implementation(project(":common"))
    implementation(project(":app:core-ui"))
    implementation(Dependencies.AndroidCompose.activity)

    implementation(Dependencies.AndroidCompose.Accompanist.systemuicontroller)


    implementation(Dependencies.Navigation.odysseyCore)
    implementation(Dependencies.Navigation.odysseyCompose)
    implementation(Dependencies.Logging.napier)
}