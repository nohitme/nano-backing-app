package info.eric.nobaking;

import android.content.Context;
import android.content.Intent;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import info.eric.nobaking.model.Recipe;
import info.eric.nobaking.model.Step;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static info.eric.nobaking.RecyclerViewMatcher.withRecyclerView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StepActivityTest extends DeviceDependentTest {

  // step activity is only available for phone layouts
  @Test
  @ForPhone
  public void stepActivityTest() throws IOException {
    launchTestStepActivity();

    // verify content
    onView(withRecyclerView(R.id.step_recycler_view).atPosition(0))
        .check(matches(hasDescendant(withId(R.id.step_player_view))));

    onView(withRecyclerView(R.id.step_recycler_view).atPosition(1))
        .check(matches(hasDescendant(withText("Recipe Introduction"))));
  }

  private void launchTestStepActivity() throws IOException {
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    final List<Recipe> recipes = FakeRecipeService.readStaticRecipes();
    final Step step = recipes.get(1).steps().get(0);
    final Intent intent = StepActivity.newIntent(context, step);
    launch(intent);
  }
}
