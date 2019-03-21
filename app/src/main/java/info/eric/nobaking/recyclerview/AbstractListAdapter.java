package info.eric.nobaking.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public abstract class AbstractListAdapter<T, VH extends AbstractViewHolder<T>>
    extends ListAdapter<T, VH> {

  protected AbstractListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
    super(diffCallback);
  }

  @Override public void onBindViewHolder(@NonNull VH holder, int position) {
    holder.bind(getItem(position));
  }

  @Override public void onViewRecycled(@NonNull VH holder) {
    holder.unbind();
    super.onViewRecycled(holder);
  }
}
