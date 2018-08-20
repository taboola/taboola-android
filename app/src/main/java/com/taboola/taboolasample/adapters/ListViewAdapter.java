package com.taboola.taboolasample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taboola.android.TaboolaWidget;
import com.taboola.taboolasample.R;
import com.taboola.taboolasample.utils.Utils;

import java.util.List;


public class ListViewAdapter extends BaseAdapter {

    private final List<ListItems.FeedListItem> mData;

    TaboolaWidget mTaboolaWidget;

    public ListViewAdapter(Context context) {
        mData = ListItems.getGeneratedData();
        mTaboolaWidget = new TaboolaWidget(context);
        buildTaboolaWidget(context);
    }

    private void buildTaboolaWidget(Context context) {
        int height = Utils.getTaboolaViewHeight(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mTaboolaWidget.setLayoutParams(params);
        mTaboolaWidget
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setMode("thumbnails-feed")
                .setPlacement("feed")
                .setTargetType("mix")
                .setPublisher("betterbytheminute-app");

        mTaboolaWidget.setInterceptScroll(true);
        mTaboolaWidget.fetchContent();
    }


    @Override
    public @ListItems.FeedListItem.ItemType
    int getItemViewType(int position) {
        ListItems.FeedListItem item = getItem(position);
        return item.type;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ListItems.FeedListItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {

            case ListItems.FeedListItem.ItemType.TABOOLA_ITEM:
                return new ViewHolderTaboola(mTaboolaWidget, viewType);

            default:
            case ListItems.FeedListItem.ItemType.RANDOM_ITEM:
                View appCompatImageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_item, parent, false);
                return new RandomImageViewHolder(appCompatImageView, viewType);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @ListItems.FeedListItem.ItemType int viewType = getItemViewType(position);
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null || ((ViewHolder) convertView.getTag()).mViewType != viewType) {
            viewHolder = onCreateViewHolder(parent, viewType);
            convertView = viewHolder.mView;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (viewType == ListItems.FeedListItem.ItemType.RANDOM_ITEM) {
            RandomImageViewHolder vh = (RandomImageViewHolder) viewHolder;
            ListItems.FeedListItem item = getItem(position);
            ListItems.RandomItem randomItem = (ListItems.RandomItem) item;
            final ImageView imageView = vh.imageView;
            imageView.setBackgroundColor(randomItem.color);
            vh.textView.setText(randomItem.randomText);
        }


        return convertView;
    }


    public static class RandomImageViewHolder extends ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        RandomImageViewHolder(View view, int viewType) {
            super(view, viewType);
            imageView = view.findViewById(R.id.feed_item_iv);
            textView = view.findViewById(R.id.feed_item_tv);
        }
    }

    static abstract class ViewHolder {

        private final @ListItems.FeedListItem.ItemType
        int mViewType;
        View mView;

        ViewHolder(View view, int viewType) {
            mView = view;
            this.mViewType = viewType;
            view.setTag(this);
        }
    }

    public static class ViewHolderTaboola extends ViewHolder {

        ViewHolderTaboola(View view, int viewType) {
            super(view, viewType);
        }
    }

}
