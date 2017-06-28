package com.taboola.nativesample2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.taboola.android.sdk.TBPlacement;
import com.taboola.android.sdk.TBPlacementRequest;
import com.taboola.android.sdk.TBRecommendationItem;
import com.taboola.android.sdk.TBRecommendationRequestCallback;
import com.taboola.android.sdk.TBRecommendationsRequest;
import com.taboola.android.sdk.TBRecommendationsResponse;
import com.taboola.android.sdk.TaboolaSdk;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private TBPlacement mPlacement;
    Snackbar snackbar;

    private List<Object> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        EndlessScrollListener scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextRecommendationsBatch();
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(scrollListener);
        mAdapter = new FeedAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        snackbar = Snackbar.make(mRecyclerView, "Waiting for network", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        fetchTaboolaRecommendations();
    }

    public void onAttributionClick(View view) {
        TaboolaSdk.getInstance().handleAttributionClick(this);
    }

    private void onRecommendationsFetched(TBPlacement placement) {
        placement.prefetchThumbnails();
        mPlacement = placement;
        List<Object> mergedFeedItems = mergeFeedItems(getDemoData(), placement.getItems());

        int currentSize = mAdapter.getItemCount();
        mData.addAll(mergedFeedItems);
        mAdapter.notifyItemRangeInserted(currentSize, mergedFeedItems.size());
    }

    private List<Object> mergeFeedItems(List<DemoItem> demoItems,
                                        List<TBRecommendationItem> recommendationItems) {
        int size = demoItems.size() + recommendationItems.size();
        List<Object> mergedFeedItems = new ArrayList<>(size);
        mergedFeedItems.addAll(demoItems);

        // insert taboola ad every 4 elements
        int nextInsertionIndex = 4;
        for (TBRecommendationItem item : recommendationItems) {
            mergedFeedItems.add(nextInsertionIndex, item);
            nextInsertionIndex += 5;
        }

        return mergedFeedItems;
    }

    private List<DemoItem> getDemoData() {
        List<DemoItem> list = new ArrayList<>(16);
        Resources res = getResources();
        for (int i = 0; i < 4; i++) {
            list.add(new DemoItem(R.drawable.image_demo, res.getString(R.string.lorem_ipsum1)));
            list.add(new DemoItem(R.drawable.image_demo, res.getString(R.string.lorem_ipsum2)));
            list.add(new DemoItem(R.drawable.image_demo, res.getString(R.string.lorem_ipsum3)));
            list.add(new DemoItem(R.drawable.image_demo, res.getString(R.string.lorem_ipsum4)));
        }
        return list;
    }

    private void fetchTaboolaRecommendations() {
        final String placementName = "list_item";
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        TBPlacementRequest placementRequest = new TBPlacementRequest(placementName, 4)
                .setThumbnailSize(screenSize.x / 2, (screenSize.y / 6)); // ThumbnailSize is optional

        TBRecommendationsRequest request = new TBRecommendationsRequest("http://example.com", "text");
        request.addPlacementRequest(placementRequest);

        TaboolaSdk.getInstance().fetchRecommendations(request, new TBRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBRecommendationsResponse response) {
                MainActivity.this.onRecommendationsFetched(response.getPlacementsMap().get(placementName));
                snackbar.dismiss();
            }

            @Override
            public void onRecommendationsFailed(Throwable throwable) {
                Toast.makeText(MainActivity.this, "Fetch failed: " + throwable.getMessage(),
                        Toast.LENGTH_LONG).show();
                snackbar.dismiss();
            }
        });
    }

    private void loadNextRecommendationsBatch() {
        TaboolaSdk.getInstance().getNextBatchForPlacement(mPlacement, new TBRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBRecommendationsResponse response) {
                TBPlacement placement = response.getPlacementsMap().values().iterator().next(); // there will be only one placement
                MainActivity.this.onRecommendationsFetched(placement);
            }

            @Override
            public void onRecommendationsFailed(Throwable throwable) {
                Toast.makeText(MainActivity.this, "Fetch failed: " + throwable.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
