package com.tomaschlapek.tcbasearchitecture.domain.model

import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.Status

/**
 * Interface for activities that need sign in and sign out functionality.
 */
interface KSignInListener {
  /**
   * Called when sign in succeeds.

   * @param result The [result][GoogleSignInResult].
   */
  fun onSignIn(result: GoogleSignInResult)

  /**
   * Called when sign out succeeds.

   * @param status The [status][Status].
   */
  fun onSignOut(status: Status)

  /**
   * Called when sign in fails.

   * @param result The [result][GoogleSignInResult].
   */
  fun onSignInFailed(result: GoogleSignInResult)

  /**
   * Called when sign out fails.

   * @param status The [status][Status].
   */
  fun onSignOutFailed(status: Status)
}