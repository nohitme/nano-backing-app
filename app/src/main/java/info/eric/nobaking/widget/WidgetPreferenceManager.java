package info.eric.nobaking.widget;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WidgetPreferenceManager {

  private static final String PREFS_NAME = "PREFS_NAME";
  private static final String PREFIX_RECIPE_NAME = "PREFIX_RECIPE_NAME";
  private static final String PREFIX_INGREDIENTS = "PREFIX_INGREDIENTS";

  private final SharedPreferences preferences;

  @Inject
  public WidgetPreferenceManager(Application application) {
    preferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
  }

  public void saveIngredients(int appWidgetId, @NonNull WidgetPreference preference) {
    preferences.edit()
        .putString(PREFIX_RECIPE_NAME + appWidgetId, preference.recipeName())
        .putString(PREFIX_INGREDIENTS + appWidgetId, preference.ingredientText())
        .apply();
  }

  public void deleteIngredients(int appWidgetId) {
    preferences.edit()
        .remove(PREFIX_RECIPE_NAME + appWidgetId)
        .remove(PREFIX_INGREDIENTS + appWidgetId)
        .apply();
  }

  @Nullable
  public WidgetPreference getIngredients(int appWidgetId) {
    final String recipeName = preferences.getString(PREFIX_RECIPE_NAME + appWidgetId, null);
    final String ingredients = preferences.getString(PREFIX_INGREDIENTS + appWidgetId, null);

    if (recipeName == null || ingredients == null) {
      return null;
    }

    return WidgetPreference.create(recipeName, ingredients);
  }
}
