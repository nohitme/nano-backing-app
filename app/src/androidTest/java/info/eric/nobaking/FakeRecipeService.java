package info.eric.nobaking;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.RecipeService;
import info.eric.nobaking.model.RecipeServiceAdapterFactory;
import io.reactivex.Single;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import okio.BufferedSource;
import okio.Okio;

/**
 * Provides a fake implementation of {@link RecipeService} that always returns the same response
 * from a json file
 */
public class FakeRecipeService implements RecipeService {

  private static final Moshi moshi =
      new Moshi.Builder().add(RecipeServiceAdapterFactory.create()).build();

  @Override public Single<List<Recipe>> recipes() {
    return Single.fromCallable(FakeRecipeService::readStaticRecipes);
  }

  static List<Recipe> readStaticRecipes() throws IOException {
    Type type = Types.newParameterizedType(List.class, Recipe.class);
    JsonAdapter<List<Recipe>> adapter = moshi.adapter(type);

    final Context context = InstrumentationRegistry.getInstrumentation().getContext();
    final InputStream inputStream = context.getAssets().open("baking.json");
    BufferedSource source = Okio.buffer(Okio.source(inputStream));
    return adapter.fromJson(source);
  }
}
