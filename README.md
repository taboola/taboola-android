# Taboola Android SDK
![Platform](https://img.shields.io/badge/platform-Android-green.svg)
[ ![Download](https://api.bintray.com/packages/taboola-devops/taboola-android-sdk/android-sdk/images/download.svg) ](https://bintray.com/taboola-devops/taboola-android-sdk/android-sdk/_latestVersion)
[ ![License](https://img.shields.io/badge/License%20-Taboola%20SDK%20License-blue.svg)]
(https://www.taboola.com/)

## Getting Started

### Minimum requirements

* Android version 2.1  (android:minSdkVersion="9")

### Incorporating the SDK

1. Add the library dependency to your project
 ```groovy
 compile 'com.taboola:android-sdk:1.1.0@aar'
 ```

2. Include this line in your app’s AndroidManifest.xml to allow Internet access
 ```xml
 <uses-permission android:name="android.permission.INTERNET" />
 ```

3. Any `Activity` which is intended to show TaboolaWidget should include the following attribute, to avoid reloading the Taboola recommendations multiple time unnecessarily:
 ```xml
 <activity
    android:configChanges="orientation|screenSize|keyboardHidden">
 </activity>
 ```
### Displaying Taboola recommendations widget

To include Taboola recommendations in your app just add a `com.taboola.android.TaboolaWidget` to your UI.
`TaboolaWidget` subclasses `ViewGroup` and behaves just like any other standard Android view.

1. Include the XML block in your `Activity` or `Fragment` layout

 ```xml
 <com.taboola.android.TaboolaWidget
    android:id="@+id/taboola_view"
    android:layout_width="match_parent"
    taboola:publisher="<my-publisher>"
    taboola:mode="<my-mode>"
    taboola:placement="Mobile below article"
    taboola:pageUrl="http://example.com"
    taboola:pageType="article"/>
 ```
2. Replace the attribute values in the XML according to the values provided by your Taboola account manager (`publisher`, `mode`, `placement`, `pageUrl`, `pageType`)

3. In your `Activity` or `Fragment` code, declare an instance on `TaboolaWidget`

 ```java
import com.taboola.android.TaboolaWidget;
//...
private TaboolaWidget taboola;
 ```

4. In your `Activity` `OnCreate` or `Fragment` `OnCreateView`, assign the inflated `TaboolaWidget` defined in the XML to the `TaboolaWidget` declared in the previous step, and have it fetch the display the recommendations
 ```java
taboola = (TaboolaWidget) rootView.findViewById(R.id.taboolaView);
taboola.fetchContent();
 ```

5. Run your app, your `Activity`/`Fragment` should now show Taboola recommendations.

### Setting the TaboolaWidget properties in code

Optionally, you can set the TaboolaWidget attributes directly in code, rather than have them set in XML

 ```java
TaboolaWidget taboola = (TaboolaWidget) findViewById(R.id.taboola_view);

// Optional - set your content data parameters via code (instead of XML)
taboola.setPublisher("<my-publisher>")
        .setMode("<my-mode>")
        .setPlacement("Mobile")
        .setPageUrl("http://www.example.com")
        .setPageType("article");

// fetch and display recommendations
taboola.fetchContent();
 ```

### Intercepting recommendation clicks

The default click behavior of TaboolaWidget is as follows:

* On devices where Chrome custom tab is supported - open the recommendation in a chrome custom tab (in-app)
* Otherwise - open the recommendation in the system default web browser (outside of the app) 

`TaboolaWidget` allows app developers to intercept recommendation clicks in order to create a click-through or to override the default way of opening the recommended article.

In order to intercept clicks, you should implement the interface `com.taboola.android.listeners.TaboolaEventListener` and connect `TaboolaWidget` to your event listener. The `Activity` or `Fragment` in which `TaboolaWidget` is displayed might be a good candidate to implement `TaboolaEventListener`.

`TaboolaEventListener` include the methods:
 ```java
public boolean taboolaViewItemClickHandler(String url, boolean isOrganic);
public void taboolaViewResizeHandler(TaboolaWidget widget, int height);
 ```

#### taboolaViewItemClickHandler

This method will be called every time a user clicks a recommendation, right before triggering the default behavior with `Intent.ACTION_VIEW`. The app can intercept the click there, and should return a `boolean` value.

* Return **`false`** - abort the default behavior, the app should display the recommendation content on its own (for example, using an in-app browser).
* Return **`true`** - this will allow the app to implement a click-through and continue to the default behaviour.

`isOrganic` indicates whether the item clicked was an organic content recommendation or not.
**Best practice would be to suppress the default behavior for organic items, and instead open the relevant screen in your app which shows that piece of content.**

##### Example:
 ```java
@Override
public boolean taboolaViewItemClickHandler(String url, boolean isOrganic) {
    if (isOragnic){
        showInAppContent(url);
        return false;
    }
    return true;
}
 ```

### Handling Taboola widget resize

`TaboolaWidget` may resize its height after loading recommendations, to make sure that the full content is displayed (based on the actual widget `mode` loaded).

After resize, `TaboolaWidget` will call `taboolaViewResizeHandler` method of the `TaboolaEventListener`, to allow the host app to adjust its layout to the changes. (This behavior may be disabled by setting the property `autoResizeHeight` to `false`.)

## Mediation

#### Supported Ad Platforms

Taboola Android SDK supports mediation via these platforms:

* DFP
* AdMob
* MoPub

<TBD>

### Catching global notifications (broadcasts) from TaboolaWidget

`TaboolaWidget` fires app level broadcasts to notify registered objects within the app about certain event. Catching those events might be useful for implementing custom event mediation adapters for ad platforms not natively supported by Taboola Android SDK.

These are the types of broadcasts sent by TaboolaWidget:

* `GlobalNotificationReceiver.TABOOLA_DID_RECEIVEAD​`
* `GlobalNotificationReceiver.TABOOLA_VIEW_RESIZED`
* `GlobalNotificationReceiver.TABOOLA_ITEM_DID_CLICK`
* `GlobalNotificationReceiver.TABOOLA_DID_FAILAD`

In order to catch those notifications, you can use the class `com.taboola.android.globalNotifications.GlobalNotificationReceiver`

1. Create a new `GlobalNotificationReceiver` object in your `Activity`/`Fragment`

2. In `OnResume()` or `onCreate()`, register the `GloablNotificationReceiver` to receive broadcasts from `TaboolaWidget`s

 ```java
LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
        mGlobalNotificationReceiver,
        new IntentFilter(GlobalNotificationReceiver.GLOBAL_NOTIFICATIONS_KEY)
);
 ```

1. Implement `OnGlobalNotificationsListener` interface - this implementing object will be called whenever a broadcast is received

2. Register the object which implements `OnGlobalNotificationsListener` with your `GlobalNotificationReceiver` using the method `registerNotificationsListener`

3. Make sure you unregister `GlobalNotificationReceiver` in `onPause()`/`onDestroy()`

 ```java
LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mGlobalNotificationReceiver);
 ```

## Public Properties

#### publisher

Mandatory. Sets the `publisher`  (can also be set via XML)

#### mode

Mandatory. Sets the widget display `mode` (can also be set via XML)

#### placement

Mandatory. Sets the widget `placement` (can also be set via XML)

#### pageType

Mandatory. (Can also be set via XML)

#### pageUrl

Mandatory. (Can also be set via XML)

#### taboolaEventListener

Optional. Attaches a `TaboolaEventListener` to the `TaboolaWidget`. Allows intercepting clicks and handle height resize events

#### itemClickEnabled

Optional. Default: `true`. (can also be set via XML)

#### optionalPageCommands
Allows pushing commands to the `TaboolaWidget`, as used in the Taboola JavaScript API

#### optionalModeCommands
Allows pushing commands to the `TaboolaWidget`, as used in the Taboola JavaScript API

#### autoResizeHeight

Default: true. Determines whether `TaboolaWidget` may resize when the loaded content requires

## Public methods

#### public void fetchContent()

After initializing the `TaboolaWidget`, this method should be called to actually fetch the recommendations

#### public void reset()

Resets the `TaboolaWidget`- All conents and pushed commands are cleared. New commands must be pushed before fetching data again.

#### public void refresh()

Refreshes the recommendations displayed on the `TaboolaWidget`.

#### public void setLogLevel(Logger.Level logLevel)
Set level of log output of the widget. (default level is `ERROR`)

#### public void pushCommands(HashMap<String, String> arrCommands)
Sets the `TaboolaWidget` attributes. You can use keys from class `com.taboola.android.utils.Const`
(Same as setting every attribute individually via `setMode(String mode)`, `setPublisher(String publisher)`, etc.)
