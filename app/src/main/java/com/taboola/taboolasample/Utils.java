package com.taboola.taboolasample;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import com.taboola.android.utils.SdkDetailsHelper;

public class Utils {
    public static int getActionBarHeight(Context context) {
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        TypedArray a = context.obtainStyledAttributes(new TypedValue().data, textSizeAttr);
        int height = a.getDimensionPixelSize(0, 0);
        a.recycle();
        return height;
    }

    public static int getStatusBarHeight(Context context) {
        int resource = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resource > 0) {
            return context.getResources().getDimensionPixelSize(resource);
        }

        return 0;
    }

    public static int getTaboolaViewHeight(Context context) {
        final int screenHeight = SdkDetailsHelper.getDisplayHeight(context);
        int statusBarHeight = getStatusBarHeight(context);
        int actionBarHeight = getActionBarHeight(context);
        return screenHeight - statusBarHeight - actionBarHeight;
    }
}
