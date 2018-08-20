package com.taboola.taboolasample.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.utils.SdkDetailsHelper;
import com.taboola.taboolasample.R;


public class EndlessFeedSampleFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endless_feed, container, false);
        TaboolaWidget taboolaWidget = view.findViewById(R.id.taboola_widget);
        taboolaWidget
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setMode("thumbnails-feed")
                .setPlacement("feed-sample-app")
                .setTargetType("mix")
                .setPublisher("betterbytheminute-app");

        taboolaWidget.setInterceptScroll(true);
        taboolaWidget.fetchContent();

        return view;

    }


}
