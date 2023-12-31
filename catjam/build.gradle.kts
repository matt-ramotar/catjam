import com.vanniktech.maven.publish.SonatypeHost.S01

plugins {
    kotlin("android")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.vanniktech.maven.publish")
}



dependencies {
    api(libs.androidx.compose.material3)

    implementation(libs.ktor.client.core)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.store)

    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.coil.compose)
    implementation(libs.coil.core)
    implementation(libs.coil.gif)
}


android {

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    compileSdk = 34
    namespace = "xyz.ramotar.catjam"

    kotlin {
        jvmToolchain(17)
    }
}

mavenPublishing {
    publishToMavenCentral(S01)
    signAllPublications()
}