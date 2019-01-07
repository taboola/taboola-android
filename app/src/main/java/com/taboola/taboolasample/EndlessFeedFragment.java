package com.taboola.taboolasample;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.utils.SdkDetailsHelper;


public class EndlessFeedFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_endless_feed, container, false);
        TaboolaWidget taboolaWidget = view.findViewById(R.id.taboola_widget);
        buildTaboolaWidget(inflater.getContext(), taboolaWidget);
        taboolaWidget.fetchContent();
        return view;
    }


    static void buildTaboolaWidget(Context context, TaboolaWidget taboolaWidget) {
        taboolaWidget.getLayoutParams().height = SdkDetailsHelper.getDisplayHeight(context);
        taboolaWidget
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setMode("thumbs-feed-01")
                .setPlacement("Feed without video")
                .setTargetType("mix")
                .setPublisher("sdk-tester");
        taboolaWidget.setFocusable(false);
        taboolaWidget.setInterceptScroll(true);
    }

}
