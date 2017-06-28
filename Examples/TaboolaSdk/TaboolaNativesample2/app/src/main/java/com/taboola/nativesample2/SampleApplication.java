package com.taboola.nativesample2;

import android.app.Application;

import com.taboola.android.sdk.TaboolaSdk;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TaboolaSdk.getInstance().init(getApplicationContext(), "betterbytheminute-app",
                "4f1e315900f2cab825a6683d265cef18cc33cd27");
        TaboolaSdk.getInstance().setImagePlaceholder(getResources().getDrawable(R.drawable.image_placeholder));
    }
}
