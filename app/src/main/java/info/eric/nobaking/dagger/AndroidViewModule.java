package info.eric.nobaking.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.eric.nobaking.MainActivity;
import info.eric.nobaking.RecipeActivity;
import info.eric.nobaking.RecipeFragment;
import info.eric.nobaking.StepActivity;
import info.eric.nobaking.StepFragment;
import info.eric.nobaking.widget.IngredientWidgetConfigureActivity;

@Module
abstract class AndroidViewModule {

  @ContributesAndroidInjector
  abstract MainActivity mainActivity();

  @ContributesAndroidInjector
  abstract RecipeActivity recipeActivity();

  @ContributesAndroidInjector
  abstract RecipeFragment recipeFragment();

  @ContributesAndroidInjector
  abstract StepActivity stepActivity();

  @ContributesAndroidInjector
  abstract StepFragment stepFragment();

  @ContributesAndroidInjector
  abstract IngredientWidgetConfigureActivity ingredientWidgetConfigureActivity();
}
