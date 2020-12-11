import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
        named("commonMain") {
            dependencies {
                kotlin("reflect", "1.4.20")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api("com.beust:klaxon:5.4")
            }
        }
        named("androidMain") {
            dependencies {
                api("androidx.appcompat:appcompat:1.3.0-alpha02")
                api("androidx.core:core-ktx:1.5.0-alpha05")
                api("com.google.android.material:material:1.3.0-alpha04")
            }
        }
    }
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "0.1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}
