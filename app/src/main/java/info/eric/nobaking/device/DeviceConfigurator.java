package info.eric.nobaking.device;

import android.app.Application;
import info.eric.nobaking.R;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeviceConfigurator {

  private Application application;

  @Inject
  public DeviceConfigurator(Application application) {
    this.application = application;
  }

  public boolean isDualPane() {
    return application.getResources().getBoolean(R.bool.use_dual_pane_mode);
  }
}
