package com.tomaschlapek.tcbasearchitecture;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.SignInActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignInTest {

  private String mEmailToBetyped;
  private String mPassToBetyped;

  @Rule
  public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
    SignInActivity.class);

  @Before
  public void initValidString() {
    // Specify a valid string.
    mEmailToBetyped = "valied@email.cz";
    mPassToBetyped = "valiedlongpassword";
  }

  @Test
  public void useAppContext() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("com.tomaschlapek.tcbasearchitecture.debug", appContext.getPackageName());
  }

  @Test
  public void fillCredentials_signActivity() {

    // Type text and then press the button.
    onView(withId(R.id.sample_sign_in_email_edit_text))
      .perform(typeText(mEmailToBetyped), closeSoftKeyboard());

    onView(withId(R.id.sample_sign_in_pass_edit_text))
      .perform(typeText(mPassToBetyped), closeSoftKeyboard());

    SystemClock
      .sleep(1000); // wait 1 second because delay between checking text changes is about 500 ms

    // Check that the text was changed.
    onView(withId(R.id.sample_sign_in_button))
      .check(matches(isEnabled()));

    onView(withId(R.id.sample_sign_in_button)).perform(click());
  }
}



