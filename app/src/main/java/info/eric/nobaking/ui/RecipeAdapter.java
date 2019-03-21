package info.eric.nobaking.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.google.common.base.Objects;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.recyclerview.AbstractListAdapter;

public class RecipeAdapter extends AbstractListAdapter<Recipe, RecipeViewHolder> {

  private RecipeAdapterCallback callback;

  public interface RecipeAdapterCallback extends RecipeViewHolder.OnRecipeClickListener {

  }

  public RecipeAdapter(RecipeAdapterCallback callback) {
    super(recipeCallback);
    this.callback = callback;
  }

  @NonNull @Override
  public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    final View itemView = inflater.inflate(R.layout.item_recipe, parent, false);
    return new RecipeViewHolder(itemView, callback);
  }

  private static final DiffUtil.ItemCallback<Recipe> recipeCallback =
      new DiffUtil.ItemCallback<Recipe>() {
        @Override public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
          return oldItem.id() == newItem.id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
          return Objects.equal(oldItem, newItem);
        }
      };
}
