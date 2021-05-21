import 'dart:async';
import 'dart:io' show Platform;

import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

enum SimpleBannerSize {
  FULL_BANNER_468x60,
  BANNER_320x50,
  LARGE_BANNER_320x100,
  LEADERBOARD_728x90,
  MEDIUM_RECTANGLE_300x250,
  WIDE_SKYSCRAPER_160x600
}

class SimpleBannerPosition {
  /// offset from top or from bottom, depends on [alignBottom]. should be >= 0.
  final int anchorVericalOffset;

  /// from -1.0 to 1.0, left is negative, zero is center.
  final double horizontalAlignment;

  /// whether to align bottom or top.
  final bool alignBottom;

  const SimpleBannerPosition(
      {this.anchorVericalOffset = 0,
      this.horizontalAlignment = 0.0,
      this.alignBottom = true});
  const SimpleBannerPosition.topLeft(this.anchorVericalOffset,
      {this.horizontalAlignment = -1.0, this.alignBottom = false});
  const SimpleBannerPosition.topCenter(this.anchorVericalOffset,
      {this.horizontalAlignment = 0.0, this.alignBottom = false});
  const SimpleBannerPosition.topRight(this.anchorVericalOffset,
      {this.horizontalAlignment = 1.0, this.alignBottom = false});
  const SimpleBannerPosition.bottomLeft(this.anchorVericalOffset,
      {this.horizontalAlignment = -1.0, this.alignBottom = true});
  const SimpleBannerPosition.bottomCenter(this.anchorVericalOffset,
      {this.horizontalAlignment = 0.0, this.alignBottom = true});
  const SimpleBannerPosition.bottomRight(this.anchorVericalOffset,
      {this.horizontalAlignment = 1.0, this.alignBottom = true});
  static const SimpleBannerPosition defaultPosition = SimpleBannerPosition();
}

class SimpleAdmob {
  static const MethodChannel _channel =
      const MethodChannel('com.github.gnudles/simple_admob');
  SimpleAdmob() {
    _channel.setMethodCallHandler(callHandler);
  }
  static Future<dynamic> callHandler(MethodCall call) {
    print(call.method);
    print(call.arguments);
    return Future(()=>null);
  }

  static Future<bool> initBanner(
    String unitId, [
    SimpleBannerSize bannerSize = SimpleBannerSize.BANNER_320x50,
  ]) async {
    bool ok = false;
    if (Platform.isAndroid) {
      ok = await _channel.invokeMethod('initBanner',
          <String, dynamic>{'unitId': unitId, 'size': bannerSize.index});
    }
    return ok;
  }

  static Future<bool> showBanner(
      [SimpleBannerPosition position =
          SimpleBannerPosition.defaultPosition]) async {
    bool ok = false;
    if (Platform.isAndroid) {
      ok = await _channel.invokeMethod('showBanner', <String, dynamic>{
        'x': position.horizontalAlignment,
        'bottom': position.alignBottom,
        'anchor': position.anchorVericalOffset
      });
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
