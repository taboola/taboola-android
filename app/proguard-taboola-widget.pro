###---------- Begin: proguard configuration for Taboola widget  ----------

# add if NOT using API SDK
-dontwarn com.taboola.android.api.**

# add if NOT using DFP mediation
-dontwarn com.taboola.android.mediation.DfpCustomEventBanner

# add if NOT using MoPub mediation
-dontwarn com.taboola.android.mediation.MoPubCustomEventBanner

# add if using DFP mediation
#-keep class com.taboola.android.mediation.DfpCustomEventBanner** { *; }

# add if using MoPub mediation
#-keep class com.taboola.android.mediation.MoPubCustomEventBanner** { *; }

###---------- End: proguard configuration for Taboola widget  ----------