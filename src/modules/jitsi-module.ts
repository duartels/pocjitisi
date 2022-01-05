import { NativeModules, NativeEventEmitter } from 'react-native';

const { JitsiModule: JitsiModuleAny } = NativeModules;

interface IJitsiModule {
  createJitsiMeeting: (
    domain: string,
    roomName: string,
    callback: () => void,
  ) => void;
  addEventListener: (eventName: string, callback: (event: any) => void) => void;
}

JitsiModuleAny.addEventListener = (
  eventName: string,
  callback: (event: any) => void,
) => {
  const eventEmitter = new NativeEventEmitter(JitsiModuleAny);
  eventEmitter.addListener(eventName, callback);
};

export const JitsiModule = JitsiModuleAny as IJitsiModule;
