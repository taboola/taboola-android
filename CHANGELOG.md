# Change Log


## [2.0.23] - 2018-08-08
### Fixed
- Fix memory leaks in sdk api
- Added BI layer(internal)
- Protect resize in sdk standard from crash. Add callback for image loading failure

## [1.3.6] - 2018-03-20
### Changed
- Add option to set text zoom on the widget (fixed text size)

## [1.3.1] - 2017-09-12
### Fixed
- Internal bug fixes

## [1.2.1] - 2017-08-09
### Changed
- Overriding default click handling for non-organic items is no longer allowed
- Fix incorrect `TABOOLA_DID_FAILAD` reporting in Widget

## [1.1.12] - 2017-06-22
### Changed
- Fix bug in resize widget
- Fix bug in organic/sponsored detection

## [1.1.11] - 2017-06-07
### Changed
- Fix bug in android version lower than 23

## [1.1.9] - 2017-05-29
### Changed
- Improve organic/sponsored links detection

## [1.1.8] - 2017-05-22
### Changed
- Fix bug on earlier versions of Android with low memory

## [1.1.7] - 2017-05-18
### Changed
- Bug fixes
- Report `TABOOLA_DID_FAILAD` when a widget is loaded with no recommendations
- Faster loading of Chrome custom tabs
- Faster reporting of `visible` events

## [1.1.6] - 2017-04-26
### Changed
- Edit `OnGlobalNotificationsListener` interface to provide `TaboolaWidget` object in all methods
