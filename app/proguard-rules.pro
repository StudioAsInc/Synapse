# Basic rules
-repackageclasses
-ignorewarnings
-dontwarn
-dontnote

# ======== Kotlin-specific rules ========
-keepattributes RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }
-keepclassmembers class **.kotlin.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# ======== Android components ========
# Activities
-keep public class * extends android.app.Activity
-keep public class * extends androidx.appcompat.app.AppCompatActivity

# Your specific activity
-keep class com.synapse.social.studioasinc.N3 { *; }

# ======== Firebase/Crashlytics ========
-keep class com.google.firebase.** { *; }
-keep class com.crashlytics.** { *; }
-keepattributes SourceFile,LineNumberTable

# ======== Glide ========
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# ======== Parcelables ========
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# ======== View Binding ========
-keepclassmembers class * {
    @androidx.viewbinding.BindView *;
}