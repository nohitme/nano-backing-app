package info.eric.nobaking.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VideoUrl {

  public abstract String url();

  public static VideoUrl create(String url) {
    return new AutoValue_VideoUrl(url);
  }
}
