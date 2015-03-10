-keepattributes Signature,SourceFile,LineNumberTable
-ignorewarnings
-dontwarn android.support.v4.**,**CompatHoneycomb,com.tenpay.android.**
-optimizations !class/unboxing/enum
-verbose
######  add for roboguice3.0 begin ##########
-keep class com.google.inject.**{
    *;
}
-keep public class roboguice.**{
    *;
}
-keep class * extends com.google.inject.Module{
    *;
}
-keepclassmembers class * {
    @javax.inject.Inject <init>(...);
    @com.google.inject.Inject <init>(...);
    @javax.inject.Inject <fields>;
    @com.google.inject.Inject <fields>;
    <init>();
}
######  add for roboguice3.0 end ##########

-keep public class com.actionbarsherlock.** { *; }


-keep class com.liuruining.model.**{
    *;
}
-keepnames public class * extends android.support.v4.app.Fragment
-keep class android.support.v4.view.ViewPager.** {*;}
-keep class * extends android.support.v4.view.ViewPager{*;}

-keep class **.R$* {*;}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.google.gson.** { *; }
-keep class com.google.gson.JsonObject { *; }
-keep class com.google.gson.examples.android.model.** { *; }