package info.eric.nobaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.common.collect.Lists;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.device.DeviceConfigurator;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.ui.RecipeDetailsAdapter;
import java.util.List;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecipeActivity extends DaggerAppCompatActivity
    implements RecipeDetailsAdapter.RecipeDetailsCallback {

  private static final String EXTRA_RECIPE = "EXTRA_RECIPE";

  @Inject DeviceConfigurator deviceConfigurator;

  @BindView(R.id.main_recycler_view) RecyclerView recyclerView;
  @BindView(R.id.main_toolbar) Toolbar toolbar;

  private RecipeDetailsAdapter detailsAdapter;
  private Recipe recipe;

  public static Intent newIntent(@NonNull Context context, @NonNull Recipe recipe) {
    Intent intent = new Intent(context, RecipeActivity.class);
    intent.putExtra(EXTRA_RECIPE, recipe);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
    checkNotNull(recipe);

    // we can reuse the layout
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    detailsAdapter = new RecipeDetailsAdapter(this);
    recyclerView.setAdapter(detailsAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    setUpAdapter();

    toolbar.setTitle(recipe.name());
    toolbar.setContentInsetStartWithNavigation(0);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void setUpAdapter() {
    List<Object> list = Lists.newArrayList();
    list.add(recipe.ingredients());
    list.addAll(recipe.steps());
    detailsAdapter.submitList(list);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onStepClicked(Step step) {

  }
}
