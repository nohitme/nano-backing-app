package info.eric.nobaking.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Ingredient {

  public abstract float quantity();

  public abstract String measure();

  public abstract String ingredient();

  public static JsonAdapter<Ingredient> jsonAdapter(Moshi moshi) {
    return new AutoValue_Ingredient.MoshiJsonAdapter(moshi);
  }
}
