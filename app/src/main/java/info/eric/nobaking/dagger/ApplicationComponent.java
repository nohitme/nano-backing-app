package info.eric.nobaking.dagger;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import info.eric.nobaking.BakingApplication;
import info.eric.nobaking.model.RecipeServiceModule;
import info.eric.nobaking.util.ResourceModule;
import info.eric.nobaking.widget.IngredientWidget;
import javax.inject.Singleton;

@Singleton
@Component(
    modules = {
        AndroidSupportInjectionModule.class,
        AndroidViewModule.class,
        ApplicationModule.class,
        ExoPlayerModule.class,
        OkHttpModule.class,
        RecipeServiceModule.class,
        ResourceModule.class,
    })
public interface ApplicationComponent extends AndroidInjector<BakingApplication> {

  void inject(IngredientWidget ingredientWidget);

  @Component.Builder
  interface Builder {

    @BindsInstance
    ApplicationComponent.Builder application(Application application);

    ApplicationComponent build();
  }
}
