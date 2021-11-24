import { NativeModules } from 'react-native';

const { JitsiModule: JitsiModuleAny } = NativeModules;

interface IJitsiModule {
  createJitsiEvent: (name: string, callback: () => void) => void;
}

export const JitsiModule = JitsiModuleAny as IJitsiModule;
