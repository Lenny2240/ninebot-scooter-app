# ProGuard rules for Ninebot Scooter App

# Keep all classes in our app
-keep class com.ninebot.scooterapp.** { *; }

# Keep serialization classes
-keepclassmembers class * implements kotlinx.serialization.KSerializer {
    *** INSTANCE;
}
-keepclasseswithmembers class * {
    kotlinx.serialization.SerialName <methods>;
}

# Keep Room database classes
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }

# Keep Koin
-keep class org.koin.** { *; }

# Keep Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepattributes Signature, InnerClasses, EnclosingMethod

# Keep OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Keep Sentry
-keep class io.sentry.** { *; }

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}