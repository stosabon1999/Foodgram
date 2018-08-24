package ru.production.ssobolevsky.foodgram;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.production.ssobolevsky.foodgram.presentation.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        onView(withId(R.id.btn_edit_profile)).check(matches(isDisplayed()));
        onView(withId(R.id.profile_appbar)).check(matches(isDisplayed()));
        onView(withId(R.id.profile_ll_friends)).check(matches(isDisplayed()));
        //onView(withId(R.id.rv_profile_posts)).check(matches(isDisplayed()));
    }
}
