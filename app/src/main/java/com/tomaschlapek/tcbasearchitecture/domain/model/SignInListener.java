package com.tomaschlapek.tcbasearchitecture.domain.model;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Status;

/**
 * Interface for activities that need sign in and sign out functionality.
 */
public interface SignInListener {
  /**
   * Called when sign in succeeds.
   *
   * @param result The {@link GoogleSignInResult result}.
   */
  void onSignIn(GoogleSignInResult result);

  /**
   * Called when sign out succeeds.
   *
   * @param status The {@link Status status}.
   */
  void onSignOut(Status status);

  /**
   * Called when sign in fails.
   *
   * @param result The {@link GoogleSignInResult result}.
   */
  void onSignInFailed(GoogleSignInResult result);

  /**
   * Called when sign out fails.
   *
   * @param status The {@link Status status}.
   */
  void onSignOutFailed(Status status);
}
