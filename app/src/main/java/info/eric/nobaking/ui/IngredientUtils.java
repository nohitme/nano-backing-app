package info.eric.nobaking.ui;

import androidx.annotation.NonNull;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import info.eric.nobaking.model.Ingredient;
import java.util.List;
import java.util.Locale;

public class IngredientUtils {

  private static final Joiner LINE_BREAK_JOINER = Joiner.on('\n');

  private IngredientUtils() {
    //no instance
  }

  public static String asListString(@NonNull List<Ingredient> ingredients) {
    return FluentIterable.from(ingredients)
        .transform(IngredientUtils::toFormattedString)
        .join(LINE_BREAK_JOINER);
  }

  private static String toFormattedString(@NonNull Ingredient ingredient) {
    return String.format(Locale.US, "%s (%.1f %s)",
        ingredient.ingredient(), ingredient.quantity(), ingredient.measure());
  }
}
