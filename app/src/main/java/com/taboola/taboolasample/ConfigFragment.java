package com.taboola.taboolasample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.utils.Const;
import com.taboola.android.utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class ConfigFragment extends Fragment implements SettingsDialog.SettingsCallback {
    private TaboolaWidget mTaboolaWidget;
    private Map<String, String> mWidgetProperties = new HashMap<>();
    private View mRootView;

    public ConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_config, container, false);
        Button reloadButton = (Button) mRootView.findViewById(R.id.reload_button);
        Button settingsButton = (Button) mRootView.findViewById(R.id.settings_button);
        mTaboolaWidget = (TaboolaWidget) mRootView.findViewById(R.id.taboola_widget);

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadWidget();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingsDialog();
            }
        });

        setDefaultProperties();
        reloadWidget();

        return mRootView;
    }

    public void onSettingsSaved(Map<String, String> newSettings) {
        mWidgetProperties = newSettings;
        reloadWidget();
        Snackbar.make(mRootView, "Settings saved", Snackbar.LENGTH_LONG).show();
    }

    private void setDefaultProperties() {
        mWidgetProperties.put(Const.PUBLISHER_KEY, "betterbytheminute-app");
        mWidgetProperties.put(Const.MODE_KEY, "thumbnails-sdk3");
        mWidgetProperties.put(Const.PLACEMENT_KEY, "Mobile");
        mWidgetProperties.put(Const.PAGE_TYPE_KEY, "article");
        mWidgetProperties.put(Const.TARGET_TYPE_KEY, "mix");
        mWidgetProperties.put(Const.PAGE_URL_KEY, "http://www.example.com");
        mWidgetProperties.put(Const.REFERRER_KEY, "http://www.example.com/ref");
        mWidgetProperties.put(Const.ARTICLE_KEY, "");
        mWidgetProperties.put(Const.SCROLL_ENABLED_KEY, "false");
        mWidgetProperties.put(Const.AUTO_RESIZE_HEIGHT_KEY, "true");
        mWidgetProperties.put(Const.ITEM_CLICK_ENABLED_KEY, "true");
    }

    private void reloadWidget() {
        mTaboolaWidget.reset();

        mTaboolaWidget.setPublisher(mWidgetProperties.get(Const.PUBLISHER_KEY));
        mTaboolaWidget.setMode(mWidgetProperties.get(Const.MODE_KEY));
        mTaboolaWidget.setPlacement(mWidgetProperties.get(Const.PLACEMENT_KEY));
        mTaboolaWidget.setPageType(mWidgetProperties.get(Const.PAGE_TYPE_KEY));
        mTaboolaWidget.setPageUrl(mWidgetProperties.get(Const.PAGE_URL_KEY));
        mTaboolaWidget.setTargetType(mWidgetProperties.get(Const.TARGET_TYPE_KEY));

        boolean shouldScroll = Boolean.parseBoolean(mWidgetProperties.get(Const.SCROLL_ENABLED_KEY));
        mTaboolaWidget.setScrollEnabled(shouldScroll);

        boolean shouldResize = Boolean.parseBoolean(mWidgetProperties.get(Const.AUTO_RESIZE_HEIGHT_KEY));
        mTaboolaWidget.setAutoResizeHeight(shouldResize);

        boolean isClickEnabled = Boolean.parseBoolean(mWidgetProperties.get(Const.ITEM_CLICK_ENABLED_KEY));
        mTaboolaWidget.setItemClickEnabled(isClickEnabled);

        HashMap<String, String> otherProperties = new HashMap<>();
        otherProperties.put(Const.PAGE_URL_KEY, mWidgetProperties.get(Const.PAGE_URL_KEY));
        otherProperties.put(Const.REFERRER_KEY, mWidgetProperties.get(Const.REFERRER_KEY));
        mTaboolaWidget.pushCommands(otherProperties);
        mTaboolaWidget.setLogLevel(Logger.DEBUG);
        mTaboolaWidget.fetchContent();
    }

    private void showSettingsDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        String tag = SettingsDialog.class.getSimpleName();
        new SettingsDialog().showDialog(fm, tag, mWidgetProperties, this);
    }
}