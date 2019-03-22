package info.eric.nobaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.device.DeviceConfigurator;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.ui.RecipeDetailsAdapter;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecipeActivity extends DaggerAppCompatActivity
    implements RecipeDetailsAdapter.RecipeDetailsCallback {

  private static final String EXTRA_RECIPE = "EXTRA_RECIPE";
  private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

  @Inject DeviceConfigurator deviceConfigurator;

  public static Intent newIntent(@NonNull Context context, @NonNull Recipe recipe) {
    Intent intent = new Intent(context, RecipeActivity.class);
    intent.putExtra(EXTRA_RECIPE, recipe);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
    checkNotNull(recipe);

    setContentView(R.layout.activity_recipe);
    ButterKnife.bind(this);

    if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.recipe_fragment_container, RecipeFragment.newInstance(recipe), FRAGMENT_TAG)
          .commit();
    }
  }

  @Override public void onStepClicked(Step step) {
    if (deviceConfigurator.isDualPane()) {

    } else {
      Intent intent = StepActivity.newIntent(this, step);
      startActivity(intent);
    }
  }
}
