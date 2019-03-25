package info.eric.nobaking.ui;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Ingredient;
import info.eric.nobaking.recyclerview.AbstractViewHolder;
import java.util.List;

public class IngredientsViewHolder extends AbstractViewHolder<List<Ingredient>> {

  @BindView(R.id.generic_text) TextView genericText;

  public IngredientsViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(List<Ingredient> item) {
    genericText.setText(IngredientUtils.asListString(item));
  }

  @Override public void unbind() {
    // do nothing
  }
}
