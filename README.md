# simple_admob

A new flutter package that helps you add AdMob banner (banner only)
Currently working only for android.
Currently only bottom positioning is available.
The highlight of this package is that it allows you to show or hide the banner, without disposing it. 

## Getting Started


```
import 'package:simple_admob/simple_admob.dart';
```

```
await SimpleAdmob.initBanner("test")); // for test
await SimpleAdmob.initBanner("ca-app-pub-yyyyyyyyyyyyyyyy/xxxxxxxxxx")); // or for production
// init starts the Banner in hidden state...
await SimpleAdmob.showBanner();
await SimpleAdmob.hideBanner();
```

don't forget to add in the AndroidManifest.xml
```
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~4354546703"/>
    </application>
</manifest>
```
