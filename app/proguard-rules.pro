# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

##############################################
#           GENERAL KEEP RULES
##############################################

# Keep Kotlin metadata for reflection, coroutines, serialization
-keep class kotlin.Metadata { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# Keep generic signatures (important for Retrofit & Room)
-keepattributes Signature
-keepattributes *Annotation*


##############################################
#           COMPOSE RULES
##############################################

# Keep all composable annotated methods
-keep class androidx.compose.** { *; }
-keep class androidx.activity.compose.** { *; }
-dontwarn androidx.compose.**

# Prevent minification issues with preview
-keep class androidx.compose.ui.tooling.preview.PreviewParameterProvider { *; }

# Avoid removal of essential compose runtime classes
-keep class androidx.compose.runtime.** { *; }


##############################################
#           HILT / DAGGER RULES
##############################################

# Keep inject constructors
-keep class * {
    @javax.inject.Inject <init>(...);
}

# Dagger/Hilt required internal classes
-keep class dagger.hilt.internal.** { *; }
-keep class dagger.hilt.android.internal.** { *; }
#-keep class androidx.hilt.work.HiltWorkerFactory { *; }

# Do not warn about dagger internal things
-dontwarn dagger.**
-dontwarn javax.inject.**


##############################################
#           RETROFIT + KOTLINX SERIALIZATION
##############################################

# Keep Retrofit interfaces
-keep interface com.dw.eduspot.data.remote.api.** { *; }

# Serialization models cannot be obfuscated
-keep class kotlinx.serialization.** { *; }
-keep class kotlinx.serialization.json.** { *; }

# Keep @Serializable models (DTOs)
-keep class com.dw.eduspot.data.remote.dto.** { *; }

# Retrofit warnings suppression
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**


##############################################
#           ROOM DATABASE RULES
##############################################

# Keep Room entities & DAO interfaces
-keep class androidx.room.** { *; }
-keep class com.dw.eduspot.data.local.entity.** { *; }
-keep interface com.dw.eduspot.data.local.dao.** { *; }

# Room uses reflection
-keep class com.dw.eduspot.data.local.** { *; }


##############################################
#           FIREBASE (Auth, Messaging, Crashlytics)
##############################################

# Firebase Messaging
#-keep class com.google.firebase.messaging.** { *; }
#-keep public class * extends com.google.firebase.messaging.FirebaseMessagingService { *; }

# Firebase Auth
-keep class com.google.firebase.auth.** { *; }

# Firebase Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }
-keepattributes SourceFile,LineNumberTable


##############################################
#           WORKMANAGER RULES
##############################################

# Keep workers – WorkManager instantiates via reflection
-keep class * extends androidx.work.Worker { *; }
-keep class * extends androidx.work.CoroutineWorker { *; }
-keep class androidx.work.impl.** { *; }
-dontwarn androidx.work.impl.**


##############################################
#           DATASTORE RULES
##############################################

# DataStore + Proto
-dontwarn androidx.datastore.**
-keep class androidx.datastore.** { *; }


##############################################
#           NAVIGATION & DEEP LINKS
##############################################

# Keep navigation destinations & deep link metadata
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**


##############################################
#           ANDROID CORE
##############################################

-keep class android.support.v4.** { *; }
-keep class androidx.** { *; }
-dontwarn android.support.**


##############################################
#           LOG + UTILS (Optional)
##############################################

# Keep Timber logs (if using)
-dontwarn timber.**