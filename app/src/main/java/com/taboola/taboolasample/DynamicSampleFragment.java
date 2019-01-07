package com.taboola.taboolasample;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.listeners.TaboolaEventListener;
import com.taboola.android.utils.Properties;
import com.taboola.android.utils.SdkDetailsHelper;

import java.util.HashMap;


public class DynamicSampleFragment extends Fragment {

    private TaboolaWidget mTaboolaView;

    public DynamicSampleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dynamic_sample, container, false);


        if (mTaboolaView == null) {
            mTaboolaView = new TaboolaWidget(inflater.getContext());
            buildTaboolaWidget(inflater.getContext(), mTaboolaView);
        }

        LinearLayout adContainer = (LinearLayout) rootView.findViewById(R.id.ad_container);

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

    static void buildTaboolaWidget(Context context, TaboolaWidget taboolaWidget) {
        int height = SdkDetailsHelper.getDisplayHeight(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        taboolaWidget.setLayoutParams(params);
        taboolaWidget
                .setPublisher("betterbytheminute-app")
                .setMode("thumbnails-sdk3")
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setPlacement("Mobile");

        taboolaWidget.setInterceptScroll(true);
    }
}
