package com.pocjitsi; // replace com.your-app-name with your app’s name
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import java.util.Map;
import java.util.HashMap;
import android.util.Log;


public class JitsiModule extends ReactContextBaseJavaModule {
    JitsiModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
      return "JitsiModule";
    }

    @ReactMethod
    public void createJitsiEvent(String name, Callback callback) {
      callback.invoke("Name here is: " + name);
    }
}
