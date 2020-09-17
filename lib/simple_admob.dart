import 'dart:async';
import 'dart:io' show Platform;

import 'package:flutter/services.dart';

enum BannerSize{
  FLUID,
  FULL_BANNER_468x60,
  BANNER_320x50,
  LARGE_BANNER_320x100,
  LEADERBOARD_728x90,
  MEDIUM_RECTANGLE_300x250,
  WIDE_SKYSCRAPER_160x600
}
class SimpleAdmob {
  static const MethodChannel _channel =
      const MethodChannel('com.github.gnudles/simple_admob');

  static Future<bool> initBanner(String unitId, [BannerSize bannerSize = BannerSize.BANNER_320x50]) async {
    bool ok = false;
    if (Platform.isAndroid) {
      ok = await _channel.invokeMethod('initBanner', <String, dynamic>{
        'unitId': unitId,
        'size':bannerSize.index
      });
    }
    return ok;
  }

  static Future<bool> showBanner() async {
    bool ok = false;
    if (Platform.isAndroid) {
      ok = await _channel.invokeMethod('showBanner');
    }
    return ok;
  }

  static Future<bool> hideBanner() async {
    bool ok = false;
    if (Platform.isAndroid) {
      ok = await _channel.invokeMethod('hideBanner');
    }
    return ok;
  }

  static Future<bool> destroyBanner() async {
    bool ok = false;
    if (Platform.isAndroid) {
      ok = await _channel.invokeMethod('destroyBanner');
    }
    return ok;
  }
}
