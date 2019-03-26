package info.eric.nobaking;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import info.eric.nobaking.dagger.AndroidViewModule;
import info.eric.nobaking.dagger.ApplicationComponent;
import info.eric.nobaking.dagger.ApplicationModule;
import info.eric.nobaking.dagger.ExoPlayerModule;
import info.eric.nobaking.dagger.OkHttpModule;
import info.eric.nobaking.model.RecipeService;
import info.eric.nobaking.util.ResourceModule;
import javax.inject.Singleton;

@Singleton
@Component(
    modules = {
        AndroidSupportInjectionModule.class,
        AndroidViewModule.class,
        ApplicationModule.class,
        ExoPlayerModule.class,
        OkHttpModule.class,
        ResourceModule.class,
    })
public interface EspressoApplicationComponent extends ApplicationComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    EspressoApplicationComponent.Builder application(Application application);

    @BindsInstance
    EspressoApplicationComponent.Builder recipeService(RecipeService recipeService);

    EspressoApplicationComponent build();
  }
}
