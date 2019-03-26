package info.eric.nobaking;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import java.lang.reflect.Method;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * Use {@link Assume} for different device configuration (phone or tablet)
 *
 * <p>reference: https://medium.com/@aitorvs/espresso-do-not-assume-just-annotate-9066cb77106e
 */
public abstract class DeviceDependentTest {

  @Rule public TestName testName = new TestName();

  @Before
  public final void setUpAssume() {
    assertDeviceOrSkip();
  }

  private void assertDeviceOrSkip() {
    final Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    final boolean isTablet = targetContext.getResources().getBoolean(R.bool.is_sw600dp);

    try {
      Method m = getClass().getMethod(testName.getMethodName());
      if (m.isAnnotationPresent(ForTablet.class)) {
        Assume.assumeTrue("device should be a tablet (sw600dp = true)", isTablet);
      } else if (m.isAnnotationPresent(ForPhone.class)) {
        Assume.assumeFalse("device should be a phone (sw600dp = false)", isTablet);
      }
    } catch (NoSuchMethodException e) {
      /* no-op */
    }
  }
}
