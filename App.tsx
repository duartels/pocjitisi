/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React from 'react';
import { Button } from 'react-native';

import { /*JitsiModule,*/ JitsiMeetModule } from './src/modules';

const App = () => {
  const onPress = () => {
    console.log('We will invoke the native module here!');
    // JitsiModule.createJitsiMeeting(
    //   'luan-gcp-jitsi.proseia.app',
    //   'pocjitsi241121test',
    //   console.log,
    // );
    JitsiMeetModule.call(
      'https://luan-gcp-jitsi.proseia.app/pocjitsi241121test',
      {
        displayName: 'Bruno',
        email: 'bruno@telluria.com.br',
        avatar:
          'https://ui-avatars.com/api/?background=FF8888&size=128&color=FFFFFF&name=Bruno',
      },
      console.log,
    );
  };

  return (
    <Button
      title="Click to invoke your native module!"
      color="#841584"
      onPress={onPress}
    />
  );
};

export default App;
