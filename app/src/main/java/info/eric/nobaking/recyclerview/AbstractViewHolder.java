package info.eric.nobaking.recyclerview;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public abstract class AbstractViewHolder<T> extends RecyclerView.ViewHolder {

  public AbstractViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public abstract void bind(T item);

  public abstract void unbind();
}
