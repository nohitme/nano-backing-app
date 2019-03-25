package info.eric.nobaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.collect.Iterables;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.ui.RecipeDetailsAdapter;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecipeActivity extends DaggerAppCompatActivity
    implements RecipeDetailsAdapter.RecipeDetailsCallback {

  private static final String EXTRA_RECIPE = "EXTRA_RECIPE";
  private static final String FRAGMENT_TAG_RECIPE = "FRAGMENT_TAG_RECIPE";
  private static final String FRAGMENT_TAG_STEP = "FRAGMENT_TAG_STEP";

  @BindView(R.id.activity_toolbar) Toolbar toolbar;

  @Nullable
  @BindView(R.id.fragment_container_step)
  FrameLayout stepContainer;

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
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_RECIPE) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container_recipe, RecipeFragment.newInstance(recipe),
              FRAGMENT_TAG_RECIPE)
          .commit();
    }

    if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_STEP) == null
        && stepContainer != null) {
      // use first step as the default
      final Step step = Iterables.getFirst(recipe.steps(), null);
      if (step != null) {
        setUpStepFragment(step);
      }
    }
  }

  private void setUpStepFragment(@NonNull Step step) {
    final StepFragment fragment = StepFragment.newInstance(step);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container_step, fragment, FRAGMENT_TAG_STEP)
        .commit();
  }

  @Override public void onStepClicked(Step step) {
    if (stepContainer != null) {
      setUpStepFragment(step);
    } else {
      Intent intent = StepActivity.newIntent(this, step);
      startActivity(intent);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
