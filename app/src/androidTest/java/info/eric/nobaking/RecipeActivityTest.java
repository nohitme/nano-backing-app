package info.eric.nobaking;

import android.content.Context;
import android.content.Intent;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import info.eric.nobaking.model.Recipe;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static info.eric.nobaking.RecyclerViewMatcher.withRecyclerView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest extends DeviceDependentTest {

  @Test
  @ForTablet
  public void recipeActivityTestForTablet() throws IOException {
    launchTestRecipeActivity();

    // verify content
    onView(withRecyclerView(R.id.recipe_recycler_view).atPosition(1))
        .check(matches(hasDescendant(withText("Recipe Introduction"))));

    onView(withRecyclerView(R.id.recipe_recycler_view).atPosition(2))
        .check(matches(hasDescendant(withText("Starting prep"))));

    // check if video view is present
    onView(withId(R.id.step_player_view)).check(matches(isDisplayed()));

    // verify click does not launch new intent
    Intents.init();
    onView(withId(R.id.recipe_recycler_view)).perform(actionOnItemAtPosition(1, click()));
    intended(anyIntent(), times(0));
    Intents.release();
  }

  @Test
  @ForPhone
  public void recipeActivityTestForPhone() throws IOException {
    launchTestRecipeActivity();

    // verify content
    onView(withRecyclerView(R.id.recipe_recycler_view).atPosition(1))
        .check(matches(hasDescendant(withText("Recipe Introduction"))));

    onView(withRecyclerView(R.id.recipe_recycler_view).atPosition(2))
        .check(matches(hasDescendant(withText("Starting prep"))));

    // check if video view is not present
    onView(withId(R.id.step_player_view)).check(doesNotExist());

    // verify click does launch step activity
    Intents.init();
    onView(withId(R.id.recipe_recycler_view)).perform(actionOnItemAtPosition(1, click()));
    intended(hasComponent(StepActivity.class.getName()));
    Intents.release();
  }

  private void launchTestRecipeActivity() throws IOException {
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    final List<Recipe> recipes = FakeRecipeService.readStaticRecipes();
    final Recipe recipe = recipes.get(1);
    final Intent intent = RecipeActivity.newIntent(context, recipe);
    launch(intent);
  }
}
