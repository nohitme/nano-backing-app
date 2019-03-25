package info.eric.nobaking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.common.collect.Lists;
import dagger.android.support.DaggerFragment;
import info.eric.nobaking.model.RecipeService;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.model.VideoUrl;
import info.eric.nobaking.ui.StepAdapter;
import info.eric.nobaking.ui.VideoViewHolderFactory;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class StepFragment extends DaggerFragment {

  private static final String ARGS_STEP = "ARGS_STEP";

  @Inject RecipeService recipeService;
  @Inject VideoViewHolderFactory videoViewHolderFactory;

  @BindView(R.id.main_recycler_view) RecyclerView recyclerView;
  @BindView(R.id.main_toolbar) Toolbar toolbar;

  @Nullable
  private Unbinder unbinder;

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
    View view = inflater.inflate(R.layout.fragment_recipe, container, false);
    unbinder = ButterKnife.bind(this, view);
    toolbar.setContentInsetStartWithNavigation(0);
    toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    return view;
  }

  @Override public void onViewCreated(@NonNull View view,
      @androidx.annotation.Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    final StepAdapter stepAdapter = new StepAdapter(this, videoViewHolderFactory);
    recyclerView.setAdapter(stepAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    stepAdapter.submitList(generateDataList());
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
    if (unbinder != null) {
      unbinder.unbind();
    }
  }
}
