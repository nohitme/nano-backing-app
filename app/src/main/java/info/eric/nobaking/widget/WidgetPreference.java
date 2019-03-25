package info.eric.nobaking.widget;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class WidgetPreference {

  public abstract String recipeName();

  public abstract String ingredientText();

  public static WidgetPreference create(String recipeName, String ingredientText) {
    return new AutoValue_WidgetPreference(recipeName, ingredientText);
  }
}
