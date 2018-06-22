# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Conor\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-dontshrink
-dontoptimize
-dontpreverify
-verbose

-keep class javax.** { *; }
-keep class org.** { *; }
-keep class twitter4j.** { *; }

# Bottom Navigation View Shifting https://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode
-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
    boolean mShiftingMode;
}

# Map marker alpha property modified by reflection
-keep class com.google.android.gms.maps.model.Marker { *; }

# Fabric Crashlytics Stuff
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-dontwarn dagger.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.simpleframework.xml.**
-dontwarn pl.charmas.android.reactivelocation2.**
-dontwarn retrofit2.**
-dontwarn com.google.**
-dontwarn afu.org.checkerframework.**
-dontwarn org.checkerframework.**
