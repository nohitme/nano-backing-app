package info.eric.nobaking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.common.collect.Lists;
import dagger.android.support.DaggerFragment;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.RecipeService;
import info.eric.nobaking.ui.RecipeDetailsAdapter;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecipeFragment extends DaggerFragment {

  private static final String ARGS_RECIPE = "ARGS_RECIPE";

  @Inject RecipeService recipeService;

  @BindView(R.id.main_recycler_view) RecyclerView recyclerView;

  @Nullable
  private RecipeDetailsAdapter.RecipeDetailsCallback recipeDetailsCallback;

  @Nullable
  private Unbinder unbinder;

  private Recipe recipe;

  public static RecipeFragment newInstance(@NonNull Recipe recipe) {
    RecipeFragment fragment = new RecipeFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARGS_RECIPE, recipe);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    recipeDetailsCallback = (RecipeDetailsAdapter.RecipeDetailsCallback) context;
  }

  @Override public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    checkNotNull(getArguments());
    recipe = getArguments().getParcelable(ARGS_RECIPE);
    checkNotNull(recipe);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_single_recyclerview, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view,
      @androidx.annotation.Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RecipeDetailsAdapter detailsAdapter = new RecipeDetailsAdapter(recipeDetailsCallback);
    detailsAdapter.setHasStableIds(true);
    recyclerView.setAdapter(detailsAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    detailsAdapter.submitList(generateDataList());
  }

  private List<Object> generateDataList() {
    List<Object> list = Lists.newArrayList();
    list.add(recipe.ingredients());
    list.addAll(recipe.steps());
    return list;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    recipeDetailsCallback = null;
  }
}
