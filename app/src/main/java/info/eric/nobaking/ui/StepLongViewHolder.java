package info.eric.nobaking.ui;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Step;
import info.eric.nobaking.recyclerview.AbstractViewHolder;

public class StepLongViewHolder extends AbstractViewHolder<Step> {

  @BindView(R.id.generic_text) TextView genericText;

  public StepLongViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(Step item) {
    genericText.setText(item.description());
  }

  @Override public void unbind() {

  }
}
