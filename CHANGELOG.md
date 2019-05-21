# Changelog

## [2.1.5](https://github.com/conor-ob/dublin-bus-pal/compare/2.1.4...2.1.5) (21-05-2019)
* Fixed crash when getting bus stop from repository in RealTimeActivity

## [2.1.4](https://github.com/conor-ob/dublin-bus-pal/compare/2.1.3...2.1.4) (21-05-2019)
* Rewrote data model and local database persistence
* Bug fixes

## [2.1.3](https://github.com/conor-ob/dublin-bus-pal/compare/2.1.2...2.1.3) (02-04-2019)
* Fix for bus stop icon flickering on map

## [2.1.2](https://github.com/conor-ob/dublin-bus-pal/compare/2.1.1...2.1.2) (07-01-2019)
* Fix for Android 9 Pie (SDK 28) devices
* Improve crash reporting logging
* Fix bug where last updated time was wrong
* Do not display drop-off stops

## [2.1.1](https://github.com/conor-ob/dublin-bus-pal/compare/2.1.0...2.1.1) (03-01-2019)
Added mechanism to retry failed API calls to return cached static data hosted on this repository

## [2.1.0](https://github.com/conor-ob/dublin-bus-pal/compare/2.0.2...2.1.0) (03-01-2019)
Added ability to reorder saved favourites by dragging and dropping

Fixed database migration crash by using destructive migrations until current database version

## [2.0.2](https://github.com/conor-ob/dublin-bus-pal/compare/2.0.1...2.0.2) (27-12-2018)
Attempt to fix bug where no stops or routes display on upgrading to 2.0.0 or 2.0.1

## [2.0.1](https://github.com/conor-ob/dublin-bus-pal/compare/2.0.0...2.0.1) (27-12-2018)
Fix for crash: https://issuetracker.google.com/issues/79478779

Solution: https://developers.google.com/maps/documentation/android-sdk/config#specify_requirement_for_apache_http_legacy_library

## [2.0.0](https://github.com/conor-ob/dublin-bus-pal/compare/1.0...2.0.0) (27-12-2018)
Added support for new Go Ahead Dublin routes (17A, 33A, 33B, 59, 45A, 45B, 63, 63A, 75, 75A, 102, 111, 175, 184, 185)

## [1.0](https://github.com/conor-ob/dublin-bus-pal/compare/1.0) (07-02-2018)
Initial Release
