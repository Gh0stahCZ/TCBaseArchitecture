package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.databinding.ActivityBaseBinding
import com.tomaschlapek.tcbasearchitecture.helper.KNavigationHelper
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.EXTRA_INT
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.EXTRA_NOTIFICATION
import com.tomaschlapek.tcbasearchitecture.util.registerPushReceiver
import org.jetbrains.anko.toast

/**
 * Base activity.
 */
abstract class KBaseActivity<TView : KIBaseView, TViewModel : KActivityPresenter<TView>> : KPresenterActivity<TView, TViewModel>() {

  /* Protected Attributes *************************************************************************/

  /**
   * Listens to connectivity changes.
   */
  //  protected NetworkStateReceiver mNetworkStateReceiver = new NetworkStateReceiver();

  lateinit protected var mBaseContainer: ViewGroup

  private val notificationReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      val notification = intent.getSerializableExtra(EXTRA_NOTIFICATION) as HashMap<String, String>
      val number = intent.getSerializableExtra(EXTRA_INT)
      presenter.onNotificationReceived(notification)
      abortBroadcast()
    }
  }

  /* Public Methods *******************************************************************************/

  /**
   * Page title.
   */
  fun getPageTitle(): String {
    return javaClass.name
  }

  override fun onStart() {
    super.onStart()
    if (abortNotification()) {
      registerNotificationAbortReceiver()
    }
  }

  override fun onStop() {
    super.onStop()
    if (abortNotification()) {
      unregisterNotificationAbortReceiver()
    }
  }


  private fun registerNotificationAbortReceiver() {
    registerPushReceiver(notificationReceiver)
  }

  private fun unregisterNotificationAbortReceiver() {
    unregisterReceiver(notificationReceiver)
  }

  /**
   * @see FragmentActivity.onBackPressed
   */
  override fun onBackPressed() {
    super.onBackPressed()
    // TODO Handle onBack();
  }

  /**
   * @see FragmentActivity.onLowMemory
   */
  override fun onLowMemory() {
    super.onLowMemory()
    // TODO Implement behavior on low memory (like images loading stop).
  }

  /**
   * Shows the keyboard.
   */
  override fun showKeyboard(view: View) {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
  }

  /**
   * Hides the keyboard, if shown.
   */
  override fun hideKeyboard() {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = currentFocus
    if (currentFocus != null) {
      manager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
  }

  /**
   * Clears the focus from the currently focused view.
   */
  override fun clearFocus() {
    val focus = currentFocus
    focus?.clearFocus()
  }

  /**
   * Sets loading progress visibility
   * @param visible
   */
  override fun setLoadingProgressVisibility(visible: Boolean) {
    // TODO
  }

  override fun onNotificationReceived(message: String) {
    toast(message)
  }

  @SuppressLint("MissingSuperCall")
  override fun onCreate(savedInstanceState: Bundle?) {

    // If this is initial launcher activity, start
    if (getPageTitle() == KInitActivity::class.java.name) {
      attemptFirstInit(savedInstanceState)
    } else {
      attemptClassicInit(savedInstanceState)
    }
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    presenter.onNewIntent(intent)
    setIntent(intent)
  }

  /**
   * Create presenter and binds view to it.
   * It needs to be separate method, otherwise view is not created during onbindview
   * @param savedInstanceState Bundle with data.
   */

  override fun onCreatePresenter(savedInstanceState: Bundle?) {
    super.onCreatePresenter(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()

    // Unregister broadcast receivers.
    //    try {
    unregisterBroadcasts()
    //    } catch (e: IllegalArgumentException) {
    // Some notificationReceiver was not registered.
    //    }
  }

  abstract fun abortNotification(): Boolean

  abstract override fun getPresenterClass(): Class<TViewModel>?

  override fun getContentContainer(): ViewGroup {
    return mBaseContainer
  }

  /**
   * Get data from sharing

   * @return data from sharing
   */
  fun getSharingData(): Bundle {
    return intent.extras
  }

  /**
   * Registers broadcast receivers.
   */
  protected fun registerBroadcasts() {
    //    registerReceiver(mNetworkStateReceiver, new IntentFilter(
    //      ConnectivityManager.CONNECTIVITY_ACTION));
  }

  /**
   * Unregisters broadcast receivers.
   */
  protected fun unregisterBroadcasts() {
    //    unregisterReceiver(mNetworkStateReceiver);
  }


  /* Private Methods ******************************************************************************/

  private fun attemptClassicInit(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = DataBindingUtil.setContentView<ActivityBaseBinding>(this, R.layout.activity_base)
    mBaseContainer = binding.baseContainer

    registerBroadcasts()
  }

  private fun attemptFirstInit(savedInstanceState: Bundle?) {
    // Dagger injection
    //    App.getAppComponent().inject(this);
    val initActivityName = App.INIT_ACTIVITY_NAME

    val sharingBundle: Bundle? = null
    val intent = intent
    val action = intent.action
    val data = intent.dataString
    val isFirstRun = App.getAppComponent().provideKPreferenceHelper().firstRun
    val isLogged = !TextUtils.isEmpty(App.getAppComponent().provideKPreferenceHelper().userLoginToken)

    KNavigationHelper.openInitActivity(applicationContext,
      initActivityName, isFirstRun, isLogged, sharingBundle)
    super.onCreate(savedInstanceState)

    finish()
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/


}