package com.taboola.taboolasample.samples.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taboola.taboolasample.R;
import com.taboola.taboolasample.adapters.FragmentsAdapter;

import java.util.Locale;

public abstract class BaseTabFragment<T extends BaseTaboolaFragment> extends Fragment implements TabsContract.TabsView {

    protected TabsContract.TabsPresenter mPresenter = new TabsPresenterImp();
    private FragmentsAdapter<T> mAdapter;
    private ViewPager mViewPager;
    private ViewPager.SimpleOnPageChangeListener mOnPageChangeListener;
    private TextView mTitleTextView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (mAdapter == null) {
            mAdapter = new FragmentsAdapter<>(getChildFragmentManager());
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = view.findViewById(R.id.tabs_viewpager);
        setupViewPagerAdapter(mAdapter);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());

        mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                mPresenter.setCurrentPage(currentItem);
                T fragment = mAdapter.getItem(currentItem);
                fragment.onPageSelected();
            }
        };
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPager.post(() -> mOnPageChangeListener.onPageSelected(mViewPager.getCurrentItem()));

    }

    protected void setupViewPagerAdapter(FragmentsAdapter<T> adapter) {
        adapter.clear();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.takeView(this);
        mPresenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

    @Override
    public void setCurrentPage(int currentPage) {
        mViewPager.setCurrentItem(currentPage, false);
    }

}
