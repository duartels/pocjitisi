package com.pocjitsi;

// import android.support.v4.app.FragmentActivity;

// import org.jitsi.meet.sdk.JitsiMeetActivity;

// // Example
// //
// public class MeetingActivity extends JitsiMeetActivity {
//     public static String domain = "meet.jit.si";
//     public static String roomName = "";
// }

/*
 * Copyright @ 2017-present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package org.jitsi.meet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.RestrictionEntry;
import android.content.RestrictionsManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The one and only Activity that the Jitsi Meet app needs. The
 * {@code Activity} is launched in {@code singleTask} mode, so it will be
 * created upon application initialization and there will be a single instance
 * of it. Further attempts at launching the application once it was already
 * launched will result in {@link MainActivity#onNewIntent(Intent)} being called.
 */
public class JitsiActivity extends JitsiMeetActivity {
    /**
     * The request code identifying requests for the permission to draw on top
     * of other apps. The value must be 16-bit and is arbitrarily chosen here.
     */
    private static final int OVERLAY_PERMISSION_REQUEST_CODE
        = (int) (Math.random() * Short.MAX_VALUE);

    /**
     * ServerURL configuration key for restriction configuration using {@link android.content.RestrictionsManager}
     */
    public static final String RESTRICTION_SERVER_URL = "SERVER_URL";

    public static String domain;
    public static String roomName;

    /**
     * Broadcast receiver for restrictions handling
     */
    private BroadcastReceiver broadcastReceiver;

    /**
     * Flag if configuration is provided by RestrictionManager
     */
    private boolean configurationByRestrictions = false;

    /**
     * Default URL as could be obtained from RestrictionManager
     */
    private String defaultURL = "https://209-50-53-173.us-chi1.upcloud.host";


    // JitsiMeetActivity overrides
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //JitsiMeet.showSplashScreen(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean extraInitialize() {
        //Log.d(this.getClass().getSimpleName(), "LIBRE_BUILD="+BuildConfig.LIBRE_BUILD);

        // Setup Crashlytics and Firebase Dynamic Links
        // Here we are using reflection since it may have been disabled at compile time.
        // try {
        //     Class<?> cls = Class.forName("com.pocjitsi.GoogleServicesHelper");
        //     Method m = cls.getMethod("initialize", JitsiMeetActivity.class);
        //     m.invoke(null, this);
        // } catch (Exception e) {
        //     // Ignore any error, the module is not compiled when LIBRE_BUILD is enabled.
        // }

        // In Debug builds React needs permission to write over other apps in
        // order to display the warning and error overlays.
        if (BuildConfig.DEBUG) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent
                    = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));

                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);

                return true;
            }
        }

        return false;
    }

    @Override
    protected void initialize() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onBroadcastReceived(intent);
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_JOINED.getAction());
        intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.getAction());
        intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.getAction());
        intentFilter.addAction(BroadcastEvent.Type.VIDEO_MUTED_CHANGED.getAction());
        intentFilter.addAction(BroadcastEvent.Type.SCREEN_SHARE_TOGGLED.getAction());
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);

        setJitsiMeetConferenceDefaultOptions();
        //super.initialize();
        super.join(roomName != null ? roomName : "default-room");
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }

        super.onDestroy();
    }

    private void setJitsiMeetConferenceDefaultOptions() {
        // Set default options
        JitsiMeetConferenceOptions defaultOptions
            = new JitsiMeetConferenceOptions.Builder()
            // .setWelcomePageEnabled(true)
            .setServerURL(buildURL(domain != null ? "https://" + domain : defaultURL))
            .setFeatureFlag("call-integration.enabled", false)
            .setFeatureFlag("resolution", 360)
            .setFeatureFlag("server-url-change.enabled", !configurationByRestrictions)
            .setFeatureFlag("add-people.enabled", false)
            .setFeatureFlag("calendar.enabled", false)
            .setFeatureFlag("chat.enabled", true)
            .setFeatureFlag("close-captions.enabled", false)
            .setFeatureFlag("invite.enabled", false)
            .setFeatureFlag("android.screensharing.enabled", true)
            .setFeatureFlag("live-streaming.enabled", false)
            .setFeatureFlag("meeting-name.enabled", false)
            .setFeatureFlag("meeting-password.enabled", false)
            .setFeatureFlag("pip.enabled",  true)
            .setFeatureFlag("kick-out.enabled", true)
            .setFeatureFlag("conference-timer.enabled", true)
            .setFeatureFlag("video-share.enabled", true)
            .setFeatureFlag("recording.enabled", false)
            .setFeatureFlag("reactions.enabled", true)
            .setFeatureFlag("raise-hand.enabled", true)
            .setFeatureFlag("tile-view.enabled", true)
            .setFeatureFlag("toolbox.alwaysVisible", false)
            .setFeatureFlag("toolbox.enabled", true)
            .setFeatureFlag("welcomepage.enabled", false)
            .setAudioMuted(false)
            .setVideoMuted(true)
            .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                initialize();
                return;
            }

            throw new RuntimeException("Overlay permission is required when running in Debug mode.");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // ReactAndroid/src/main/java/com/facebook/react/ReactActivity.java
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (BuildConfig.DEBUG && keyCode == KeyEvent.KEYCODE_MENU) {
            JitsiMeet.showDevOptions();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    // @Override
    // public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
    //     super.onPictureInPictureModeChanged(isInPictureInPictureMode);

    //     Log.d(TAG, "Is in picture-in-picture mode: " + isInPictureInPictureMode);

    //     if (!isInPictureInPictureMode) {
    //         this.startActivity(new Intent(this, getClass())
    //             .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    //     }
    // }

    // Helper methods
    //

    private @Nullable URL buildURL(String urlStr) {
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public WritableMap convertHashMapToWritableMap(HashMap<String, Object> hashMap) {
        WritableMap map = Arguments.createMap();

        for (Map.Entry<String, Object> pair : hashMap.entrySet()) {
            String key = pair.getKey();
            Object value = pair.getValue();

            if (value instanceof Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                map.putString(key, (String) value);
            } else {
                map.putString(key, value.toString());
            }
        }

        return map;
    }

    private void onBroadcastReceived(Intent intent) {
        if (intent != null) {
            BroadcastEvent event = new BroadcastEvent(intent);

            switch (event.getType()) {
                case CONFERENCE_JOINED:
                    onConferenceJoined(event.getData());
                    JitsiModule.sendEvent("conferenceJoined", convertHashMapToWritableMap(event.getData()));
                    break;
                case CONFERENCE_WILL_JOIN:
                    onConferenceWillJoin(event.getData());
                    break;
                case CONFERENCE_TERMINATED:
                    onConferenceTerminated(event.getData());
                    JitsiModule.sendEvent("conferenceTerminated", convertHashMapToWritableMap(event.getData()));
                    break;
                case PARTICIPANT_JOINED:
                    onParticipantJoined(event.getData());
                    break;
                case PARTICIPANT_LEFT:
                    onParticipantLeft(event.getData());
                    break;
                case READY_TO_CLOSE:
                    onReadyToClose();
                    break;
                case AUDIO_MUTED_CHANGED:
                    JitsiModule.sendEvent("audioMutedChanged", convertHashMapToWritableMap(event.getData()));
                    break;
                case VIDEO_MUTED_CHANGED:
                    JitsiModule.sendEvent("videoMutedChanged", convertHashMapToWritableMap(event.getData()));
                    break;
                case SCREEN_SHARE_TOGGLED:
                    JitsiModule.sendEvent("screenShareToggled", convertHashMapToWritableMap(event.getData()));
                    break;
            }
        }
    }
}
