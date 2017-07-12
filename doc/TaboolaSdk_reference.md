# Taboola Native SDK Reference
-
**Note**: if you haven't been specifically instructed by your Tabooola account manager to use this section of TaboolaApi, you should probably use [TaboolaWidget](../README.md]) which is faster and more simple to integrate. 

-
# TaboolaApi

### `public TaboolaApi init(Context applicationContext, String publisherId, String apiKey)`

Initializes TaboolaApi. Must be called before any other method of the SDK. Typically you would want to do it in you Application class in OnCreate()

#### **Parameters:**
   * `applicationContext` — Application Context
   * `publisherId` — The publisher id in the Taboola system
   * `apiKey` — The API key that was provided by Taboola for the application
   
### `public static TaboolaApi getInstance()`

#### **Returns:** 
* a singleton instance of the SDK

### `public void fetchRecommendations(final TBRecommendationsRequest request, final TBRecommendationRequestCallback callback)`

Requests recommendation items.

#### **Parameters:**
   * `request` — `TBRecommendationsRequest` with at least one `TBPlacementRequest`
   * `callback` — callback to pass result into

### `public void getNextBatchForPlacement(TBPlacement placement, final TBRecommendationRequestCallback callback)`

Used for implementing pagination or infinite scroll (load more items as the user scrolls down). 
The method gets the next batch of recommendation items for a specified placement. The number of recommendation items in the batch will be the same as in the last request. The name of the returned Placement will have a "counter" added as a suffix. For example, if the original placement name was "article" the new name will be "article 1", next one "article 2", and so on. The counter is incremented on each successful fetch. 

#### **Parameters:**
   * `placement` — placement to request next recommendation items for
   * `callback` — this callback will get the results

### `public void getNextBatchForPlacement(TBPlacement placement, int count, final TBRecommendationRequestCallback callback)`

Used for implementing pagination or inifinite scroll (load more items as the user scrolls down). 
The method gets the next batch of recommendation items for a specified placement. The name of the returned Placement will have a "counter" added as a suffix. For example, if the original placement name was "article" the new name will be "article 1", next one "article 2", and so on. The counter is incremented on each successful fetch.  
#### **Parameters:**
   * `placement` — placement to request next recommendation items for
   * `count` — amount of recommendation items to fetch
   * `callback` — callback to pass result into

### `public void handleAttributionClick(Context activityContext)`

Shows a standard popup with attribution, ad-choices and opt-out data.

This method **Must** be called when the user clicks on the attribution view ("By Taboola" text or the ad-choices icon).

#### **Parameters:** 
* `activityContext` — is used to display popup

### `public TaboolaApi setOnClickListener(TaboolaOnClickListener onClickListener)`

TaboolaApi allows to intercept recommendation clicks and block default click handling for organic items. If you want to block the default behavior in order to open the link to your organic content natively within your app, return `false` in `TaboolaOnClickListener#onItemClick(String, String, String, boolean)`

The return value will be ignored for non-organic items.

The listener will be called every time a user clicks a recommendation, right before triggering the default behavior

### `public TaboolaApi setOnClickIgnoreTimeMs(int onClickIgnoreTimeMs)`

To avoid accidental user clicks, the TB views will ignore clicks that were done immediately after the view became visible.

**DONT CHANGE THIS VALUE** without consulting your Taboola account manager.

#### **Parameters:** 
* `onClickIgnoreTimeMs` — time in milliseconds

### `public void setLogLevel(int logLevel)`

Sets log level for the SDK. You can find logLevel constants in the `Logger` class, for example `Logger.DEBUG`.

### `public TaboolaApi setImagePlaceholder(Drawable placeholderDrawable)`

#### **Parameters:**
* `placeholderDrawable` — A placeholder drawable to be used while the image is being loaded.

-

# TBRecommendationRequest

### `public TBRecommendationsRequest(String pageUrl, String sourceType)`

Initializes a Recommendation request with the required parameters. Placments must be added to the request after initialization in order to fetch recommendations.

#### **Parameters:**
   * `pageUrl` — a fully qualified (http:// or https://) publicly accessible URL that contains
     the content and/or meta data for the current source item (the piece of
     content the recommendations are going to be placed next to).
   * `sourceType` — the type of the content the recommendations will be placed next to.

### `public TBRecommendationsRequest addPlacementRequest(TBPlacementRequest placementRequest)`

Adds a `TBPlacementRequest` to the `TBRecommendationsRequest`. Up to 12 `TBPlacementRequest` per `TBRecommendationsRequest` are allowed. All placements must have unique names.

#### **Parameters:** 
*`placementRequest` — placement to be added

### `public TBRecommendationsRequest setSourceType(String sourceType)`

Sets the type of the content the recommendations will be placed next to.

The following values are supported: <li> video - when the recommendations are placed near a video</li> <li> text - when the recommendations are placed near a textual piece such as an article / story</li> <li> photo - when the recommendations are placed near a photo or gallery </li> <li> home - when the recommendations are placed on the app entry (home) screen </li> <li> section - when the recommendations are placed on a screen representing a “section front” or” topic” </li> <li> search - when the recommendations are placed on a search results page</li>

#### **Parameters:**
* `sourceType`

### `public TBRecommendationsRequest setPageUrl(String url)`

#### **Parameters:** 
* `url` — A fully qualified (http:// or https://) publicly accessible URL that contains the content and/or meta data for the current source item (the piece of content the recommendations are going to be placed next to).

### `public TBRecommendationsRequest setUserReferrer(String userReferrer)`

#### **Parameters:**
* `userReferrer` — The referrer (HTTP header) from the request that led to the current page.

### `public TBRecommendationsRequest setUserUnifiedId(String userUnifiedId)`

 * **Parameters:** `userUnifiedId` — An opaque, anonymized and unique identifier of the user in the publisher’s eco-system. This identifier should be identical cross application and device (e.g. hashed e-mail, or login)

-
 
# TBPlacementRequest

### `public TBPlacementRequest(String name, int recCount)`

Creates request for a specific placement with the required parameters

#### **Parameters:**
   * `name` — A text string, uniquely identifying the placement
   * `recCount` — A non-negative integer specifying how many recommendations are requested.
     The API will not return more recommendations than this, though if there are
     not enough good quality recommendations to satisfy the request,
     fewer items might be returned.

### `public TBPlacementRequest setName(String name)`

#### **Parameters:**
* `name` — A text string, uniquely identifying the placement

### `public TBPlacementRequest setRecCount(int recCount)`

#### **Parameters:** 
* `recCount` — A non-negative integer specifying how many recommendations are requested.
     The API will not return more recommendations than this, though if there are
     not enough good quality recommendations to satisfy the request,
     fewer items might be returned.

### `public TBPlacementRequest setTargetType(String targetType)`

Note that this parameter does not influence the type of sponsored content returned – the mix of the sponsored content types is currently determined by a server side configuration.

Valid values: <li>video - return only video content </li> <li>text - return only textual (stories) content </li> <li>photo - return only photo / galleries content </li> <li>mix - (default) return a mix of several content types. In case a mix is requested, the exact mix is determined by server side configuration. Different configurations can be used for different source.type and placement parameters. </li>

#### **Parameters:**
* `targetType` — Type of recommended organic content to return.

### `public TBPlacementRequest setThumbnailSize(int width, int height)`

Sets the size (in pixels) of the thumbnails returned for recommendations. Both parameters (height and width) should either appear together or not appear at all.

In case the thumbnail size parameters are specified, the thumbnail would always preserve its original aspect ratio – it will be scaled to the required size, centered and cropped if needed.

In addition, in case the thumbnail contains a face, we will detect that face by default and ensure it is contained within the generated thumbnail.

#### **Parameters:**
   * `width`
   * `height`
 
  
-
  
# TBPlacement

### `public List<TBRecommendationItem> getItems()`

#### **Returns:** 
* list of `TBRecommendationItems` for this placement

### `public void prefetchThumbnails()`

Asynchronously loads the thumbnail images for all RecommendationItems in this Placement to the cache, using the default caching mechanism from Picasso, which is: <ul> <li>LRU memory cache of 15% the available application RAM</li> <li>Disk cache of 2% storage space up to 50MB but no less than 5MB. (Note: this is only available on API 14+ <em>or</em> if you are using a standalone library that provides a disk cache on all API levels like OkHttp)</li> <li>Three download threads for disk and network access.</li> </ul>

Use this method to make sure the thumbnail images are loaded fast and to create a better user experience. Avoid using it in order reduce device resource consumption, if the app is running on a low-end device.

-
  
# TBRecommendationsResponse

### `public Map<String, TBPlacement> getPlacementsMap()`

#### **Returns:** 
* a map with the placements where the key is the placement name and the value is a `TBPlacement` object

-

# TBRecommendationItem

### `@NonNull public TBImageView getThumbnailView(Context context)`

Returns TBImageView that contains the thumbnail image of the recommendation item. If the `TBPlacementRequest` contained a thumbnail size, it will be set to that size before the image is loaded.

#### **Parameters:** 
 * `context` — Activity context

#### **Returns:** 
* `TBImageView`


### `@NonNull public TBTextView getTitleView(Context context)`

Returns a TBTextView which contains the title of the recommended item.

#### **Parameters:** 
* `context` — Activity context

#### **Returns:** 
* `TBTextView`

### `@Nullable public TBTextView getBrandingView(Context context)`

If branding text is available for the current recommendation item, returns a TBTextView which contains the branding text for the item.

If branding text is not available returns `null`

#### **Parameters:** 
* `context` — Activity context

#### **Returns:** 
* `TBTextView` or `null`

### `public void prefetchThumbnail()`

Asynchronously loads the thumbnail image for this RecommendationItem to the cache, using the default caching mechanism from Picasso, which is: <ul> <li>LRU memory cache of 15% the available application RAM</li> <li>Disk cache of 2% storage space up to 50MB but no less than 5MB. (Note: this is only available on API 14+ <em>or</em> if you are using a standalone library that provides a disk cache on all API levels like OkHttp)</li> <li>Three download threads for disk and network access.</li> </ul>

Use this method to make sure the thumbnail images are loaded fast and to create a better user experience. Avoid using it in order reduce device resource consumption, if the app is running on a low-end device.

No need to call the method again if it was already called on the placement level.

### `public void handleClick(Context context)`

Triggers OnClick event on the RecommendationItem. 
Should be used when implementing a different gesture for opening recommendation items by the user (e.g. swipe)

Ignores visibility requirements for the click.

-

# TBImageView

### `public void handleClick()`


Triggers OnClick event on the RecommendationItem. 
Should be used when implementing a different gesture for opening recommendation items by the user (e.g. swipe)

Ignores visibility requirements for the click.

-

# TBTextView

### `public void handleClick()`

Triggers OnClick event on the RecommendationItem. 
Should be used when implementing a different gesture for opening recommendation items by the user (e.g. swipe)

Ignores visibility requirements for the click.

-

# TaboolaOnClickListener

### `boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic)`

TaboolaApi allows to intercept recommendation clicks and block default click handling for organic items. If you want to block the default behavior in order to open the link to your organic content natively within your app, return `false`.

The return value will be ignored for non-organic items.

The listener will be called every time a user clicks a recommendation, right before triggering the default behavior
