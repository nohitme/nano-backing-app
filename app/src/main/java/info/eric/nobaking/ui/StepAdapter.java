package info.eric.nobaking.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;
import com.google.common.base.Objects;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.recyclerview.AbstractListAdapter;
import info.eric.nobaking.recyclerview.AbstractViewHolder;

public class StepAdapter extends AbstractListAdapter<Object, AbstractViewHolder<Object>> {

  private static final int VIEW_TYPE_VIDEO = 0;
  private static final int VIEW_TYPE_STEP = 1;

  private final LifecycleOwner lifecycleOwner;
  private final VideoViewHolderFactory videoViewHolderFactory;

  public StepAdapter(LifecycleOwner lifecycleOwner, VideoViewHolderFactory videoViewHolderFactory) {
    super(ITEM_CALLBACK);
    this.lifecycleOwner = lifecycleOwner;
    this.videoViewHolderFactory = videoViewHolderFactory;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    if (viewType == VIEW_TYPE_VIDEO) {
      View itemView = inflater.inflate(R.layout.item_video_player, parent, false);
      return videoViewHolderFactory.create(itemView, lifecycleOwner);
    } else {
      View itemView = inflater.inflate(R.layout.item_generic_text, parent, false);
      return new StepLongViewHolder(itemView);
    }
  }

  @Override public int getItemViewType(int position) {
    Object item = getItem(position);
    return item instanceof Step ? VIEW_TYPE_STEP : VIEW_TYPE_VIDEO;
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
