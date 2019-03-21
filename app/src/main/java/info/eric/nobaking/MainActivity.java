package info.eric.nobaking;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.model.RecipeService;
import info.eric.nobaking.ui.RecipeAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity {

  @Inject RecipeService recipeService;

  @BindView(R.id.main_recycler_view) RecyclerView recyclerView;

  @BindView(R.id.main_toolbar) Toolbar toolbar;

  private final RecipeAdapter recipeAdapter = new RecipeAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    recipeAdapter.setHasStableIds(true);
    recyclerView.setAdapter(recipeAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    setSupportActionBar(toolbar);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Disposable disposable = recipeService.recipes()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((recipeAdapter::submitList), throwable -> {
          Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG).show();
          Timber.i(throwable);
        });
  }
}
