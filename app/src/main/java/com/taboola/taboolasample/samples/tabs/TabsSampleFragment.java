package com.taboola.taboolasample.samples.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.TaboolaWidget;
import com.taboola.taboolasample.R;
import com.taboola.taboolasample.adapters.FragmentsAdapter;
import com.taboola.taboolasample.utils.Utils;

public class TabsSampleFragment extends BaseTabFragment<TabsSampleFragment.SampleTaboolaFragment> {

    @Override
    protected void setupViewPagerAdapter(FragmentsAdapter<SampleTaboolaFragment> adapter) {
        super.setupViewPagerAdapter(adapter);
        adapter.addFragment(SampleTaboolaFragment.getInstance());
        adapter.addFragment(SampleTaboolaFragment.getInstance());
        adapter.addFragment(SampleTaboolaFragment.getInstance());
    }


    public static class SampleTaboolaFragment extends BaseTaboolaFragment {

        private TaboolaWidget mTaboolaWidget;
        private boolean mShouldFetch;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_page_sample, container, false);
        }


        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mTaboolaWidget = view.findViewById(R.id.taboola_widget);
            Utils.buildTaboolaWidget(view.getContext(), mTaboolaWidget);

            if (mShouldFetch) {
                mShouldFetch = false;
                mTaboolaWidget.fetchContent();
            }
        }

        @Override
        public void onPageSelected() {
            if (mTaboolaWidget == null) {
                mShouldFetch = true;
            } else {
                mTaboolaWidget.fetchContent();
            }
        }


        public static SampleTaboolaFragment getInstance() {
            SampleTaboolaFragment baseTaboolaFragment = new SampleTaboolaFragment();
            Bundle bundle = new Bundle();
            baseTaboolaFragment.setArguments(bundle);
            return baseTaboolaFragment;
        }

    }
}
