# Taboola Android SDK
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
[ ![Download](https://api.bintray.com/packages/taboola-com/taboola-android-sdk/android-sdk/images/download.svg) ](https://bintray.com/taboola-com/taboola-android-sdk/android-sdk/_latestVersion)[![License](https://img.shields.io/badge/License%20-Taboola%20SDK%20License-blue.svg)](https://github.com/taboola/taboola-android/blob/master/LICENSE)

## Table Of Contents
1. [Getting Started](#1-getting-started)
2. [Example App](#2-example-app)
3. [SDK Reference](#3-sdk-reference)
4. [GDPR](#4-gdpr)
5. [Proguard](#5-proguard)
6. [License](#6-license)


## 1. Getting Started

### 1.1. Minimum requirements

* Android version 4.0  (```android:minSdkVersion="14"```)

### 1.2. Incorporating the SDK

1. Add the library dependency to your project
implement latest version
 ```groovy
     implementation 'com.taboola:android-sdk:2.0.23@aar'

     // include to have clicks open in chrome tabs rather than in a default browser (mandatory)
     implementation 'com.android.support:customtabs:26.+'

     // include if you are using DFP mediation
     implementation 'com.google.firebase:firebase-ads:11.+'

     // include if you are using MoPub mediation
     implementation('com.mopub:mopub-sdk-banner:4.16.+@aar') {
         transitive = true
     }
 ```
> ## Notice
> We encourage developers to use the latest SDK version. In order to stay up-to-date, we suggest to subscribe in order to get github notifications whenever there is a new release. For more information check: https://help.github.com/articles/managing-notifications-for-pushes-to-a-repository/


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

### 1.3. Displaying Taboola recommendations widget

To include Taboola recommendations in your app just add `com.taboola.android.TaboolaWidget` to your UI.
`TaboolaWidget` subclass `WebView` behaves just like any other standard Android view.

1. Include the XML block in your `Activity` or `Fragment` layout

 ```xml
 <!-- Specify target_type only if it's specified by your Taboola account manager. -->
 <com.taboola.android.TaboolaWidget
    android:id="@+id/taboola_view"
    android:layout_width="match_parent"
    taboola:publisher="<publisher-as-supplied-by-taboola>"
    taboola:mode="<mode-as-supplied-by-taboola>"
    taboola:placement="<placement-as-supplied-by-taboola>"
    taboola:url="<public-web-url-which-reflects-the-current-content>"
    taboola:page_type="<my-page-type>"
    taboola:target_type="<my-target-type>"
    />
 ```
2. Replace the attribute values in the XML according to the values provided by your Taboola account manager (`publisher`, `mode`, `placement`, `url`, `page_type`, `target_type`)

3. In your `Activity` or `Fragment` code, declare an instance on `TaboolaWidget`

 ```java
import com.taboola.android.TaboolaWidget;
//...
private TaboolaWidget taboola;
 ```

4. In your `Activity` `OnCreate` or `Fragment` `OnCreateView`, assign the inflated `TaboolaWidget` defined in the XML to the `TaboolaWidget` declared in the previous step, and then fetch the display of recommendations
 ```java
taboola = (TaboolaWidget) rootView.findViewById(R.id.taboolaView);
taboola.fetchContent();
 ```

5. Run your app, your `Activity`/`Fragment` should now show Taboola recommendations.

### 1.4. Setting the TaboolaWidget properties in code

Optionally, you can set the TaboolaWidget attributes directly in code, rather than have them set in XML

 ```java
TaboolaWidget taboola = (TaboolaWidget) findViewById(R.id.taboola_view);

// Optional - set your content data parameters via code (instead of XML)
taboola.setPublisher("<publisher-as-supplied-by-taboola>")
        .setMode("<mode-as-supplied-by-taboola>")
        .setPlacement("<placement-as-supplied-by-taboola>")
        .setPageUrl("<public-web-url-which-reflects-the-current-content>")
        .setPageType("<my-page-type>")
        .setPageId("<my-page-URI>"); //default value is the relative path of the pageUrl provided.


// Optional. Set this parameter only if instructed by your Taboola account manager.
taboola.setTargetType("<my-target-type>");

// Optional. Set text size in zoom.
taboola.setTextZoom(<text-size>)

// fetch and display recommendations
taboola.fetchContent();
 ```

### 1.5. Intercepting recommendation clicks

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

##### 1.5.1. taboolaViewItemClickHandler

This method will be called every time a user clicks on a recommendation, right before triggering the default behavior with `Intent.ACTION_VIEW`. The app can intercept the click there, and should return a `boolean` value.

* Return **`false`** - abort the default behavior, the app should display the recommendation content on its own (for example, using an in-app browser). (Since 1.2.1 aborts only for organic items!)
* Return **`true`** - this will allow the app to implement a click-through and continue to the default behaviour.

`isOrganic` indicates whether the item clicked was an organic content recommendation or not.
**The best practice would be to suppress the default behavior for organic items, and instead open the relevant screen in your app which will show that piece of content.**

##### 1.5.1.1. Example:
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
### 1.6. How to set TaboolaView height and scroll:
#### 1.6.1 For widget:
* Choose between fixed or automatic height

#### Automatic height resize
By default, TaboolaView automatically adjusts its own height in run time to show the entire widget.
The SDK will automatically decide the height, so you don’t need to give it.

```
 taboolaView.setAutoResizeHeight(true); // This is the default, no need to add this code
```

```
//Disable scroll inside the widget
taboolaView. setInterceptScroll(false); // This is the default, no need to add this code
```

#### Fixed height:

 * Set the TaboolaView frame(The most important is the height)
```
 taboolaView.setAutoResizeHeight(false);
```

```
//Enable scroll inside the widget
taboolaView. setInterceptScroll(true);
```

#### 1.6.2 For Feed:
Our widget is a custom webview. The feed is endless and it has a scroll functionality.When implementing feed, the view has a fixed size, usually in the bottom of the screen. When the app is scrolled and the view is taking up all the screen, the app scroll should hand over the scroll to our view (inner scroll of the webview).

```
// To enable scroll switch between the scrollView and taboolaView
taboolaView. setInterceptScroll(true);
```
#### Automatic height
By default, TaboolaView automatically adjusts its own height in run time to show the entire widget.

```
//To get the automatic height
taboolaView.widgetHeight;
```
In collectionView or tabolaView, set your cell height with ```taboolaView.widgetHeight;```

```
taboolaView.setAutoResizeHeight(true); // This is the default, no need to add this code
```

```
//Disable scroll inside the widget
taboolaView. setScrollEnabled(false); //This is the default, no need to add this code
```
####  Fixed height:

* Set the TaboolaView frame (The most important is the height).
* In CollectionView or tableView, set the cell height the same to tabolaView.

```
taboolaView.setAutoResizeHeight(false);
```
```
//Enable scroll inside the widget
taboolaView. setScrollEnabled(true);
```

### 1.7. Handling Taboola widget resize

`TaboolaWidget` may resize its height after loading recommendations, to make sure that the full content is displayed (based on the actual widget `mode` loaded).

After resize, `TaboolaWidget` will call `taboolaViewResizeHandler` method of the `TaboolaEventListener`, to allow the host app to adjust its layout to the changes. (This behavior may be disabled by setting the property `autoResizeHeight` to `false`.)

### 1.8. Catching global notifications (broadcasts) from TaboolaWidget

`TaboolaWidget` fires app level broadcasts to notify registered objects within the app about certain event. Catching those events might be useful for implementing custom event mediation adapters for ad platforms not natively supported by Taboola Android SDK.

These are the types of broadcasts sent by TaboolaWidget:

* `GlobalNotificationReceiver.TABOOLA_DID_RECEIVE_AD​`
* `GlobalNotificationReceiver.TABOOLA_VIEW_RESIZED`
* `GlobalNotificationReceiver.TABOOLA_ITEM_DID_CLICK`
* `GlobalNotificationReceiver.TABOOLA_DID_FAIL_AD`

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

## 2. Example App
This repository includes an example Android app which uses the Taboola SDK. To use it, just clone this repository and open the project wih Android Studio.

In case you encounter some issues while integrating the SDK into your app, try to recreate the scenario within the example app. This might help to isolate the problems, and in case you weren't able to solve it, you would be able to send the example app with your recreated issue to Taboola's support (for more help).

## 3. SDK Reference
### 3.1. Public Properties
##### `String publisher`

Mandatory. Sets the `publisher`  (can also be set via XML as `publisher`)

##### `String mode`

Mandatory. Sets the widget display `mode` (can also be set via XML as `mode`)

##### `String placement`

Mandatory. Sets the widget `placement` (can also be set via XML as `placement`)

##### `String pageType`

Mandatory. (Can also be set via XML as `page_type`)

##### `String pageUrl`

Mandatory. (Can also be set via XML as `url`)

##### `String targetType`

Optional. Default: `"mix"`. (can also be set via XML as `target_type`).
Change only if it's specified by your Taboola account manager.

##### `boolean itemClickEnabled`

Optional. Default: `true`. (can also be set via XML as `item_click_enabled`)

##### `boolean autoResizeHeight`

Default: true. Determines whether `TaboolaWidget` may resize when the loaded content requires (can also be set via XML as `auto_resize_height`)

##### `TaboolaEventListener taboolaEventListener`

Optional. Attaches a `TaboolaEventListener` to the `TaboolaWidget`. Allows intercepting clicks and handle height resize events

##### `HashMap<String, String> optionalPageCommands`
Allows pushing commands to the `TaboolaWidget`, as used in the Taboola JavaScript API

##### `HashMap<String, String> optionalModeCommands`
Allows pushing commands to the `TaboolaWidget`, as used in the Taboola JavaScript API

##### `ScrollToTopListener`
Optional. A callback that notifies when the widget is on the top of the screen and is scrolled up (used for feed handling).


### 3.2. Public methods

##### `public void fetchContent()`

After initializing the `TaboolaWidget`, this method should be called to actually fetch the recommendations

##### `public void reset()`

Resets the `TaboolaWidget`- All conents and pushed commands are cleared. New commands must be pushed before fetching data again.

##### `public void refresh()`

Refreshes the recommendations displayed on the `TaboolaWidget`.

##### `public void setLogLevel(Logger.Level logLevel)`
Set level of log output of the widget. (default level is `ERROR`)

##### `public void pushCommands(HashMap<String, String> arrCommands)`
Sets the `TaboolaWidget` attributes. You can use keys from class `com.taboola.android.utils.Const`
(Same as setting every attribute individually via `setMode(String mode)`, `setPublisher(String publisher)`, etc.)

##### `setInterceptScroll`
Set whether the widget should handle the scroll automatically (see the feed section).

## 4. GDPR

In order to support the The EU General Data Protection Regulation (GDPR - https://www.eugdpr.org/) in Taboola Mobile SDK, application developer should show a pop up asking the user's permission for storing their personal data in the App. In order to control the user's personal data (to store in the App or not) there exists a flag `User_opt_out`. It's mandatory to set this flag when using the Taboola SDK. The way to set this flag depends on the type of SDK you are using. By default we assume no permission from the user on a pop up, so the personal data will not be saved.

### 4.1. How to set the flag in the SDK integration
Below you can find the way how to set the flag on Android SDK Standard we support. It's recommended to put these lines alongside the other settings, such as publisher name, etc

```javascript
// Sample code
   HashMap<String, String> optionalPageCommands = new HashMap<>();
   TaboolaWidget taboola = new TaboolaWidget(getContext());
   taboola.setPublisher("the-publisher-name")
           .setMode("thumbnails-a")
           .setPageType("home")
           .setPageUrl("http://www.example.com/")
           .setPlacement("Below Homepage Thumbnails");
       optionalPageCommands.put("user_opt_out","true");
       Taboola.setOptionalPageCommands(optionalPageCommands);

```

## 5. ProGuard
You can find proguard rules for Taboola Widget in [proguard-taboola-widget.pro](app/proguard-taboola-widget.pro) file.
The file contains instructions on which rules to comment/uncomment depending on which parts of the SDK you are using.

## 6. License
This program is licensed under the Taboola, Inc. SDK License Agreement (the “License Agreement”).  By copying, using or redistributing this program, you agree with the terms of the License Agreement.  The full text of the license agreement can be found at https://github.com/taboola/taboola-android/blob/master/LICENSE.
Copyright 2017 Taboola, Inc.  All rights reserved.
