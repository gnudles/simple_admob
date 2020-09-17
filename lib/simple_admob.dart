
import 'dart:async';

import 'package:flutter/services.dart';

class SimpleAdmob {
  static const MethodChannel _channel =
      const MethodChannel('com.github.gnudles/simple_admob');

  static Future<bool> initBanner(String unitId) async {
    final String status = await _channel.invokeMethod('initBanner',<String, dynamic>{
        'unitId': unitId,
      });
    return status;
  }
  static Future<bool> showBanner() async {
    final bool ok = await _channel.invokeMethod('showBanner');
    return ok;
  }
  static Future<bool> hideBanner() async {
    final bool ok = await _channel.invokeMethod('hideBanner');
    return ok;
  }
  static Future<bool> destroyBanner() async {
    final bool ok = await _channel.invokeMethod('destroyBanner');
    return ok;
  }
}

