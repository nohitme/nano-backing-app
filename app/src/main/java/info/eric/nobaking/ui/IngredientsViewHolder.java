package info.eric.nobaking.ui;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Ingredient;
import info.eric.nobaking.recyclerview.AbstractViewHolder;
import java.util.List;
import java.util.Locale;

public class IngredientsViewHolder extends AbstractViewHolder<List<Ingredient>> {

  private static final Joiner LINE_BREAK_JOINER = Joiner.on('\n');

  @BindView(R.id.generic_text) TextView genericText;

  public IngredientsViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(List<Ingredient> item) {
    String ingredient =
        FluentIterable.from(item).transform(this::toIngredientString).join(LINE_BREAK_JOINER);
    genericText.setText(ingredient);
  }

  @Override public void unbind() {
    // do nothing
  }

  private String toIngredientString(@NonNull Ingredient ingredient) {
    return String.format(Locale.US, "%s (%.1f %s)",
        ingredient.ingredient(), ingredient.quantity(), ingredient.measure());
  }
}
