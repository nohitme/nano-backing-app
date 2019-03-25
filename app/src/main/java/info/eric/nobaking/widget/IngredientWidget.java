package info.eric.nobaking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import info.eric.nobaking.BakingApplication;
import info.eric.nobaking.R;
import javax.inject.Inject;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link IngredientWidgetConfigureActivity
 * IngredientWidgetConfigureActivity}
 */
public class IngredientWidget extends AppWidgetProvider {

  @Inject
  WidgetPreferenceManager widgetPreferenceManager;

  static void updateAppWidget(
      Context context,
      int appWidgetId,
      @Nullable WidgetPreference preference) {

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
    final String recipeName = preference != null ? preference.recipeName() : null;
    final String ingredientText = preference != null ? preference.ingredientText() : null;

    views.setTextViewText(R.id.widget_recipe_name, recipeName);
    views.setTextViewText(R.id.widget_ingredients, ingredientText);

    // Instruct the widget manager to update the widget
    AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    inject(context);
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      final WidgetPreference preference = widgetPreferenceManager.getIngredients(appWidgetId);
      updateAppWidget(context, appWidgetId, preference);
    }
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    inject(context);
    // When the user deletes the widget, delete the preference associated with it.
    for (int appWidgetId : appWidgetIds) {
      widgetPreferenceManager.deleteIngredients(appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }

  private void inject(@NonNull Context context) {
    final BakingApplication application = (BakingApplication) context.getApplicationContext();
    application.getApplicationComponent().inject(this);
  }
}

