package info.eric.nobaking.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import info.eric.nobaking.R;
import info.eric.nobaking.model.Ingredient;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.recyclerview.AbstractViewHolder;

public class RecipeViewHolder extends AbstractViewHolder<Recipe> {

  public interface OnRecipeClickListener {

    void onRecipeClicked(Recipe recipe);
  }

  private static final Joiner COMMA_JOINER = Joiner.on(", ");
  private final OnRecipeClickListener onRecipeClickListener;

  @BindView(R.id.recipe_name) TextView nameText;
  @BindView(R.id.recipe_servings) TextView servingText;
  @BindView(R.id.generic_text) TextView ingredientText;

  public RecipeViewHolder(
      @NonNull View itemView,
      @NonNull OnRecipeClickListener onRecipeClickListener) {
    super(itemView);
    this.onRecipeClickListener = onRecipeClickListener;
  }

  @Override public void bind(Recipe item) {
    Context context = itemView.getContext();
    nameText.setText(item.name());
    servingText.setText(context.getString(R.string.recipe_servings, item.servings()));

    String ingredients = FluentIterable.from(item.ingredients())
        .transform(Ingredient::ingredient)
        .join(COMMA_JOINER);
    ingredientText.setText(context.getString(R.string.recipe_ingredients, ingredients));

    itemView.setOnClickListener(v -> onRecipeClickListener.onRecipeClicked(item));
  }

  @Override public void unbind() {
    // do nothing
  }
}
