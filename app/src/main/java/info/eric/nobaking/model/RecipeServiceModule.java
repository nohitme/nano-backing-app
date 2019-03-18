package info.eric.nobaking.model;

import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import info.eric.nobaking.util.ExecutorProvider;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class RecipeServiceModule {

  private static final String RECIPE_BASE_URL = "http://go.udacity.com";

  @Provides
  @Singleton
  RecipeService recipeService(
      OkHttpClient okHttpClient,
      ExecutorProvider executorProvider) {
    Moshi moshi = new Moshi.Builder().add(RecipeServiceAdapterFactory.create()).build();

    Retrofit retrofit =
        new Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(executorProvider.ioScheduler()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(getRecipeBaseUrl())
            .build();

    return retrofit.create(RecipeService.class);
  }

  protected String getRecipeBaseUrl() {
    return RECIPE_BASE_URL;
  }
}
