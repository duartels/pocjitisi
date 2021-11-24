package com.pocjitsi;

import android.os.Bundle;

import org.jitsi.meet.sdk.JitsiMeetActivity;

// Example
//
public class MeetingActivity extends JitsiMeetActivity  {
    // private JitsiMeetView view;
    // public static String domain = "meet.jit.si";
    // public static String roomName = "";

    // @Override
    // protected void onActivityResult(
    //         int requestCode,
    //         int resultCode,
    //         Intent data) {
    //     JitsiMeetActivityDelegate.onActivityResult(
    //             this, requestCode, resultCode, data);
    // }

    // @Override
    // public void onBackPressed() {
    //     JitsiMeetActivityDelegate.onBackPressed();
    // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        JitsiModule.callback.invoke("MeetingActivity onCreate");
        super.onCreate(savedInstanceState);

        // view = new JitsiMeetView(this);
        // JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
        //     .setRoom("https://" + domain + "/" + roomName)
        //     .build();
        // view.join(options);

        // setContentView(view);
    }

    // @Override
    // protected void onDestroy() {
    //     super.onDestroy();

    //     view.dispose();
    //     view = null;

    //     JitsiMeetActivityDelegate.onHostDestroy(this);
    // }

    // @Override
    // public void onNewIntent(Intent intent) {
    //     JitsiMeetActivityDelegate.onNewIntent(intent);
    // }

    // @Override
    // public void onRequestPermissionsResult(
    //         final int requestCode,
    //         final String[] permissions,
    //         final int[] grantResults) {
    //     JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    // }

    // @Override
    // protected void onResume() {
    //     super.onResume();

    //     JitsiMeetActivityDelegate.onHostResume(this);
    // }

    // @Override
    // protected void onStop() {
    //     super.onStop();

    //     JitsiMeetActivityDelegate.onHostPause(this);
    // }
}