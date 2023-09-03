buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")

    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.jetbrains.compose.gradle.plugin)
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.serialization.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }

    group = "xyz.ramotar"
    version = "1.0.0-SNAPSHOT"
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
}
