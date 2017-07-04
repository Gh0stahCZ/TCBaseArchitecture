package com.tomaschlapek.tcbasearchitecture;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.OnboardingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support .test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnboardingTest {

  @Rule
  public ActivityTestRule<OnboardingActivity> mActivityRule = new ActivityTestRule<>(
    OnboardingActivity.class);

  @Test
  public void useAppContext() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();
    assertEquals("com.tomaschlapek.tcbasearchitecture.debug", appContext.getPackageName());
  }

  @Test
  public void passOnboarding() {

    // Type text and then press the button.
    onView(withId(R.id.onboarding_viewpager)).perform(swipeLeft());
    onView(allOf(withId(R.id.negative_button), isCompletelyDisplayed())).perform(click());
  }


}



