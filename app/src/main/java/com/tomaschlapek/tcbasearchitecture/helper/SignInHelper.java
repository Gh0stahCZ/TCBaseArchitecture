package com.tomaschlapek.tcbasearchitecture.helper;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.domain.model.SignInListener;
import com.tomaschlapek.tcbasearchitecture.util.Constants;

import java.lang.ref.WeakReference;

import timber.log.Timber;

/**
 * Created by tomaschlapek on 22/8/17.
 */

public class SignInHelper {

  /* Private Constants ****************************************************************************/

  /* Private Attributes ***************************************************************************/

  /**
   * Reference to the Activity this object is bound to (we use a weak ref to avoid context leaks).
   */
  private WeakReference<Activity> mActivityRef;

  /**
   * Callbacks interface we invoke to notify the user of this class of events.
   */
  private WeakReference<SignInListener> mSignInListenerRef;

  /**
   * The entry point to Google Play Services.
   */
  private GoogleApiClient mGoogleApiClient;

  private final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

  /* Constructor **********************************************************************************/
  /* Public Static Methods ************************************************************************/

  public static GoogleSignInOptions getGoogleSignInOptions(String webClientId) {
    return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(webClientId)
      .requestEmail()
      .build();
  }

  /* Public Methods *******************************************************************************/

  public void signIn() {
    Timber.d("Starting sign in");

    final Activity activity = getActivity();
    if (activity == null) {
      return;
    }

    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    activity.startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
  }

  public void signOut() {
    Timber.d("Signing out user");

    final Activity activity = getActivity();
    if (activity == null) {
      return;
    }

    final SignInListener signInListener = getSignInListener();
    if (signInListener == null) {
      return;
    }

    // Signing out requires a connected GoogleApiClient. It is the responsibility of the bound
    // activity to ensure that GoogleApiClient is connected.
    if (!mGoogleApiClient.isConnected()) {
      return;
    }

    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
      status -> {
        if (status.isSuccess()) {
          performPostSignOutTasks(status);
        } else {
          Timber.w("Failed to sign out");
          if (signInListener != null) {
            signInListener.onSignOutFailed(status);
          }
        }
      });
  }

  /**
   * Method for processing sign in logic in the bonding activity's
   * {@link Activity#onActivityResult(int, int, Intent)}.
   *
   * @param requestCode The requestCode argument of the activity's onActivityResult.
   * @param resultCode The resultCode argument of the activity's onActivityResult.
   * @param data The Intent argument of the activity's onActivityResult.
   */
  public void onActivityResult(final int requestCode, final int resultCode,
    final Intent data) {

    final SignInListener signInListener = getSignInListener();
    if (signInListener == null) {
      return;
    }

    if (requestCode == Constants.RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        GoogleSignInAccount acct = result.getSignInAccount();
        if (acct != null) {
          performPostSignInTasks(acct, result);
        }
      } else {
        Timber.w("Sign in failed");
        signInListener.onSignInFailed(result);
      }
    }
  }

  /* Private Methods ******************************************************************************/

  private Activity getActivity() {
    Activity activity = mActivityRef.get();
    if (activity == null) {
      Timber.d("Activity is null");
    }
    return activity;
  }

  private SignInListener getSignInListener() {
    SignInListener signInListener = mSignInListenerRef.get();
    if (signInListener == null) {
      Timber.d("SignInListener is null");
    }
    return signInListener;
  }

  /**
   * Called once a user has signed in.
   *
   * @param acct The sign in account.
   * @param result The sign in result.
   */
  private void performPostSignInTasks(GoogleSignInAccount acct, GoogleSignInResult result) {

    final Activity activity = getActivity();
    if (activity == null) {
      return;
    }

    final SignInListener signInListener = getSignInListener();
    if (signInListener == null) {
      return;
    }

    // Perform Firebase auth
    firebaseAuthWithGoogle(acct);

    // Register this account/device pair within the server.
    registerWithServer(activity, true);

    // Note: Post Sign in work related to user data is done in the following service.
    // This also includes calling the sync for user data.
    //    PostSignInUpgradeService.upgradeToSignedInUser(
    //      activity, AccountUtils.getActiveAccountName(activity));

    // Tasks executed by the binding activity on sign in.
    signInListener.onSignIn(result);
    //    AnalyticsHelper.setUserSignedIn(true);
  }

  /**
   * Called once a user has signed out.
   *
   * @param status The status returned when Auth.GoogleSignInApi.signOut(...) is called.
   */
  private void performPostSignOutTasks(Status status) {
    Timber.d("Successfully signed out.");

    final Activity activity = getActivity();
    if (activity == null) {
      return;
    }

    final SignInListener signInListener = getSignInListener();
    if (signInListener == null) {
      return;
    }

    registerWithServer(activity, false);

    App.getAppComponent().providePreferenceHelper().clearUserData();

    FirebaseAuth.getInstance().signOut();

    // Tasks executed by the binding activity upon sign out.
    signInListener.onSignOut(status);
  }

  /**
   * Connect to Firebase to get login details. Once available, continue execution.
   *
   * @param acct Account for currently logged-in user
   */
  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    Timber.d("firebaseAuthWithGoogle:" + acct.getId());

    final Activity activity = getActivity();
    if (activity == null) {
      return;
    }

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

    mFirebaseAuth.signInWithCredential(credential)
      .addOnCompleteListener(activity, task -> {
        if (!task.isSuccessful()) {
          Timber.w("signInWithCredential", task.getException());
          return;
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
          Timber.d("signInWithCredential:onComplete:" + task.isSuccessful());

          // Check if user is registered.
//          RegistrationStatusService.updateRegStatusInBackground(activity);
        }
      });
  }

  /**
   * Register this account/device pair with the server.
   *
   * @param activity The bound activity.
   * @param signedIn Whether the user is signed in.
   */
  private void registerWithServer(Activity activity, boolean signedIn) {
    // TODO
  }



  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}
