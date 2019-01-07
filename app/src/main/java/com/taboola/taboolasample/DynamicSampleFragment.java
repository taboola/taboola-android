package com.taboola.taboolasample;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.listeners.TaboolaEventListener;


public class DynamicSampleFragment extends Fragment {

    private TaboolaWidget mTaboolaView;

    public DynamicSampleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dynamic_sample, container, false);

        if (mTaboolaView == null) {
            mTaboolaView = new TaboolaWidget(inflater.getContext());
            buildTaboolaWidget(mTaboolaView);
        }

        LinearLayout adContainer = rootView.findViewById(R.id.ad_container);

        // Optional. Set targetType only if it's specified by your Taboola account manager
        // taboola.setTargetType("<my-target-type>");

        // Optional - Setting the event listener to intercept clicks and/or size changes
        mTaboolaView.setTaboolaEventListener(new TaboolaEventListener() {
            @Override
            public boolean taboolaViewItemClickHandler(String s, boolean b) {
                return true;
            }

            @Override
            public void taboolaViewResizeHandler(TaboolaWidget taboolaWidget, int i) {
            }
        });

        adContainer.addView(mTaboolaView);
        mTaboolaView.fetchContent();

        return rootView;
    }

    static void buildTaboolaWidget(TaboolaWidget taboolaWidget) {
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        taboolaWidget.setLayoutParams(params);
        taboolaWidget
                .setPublisher("sdk-tester")
                .setMode("alternating-widget-without-video")
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setPlacement("Below Article");
    }
}
