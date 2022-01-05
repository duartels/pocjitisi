package com.pocjitsi; // replace com.your-app-name with your app’s name
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.util.Map;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class JitsiModule extends ReactContextBaseJavaModule {
  private static ReactApplicationContext reactContext;

  JitsiModule(ReactApplicationContext context) {
      super(context);
      reactContext = context;
  }

  @Override
  public String getName() {
    return "JitsiModule";
  }

  @ReactMethod
  public void createJitsiMeeting(String domain, String roomName, Callback callback) {
    callback.invoke("url: https://" + domain + "/" + roomName);
    Log.d("Teste", "Chegou aqui ------------------------------");
    JitsiActivity.domain = domain;
    JitsiActivity.roomName = roomName;
    Activity activity = getCurrentActivity();
    Intent intent = new Intent(activity, JitsiActivity.class);
    activity.startActivity(intent);
  }

  public static void sendEvent(String eventName, WritableMap params) {
    reactContext
     .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
     .emit(eventName, params);
  }

  // @ReactMethod
  // public void addListener(String eventName) {
  //   Log.d("addListener", "addListener ------------------------------");
  //   // Set up any upstream listeners or background tasks as necessary
  // }

  // @ReactMethod
  // public void removeListeners(Integer count) {
  //   Log.d("removeListeners", "removeListeners ------------------------------");
  //   // Remove upstream listeners, stop unnecessary background tasks
  // }

}
