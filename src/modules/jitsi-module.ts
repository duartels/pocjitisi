import { NativeModules } from 'react-native';

const { JitsiModule: JitsiModuleAny } = NativeModules;

interface IJitsiModule {
  createJitsiMeeting: (
    domain: string,
    roomName: string,
    callback: () => void,
  ) => void;
}

export const JitsiModule = JitsiModuleAny as IJitsiModule;
