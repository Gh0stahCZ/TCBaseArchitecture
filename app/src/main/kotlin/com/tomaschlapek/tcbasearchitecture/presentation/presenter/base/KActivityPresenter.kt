package com.tomaschlapek.tcbasearchitecture.presentation.presenter.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.engine.UserEngine
import com.tomaschlapek.tcbasearchitecture.helper.KNavigationHelper
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import com.tomaschlapek.tcbasearchitecture.helper.KRealmHelper
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView
import com.tomaschlapek.tcbasearchitecture.util.str
import eu.inloop.viewmodel.AbstractViewModel
import io.realm.Realm
import okhttp3.WebSocketListener
import timber.log.Timber


/**
 * Base class for view models of [PresenterActivity]
 */
abstract class KActivityPresenter<TView : KIBaseView> : KBasePresenter<TView>() {

  /* Protected Attributes *************************************************************************/

  /**
   * Link to view (FragmentActivity).
   */
  lateinit var mActivity: FragmentActivity

  /**
   * Holds default instance of realm.
   */
  protected var mRealm: Realm? = null
  private var mState: Bundle? = null

  lateinit var userEngine: UserEngine
  lateinit var preferenceHelper: KPreferenceHelper
  lateinit var realmHelper: KRealmHelper

  protected val mAuth by lazy {
    FirebaseAuth.getInstance()
  }

  abstract fun registerSubscribers()

  abstract fun unregisterSubscribers()


  /* Public Methods *******************************************************************************/

  override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
    super.onCreate(arguments, savedInstanceState)
    mState = savedInstanceState ?: arguments

    userEngine = App.getAppComponent().provideUserEngine()
    preferenceHelper = App.getAppComponent().provideKPreferenceHelper()
    realmHelper = App.getAppComponent().provideRealmHelper()

    val sckt: WebSocketListener
  }

  /**
   * @see AbstractViewModel.onBindView
   */
  override fun onBindView(view: TView) {
    super.onBindView(view)

    // Remember the link to activity.
    mActivity = view as FragmentActivity

    mRealm = Realm.getDefaultInstance()

    if (mState != null) {
      loadArguments(mState)
    }

    registerSubscribers()
  }

  override fun clearView() {
    super.clearView()

    unregisterSubscribers()

    // Close the default realm instance.
    if (mRealm != null && !mRealm!!.isClosed) {
      mRealm!!.close()
      mRealm = null
    }
  }

  open fun onNewIntent(intent: Intent) {
    Timber.d("onNewIntent() : " + intent)
  }


  fun onNotificationReceived(notification: Map<String, String>?) {
    Timber.d(notification?.toString())
    view?.onNotificationReceived(notification?.toString() ?: "Unknown data")
  }

  fun sendVerifyEmail() {
    val user = mAuth.currentUser

    val dynamicLink = str(R.string.link_verify_email)
    val actionCodeSettings = ActionCodeSettings.newBuilder()
      .setUrl(dynamicLink)
      .build()

    user?.sendEmailVerification(actionCodeSettings)?.addOnCompleteListener(mActivity) { task ->
      if (task.isSuccessful) {
        view?.showSnack(R.string.email_sent, R.string.open_email) {
          KNavigationHelper.openEmailClient(mActivity)
        }
      } else {
        view?.showSnack(R.string.email_not_sent)
      }
    }
  }

  fun blockUnauthorized() {
    if (isUserLogged && isUserVerified) {
      return
    } else {
      view?.showVerifyBlock()
    }
  }


  override fun onSaveInstanceState(bundle: Bundle) {
    super.onSaveInstanceState(bundle)
  }

  val isUserVerified: Boolean
    get() = mAuth.currentUser?.isEmailVerified ?: false

  val isUserLogged: Boolean
    get() = !TextUtils.isEmpty(preferenceHelper.userLoginToken)

  protected val isUserLoggedByFb: Boolean
    get() = !TextUtils.isEmpty(preferenceHelper.userFbToken)

  /**
   * Loads arguments from SavedInstance or passed bundle.
   * @param state Bundle with data.
   */
  private fun loadArguments(state: Bundle?) {
    // Load arguments.
    if (state != null) {
      //      if (state.containsKey(Argument.EXTRA_GAME_ID)) {
      //        gameId = state.getString(Argument.EXTRA_GAME_ID);
      //      }
    }
  }
}
