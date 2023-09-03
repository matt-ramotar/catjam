plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 27
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    namespace = "com.dropbox.catjam.sample.android"

    kotlin {
        jvmToolchain(17)
    }
}


dependencies {
    implementation(project(":catjam"))

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.compose.ui:ui:1.6.0-alpha04")
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha04")
    implementation("androidx.compose.material:material:1.6.0-alpha04")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-json:2.2.4")
    implementation("io.ktor:ktor-client-serialization:2.2.4")

    implementation("androidx.activity:activity-compose:1.7.2")
}