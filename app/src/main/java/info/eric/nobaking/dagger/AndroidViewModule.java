package info.eric.nobaking.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.eric.nobaking.MainActivity;

@Module
abstract class AndroidViewModule {

  @ContributesAndroidInjector
  abstract MainActivity mainActivity();
}
