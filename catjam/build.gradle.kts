plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    android()
    jvm()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.material3)

                implementation(libs.ktor.client.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.store)

                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.coil.compose)
                implementation(libs.coil.core)
                implementation("io.coil-kt:coil-gif:2.4.0")
            }
        }
    }

}

android {

    defaultConfig {
        minSdk = 27
    }

    compileSdk = 34
    namespace = "com.dropbox.catjam.catjam"

    kotlin {
        jvmToolchain(17)
    }
}
