package com.taboola.taboolasample.samples;

import android.app.Dialog;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.taboola.taboolasample.Properties;
import com.taboola.taboolasample.R;
import com.taboola.taboolasample.databinding.DialogConfigBinding;

import java.util.ArrayList;
import java.util.List;

public class SettingsDialogFragment extends DialogFragment {

    private static final String TAG = "SettingsDialogFragment";
    private static final String ARG_PROPERTIES = TAG + "ARG_PROPERTIES";


    private SettingsCallback mCallback;
    /**
     * using Two-Way Data Binding we pass the Properties ref that will be used to update the xml views and automatic save any change of them
     */
    private DialogConfigBinding mBinding;
    private static final Typeface ROBOTO_BOLD = Typeface.create("sans-serif", Typeface.BOLD);


    static void showSettingsDialog(FragmentManager fragmentManager, Properties properties, SettingsCallback callback) {
        SettingsDialogFragment propertiesDialogFragment = new SettingsDialogFragment();
        propertiesDialogFragment.mCallback = callback;

        Bundle args = new Bundle();
        //using new instance, will used later for data changes (and should override the original Properties only if user press save).
        args.putParcelable(ARG_PROPERTIES, new Properties(properties));
        propertiesDialogFragment.setArguments(args);
        propertiesDialogFragment.show(fragmentManager, TAG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_config, null, false);
        Properties properties = getArguments() != null ? getArguments().getParcelable(ARG_PROPERTIES) : null;
        mBinding.setProperties(properties);
        List<EditText> requiredFields = new ArrayList<>(); //list contains all the mandatory fields
        mBinding.setRequiredFields(requiredFields);
        return buildDialog(mBinding.getRoot());
    }


    private Dialog buildDialog(View view) {
        return new AlertDialog.Builder(view.getContext())
                .setView(view)
                .setPositiveButton(R.string.alert_dialog_ok, (dialog, which) -> {
                    //Do nothing here because we override this button later to change the close behaviour.
                })
                .setNegativeButton(R.string.alert_dialog_cancel, (dialog, whichButton) -> dismiss())
                .setCancelable(false)
                .create();
    }


    /**
     * @param editText       EditText that represent one of the Properties field
     * @param isRequired     is this editText represent mandatory field
     * @param requiredFields list contains all the mandatory fields
     */
    @BindingAdapter({"required", "requiredFieldsList"})
    public static void setRequired(EditText editText, boolean isRequired, List<EditText> requiredFields) {
        if (isRequired) {
            requiredFields.add(editText);

            //assuming that all EditText wrapped by TextInputLayout
            final TextInputLayout textInputLayout = (TextInputLayout) editText.getParent().getParent();
            textInputLayout.setHint(textInputLayout.getHint() + "*");
            textInputLayout.setTypeface(ROBOTO_BOLD);
        }
    }

    //onStart() is where dialog.show() is actually called on the underlying dialog,
    // so we have to do it there or later in the lifecycle.
    //Doing it in onResume() makes sure that even if there is a config change environment that skips onStart
    // then the dialog will still be functioning properly after a rotation.
    // for more info: https://stackoverflow.com/a/15619098
    @Override
    public void onResume() {
        super.onResume();

        // positive button will close dialog and save settings only if all required fields are set
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null) {
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                Boolean shouldCloseDialog = checkRequiredFields();
                if (shouldCloseDialog) {
                    saveProperties();
                    alertDialog.dismiss();
                }
                //else wait user to specify all required fields
            });
        }
    }

    private boolean checkRequiredFields() {
        boolean isAllSet = true;
        //this list contains all the relevant EditText that represents Mandatory fields for TaboolaWidget. all this EditText's have custom:required="@{true}"
        List<EditText> requiredFields = mBinding.getRequiredFields();
        for (EditText et : requiredFields) {
            //detect if the EditText has valid value (not empty or null)
            if (et.getText().toString().trim().isEmpty()) {
                et.setError(getString(R.string.required));
                isAllSet = false;
            }
        }

        return isAllSet;
    }


    private void saveProperties() {
        //the properties are updated dynamically by data binding (see layout xml for more info)
        mCallback.onSettingsSaved(mBinding.getProperties());
    }


    interface SettingsCallback {
        void onSettingsSaved(Properties properties);
    }
}