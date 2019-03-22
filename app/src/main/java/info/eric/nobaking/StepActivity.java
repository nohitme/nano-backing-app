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
import info.eric.nobaking.model.Step;
import info.eric.nobaking.model.VideoUrl;
import info.eric.nobaking.ui.StepAdapter;
import info.eric.nobaking.ui.VideoViewHolderFactory;
import java.util.List;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class StepActivity extends DaggerAppCompatActivity {

  private static final String EXTRA_STEP = "EXTRA_STEP";

  @Inject DeviceConfigurator deviceConfigurator;
  @Inject VideoViewHolderFactory videoViewHolderFactory;

  @BindView(R.id.main_recycler_view) RecyclerView recyclerView;
  @BindView(R.id.main_toolbar) Toolbar toolbar;

  private StepAdapter stepAdapter;
  private Step step;

  public static Intent newIntent(@NonNull Context context, @NonNull Step step) {
    Intent intent = new Intent(context, StepActivity.class);
    intent.putExtra(EXTRA_STEP, step);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    step = getIntent().getParcelableExtra(EXTRA_STEP);
    checkNotNull(step);

    // we can reuse the layout
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    stepAdapter = new StepAdapter(this, videoViewHolderFactory);
    recyclerView.setAdapter(stepAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    stepAdapter.submitList(generateDataList());

    toolbar.setContentInsetStartWithNavigation(0);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private List<Object> generateDataList() {
    List<Object> list = Lists.newArrayList();
    if (!step.videoURL().isEmpty()) {
      VideoUrl videoUrl = VideoUrl.create(step.videoURL());
      list.add(videoUrl);
    }

    list.add(step);
    return list;
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
