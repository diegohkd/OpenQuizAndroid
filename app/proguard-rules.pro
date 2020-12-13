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
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

#-dontobfuscate
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable
#-keepattributes *Annotation*

-keep public class * extends java.lang.Exception

-keep class mobdao.com.openquiz.models.Question
-keep class mobdao.com.openquiz.utils.pokos.ResultsReport

-keep class mobdao.com.openquiz.data.server.responses.QuestionsResponse { *; }
-keep class mobdao.com.openquiz.data.server.responses.QuestionResponse { *; }
-keep class mobdao.com.openquiz.data.server.responses.SessionTokenResponse { *; }

-keepclassmembers enum mobdao.com.openquiz.models.Category { *; }
-keepclassmembers enum mobdao.com.openquiz.models.QuestionType { *; }
-keepclassmembers enum mobdao.com.openquiz.models.Difficulty { *; }