package com.tomaschlapek.tcbasearchitecture.helper

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.domain.model.KSignInListener
import com.tomaschlapek.tcbasearchitecture.util.Konstants
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * Sign in with Google.
 */
class KGoogleSignInHelper {

  /* Private Constants ****************************************************************************/

  /* Private Attributes ***************************************************************************/

  /**
   * Reference to the Activity this object is bound to (we use a weak ref to avoid context leaks).
   */
   var mActivityRef: WeakReference<Activity>? = null

  /**
   * Callbacks interface we invoke to notify the user of this class of events.
   */
   var mSignInListenerRef: WeakReference<KSignInListener>? = null

  /**
   * The entry point to Google Play Services.
   */
   var mGoogleApiClient: GoogleApiClient? = null

  private val mFirebaseAuth = FirebaseAuth.getInstance()

  /* Constructor **********************************************************************************/
  /* Public Static Methods ************************************************************************/

  fun getGoogleSignInOptions(webClientId: String): GoogleSignInOptions {
    return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//      .requestIdToken(webClientId)
      .requestEmail()
      .build()
  }

  /* Public Methods *******************************************************************************/

  fun signIn() {
    Timber.d("Starting sign in")

    val activity = getActivity() ?: return

    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
    activity.startActivityForResult(signInIntent, Konstants.RC_SIGN_IN)
  }

  fun signOut() {
    Timber.d("Signing out user")

    val activity = getActivity() ?: return

    val signInListener = getSignInListener() ?: return

    // Signing out requires a connected GoogleApiClient. It is the responsibility of the bound
    // activity to ensure that GoogleApiClient is connected.
    if (!mGoogleApiClient!!.isConnected) {
      return
    }

    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { status ->
      if (status.isSuccess) {
        performPostSignOutTasks(status)
      } else {
        Timber.w("Failed to sign out")
        signInListener?.onSignOutFailed(status)
      }
    }
  }

  /**
   * Method for processing sign in logic in the bonding activity's
   * [Activity.onActivityResult].

   * @param requestCode The requestCode argument of the activity's onActivityResult.
   * *
   * @param resultCode The resultCode argument of the activity's onActivityResult.
   * *
   * @param data The Intent argument of the activity's onActivityResult.
   */
  fun onActivityResult(requestCode: Int, resultCode: Int,
    data: Intent) {

    val signInListener = getSignInListener() ?: return

    if (requestCode == Konstants.RC_SIGN_IN) {
      val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
      if (result.isSuccess) {
        val acct = result.signInAccount
        if (acct != null) {
          performPostSignInTasks(acct, result)
        }
      } else {
        Timber.w("Sign in failed")
        signInListener.onSignInFailed(result)
      }
    }
  }

  /* Private Methods ******************************************************************************/

  private fun getActivity(): Activity? {
    val activity = mActivityRef!!.get()
    if (activity == null) {
      Timber.d("Activity is null")
    }
    return activity
  }

  private fun getSignInListener(): KSignInListener? {
    val signInListener = mSignInListenerRef!!.get()
    if (signInListener == null) {
      Timber.d("SignInListener is null")
    }
    return signInListener
  }

  /**
   * Called once a user has signed in.

   * @param acct The sign in account.
   * *
   * @param result The sign in result.
   */
  private fun performPostSignInTasks(acct: GoogleSignInAccount, result: GoogleSignInResult) {

    val activity = getActivity() ?: return

    val signInListener = getSignInListener() ?: return

    // Perform Firebase auth
    firebaseAuthWithGoogle(acct)

    // Register this account/device pair within the server.
    registerWithServer(activity, true)

    // Note: Post Sign in work related to user data is done in the following service.
    // This also includes calling the sync for user data.
    //    PostSignInUpgradeService.upgradeToSignedInUser(
    //      activity, AccountUtils.getActiveAccountName(activity));

    // Tasks executed by the binding activity on sign in.
    signInListener.onSignIn(result)
    //    AnalyticsHelper.setUserSignedIn(true);
  }

  /**
   * Called once a user has signed out.

   * @param status The status returned when Auth.GoogleSignInApi.signOut(...) is called.
   */
  private fun performPostSignOutTasks(status: Status) {
    Timber.d("Successfully signed out.")

    val activity = getActivity() ?: return

    val signInListener = getSignInListener() ?: return

    registerWithServer(activity, false)

    App.getAppComponent().provideUserEngine().clearUserSession()

    FirebaseAuth.getInstance().signOut()

    // Tasks executed by the binding activity upon sign out.
    signInListener.onSignOut(status)
  }

  /**
   * Connect to Firebase to get login details. Once available, continue execution.

   * @param acct Account for currently logged-in user
   */
  private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
    Timber.d("firebaseAuthWithGoogle:" + acct.id!!)

    val activity = getActivity() ?: return

    val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

    mFirebaseAuth.signInWithCredential(credential)
      .addOnCompleteListener(activity) { task ->
        if (!task.isSuccessful) {
          Timber.w("signInWithCredential", task.exception)
          return@addOnCompleteListener
        }

        if (FirebaseAuth.getInstance().currentUser != null) {
          Timber.d("signInWithCredential:onComplete:" + task.isSuccessful)

          // Check if user is registered.
          // RegistrationStatusService.updateRegStatusInBackground(activity);
        }
      }
  }

  /**
   * Register this account/device pair with the server.

   * @param activity The bound activity.
   * *
   * @param signedIn Whether the user is signed in.
   */
  private fun registerWithServer(activity: Activity, signedIn: Boolean) {
    // TODO
  }


}
