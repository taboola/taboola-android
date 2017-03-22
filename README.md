# Taboola Android SDK
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
[ ![Download](https://api.bintray.com/packages/taboola-com/taboola-android-sdk/android-sdk/images/download.svg) ](https://bintray.com/taboola-com/taboola-android-sdk/android-sdk/_latestVersion)
[![License](https://img.shields.io/badge/License%20-Taboola%20SDK%20License-blue.svg)](https://github.com/taboola/taboola-android/blob/master/LICENSE)

## Table Of Contents
1. [Getting Started](#1-getting-started)
2. [Example App](#2-example-app)
3. [Mediation](#3-mediation)
4. [SDK Reference](#4-sdk-reference)
5. [License](#5-license)


## 1. Getting Started

### 1.1. Minimum requirements

* Android version 2.1  (```android:minSdkVersion="9"```)

### 1.2. Incorporating the SDK

1. Add the library dependency to your project
 ```groovy
     compile 'com.taboola:android-sdk:1.+@aar'

     // include if you want ads to be open in chrome tabs rather than in a default browser
     compile 'com.android.support:customtabs:25.+'

     // include if you are using DFP mediation
     compile 'com.google.firebase:firebase-ads:10.+'

     // include if you are using MoPub mediation
     compile('com.mopub:mopub-sdk-banner:4.+@aar') {
         transitive = true
     }
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
 
### 1.3. Displaying Taboola recommendations widget

To include Taboola recommendations in your app just add a `com.taboola.android.TaboolaWidget` to your UI.
`TaboolaWidget` subclasses `ViewGroup` and behaves just like any other standard Android view.

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
    taboola:target_type="<my-page-type>"
    />
 ```
2. Replace the attribute values in the XML according to the values provided by your Taboola account manager (`publisher`, `mode`, `placement`, `url`, `page_type`)

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

### 1.4. Setting the TaboolaWidget properties in code

Optionally, you can set the TaboolaWidget attributes directly in code, rather than have them set in XML

 ```java
TaboolaWidget taboola = (TaboolaWidget) findViewById(R.id.taboola_view);

// Optional - set your content data parameters via code (instead of XML)
taboola.setPublisher("<my-publisher>")
        .setMode("<my-mode>")
        .setPlacement("<my-placement>")
        .setPageUrl("http://www.example.com")
        .setPageType("<my-page-type>");

// Optional. Set targetType only if it's specified by your Taboola account manager
taboola.setTargetType("<my-target-type>");

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

This method will be called every time a user clicks a recommendation, right before triggering the default behavior with `Intent.ACTION_VIEW`. The app can intercept the click there, and should return a `boolean` value.

* Return **`false`** - abort the default behavior, the app should display the recommendation content on its own (for example, using an in-app browser).
* Return **`true`** - this will allow the app to implement a click-through and continue to the default behaviour.

`isOrganic` indicates whether the item clicked was an organic content recommendation or not.
**Best practice would be to suppress the default behavior for organic items, and instead open the relevant screen in your app which shows that piece of content.**

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

### 1.6. Handling Taboola widget resize

`TaboolaWidget` may resize its height after loading recommendations, to make sure that the full content is displayed (based on the actual widget `mode` loaded).

After resize, `TaboolaWidget` will call `taboolaViewResizeHandler` method of the `TaboolaEventListener`, to allow the host app to adjust its layout to the changes. (This behavior may be disabled by setting the property `autoResizeHeight` to `false`.)

### 1.7. Catching global notifications (broadcasts) from TaboolaWidget

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

## 2. Example App
This repository includes an example Android app which uses the Taboola SDK. To use it, just clone this repository and open the project wih Android Studio. 

In case you encounter some issues when integrating the SDK into your app, try recreating the scenario within the example app. This might help isolate the problem, and in case you weren't able to solve it, you'll be able to send the example app with your recreated issue to Taboola's support for more help.


## 3. Mediation

#### 3.1. Supported Ad Platforms

Taboola Android SDK supports mediation via these platforms:

* [DFP](https://developers.google.com/mobile-ads-sdk/docs/dfp/android/custom-events)
* [AdMob](https://firebase.google.com/docs/admob/android/custom-events)
* [MoPub](http://www.mopub.com/resources/docs/mopub-ui-account-setup/ad-network-set-up/)

#### 3.2 Required Setup
In order to configure mediation of Taboola SDK via a 3rd party platform, follow the steps listed below. 

1. Include the Taboola SDK in your app as explained under [1.2. Incorporating the SDK](#12-incorporating-the-sdk)

2. In the required platform web managemnt interface, create a new "custom event" network named "Taboola", and fill the parameters as described [below](#33-parameters-for-custom-events-configuration).

3. Target impressions from the newly created Taboola network into the required ad-units within your app.

In this way, the platform would automatically use the Taboola SDK when an impression from Taboola should be displayed.

These steps are similar between all platforms, more detailed information can be found in these links for the specific platforms:

* [DFP](https://developers.google.com/mobile-ads-sdk/docs/dfp/android/custom-events)
* [AdMob](https://firebase.google.com/docs/admob/android/custom-events)
* [MoPub](http://www.mopub.com/resources/docs/mopub-ui-account-setup/ad-network-set-up/)

#### 3.3 Parameters for Custom Events configuration

##### 3.3.1 DFP & AdMob
* **Class name**: com.taboola.android.mediation.DfpCustomEventBanner
* **Parameters**: Parameters for the Taboola SDK can be configured either from the DFP web interface or within the code (**settings from web interface take precedence over settings configured in code**). 
	* 	**Configuring from DFP web interface**: The "parameter" field in the DFP custom event configuration screen, should contain a JSON string with the required properties. Notice that strings should be enclosed within ***escaped double quotes***.

	```javascript
	{
		\"publisher\":\"<publisher code>\",
		\"mode\":\"<mode>\",
		\"url\":\"<url>\",
		\"placement\":\"<placement>\",
		\"article\":\"auto\",
		\"referrer\":\"<ref url>"
	}
	```

	* **Configuring within the app code**: Use the following DFP method to send a NetworkExtrasBundle to the TaboolaSDK. The Bundle should contain key/value pairs with the required parameters.
	
	```java
	public AdRequest.Builder addNetworkExtrasBundle (Class<? extends MediationAdapter> adapterClass, Bundle networkExtras) 
	```

##### 3.3.2 MoPub
* **Class name**: com.taboola.android.mediation.MoPubCustomEventBanner
* **Parameters**: Parameters for the Taboola SDK can be configured either from the MoPub web interface or within the code (**settings from web interface take precedence over settings configured in code**). 
	* 	**Configuring from MoPub web interface**: The "Custom Class Data" field in the MoPub custom network configuration screen, should contain a JSON string with the required properties. Notice that strings should be enclosed within ***double quotes***.

	```
	{
  		"publisher": "<publisher>",
  		"mode": "<mode>",
  		"url": "http://www.example.com",
  		"article": "auto",
  		"page_type" : "<page_type>",
  		"referrer": "http://www.example.com/ref"
	}
	```

	* **Configuring within the app code**: Use the following MoPub method to send a map of LocalExtras to the TaboolaSDK. The Map should contain key/value pairs with the required parameters.
	
	```java
	public void setLocalExtras(Map<String, Object> localExtras)
	```
	


## 4. SDK Reference 
### 4.1. Public Properties
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

### 4.2. Public methods

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


## 5. License
This program is licensed under the Taboola, Inc. SDK License Agreement (the “License Agreement”).  By copying, using or redistributing this program, you agree to the terms of the License Agreement.  The full text of the license agreement can be found at https://github.com/taboola/taboola-android/blob/master/LICENSE.
Copyright 2017 Taboola, Inc.  All rights reserved.
