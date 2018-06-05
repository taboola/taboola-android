package com.taboola.taboolasample;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.TaboolaWidget;


public class EndlessFeedFragment extends Fragment {

    private TaboolaWidget mTaboolaWidget;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_endless_feed, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTaboolaWidget = view.findViewById(R.id.taboola_widget);
        mTaboolaWidget
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setMode("thumbnails-feed")
                .setPlacement("feed-sample-app")
                .setTargetType("mix")
                .setPublisher("betterbytheminute-app");

        mTaboolaWidget.fetchContent();
    }

}
