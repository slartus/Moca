import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":app:core"))
                implementation(project(":app:data"))
                implementation(project(":common"))
                implementation(project(":app:core-ui"))
                implementation(Dependencies.Navigation.odysseyCore)
                implementation(Dependencies.Navigation.odysseyCore)
                implementation(Dependencies.Navigation.odysseyCompose)
                implementation(Dependencies.Logging.napier)
                implementation(Dependencies.DI.kodein)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinMultiplatformComposeDesktopApplication"
            packageVersion = "1.0.0"
        }
    }
}
