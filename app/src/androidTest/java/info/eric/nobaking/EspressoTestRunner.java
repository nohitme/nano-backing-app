package info.eric.nobaking;

import android.app.Application;
import android.content.Context;
import androidx.test.runner.AndroidJUnitRunner;

public class EspressoTestRunner extends AndroidJUnitRunner {

  @Override
  public Application newApplication(ClassLoader cl, String className, Context context)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException {

    return super.newApplication(cl, EspressoApplication.class.getName(), context);
  }
}
