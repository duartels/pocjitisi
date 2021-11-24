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

import { JitsiModule } from './src/modules';

const App = () => {
  const onPress = () => {
    console.log('We will invoke the native module here!');
    JitsiModule.createJitsiEvent('teste', console.log);
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
