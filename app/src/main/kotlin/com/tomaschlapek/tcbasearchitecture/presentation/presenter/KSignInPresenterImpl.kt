package com.tomaschlapek.tcbasearchitecture.presentation.presenter

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.KISignInPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISignInActivityView
import com.tomaschlapek.tcbasearchitecture.util.str
import timber.log.Timber


/**
 * Sign in activity
 */
class KSignInPresenterImpl : KActivityPresenter<KISignInActivityView>(), KISignInPresenter {

  /**
   * Extra identifiers.
   */
  companion object Argument {
    val GAME_ID = "game_id"

    val MIN_FULL_NAME_SIZE = 5
    val MIN_PASS_SIZE = 5
    val MIN_TEXT_TYPING_INTERVAL = 500 // 500 ms
  }

  /* Private Attributes ***************************************************************************/

  /**
   * ID of game.
   */
  private var mGameId: String? = null


  /* Constructor **********************************************************************************/
  /* Public Methods *******************************************************************************/

  override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
    super.onCreate(arguments, savedInstanceState)

    val state = savedInstanceState ?: arguments
    loadArguments(state) // Load arguments.
  }

  override fun onBindView(view: KISignInActivityView) {
    super.onBindView(view)
    init()
  }

  override fun onSaveInstanceState(bundle: Bundle) {
    super.onSaveInstanceState(bundle)
    bundle.putString(GAME_ID, mGameId)
  }

  override fun getSharingText(): String {
    return str(R.string.me)
  }

  override fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  override fun isValidPassword(pass: String): Boolean {
    return !TextUtils.isEmpty(pass) && pass.length >= MIN_PASS_SIZE
  }

  override fun onSkipClick() {

    mAuth.signInAnonymously().addOnCompleteListener(mActivity) { task ->
      if (task.isSuccessful) {
        // Sign in success, update UI with the signed-in user's information
        Timber.d("createUserWithEmail:success")
        val user = mAuth.currentUser
        user?.let { loggedUser ->
          userEngine.startUserSession(loggedUser)
          view?.onSuccessfulSignIn()
        } ?: view?.showSnack(str(R.string.general_error_message))
      } else {
        // If sign in fails, display a message to the user.
        Timber.w("createUserWithEmail:failure", task.exception?.message)
        view?.showSnack(task.exception?.localizedMessage ?: task.exception?.message ?: str(R.string.sample_sign_invalid_credentials))
      }
    }
  }

  override fun onSignInButtonClick(email: String, pass: String) {

    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(mActivity) { task ->
      if (task.isSuccessful) {
        // Sign in success, update UI with the signed-in user's information
        Timber.d("createUserWithEmail:success")
        val user = mAuth.currentUser
        user?.let { loggedUser ->
          userEngine.startUserSession(loggedUser)
          view?.onSuccessfulSignIn()
        } ?: view?.showSnack(str(R.string.general_error_message))
      } else {
        // If sign in fails, display a message to the user.
        Timber.w("createUserWithEmail:failure", task.exception?.message)
        view?.showSnack(task.exception?.localizedMessage ?: task.exception?.message ?: str(R.string.sample_sign_invalid_credentials))
        //              updateUI(null)
      }
    }

  }

  override fun registerSubscribers() {

  }

  override fun unregisterSubscribers() {

  }

  /* Private Methods ******************************************************************************/

  /**
   * Loads arguments from SavedInstance or passed bundle.

   * @param state Bundle with data.
   */
  private fun loadArguments(state: Bundle?) {
    // Load arguments.
    if (state != null) {
      if (state.containsKey(GAME_ID)) {
        mGameId = state.getString(GAME_ID)
      }
    }
  }

  private fun init() {
    Timber.d("init()")
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}