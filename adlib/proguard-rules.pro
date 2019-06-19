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

#如果您需要使用 proguard 混淆代码，需确保不要混淆 SDK 的代码和 support-v4 和 support-v7。
    -keep class com.qq.e.** {
        public protected *;
    }
    -keep class android.support.v4.**{
        public *;
    }
    -keep class android.support.v7.**{
        public *;
    }

#如果您使用的是 X5 内核加强版 SDK，还需要在混淆配置文件中加入下面的代码。
     -keep class MTT.ThirdAppInfoNew {
            *;
        }
      -keep class com.tencent.** {
            *;
        }
      -dontwarn dalvik.**
      -dontwarn com.tencent.smtt.**