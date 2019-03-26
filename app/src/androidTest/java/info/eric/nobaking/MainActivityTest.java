package info.eric.nobaking;

import android.content.Intent;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static info.eric.nobaking.RecyclerViewMatcher.withRecyclerView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  @Test
  public void mainActivityTest() {
    launch(MainActivity.class);

    // verify content
    onView(withRecyclerView(R.id.main_recycler_view).atPosition(0))
        .check(matches(hasDescendant(withText("Nutella Pie"))));

    onView(withRecyclerView(R.id.main_recycler_view).atPosition(1))
        .check(matches(hasDescendant(withText("Brownies"))));


    // test if we launch the right intent
    Intents.init();

    onView(withId(R.id.main_recycler_view)).perform(actionOnItemAtPosition(1, click()));

    intended(hasComponent(RecipeActivity.class.getName()));
    Intents.release();
  }

}
