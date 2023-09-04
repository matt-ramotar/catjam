pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("android").version(extra["kotlin.version"] as String)
        id("com.android.application").version(extra["agp.version"] as String)
        id("com.android.library").version(extra["agp.version"] as String)
        id("io.ktor.plugin").version(extra["ktor.version"] as String)
        id("app.cash.sqldelight").version("2.0.0-alpha05")
        id("org.gradle.toolchains.foojay-resolver-convention") version("0.5.0")
    }
}

rootProject.name = "catjam"

include(":catjam")
include(":sample:android")