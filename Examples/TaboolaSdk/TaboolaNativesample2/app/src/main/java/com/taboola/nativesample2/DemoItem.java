package com.taboola.nativesample2;

public class DemoItem {
    private Integer mImageResourceId;
    private String mText;

    public DemoItem(Integer imageResourceId, String text) {
        mImageResourceId = imageResourceId;
        mText = text;
    }

    public Integer getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(Integer imageResourceId) {
        mImageResourceId = imageResourceId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
