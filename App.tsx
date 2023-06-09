/* eslint-disable react-native/no-inline-styles */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, {useEffect, useState} from 'react';
import {
  Button,
  SafeAreaView,
  ScrollView,
  StatusBar,
  useColorScheme,
  View,
  Linking,
  Text,
  Image,
} from 'react-native';
import RNFS from 'react-native-fs';

import {Colors, Header} from 'react-native/Libraries/NewAppScreen';
import RTNNotification from 'rtn-notification/js/NativeNotification';

const useInitialURL = () => {
  const [url, setUrl] = useState<string | null>(null);

  useEffect(() => {
    const getUrlAsync = async () => {
      const initialUrl = await Linking.getInitialURL();
      setUrl(initialUrl);
    };

    getUrlAsync();
  }, []);

  return {url};
};

function App(): JSX.Element {
  const [color, setColor] = useState(false);
  const [icon, setIcon] = useState<any>();
  const isDarkMode = useColorScheme() === 'dark';
  const {url: initialUrl} = useInitialURL();

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  const onPress = () => setColor(!color);

  const convertToBase64 = async () => {
    const byteArray = await RNFS.readFileRes('icon.png', 'base64');

    setIcon(byteArray);
  };

  useEffect(() => {
    convertToBase64();
  }, []);

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <Header />

        <Button
          title="press"
          onPress={async () => {
            await RTNNotification?.show('header', 'message', onPress, icon);
          }}
        />
      </ScrollView>
      <View
        style={{
          backgroundColor: color ? 'red' : 'black',
          height: 200,
        }}
      />
      <Text>Link {initialUrl}</Text>
      <Image source={require('./icon.png')} style={{backgroundColor: 'red'}} />
    </SafeAreaView>
  );
}

export default App;
