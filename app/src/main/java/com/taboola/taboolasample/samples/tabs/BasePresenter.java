package com.taboola.taboolasample.samples.tabs;

import android.os.Bundle;

public interface BasePresenter<T> {

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    void takeView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();

    void onActivityCreated(Bundle savedInstanceState);

    void onSaveInstanceState(Bundle savedInstanceState);

    void onStart();

    void onStop();

    void onDestroyView();

}