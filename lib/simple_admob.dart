import 'dart:async';
import 'dart:io' show Platform;

import 'package:flutter/services.dart';

class SimpleAdmob {
  static const MethodChannel _channel =
      const MethodChannel('com.github.gnudles/simple_admob');

  static Future<bool> initBanner(String unitId) async {
    bool ok = false;
    if (Platform.isAndroid) {
      ok = await _channel.invokeMethod('initBanner', <String, dynamic>{
        'unitId': unitId,
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
