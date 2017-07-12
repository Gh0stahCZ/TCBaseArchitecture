package com.tomaschlapek.tcbasearchitecture.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.tomaschlapek.fancysignup.SignUpActivity;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.ChatActivity;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.OnboardingActivity;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.SampleActivity;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.SignInActivity;

import timber.log.Timber;

/**
 * Navigation manager.
 */
public class NavigationHelper {

  /* Private Attributes ***************************************************************************/

  /**
   * Time of manager initialization
   */
  private long mTime;

  private static boolean isDebug = true;

  /* Constructor *********************************************************************************/

  public NavigationHelper() {
    this.mTime = System.currentTimeMillis();
  }

  /* Public methods ******************************************************************************/

  /**
   * Opens sample activity..
   */
  public static void openSampleActivity(Context context, boolean isInitActivity) {
    Timber.d("openSampleActivity()");

    Intent intent = new Intent(context, SampleActivity.class);

    if (isInitActivity) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    } else {
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    context.startActivity(intent);
  }

  /**
   * Opens chat activity..
   */
  public static void openChatActivity(Context context, boolean isInitActivity) {
    Timber.d("openChatActivity()");

    Intent intent = new Intent(context, ChatActivity.class);

    if (isInitActivity) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    } else {
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    context.startActivity(intent);
  }

  /**
   * Opens onboarding activity.
   */
  public static void openOnboardingActivity(Context context, boolean isInitActivity) {
    Timber.d("openOnboardingActivity()");
    Intent intent = new Intent(context, OnboardingActivity.class);

    if (isInitActivity) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    } else {
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    context.startActivity(intent);
  }

  /**
   * Opens sign in activity
   */
  public static void openSignInActivity(Context context, boolean isInitActivity) {
    Timber.d("openSignInActivity()");
    Intent intent = new Intent(context, SignInActivity.class);

    if (isInitActivity) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    } else {
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    context.startActivity(intent);
  }

  /**
   * Opens sign up activity
   */
  public static void openSignUpActivity(Context context, boolean isInitActivity) {
    Timber.d("openSignUpActivity()");
    Intent intent = new Intent(context, com.tomaschlapek.fancysignup.SignUpActivity.class);

    if (isInitActivity) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    } else {
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    context.startActivity(intent);
  }

  public static void openInitActivity(Context context, String activityName,
    boolean isFirstRun, boolean isLogged, Bundle sharingBundle) {
    openInitActivityWithData(context, activityName, isFirstRun, isLogged, sharingBundle);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  public static void openInitActivityWithData(Context context, String activityName,
    boolean isFirstRun, boolean isLogged, Bundle sharingData) {
    Timber.d("openActivityWithData()");

    boolean isInitActivity = true;

    if (isFirstRun) {
      openOnboardingActivity(context, isInitActivity);
    } else if (!isLogged/* || !App.sDebug*/) {
      openSignInActivity(context, isInitActivity);
    } else if (SampleActivity.class.getName().equals(activityName)) {
      openSampleActivity(context, isInitActivity);
    } else if (OnboardingActivity.class.getName().equals(activityName)) {
      openOnboardingActivity(context, isInitActivity);
    } else if (SignInActivity.class.getName().equals(activityName)) {
      openSignInActivity(context, isInitActivity);
    } else if (SignUpActivity.class.getName().equals(activityName)) {
      openSignUpActivity(context, isInitActivity);
    } else if (ChatActivity.class.getName().equals(activityName)) {
      openChatActivity(context, isInitActivity);
    } else {
      openSampleActivity(context, isInitActivity);
    }
  }

  /* Private Methods ******************************************************************************/

  /**
   * Replaces fragment in certain container.
   */
  private static void replaceFragment(FragmentTransaction transaction,
    int containerId, Fragment fragment, boolean addToBackStack) {
    replaceFragment(transaction, containerId, fragment, addToBackStack, -1, -1);
  }

  /**
   * Replaces fragment in certain container.
   */
  private static void replaceFragment(FragmentTransaction transaction, int containerId,
    Fragment fragment, boolean addToBackStack, int enterAnim, int exitAnim) {
    Timber.d("replaceFragment(): addToBackStack: " + addToBackStack);

    // Set transaction animations if passed.
    if ((enterAnim >= 0) && (exitAnim >= 0)) {
      transaction.setCustomAnimations(enterAnim, R.anim.no_anim, R.anim.no_anim, exitAnim);
    }

    // Replace fragment in container.
    transaction.replace(containerId, fragment);

    // Add to back-stack.
    if (addToBackStack) {
      transaction.addToBackStack(null);
    }

    transaction.commitAllowingStateLoss();
  }

  /**
   * Commit fragment in activity.
   *
   * @param activity Parent activity.
   *
   * @return Fragment transaction.
   */
  @SuppressLint("CommitTransaction")
  private static FragmentTransaction beginTransaction(FragmentActivity activity) {
    return activity.getSupportFragmentManager().beginTransaction();
  }

  /**
   * Commit fragment nested in other fragment.
   *
   * @param fragment Parent activity.
   *
   * @return Fragment transaction.
   */
  @SuppressLint("CommitTransaction")
  private static FragmentTransaction beginTransaction(Fragment fragment) {
    return fragment.getFragmentManager().beginTransaction();
  }
}
