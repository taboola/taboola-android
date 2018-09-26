package com.taboola.taboolasample.samples;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.utils.Const;
import com.taboola.android.utils.Logger;
import com.taboola.taboolasample.Properties;
import com.taboola.taboolasample.R;

import java.util.HashMap;
import java.util.Map;

public class ConfigFragment extends Fragment implements SettingsDialogFragment.SettingsCallback {
    private TaboolaWidget mTaboolaWidget;
    private Map<String, String> mWidgetProperties = new HashMap<>();
    private Properties mProperties = new Properties();
    private View mRootView;

    public ConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_config, container, false);
        Button reloadButton = mRootView.findViewById(R.id.reload_button);
        Button settingsButton = mRootView.findViewById(R.id.settings_button);
        mTaboolaWidget = mRootView.findViewById(R.id.taboola_widget);

        reloadButton.setOnClickListener(view -> reloadWidget());
        settingsButton.setOnClickListener(view -> showSettingsDialog());

        setDefaultProperties();
        reloadWidget();

        return mRootView;
    }

    public void onSettingsSaved(Properties properties) {
        mProperties = new Properties(properties);
        reloadWidget();
        Snackbar.make(mRootView, "Settings saved", Snackbar.LENGTH_LONG).show();
    }

    private void setDefaultProperties() {
        mProperties.setPublisher("betterbytheminute-app");
        mProperties.setMode("thumbnails-sdk3");
        mProperties.setPlacement("Mobile");
        mProperties.setPageType("article");
        mProperties.setTargetType("mix");
        mProperties.setPageUrl("http://www.example.com");
        mProperties.setReferrer("http://www.example.com/ref");
        mProperties.setShouldScroll(false);
        mProperties.setShouldAutoResizeHeight(true);
    }

    private void reloadWidget() {
        mTaboolaWidget.reset();

        mTaboolaWidget.setPublisher(mProperties.getPublisher());
        mTaboolaWidget.setMode(mProperties.getMode());
        mTaboolaWidget.setPlacement(mProperties.getPlacement());
        mTaboolaWidget.setPageType(mProperties.getPageType());
        mTaboolaWidget.setPageUrl(mProperties.getPageUrl());
        mTaboolaWidget.setTargetType(mProperties.getTargetType());
        mTaboolaWidget.setScrollEnabled(mProperties.isShouldScroll());
        mTaboolaWidget.setAutoResizeHeight(mProperties.isShouldAutoResizeHeight());

        HashMap<String, String> otherProperties = new HashMap<>();
        otherProperties.put(Const.PAGE_URL_KEY, mProperties.getPageUrl());
        otherProperties.put(Const.REFERRER_KEY, mProperties.getReferrer());
        mTaboolaWidget.pushCommands(otherProperties);
        mTaboolaWidget.setLogLevel(Logger.DEBUG);
        mTaboolaWidget.fetchContent();
    }

    private void showSettingsDialog() {
        SettingsDialogFragment.showSettingsDialog(getFragmentManager(), mProperties, this);
    }
}