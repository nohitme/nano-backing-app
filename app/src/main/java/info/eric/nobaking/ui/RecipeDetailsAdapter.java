package info.eric.nobaking.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.google.common.base.Objects;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.recyclerview.AbstractListAdapter;
import info.eric.nobaking.recyclerview.AbstractViewHolder;

public class RecipeDetailsAdapter extends AbstractListAdapter<Object, AbstractViewHolder<Object>> {

  private static final int VIEW_TYPE_INGREDIENTS = 0;
  private static final int VIEW_TYPE_STEP = 1;
  private RecipeDetailsCallback callback;

  public interface RecipeDetailsCallback extends StepShortViewHolder.OnClickStepListener {

  }

  public RecipeDetailsAdapter(RecipeDetailsCallback callback) {
    super(ITEM_CALLBACK);
    this.callback = callback;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.item_generic_text, parent, false);
    if (viewType == VIEW_TYPE_INGREDIENTS) {
      return new IngredientsViewHolder(itemView);
    } else {
      return new StepShortViewHolder(itemView, callback);
    }
  }

  @Override public int getItemViewType(int position) {
    Object item = getItem(position);
    return item instanceof Step ? VIEW_TYPE_STEP : VIEW_TYPE_INGREDIENTS;
  }

  private static final DiffUtil.ItemCallback<Object> ITEM_CALLBACK =
      new DiffUtil.ItemCallback<Object>() {
        @Override public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
          return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
          return Objects.equal(oldItem, newItem);
        }
      };
}
