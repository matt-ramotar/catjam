buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")

    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.serialization.plugin)
        classpath(libs.maven.publish.plugin)
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
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
}
