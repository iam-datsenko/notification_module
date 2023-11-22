import type {TurboModule} from 'react-native/Libraries/TurboModule/RCTExport';
import {TurboModuleRegistry} from 'react-native';

export interface Spec extends TurboModule {
  showNotification(title: string, message: string): void;
}

export default TurboModuleRegistry.get<Spec>('RTNNotification') as Spec | null;
