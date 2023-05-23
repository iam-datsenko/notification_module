import type {TurboModule} from 'react-native/Libraries/TurboModule/RCTExport';
import {TurboModuleRegistry} from 'react-native';

export interface Spec extends TurboModule {
  show(
    header: string,
    message: string,
    onPress: () => void,
    icon: string,
  ): Promise<any>;
}

export default TurboModuleRegistry.get<Spec>('RTNNotification') as Spec | null;
