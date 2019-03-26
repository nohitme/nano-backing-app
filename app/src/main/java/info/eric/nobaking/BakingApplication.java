package info.eric.nobaking;

import android.os.StrictMode;
import androidx.annotation.VisibleForTesting;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import info.eric.nobaking.dagger.ApplicationComponent;
import info.eric.nobaking.dagger.DaggerApplicationComponent;
import info.eric.nobaking.timber.ThreadNameTree;
import timber.log.Timber;

public class BakingApplication extends DaggerApplication {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    applicationComponent = buildApplicationComponent();
    super.onCreate();
    Timber.plant(new ThreadNameTree());
    Timber.d("timber trees planted, count: %s", Timber.treeCount());

    StrictMode.setThreadPolicy(
        new StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .penaltyFlashScreen()
            .build());
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
  }

  @VisibleForTesting
  protected ApplicationComponent buildApplicationComponent() {
    return DaggerApplicationComponent.builder().application(this).build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return applicationComponent;
  }
}
