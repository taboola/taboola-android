package com.taboola.nativesample4;

import android.app.Application;

import com.taboola.android.api.TaboolaApi;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TaboolaApi.getInstance().init(getApplicationContext(), "betterbytheminute-app",
                "4f1e315900f2cab825a6683d265cef18cc33cd27");
    }
}