package info.eric.nobaking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.common.collect.Lists;
import dagger.android.support.DaggerFragment;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.model.VideoUrl;
import info.eric.nobaking.ui.PlayerViewPresenter;
import info.eric.nobaking.ui.PlayerViewPresenterFactory;
import info.eric.nobaking.ui.StepAdapter;
import info.eric.nobaking.ui.VideoViewHolderFactory;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class StepFragment extends DaggerFragment {

  private static final String ARGS_STEP = "ARGS_STEP";

  @Inject VideoViewHolderFactory videoViewHolderFactory;
  @Inject PlayerViewPresenterFactory playerViewPresenterFactory;

  @BindView(R.id.main_recycler_view)
  @Nullable
  RecyclerView recyclerView;

  @BindView(R.id.step_player_view)
  @Nullable PlayerView playerView;

  @Nullable
  private Unbinder unbinder;

  @Nullable
  private PlayerViewPresenter playerViewPresenter;

  private Step step;

  public static StepFragment newInstance(@NonNull Step step) {
    StepFragment fragment = new StepFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARGS_STEP, step);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    checkNotNull(getArguments());
    step = getArguments().getParcelable(ARGS_STEP);
    checkNotNull(step);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_step, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view,
      @androidx.annotation.Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // sanity check
    checkState(recyclerView != null || playerView != null);

    if (recyclerView != null) {
      setUpRecyclerView(recyclerView);
    } else {
      setUpPlayerView(playerView);
    }
  }

  private void setUpRecyclerView(@NonNull RecyclerView recyclerView) {
    final StepAdapter stepAdapter = new StepAdapter(this, videoViewHolderFactory);
    recyclerView.setAdapter(stepAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    stepAdapter.submitList(generateDataList());
  }

  private void setUpPlayerView(@NonNull PlayerView playerView) {
    if (step.videoURL().isEmpty()) {
      Toast.makeText(requireContext(), R.string.step_no_video_url, Toast.LENGTH_LONG).show();
      playerView.setVisibility(View.GONE);
      return;
    }

    playerView.setVisibility(View.VISIBLE);
    playerViewPresenter = playerViewPresenterFactory.create(playerView, this);
    playerViewPresenter.create(step.videoURL());
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

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (playerViewPresenter != null) {
      playerViewPresenter.destroy();
    }

    if (unbinder != null) {
      unbinder.unbind();
    }
  }
}
