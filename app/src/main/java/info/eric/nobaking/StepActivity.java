package info.eric.nobaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import info.eric.nobaking.device.DeviceConfigurator;
import info.eric.nobaking.model.Step;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class StepActivity extends DaggerAppCompatActivity {

  private static final String EXTRA_STEP = "EXTRA_STEP";
  private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

  @Inject DeviceConfigurator deviceConfigurator;

  @BindView(R.id.activity_toolbar)
  @Nullable
  Toolbar toolbar;

  public static Intent newIntent(@NonNull Context context, @NonNull Step step) {
    Intent intent = new Intent(context, StepActivity.class);
    intent.putExtra(EXTRA_STEP, step);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Step step = getIntent().getParcelableExtra(EXTRA_STEP);
    checkNotNull(step);

    // we can reuse the layout
    setContentView(R.layout.activity_step);
    ButterKnife.bind(this);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, StepFragment.newInstance(step), FRAGMENT_TAG)
          .commit();
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
