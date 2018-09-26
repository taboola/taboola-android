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

import com.taboola.taboolasample.R;
import com.taboola.taboolasample.adapters.FragmentsAdapter;

public class TabsSampleFragment extends Fragment implements TabsContract.TabsView{

    protected TabsContract.TabsPresenter mPresenter = new TabsPresenterImp();
    private ViewPager mViewPager;
    private FragmentsAdapter<PageFragment> mAdapter;

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
        mViewPager = view.findViewById(R.id.album_tabs_viewpager);
        setupViewPager();
    }


    private void setupViewPager() {
        mAdapter.addFragment(PageFragment.getInstance("page 1"));
        mAdapter.addFragment(PageFragment.getInstance("page 2"));
        mAdapter.addFragment(PageFragment.getInstance("page 3"));
        mViewPager.setAdapter(mAdapter);
        mViewPager.setSaveEnabled(false);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());

        final ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                mPresenter.setCurrentPage(currentItem);
                PageFragment fragment = mAdapter.getItem(currentItem);
                fragment.onPageSelected();
            }
        };
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        mViewPager.post(() -> onPageChangeListener.onPageSelected(mViewPager.getCurrentItem()));
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
