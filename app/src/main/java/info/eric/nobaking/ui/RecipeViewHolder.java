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

  private static Joiner commaJoiner = Joiner.on(", ");

  @BindView(R.id.recipe_name) TextView nameText;
  @BindView(R.id.recipe_servings) TextView servingText;
  @BindView(R.id.recipe_ingredients) TextView ingredientText;

  public RecipeViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(Recipe item) {
    Context context = itemView.getContext();
    nameText.setText(item.name());
    servingText.setText(context.getString(R.string.recipe_servings, item.servings()));

    String ingredients = FluentIterable.from(item.ingredients())
        .transform(Ingredient::ingredient)
        .join(commaJoiner);
    ingredientText.setText(context.getString(R.string.recipe_ingredients, ingredients));
  }

  @Override public void unbind() {
    // do nothing
  }
}
