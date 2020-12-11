buildscript {
    repositories {
        google()
        jcenter()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath("org.jetbrains.compose:compose-gradle-plugin:0.3.0-build134")
        classpath("com.android.tools.build:gradle:7.0.0-alpha02")
        classpath(kotlin("gradle-plugin", "1.4.20"))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://kotlin.bintray.com/kotlinx/")
    }
}