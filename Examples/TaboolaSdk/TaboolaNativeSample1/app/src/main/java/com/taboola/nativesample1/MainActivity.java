package com.taboola.nativesample1;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taboola.android.api.TBPlacementRequest;
import com.taboola.android.api.TBRecommendationItem;
import com.taboola.android.api.TBRecommendationRequestCallback;
import com.taboola.android.api.TBRecommendationsRequest;
import com.taboola.android.api.TBRecommendationsResponse;
import com.taboola.android.api.TaboolaSdk;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mAdContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdContainer = (LinearLayout) findViewById(R.id.main_root);

        final String placementName = "article";
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        TBPlacementRequest placementRequest = new TBPlacementRequest(placementName, 1)
                .setThumbnailSize(screenSize.x, (screenSize.y / 3)); // ThumbnailSize is optional

        TBRecommendationsRequest request = new TBRecommendationsRequest("http://example.com", "text");
        request.addPlacementRequest(placementRequest);

        TaboolaApi.getInstance().fetchRecommendations(request, new TBRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBRecommendationsResponse response) {
                TBRecommendationItem item = response.getPlacementsMap().get(placementName)
                        .getItems().get(0);
                mAdContainer.findViewById(R.id.attribution_view).setVisibility(View.VISIBLE);
                mAdContainer.addView(item.getThumbnailView(MainActivity.this));
                mAdContainer.addView(item.getTitleView(MainActivity.this));
                mAdContainer.addView(item.getBrandingView(MainActivity.this));
            }

            @Override
            public void onRecommendationsFailed(Throwable throwable) {
                Toast.makeText(MainActivity.this, "Failed: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAttributionClick(View view) {
        TaboolaApi.getInstance().handleAttributionClick(this);
    }
}
