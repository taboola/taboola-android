###---------- Begin: proguard configuration for Taboola SDK API  ----------
-keepnames class com.taboola.android.integration_verifier.testing.tests.proguard_stub.ProguardVerificationStub
-keep class com.taboola.android.monitor.** {*;}
-dontwarn com.google.gson.annotations.**
###---------- End: proguard configuration for Taboola API  ----------

#### All of the rules below are for Taboola API dependencies. You don't have to modify them

###--------------- Begin: proguard configuration for Gson ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

-keepattributes EnclosingMethod

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

###--------------- End: proguard configuration for Gson ----------

###--------------- Begin: proguard configuration for Picasso ----------
-dontwarn com.squareup.okhttp.**

###--------------- End: proguard configuration for Picasso ------
