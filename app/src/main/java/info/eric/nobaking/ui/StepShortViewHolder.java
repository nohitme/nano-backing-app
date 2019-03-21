package info.eric.nobaking.ui;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.recyclerview.AbstractViewHolder;

public class StepShortViewHolder extends AbstractViewHolder<Step> {

  private final OnClickStepListener onClickStepListener;

  public interface OnClickStepListener {

    void onStepClicked(Step step);
  }

  @BindView(R.id.generic_text) TextView genericText;

  public StepShortViewHolder(@NonNull View itemView, OnClickStepListener onClickStepListener) {
    super(itemView);
    this.onClickStepListener = onClickStepListener;
  }

  @Override public void bind(Step item) {
    genericText.setText(item.shortDescription());
    itemView.setOnClickListener(v -> onClickStepListener.onStepClicked(item));
  }

  @Override public void unbind() {

  }
}
