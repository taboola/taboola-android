# Taboola Android SDK

Version 1.001.0

[[TOC]]

## Getting Started

### Minimum requirements

* Android version 2.1  (android:minSdkVersion="9")

### Incorporating the SDK

1. Add the library .aar to your project (AndroidStudio)

2. Include this line in your app’s AndroidManifest.xml to allow Internet access
 `<uses-permission android:name="android.permission.INTERNET" />`

3. Any activity which is intended to show TaboolaWidget should include the following attribute, to avoid reloading the Taboola recommendations multiple time unnecessarily: android:configChanges="orientation|screenSize|keyboardHidden"

### Displaying Taboola recommendations widget

To include Taboola recommendations in your app just add a com.taboola.android.TaboolaWidget to your UI.
TaboolaWidget subclasses ViewGroup and behaves just like any other standard Android view.

1. Include the XML block in your activity or fragment layout

<table>
  <tr>
    <td><com.taboola.android.TaboolaWidget
   android:id="@+id/taboolaView"
   android:layout_width="match_parent"
   taboola:mode="thumbnails-a"
   taboola:pageType="article"
   taboola:pageUrl="http://example.com"
   taboola:placement="Mobile below article"
   taboola:publisher="betterbytheminute" /></td>
  </tr>
</table>


2. Replace the values for publisher, mode, and placement with those provided by your Taboola account manager.

3. In your activity or fragment code, declare an instance on TaboolaWidget

<table>
  <tr>
    <td>import com.taboola.android.TaboolaWidget;
...
private TaboolaWidget taboola;</td>
  </tr>
</table>


1. In your activity OnCreate or fragment OnCreateView, assign the inflated TaboolaWidget defined in the XML to the TaboolaWidget declared in the previous step, and have it fetch the display the recommendations

<table>
  <tr>
    <td>...
taboola = (TaboolaWidget) rootView.findViewById(R.id.taboolaView);
taboola.fetchContent();
...</td>
  </tr>
</table>


1. Run your app, your activity/fragment should now show Taboola recommendations.

2. Replace the attribute values in the XML according to the values provided by your Taboola account manager (publisher, mode, placement, pageUrl)

### Setting the TaboolaWidget properties in code

Optionally, you can set the TaboolaWidget attributes directly in code, rather than have them set in XML

<table>
  <tr>
    <td>TaboolaWidget taboola = (TaboolaWidget) findViewById(R.id.taboolaView);

// Optional - Set the mode in publisher via code (instead of XML)
// taboola.setPublisher("<my-publisher>");
// taboola.setMode("<my-mode>");

// set your content data parameters
HashMap<String, String> contentCmd = new HashMap<String,String>();
contentCmd.put("article","auto");
contentCmd.put("url","http://www.example.com");
contentCmd.put("referrer","http://www.example.com/ref");
taboola.pushCommands(contentCmd);

// fetch and display recommendations 
taboola.fetchContent();</td>
  </tr>
</table>


### Intercepting recommendation clicks

The default click behavior of TaboolaWidget is as follows:

* On devices where Chrome custom tab is supported - open the recommendation in a chrome custom tab (in-app)

* Otherwise - open the recommendation in the system default web browser (outside of the app) 

TaboolaWidget allows app developers to intercept recommendation clicks in order to create a click-through or to override the default way of opening the recommended article.

In order to intercept clicks, you should implement the interface com.taboola.android.TaboolaEventListener and connect TaboolaWidget to your event listener. The activity or fragment in which TaboolaWidget is displayed might be a good candidate to implement TaboolaEventListener.

TaboolaEventListener include the methods:

<table>
  <tr>
    <td>Public boolean taboolaViewItemClickHandler(String url, boolean isOrganic);
public void taboolaViewResizeHandler(TaboolaWidget widget, int height);</td>
  </tr>
</table>


#### taboolaViewItemClickHandler

This method will be called every time a user clicks a recommendation, right before triggering the default behavior with Intent.ACTION_VIEW. The app can intercept the click there, and should return a boolean value. 

* Return **false** - abort the default behavior, the app should display the recommendation content on its own (for example, using an in-app browser). 

* Return **true** - this will allow the app to implement a click-through and continue to the default behaviour.

isOrganic indicates whether the item clicked was an organic content recommendation or not. **Best practice would be to suppress the default behavior for organic items, and instead open the relevant screen in your app which shows that piece of content. **

##### Example:

<table>
  <tr>
    <td>@Override
public boolean taboolaViewItemClickHandler(String url, boolean isOrganic) {
    if (isOragnic){
        showInAppContent(url);
        return false;
    }
    return true;
}</td>
  </tr>
</table>


### Handling Taboola widget resize

TaboolaWidget may resize its height after loading recommendations, to make sure that the full content is displayed (based on the actual widget "mode" loaded).

After resize, TaboolaWidget will call taboolaViewResizeHandler method of the TaboolaEventListener, to allow the host app to adjust its layout to the changes. (This behavior may be disabled by setting the property autoResizeHeight to false.)

## Mediation

### Suppored Ad Platforms

Taboola Android SDK supports mediation via these platforms:

* DFP

* AdMob

* MoPub

<TBD>

### Catching global notifications (broadcasts) from TaboolaWidget

TaboolaWidget fires app level broadcasts to notify registered objects within the app about certain event. Catching those events might be useful for implementing custom event mediation adapters for ad platforms not natively supported by Taboola Android SDK.

These are the types of broadcasts sent by TaboolaWidget:

* GlobalNotificationReceiver.TABOOLA_DID_RECEIVEAD​

* GlobalNotificationReceiver.TABOOLA_VIEW_RESIZED

* GlobalNotificationReceiver.TABOOLA_ITEM_DID_CLICK

* GlobalNotificationReceiver.TABOOLA_DID_FAILAD

In order to catch those notifications, you can use the class com.taboola.android.globalNotifications.GlobalNotificationReceiver

1. Create a new GlobalNotificationReceiver object in your activity/fragment

2. In OnResume() or onCreate(), register the GloablNotificationReceiver to receive broadcasts from TaboolaWidgets

<table>
  <tr>
    <td>LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
    mGlobalNotificationReceiver,
    new IntentFilter(GlobalNotificationReceiver.GLOBAL_NOTIFICATIONS_KEY)
);</td>
  </tr>
</table>


1. Implement OnGlobalNotificationsListener interface - this implementing object will be called whenever a broadcast is received

2. Register the object which implements OnGlobalNotificationsListener with your GlobalNotificationReceiver using the method registerNotificationsListener 

3. Make sure you unregister GlobalNotificationReceiver in onPause()/onDestroy()

<table>
  <tr>
    <td>LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
mGlobalNotificationReceiver);</td>
  </tr>
</table>


## Public Properties

#### publisher

Mandatory.sets the publisher  (can also be set via XML)

#### mode

Mandatory.sets the widget display mode (can also be set via XML)

#### placement

Mandatory. sets the widget placement (can also be set via XML)

#### pageType

Mandatory. (Can also be set via XML)

#### pageUrl

Mandatory. (Can also be set via XML)

#### taboolaEventListener

Optional. Attaches a TaboolaEventListener to the TaboolaWidget. Allows intercepting clicks and handle height resize events

#### itemClickEnabled

Optional. Default: true. (can also be set via XML)

#### optionalPageCommands
allows pushing commands to the Taboola widget, as used in the Taboola JavaScript API

#### optionalModeCommands
allows pushing commands to the Taboola widget, as used in the Taboola JavaScript API

#### autoResizeHeight

Default: true. Determines whether TaboolaWidget may resize when the loaded content requires

## Public methods

#### public void fetchContent()

After initializing the TaboolaView, this method should be called to actually fetch the recommendations

#### public void reset()

resets the TaboolaView - All conents and pushed commands are cleared. new commands must be pushed before fetching data again.

#### public void refresh()

refreshes the recommendations displayed on the TaboolaView.

