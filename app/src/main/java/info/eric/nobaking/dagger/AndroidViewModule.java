package info.eric.nobaking.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.eric.nobaking.MainActivity;
import info.eric.nobaking.RecipeActivity;
import info.eric.nobaking.StepActivity;

@Module
abstract class AndroidViewModule {

  @ContributesAndroidInjector
  abstract MainActivity mainActivity();

  @ContributesAndroidInjector
  abstract RecipeActivity recipeActivity();

  @ContributesAndroidInjector
  abstract StepActivity stepActivity();
}
