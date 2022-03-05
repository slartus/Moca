buildscript {
    // __LATEST_COMPOSE_RELEASE_VERSION__
    val composeVersion = "1.1.0"

    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    dependencies {
        classpath("org.jetbrains.compose:compose-gradle-plugin:$composeVersion")
        classpath("com.android.tools.build:gradle:4.1.3")
        // __KOTLIN_COMPOSE_VERSION__
        classpath(kotlin("gradle-plugin", version = "1.6.10"))
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
