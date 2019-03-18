package info.eric.nobaking;

import android.os.Bundle;
import android.widget.Toast;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.model.RecipeService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity {

  @Inject RecipeService recipeService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Disposable disposable = recipeService.recipes()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((recipes -> {
          Toast.makeText(this, String.format("list size: %s", recipes.size()),
              Toast.LENGTH_LONG).show();
        }), throwable -> {
          Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG).show();
          Timber.i(throwable);
        });
  }
}
