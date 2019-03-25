package info.eric.nobaking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.base.Objects;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.RecipeService;
import info.eric.nobaking.ui.IngredientUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * The configuration screen for the {@link IngredientWidget IngredientWidget} AppWidget.
 */
public class IngredientWidgetConfigureActivity extends DaggerAppCompatActivity {

  private static final String PREFS_NAME = "info.eric.nobaking.widget.IngredientWidget";
  private static final String PREF_PREFIX_KEY = "appwidget_";

  private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

  @Inject RecipeService recipeService;
  @Inject WidgetPreferenceManager widgetPreferenceManager;

  @BindView(R.id.main_recycler_view)
  RecyclerView recyclerView;

  @BindView(R.id.main_toolbar)
  Toolbar toolbar;

  private RecipeAdapter recipeAdapter;

  @Nullable
  private Disposable disposable;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    // Set the result to CANCELED.  This will cause the widget host to cancel
    // out of the widget placement if the user presses the back button.
    setResult(RESULT_CANCELED);

    // Find the widget id from the intent.
    Intent intent = getIntent();
    appWidgetId = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID);

    // If this activity was started with an intent without an app widget ID, finish with an error.
    if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
      finish();
      return;
    }

    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recipeAdapter = new RecipeAdapter();
    recyclerView.setAdapter(recipeAdapter);
  }

  @Override
  protected void onStart() {
    super.onStart();
    disposable = recipeService.recipes()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((recipeAdapter::submitList), throwable -> {
          Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG).show();
          Timber.i(throwable);
        });
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (disposable != null) {
      disposable.dispose();
    }
  }

  private void onSelectRecipe(@NonNull Recipe recipe) {
    final String ingredients = IngredientUtils.asListString(recipe.ingredients());
    final WidgetPreference preference = WidgetPreference.create(recipe.name(), ingredients);
    widgetPreferenceManager.saveIngredients(appWidgetId, preference);

    // It is the responsibility of the configuration activity to update the app widget
    IngredientWidget.updateAppWidget(this, appWidgetId, preference);

    // Make sure we pass back the original appWidgetId
    Intent resultValue = new Intent();
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
    setResult(RESULT_OK, resultValue);
    finish();
  }

  static class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.widget_text) TextView textView;

    RecipeViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  private class RecipeAdapter extends ListAdapter<Recipe, RecipeViewHolder> {

    RecipeAdapter() {
      super(ITEM_CALLBACK);
    }

    @NonNull @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      final View itemView = inflater.inflate(R.layout.item_widget_configure, parent, false);
      return new RecipeViewHolder(itemView);
    }

    @Override public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
      final Recipe recipe = getItem(position);
      holder.textView.setText(recipe.name());
      holder.itemView.setOnClickListener(v -> onSelectRecipe(recipe));
    }
  }

  private static final DiffUtil.ItemCallback<Recipe> ITEM_CALLBACK =
      new DiffUtil.ItemCallback<Recipe>() {
        @Override public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
          return oldItem.id() == newItem.id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
          return Objects.equal(oldItem, newItem);
        }
      };
}

