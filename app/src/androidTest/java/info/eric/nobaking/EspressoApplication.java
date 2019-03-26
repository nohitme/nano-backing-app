package info.eric.nobaking;

import info.eric.nobaking.dagger.ApplicationComponent;

public class EspressoApplication extends BakingApplication {

  @Override
  protected ApplicationComponent buildApplicationComponent() {
    return DaggerEspressoApplicationComponent.builder()
        .application(this)
        .recipeService(new FakeRecipeService())
        .build();
  }

  @Override
  public EspressoApplicationComponent getApplicationComponent() {
    return (EspressoApplicationComponent) super.getApplicationComponent();
  }
}
