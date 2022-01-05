import { NativeModules, NativeEventEmitter } from 'react-native';

const { JitsiModule: JitsiModuleAny } = NativeModules;

interface IJitsiModule {
  createJitsiMeeting: (
    domain: string,
    roomName: string,
    callback: () => void,
  ) => void;
  onConferenceJoined: (callback: () => void) => void;
  onConferenceTerminated: (callback: () => void) => void;
  onAudioMutedChange: (callback: (muted: boolean) => void) => void;
  onVideoMutedChange: (callback: (muted: boolean) => void) => void;
  onScreenShareToggled: (callback: (sharing: boolean) => void) => void;
}

JitsiModuleAny.onConferenceJoined = (callback: () => void) => {
  const eventEmitter = new NativeEventEmitter(JitsiModuleAny);
  eventEmitter.removeAllListeners('conferenceJoined');
  eventEmitter.addListener('conferenceJoined', callback);
};

JitsiModuleAny.onConferenceTerminated = (callback: () => void) => {
  const eventEmitter = new NativeEventEmitter(JitsiModuleAny);
  eventEmitter.removeAllListeners('conferenceTerminated');
  eventEmitter.addListener('conferenceTerminated', callback);
};

JitsiModuleAny.onAudioMutedChange = (callback: (muted: boolean) => void) => {
  const eventEmitter = new NativeEventEmitter(JitsiModuleAny);
  const innerCallback = (e: { muted: any }) => callback(e.muted === 'true');
  eventEmitter.removeAllListeners('audioMutedChanged');
  eventEmitter.addListener('audioMutedChanged', innerCallback);
};

JitsiModuleAny.onVideoMutedChange = (callback: (muted: boolean) => void) => {
  const eventEmitter = new NativeEventEmitter(JitsiModuleAny);
  const innerCallback = (e: { muted: any }) =>
    callback(e.muted === 'true' || e.muted === '4.0');
  eventEmitter.removeAllListeners('videoMutedChanged');
  eventEmitter.addListener('videoMutedChanged', innerCallback);
};

JitsiModuleAny.onScreenShareToggled = (
  callback: (sharing: boolean) => void,
) => {
  const eventEmitter = new NativeEventEmitter(JitsiModuleAny);
  const innerCallback = (e: { sharing: any }) => callback(e.sharing === 'true');
  eventEmitter.removeAllListeners('screenShareToggled');
  eventEmitter.addListener('screenShareToggled', innerCallback);
};

export const JitsiModule = JitsiModuleAny as IJitsiModule;
