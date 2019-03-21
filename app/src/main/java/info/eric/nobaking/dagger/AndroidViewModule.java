package info.eric.nobaking.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.eric.nobaking.MainActivity;
import info.eric.nobaking.RecipeActivity;

@Module
abstract class AndroidViewModule {

  @ContributesAndroidInjector
  abstract MainActivity mainActivity();

  @ContributesAndroidInjector
  abstract RecipeActivity recipeActivity();
}
