package com.taboola.taboolasample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.listeners.TaboolaEventListener;


public class DynamicSampleFragment extends Fragment {

    public DynamicSampleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dynamic_sample, container, false);
        LinearLayout adContainer = (LinearLayout) rootView.findViewById(R.id.ad_container);

        TaboolaWidget taboola = new TaboolaWidget(getContext());
        taboola.setPublisher("betterbytheminute")
                .setMode("thumbnails-a")
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setPlacement("Mobile");

        // Optional - Setting the event listener to intercept clicks and/or size changes
        taboola.setTaboolaEventListener(new TaboolaEventListener() {
            @Override
            public boolean taboolaViewItemClickHandler(String s, boolean b) {
                return true;
            }

            @Override
            public void taboolaViewResizeHandler(TaboolaWidget taboolaWidget, int i) {
            }
        });

        adContainer.addView(taboola);
        taboola.fetchContent();

        return rootView;
    }
}