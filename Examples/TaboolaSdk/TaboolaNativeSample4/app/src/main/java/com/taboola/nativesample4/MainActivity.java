package com.taboola.nativesample4;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taboola.android.sdk.TBPlacementRequest;
import com.taboola.android.sdk.TBRecommendationItem;
import com.taboola.android.sdk.TBRecommendationRequestCallback;
import com.taboola.android.sdk.TBRecommendationsRequest;
import com.taboola.android.sdk.TBRecommendationsResponse;
import com.taboola.android.sdk.TBTextView;
import com.taboola.android.sdk.TaboolaSdk;

public class MainActivity extends AppCompatActivity {
    private final static int STATE_OFF = 0;
    private final static int STATE_ON = 1;

    private LinearLayout mAdContainer;
    private ImageButton mFlashlightButton;

    private int flashLightState = STATE_OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdContainer = (LinearLayout) findViewById(R.id.ad_container);
        mFlashlightButton = (ImageButton) findViewById(R.id.main_button);
        mFlashlightButton.setColorFilter(Color.WHITE);

        initOnButton();
        initTaboolaViews();
    }

    private void initTaboolaViews() {
        final String placementName = "article";
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        TBPlacementRequest placementRequest = new TBPlacementRequest(placementName, 1)
                .setThumbnailSize(screenSize.x, (screenSize.y / 3)); // ThumbnailSize is optional

        TBRecommendationsRequest request = new TBRecommendationsRequest("http://example.com", "text");
        request.addPlacementRequest(placementRequest);

        TaboolaSdk.getInstance().fetchRecommendations(request, new TBRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBRecommendationsResponse response) {
                TBRecommendationItem item = response.getPlacementsMap().get(placementName)
                        .getItems().get(0);

                mAdContainer.findViewById(R.id.attribution_view).setVisibility(View.VISIBLE);
                mAdContainer.addView(item.getThumbnailView(MainActivity.this));

                TBTextView titleView = item.getTitleView(MainActivity.this);
                titleView.setTextColor(Color.WHITE);
                mAdContainer.addView(titleView);

                TBTextView brandingView = item.getBrandingView(MainActivity.this);
                brandingView.setTextColor(Color.WHITE);
                mAdContainer.addView(brandingView);
            }

            @Override
            public void onRecommendationsFailed(Throwable throwable) {
                Toast.makeText(MainActivity.this, "Failed: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAttributionClick(View view) {
        TaboolaSdk.getInstance().handleAttributionClick(this);
    }

    private void initOnButton() {
        mFlashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashLightState == STATE_OFF) {
                    turnOnFlashlight();
                } else if (flashLightState == STATE_ON) {
                    turnOffFlashlight();
                }
            }
        });
    }

    private void turnOnFlashlight() {
        flashLightState = STATE_ON;
        mFlashlightButton.setColorFilter(Color.YELLOW);
        Toast.makeText(this, "Flashlight is ON", Toast.LENGTH_SHORT).show();
        //TODO: flashlight on implementation can go here
    }

    private void turnOffFlashlight() {
        flashLightState = STATE_OFF;
        mFlashlightButton.setColorFilter(Color.WHITE);
        Toast.makeText(this, "Flashlight is OFF", Toast.LENGTH_SHORT).show();
        //TODO: flashlight off implementation can go here
    }

}
