package info.eric.nobaking.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;

@AutoValue
public abstract class RecipeResponse {

  public abstract List<Recipe> recipes();

  public static JsonAdapter<RecipeResponse> jsonAdapter(Moshi moshi) {
    return new AutoValue_RecipeResponse.MoshiJsonAdapter(moshi);
  }
}
