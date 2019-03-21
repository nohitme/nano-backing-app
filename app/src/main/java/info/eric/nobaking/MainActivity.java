package info.eric.nobaking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.device.DeviceConfigurator;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.RecipeService;
import info.eric.nobaking.ui.RecipeAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import javax.annotation.Nullable;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity
    implements RecipeAdapter.RecipeAdapterCallback {

  @Inject RecipeService recipeService;
  @Inject DeviceConfigurator deviceConfigurator;

  @BindView(R.id.main_recycler_view) RecyclerView recyclerView;

  @BindView(R.id.main_toolbar) Toolbar toolbar;

  private RecipeAdapter recipeAdapter;

  @Nullable
  private Disposable disposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    recipeAdapter = new RecipeAdapter(this);
    recipeAdapter.setHasStableIds(true);
    recyclerView.setAdapter(recipeAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    setSupportActionBar(toolbar);
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

  @Override public void onRecipeClicked(Recipe recipe) {
    if (deviceConfigurator.isDualPane()) {
    } else {
      Intent intent = RecipeActivity.newIntent(this, recipe);
      startActivity(intent);
    }
  }
}
