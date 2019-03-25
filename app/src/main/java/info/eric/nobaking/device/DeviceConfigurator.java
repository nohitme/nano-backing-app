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

  /**
   * Returns true if the device should be considered as a "tablet". Although this methodology is
   * proven not the best way to differentiate devices (does not work for 6" phones), this is called
   * out in the udacity UI mocks.
   */
  public boolean isTablet() {
    return application.getResources().getBoolean(R.bool.is_sw600dp);
  }
}
