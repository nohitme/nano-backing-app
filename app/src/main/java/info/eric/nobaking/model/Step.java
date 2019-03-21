package info.eric.nobaking.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Step implements Parcelable {

  public abstract int id();

  public abstract String shortDescription();

  public abstract String description();

  public abstract String videoURL();

  public abstract String thumbnailURL();

  public static JsonAdapter<Step> jsonAdapter(Moshi moshi) {
    return new AutoValue_Step.MoshiJsonAdapter(moshi);
  }
}
