package info.eric.nobaking.util;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ResourceModule {

  @Binds
  abstract ExecutorProvider provideExecutorProvider(ExecutorProviderImpl impl);
}
