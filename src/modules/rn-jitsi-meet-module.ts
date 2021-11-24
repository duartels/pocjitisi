/**
 * @providesModule JitsiMeet
 */

import { NativeModules, requireNativeComponent } from 'react-native';

export const JitsiMeetView = requireNativeComponent('RNJitsiMeetView');

const { RNJitsiMeetModule } = NativeModules;

interface IJitsiMeetModule {
  call: (url: string, userInfo: {}) => void;
  audioCall: (url: string, userInfo: {}) => void;
}

export const JitsiMeetModule = RNJitsiMeetModule as IJitsiMeetModule;
