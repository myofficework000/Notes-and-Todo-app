buildscript {
    dependencies {
        // Pin the Kotlin toolchain used by AGP's built-in Kotlin support.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.21")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.3.10")
    }
}

plugins {
    id("com.android.application") version "9.4.1" apply false
}
