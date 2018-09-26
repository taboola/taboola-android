package com.taboola.taboolasample.samples.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.TaboolaWidget;
import com.taboola.taboolasample.R;

public class PageFragment extends Fragment {

    public static final String SCREEN_NAME = "ScreenName";
    private TaboolaWidget mTaboolaWidget;
    private OnFragmentInteractionListener mListener;
    private String mScreenName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mScreenName = getArguments().getString(SCREEN_NAME);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_page_sample, container, false);

        mTaboolaWidget = rootView.findViewById(R.id.taboola_widget);
        mTaboolaWidget
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setMode("thumbnails-feed")
                .setPlacement("feed-sample-app")
                .setTargetType("mix")
                .setPublisher("betterbytheminute-app");

        mTaboolaWidget.setViewId(mListener.getViewID());
        mTaboolaWidget.setInterceptScroll(true);
        mTaboolaWidget.getSettings().setNeedInitialFocus(false);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void onPageSelected() {
        mTaboolaWidget.fetchContent();
    }

    public interface OnFragmentInteractionListener {
        String getViewID();
    }

    public static PageFragment getInstance(String screenName) {
        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SCREEN_NAME, screenName);
        pageFragment.setArguments(bundle);
        return pageFragment;
    }
}
