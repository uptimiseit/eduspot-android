// root build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false

    // Use KAPT (not KSP) for annotation processors
    alias(libs.plugins.kotlin.kapt) apply false

    // Hilt + Firebase (available to modules)
    alias(libs.plugins.hilt) apply false

    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


//id("com.google.gms.google-services") version "4.4.4" apply false
//
//plugins {
//    id("com.android.application")
//
//    // Add the Google services Gradle plugin
//    id("com.google.gms.google-services")
//
//    ...
//}
//
//dependencies {
//    // Import the Firebase BoM
//    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
//
//
//    // TODO: Add the dependencies for Firebase products you want to use
//    // When using the BoM, don't specify versions in Firebase dependencies
//    implementation("com.google.firebase:firebase-analytics")
//
//
//    // Add the dependencies for any other desired Firebase products
//    // https://firebase.google.com/docs/android/setup#available-libraries
//}