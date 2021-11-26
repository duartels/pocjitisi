package com.pocjitsi; // replace com.your-app-name with your app’s name
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import java.util.Map;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
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
  public void createJitsiMeeting(String domain, String roomName, Callback callback) {
    callback.invoke("url: https://" + domain + "/" + roomName);
    Log.d("Teste", "Chegou aqui ------------------------------");
    //MeetingActivity.domain = domain;
    //MeetingActivity.roomName = roomName;
    Activity activity = getCurrentActivity();
    Intent intent = new Intent(activity, MeetingActivity.class);
    activity.startActivity(intent);
  }
}
