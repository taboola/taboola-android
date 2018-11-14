package com.taboola.taboolasample.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.utils.SdkDetailsHelper;

public class Utils {
    public static void buildTaboolaWidget(Context context, TaboolaWidget taboolaWidget) {
        int height = SdkDetailsHelper.getDisplayHeight(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        taboolaWidget.setLayoutParams(params);
        taboolaWidget
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setMode("thumbnails-feed")
                .setPlacement("feed-sample-app")
                .setTargetType("mix")
                .setPublisher("betterbytheminute-app");

        taboolaWidget.setInterceptScroll(true);
        taboolaWidget.fetchContent();
    }
}
