package com.taboola.taboolasample.samples.tabs;

import android.os.Bundle;

public class TabsPresenterImp implements TabsContract.TabsPresenter {

    private static final String TAG = "PagePresenterImp";
    private static final String PAGE_KEY = TAG + " " + "page_key";
    private TabsContract.TabsView mAlbumTabsView;
    private int mCurrentPage;


    @Override
    public void takeView(TabsContract.TabsView view) {
        mAlbumTabsView = view;
    }

    @Override
    public void dropView() {
        mAlbumTabsView = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        mCurrentPage = savedInstanceState.getInt(PAGE_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        out.putInt(PAGE_KEY, mCurrentPage);
    }

    @Override
    public void onStart() {
        mAlbumTabsView.setCurrentPage(mCurrentPage);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {
        dropView();
    }


    @Override
    public void setCurrentPage(int position) {
        mCurrentPage = position;
    }
}
