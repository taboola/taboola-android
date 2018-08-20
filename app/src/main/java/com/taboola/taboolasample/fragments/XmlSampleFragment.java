package com.taboola.taboolasample.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.listeners.TaboolaEventListener;
import com.taboola.taboolasample.R;


public class XmlSampleFragment extends Fragment {

    public XmlSampleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_xml_sample, container, false);
        TaboolaWidget taboola = (TaboolaWidget) rootView.findViewById(R.id.taboola_widget);

        // Not setting any params here, everything is set in the layout XML

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

        taboola.fetchContent();

        return rootView;
    }
}