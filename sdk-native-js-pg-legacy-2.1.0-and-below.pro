###---------- Begin: proguard configuration for Taboola Android SDK 2.1.0 (including) and below  ----------
### SDK via Native and SDK via JS

-keep class com.taboola.android.monitor.** {*;}
-dontwarn com.taboola.android.api.**
-dontwarn com.google.gson.annotations.**
-dontwarn com.taboola.android.mediation.DfpCustomEventBanner
-dontwarn com.taboola.android.mediation.MoPubCustomEventBanner

###---------- End: proguard configuration for Taboola Android SDK  ----------
