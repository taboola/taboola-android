package com.taboola.taboolasample.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Properties implements Parcelable {

    private String pageType;
    private String pageUrl;
    private String mode;
    private String placement;
    private String targetType;
    private String publisher;
    private String referrer;
    private boolean shouldScroll;
    private boolean shouldAutoResizeHeight;

    public Properties() {
    }

    public Properties(String pageType, String pageUrl, String mode, String placement, String targetType, String publisher, String referrer,
                      boolean shouldScroll, boolean autoResizeHeight) {
        this.pageType = pageType;
        this.pageUrl = pageUrl;
        this.mode = mode;
        this.placement = placement;
        this.targetType = targetType;
        this.publisher = publisher;
        this.referrer = referrer;
        this.shouldScroll = shouldScroll;
        this.shouldAutoResizeHeight = autoResizeHeight;
    }

    public Properties(Properties other) {
        this.pageType = other.pageType;
        this.pageUrl = other.pageUrl;
        this.mode = other.mode;
        this.placement = other.placement;
        this.targetType = other.targetType;
        this.publisher = other.publisher;
        this.referrer = other.referrer;
        this.shouldScroll = other.shouldScroll;
        this.shouldAutoResizeHeight = other.shouldAutoResizeHeight;
    }

    public String getPageType() {
        return pageType;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public String getMode() {
        return mode;
    }

    public String getPlacement() {
        return placement;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReferrer() {
        return referrer;
    }

    public boolean isShouldScroll() {
        return shouldScroll;
    }

    public boolean isShouldAutoResizeHeight() {
        return shouldAutoResizeHeight;
    }

    public Properties setPageType(String pageType) {
        this.pageType = pageType;
        return this;
    }

    public Properties setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
        return this;
    }

    public Properties setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public Properties setPlacement(String placement) {
        this.placement = placement;
        return this;
    }

    public Properties setTargetType(String targetType) {
        this.targetType = targetType;
        return this;
    }

    public Properties setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }


    public Properties setReferrer(String referrer) {
        this.referrer = referrer;
        return this;
    }

    public Properties setShouldScroll(boolean shouldScroll) {
        this.shouldScroll = shouldScroll;
        return this;
    }

    public Properties setShouldAutoResizeHeight(boolean shouldAutoResizeHeight) {
        this.shouldAutoResizeHeight = shouldAutoResizeHeight;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pageType);
        dest.writeString(this.pageUrl);
        dest.writeString(this.mode);
        dest.writeString(this.placement);
        dest.writeString(this.targetType);
        dest.writeString(this.publisher);
        dest.writeString(this.referrer);
        dest.writeByte(this.shouldScroll ? (byte) 1 : (byte) 0);
        dest.writeByte(this.shouldAutoResizeHeight ? (byte) 1 : (byte) 0);
    }

    protected Properties(Parcel in) {
        this.pageType = in.readString();
        this.pageUrl = in.readString();
        this.mode = in.readString();
        this.placement = in.readString();
        this.targetType = in.readString();
        this.publisher = in.readString();
        this.referrer = in.readString();
        this.shouldScroll = in.readByte() != 0;
        this.shouldAutoResizeHeight = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Properties> CREATOR = new Parcelable.Creator<Properties>() {
        @Override
        public Properties createFromParcel(Parcel source) {
            return new Properties(source);
        }

        @Override
        public Properties[] newArray(int size) {
            return new Properties[size];
        }
    };
}
