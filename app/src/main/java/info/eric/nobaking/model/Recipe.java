package info.eric.nobaking.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;

@AutoValue
public abstract class Recipe implements Parcelable {

  public abstract int id();

  public abstract int servings();

  public abstract String name();

  public abstract String image();

  public abstract List<Ingredient> ingredients();

  public abstract List<Step> steps();

  public static JsonAdapter<Recipe> jsonAdapter(Moshi moshi) {
    return new AutoValue_Recipe.MoshiJsonAdapter(moshi);
  }
}
