package com.taboola.taboolasample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.taboola.android.utils.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingsDialog extends android.support.v4.app.DialogFragment {
    private SettingsCallback mCallback;
    private Map<String, String> mWidgetProperties;

    private EditText publisherEditText;
    private EditText modeEditText;
    private EditText placementEditText;
    private EditText pageTypeEditText;
    private EditText targetTypeEditText;
    private EditText pageUrlEditText;
    private EditText referrerEditText;
    private EditText articleEditText;
    private EditText scrollEnabledEditText;
    private EditText autoResizeHeightEditText;
    private EditText itemClickEnabledEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View rootView = inflater.inflate(R.layout.dialog_config, null);
        publisherEditText = (EditText) rootView.findViewById(R.id.dialog_publisher_edit_text);
        modeEditText = (EditText) rootView.findViewById(R.id.dialog_mode_edit_text);
        placementEditText = (EditText) rootView.findViewById(R.id.dialog_placement_edit_text);
        pageTypeEditText = (EditText) rootView.findViewById(R.id.dialog_page_type_edit_text);
        targetTypeEditText = (EditText) rootView.findViewById(R.id.dialog_target_type_edit_text);
        pageUrlEditText = (EditText) rootView.findViewById(R.id.dialog_page_url_edit_text);
        referrerEditText = (EditText) rootView.findViewById(R.id.dialog_referrer_edit_text);
        articleEditText = (EditText) rootView.findViewById(R.id.dialog_article_edit_text);
        scrollEnabledEditText = (EditText) rootView.findViewById(R.id.scroll_enabled_edit_text);
        autoResizeHeightEditText = (EditText) rootView.findViewById(R.id.auto_resize_height_edit_text);
        itemClickEnabledEditText = (EditText) rootView.findViewById(R.id.item_click_enabled_edit_text);

        setStartingValues(mWidgetProperties);

        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing here because we override this button later to change the close behaviour.
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SettingsDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        // positive button will close dialog and save settings only if all required fields are set
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean shouldCloseDialog = checkRequiredFields();
                    if (shouldCloseDialog) {
                        saveProperties();
                        d.dismiss();
                    }
                    //else wait user to specify all required fields
                }
            });
        }
    }

    private void setStartingValues(Map<String, String> widgetProperties) {
        publisherEditText.setText(widgetProperties.get(Const.PUBLISHER_KEY));
        modeEditText.setText(widgetProperties.get(Const.MODE_KEY));
        placementEditText.setText(widgetProperties.get(Const.PLACEMENT_KEY));
        pageTypeEditText.setText(widgetProperties.get(Const.PAGE_TYPE_KEY));
        targetTypeEditText.setText(widgetProperties.get(Const.TARGET_TYPE_KEY));
        pageUrlEditText.setText(widgetProperties.get(Const.PAGE_URL_KEY));
        referrerEditText.setText(widgetProperties.get(Const.REFERRER_KEY));
        articleEditText.setText(widgetProperties.get(Const.ARTICLE_KEY));

        scrollEnabledEditText.setText(widgetProperties.get(Const.SCROLL_ENABLED_KEY));
        autoResizeHeightEditText.setText(widgetProperties.get(Const.AUTO_RESIZE_HEIGHT_KEY));
        itemClickEnabledEditText.setText(widgetProperties.get(Const.ITEM_CLICK_ENABLED_KEY));
    }

    private boolean checkRequiredFields() {
        boolean isAllSet = true;

        List<EditText> requiredFields = new ArrayList<>();
        requiredFields.add(publisherEditText);
        requiredFields.add(modeEditText);
        requiredFields.add(placementEditText);
        requiredFields.add(pageTypeEditText);
        requiredFields.add(pageUrlEditText);

        for (EditText et : requiredFields) {
            if (et.getText().toString().trim().isEmpty()) {
                et.setError("Required");
                isAllSet = false;
            }
        }

        return isAllSet;
    }

    private void saveProperties() {
        mWidgetProperties.put(Const.PUBLISHER_KEY, publisherEditText.getText().toString());
        mWidgetProperties.put(Const.MODE_KEY, modeEditText.getText().toString());
        mWidgetProperties.put(Const.PLACEMENT_KEY, placementEditText.getText().toString());
        mWidgetProperties.put(Const.PAGE_TYPE_KEY, pageTypeEditText.getText().toString());
        mWidgetProperties.put(Const.TARGET_TYPE_KEY, targetTypeEditText.getText().toString());
        mWidgetProperties.put(Const.PAGE_URL_KEY, pageUrlEditText.getText().toString());
        mWidgetProperties.put(Const.REFERRER_KEY, referrerEditText.getText().toString());
        mWidgetProperties.put(Const.ARTICLE_KEY, articleEditText.getText().toString());

        mWidgetProperties.put(Const.SCROLL_ENABLED_KEY, scrollEnabledEditText.getText().toString());
        mWidgetProperties.put(Const.AUTO_RESIZE_HEIGHT_KEY, autoResizeHeightEditText.getText().toString());
        mWidgetProperties.put(Const.ITEM_CLICK_ENABLED_KEY, itemClickEnabledEditText.getText().toString());

        mCallback.onSettingsSaved(mWidgetProperties);
    }

    public void showDialog(FragmentManager manager, String tag, Map<String, String> widgetProperties,
                           SettingsCallback callback) {
        mWidgetProperties = widgetProperties;
        mCallback = callback;
        show(manager, tag);
    }

    interface SettingsCallback {
        void onSettingsSaved(Map<String, String> newSettings);
    }
}