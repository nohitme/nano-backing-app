package info.eric.nobaking.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VideoUrl {

  public abstract String url();

  public abstract long position();

  public static VideoUrl create(String url, long position) {
    return new AutoValue_VideoUrl(url, position);
  }
}
