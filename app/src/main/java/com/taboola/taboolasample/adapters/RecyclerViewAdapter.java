package com.taboola.taboolasample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taboola.android.TaboolaWidget;
import com.taboola.android.utils.SdkDetailsHelper;
import com.taboola.taboolasample.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<ListItems.FeedListItem> mData;

    public RecyclerViewAdapter() {
        mData = ListItems.getGeneratedData();
    }

    private static void buildTaboolaWidget(Context context, TaboolaWidget taboolaWidget) {
        int height = SdkDetailsHelper.getDisplayHeight(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        taboolaWidget.setLayoutParams(params);
        taboolaWidget
                .setPageType("article")
                .setPageUrl("http://www.example.com")
                .setMode("thumbnails-feed")
                .setPlacement("feed-sample-app")
                .setTargetType("mix")
                .setPublisher("betterbytheminute-app");

        taboolaWidget.setInterceptScroll(true);
        taboolaWidget.fetchContent();
    }


    @Override
    public int getItemViewType(int position) {
        ListItems.FeedListItem item = getItem(position);
        return item.type;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    private ListItems.FeedListItem getItem(int position) {
        return mData.get(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {

            case ListItems.FeedListItem.ItemType.TABOOLA_ITEM:
                return new ViewHolderTaboola(new TaboolaWidget(parent.getContext()));

            default:
            case ListItems.FeedListItem.ItemType.RANDOM_ITEM:
                View appCompatImageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_item, parent, false);
                return new RandomImageViewHolder(appCompatImageView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItems.FeedListItem item = getItem(position);

        if (item.type == ListItems.FeedListItem.ItemType.RANDOM_ITEM) {
            RandomImageViewHolder vh = (RandomImageViewHolder) holder;
            ListItems.RandomItem randomItem = (ListItems.RandomItem) item;
            final ImageView imageView = vh.imageView;
            imageView.setBackgroundColor(randomItem.color);
            vh.textView.setText(randomItem.randomText);
        }
    }


    public static class RandomImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        RandomImageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.feed_item_iv);
            textView = view.findViewById(R.id.feed_item_tv);
        }
    }

    public static class ViewHolderTaboola extends RecyclerView.ViewHolder {

        ViewHolderTaboola(TaboolaWidget taboolaWidget) {
            super(taboolaWidget);
            buildTaboolaWidget(taboolaWidget.getContext(), taboolaWidget);
        }
    }


}
