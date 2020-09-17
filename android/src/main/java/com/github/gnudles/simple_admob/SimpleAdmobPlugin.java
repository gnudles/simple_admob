package com.github.gnudles.simple_admob;

import android.app.Activity;

import android.util.Size;
import android.view.View;
import android.view.Gravity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;




/** SimpleAdmobPlugin */
public class SimpleAdmobPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel _channel;
  private Activity _activity;
  private AdView _adView;
  private AdSize _adSize;
  private Size _adPixelSize;
  LinearLayout _layout;
  private FlutterPluginBinding pluginBinding;

  private void initializePlugin(Activity activity) {
    this._activity = activity;

    _channel = new MethodChannel(pluginBinding.getBinaryMessenger(), "com.github.gnudles/simple_admob");
    _channel.setMethodCallHandler(this);

  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

    pluginBinding=flutterPluginBinding;
  }
  final AdSize  adSizesMap [] = {
  AdSize.FULL_BANNER,
  AdSize.BANNER,
  AdSize.LARGE_BANNER,
  AdSize.LEADERBOARD,
  AdSize.MEDIUM_RECTANGLE,
  AdSize.WIDE_SKYSCRAPER
  };
  final Size  adPixelSizesMap [] = {
          new Size(468,60),
          new Size(320,50),
          new Size(320,100),
          new Size(728,90),
          new Size(300,250),
          new Size(160,600)
  };
  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("initBanner")) {
      int sizeKey = (int)call.argument("size");
      _adSize = adSizesMap[sizeKey];
      _adPixelSize = adPixelSizesMap[sizeKey];

      MobileAds.initialize(_activity, new OnInitializationCompleteListener() {
        @Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {
                
        }
      });

      if (_adView!=null)
      {
        _adView.destroy();
        _layout.removeAllViews();
      }
      _adView = new AdView(_activity);

      _adView.setAdSize(_adSize);

      String unitId = call.argument("unitId");
      if (unitId.equals("test"))
      {
        unitId="ca-app-pub-3940256099942544/8865242552";
      }
      _adView.setAdUnitId(unitId);


      AdRequest adRequest = new AdRequest.Builder().build();
      _adView.loadAd(adRequest);
      _adView.pause();
        
      
      
       {
        int anchorType =Gravity.TOP;
        if (_layout == null)
        {
            _layout = new LinearLayout(_activity);
            _activity.addContentView(
            _layout,
            new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        _layout.setOrientation(LinearLayout.VERTICAL);
        _layout.setGravity(anchorType);
        _layout.addView(_adView);
        _layout.setVisibility (View.INVISIBLE);
      }
      result.success(true);

    }
    else if (call.method.equals("hideBanner")) {
      if (_adView!=null)
      {
        _adView.pause();
        _layout.setVisibility (View.INVISIBLE);
        result.success(true);
      }
      else
      {
        result.success(false);
      }
    }
    else if (call.method.equals("showBanner")) {
      double x = (double)call.argument("x");
      boolean alignBottom = (boolean)call.argument("bottom");
      int verticalAnchor = (int)call.argument("anchor");
      final float scale = _activity.getResources().getDisplayMetrics().density;
      int left = (int)(((x+1.0)/2.0)*(_activity.getResources().getDisplayMetrics().widthPixels-scale*_adPixelSize.getWidth()));
      //int top = (int)(((y+1.0)/2.0)*(_activity.getResources().getDisplayMetrics().heightPixels-scale*_adPixelSize.getHeight()));
      //int bottom = _activity.getResources().getDisplayMetrics().heightPixels - top - _adPixelSize.getHeight();
      int right = _activity.getResources().getDisplayMetrics().widthPixels - left - (int)(scale*_adPixelSize.getWidth());
      int top = alignBottom?0:(int)(verticalAnchor*scale);
      int bottom = alignBottom?(int)(verticalAnchor*scale):0;
      _layout.setGravity(alignBottom?Gravity.BOTTOM:Gravity.TOP);
      _layout.setPadding(left,top,right,bottom);
      //_activity.getResources().getDisplayMetrics().heightPixels;
      //_activity.getResources().getDisplayMetrics().heightPixels;

      _layout.setVisibility (View.VISIBLE);
      _adView.resume();
      result.success(true);
    }
    else if (call.method.equals("destroyBanner")) {
      _layout.removeAllViews();
      
      _adView.destroy();

      
      result.success(true);
    }
    else {
      result.notImplemented();

    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    _channel.setMethodCallHandler(null);
    if (_adView!=null)
    {
      _layout.removeAllViews();
      _adView.destroy();
    }
    pluginBinding = null;
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    initializePlugin(
            binding.getActivity());
  }
  @Override
  public void onDetachedFromActivityForConfigChanges() {

    onDetachedFromActivity();
  }
  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    onAttachedToActivity(binding);
  }
  @Override
  public void onDetachedFromActivity() {
    if (_adView!=null)
    {
      _layout.removeAllViews();
      _adView.destroy();
    }
    _activity = null;
    _layout = null;
    _adView = null;
  }
}

