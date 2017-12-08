package com.tomaschlapek.tcbasearchitecture.presentation.presenter

import android.content.Intent
import android.os.Bundle
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.KISamplePresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISampleActivityView
import com.tomaschlapek.tcbasearchitecture.util.str
import io.realm.Realm
import timber.log.Timber

const val EXTRA_NOTIF_DATA = "extra_notif_data"

/**
 * Sample presenter.
 */
class KSamplePresenterImpl : KActivityPresenter<KISampleActivityView>(), KISamplePresenter {

  /* Public Constants *****************************************************************************/

  /**
   * Extra identifiers.
   */
  companion object Argument {
    const val GAME_ID = "game_id"
  }

  /* Private Attributes ***************************************************************************/

  var mGameId: String? = null
  var mNotifData: String? = null

  /* Public Methods *******************************************************************************/

  override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
    super.onCreate(arguments, savedInstanceState)
    val state = savedInstanceState ?: arguments
    loadArguments(state) // Load arguments.
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString(EXTRA_NOTIF_DATA, mNotifData)
  }

  override fun onBindView(view: KISampleActivityView) {
    super.onBindView(view)
    init()
  }

  override fun registerSubscribers() {
  }

  override fun unregisterSubscribers() {
  }

  override fun onButtonClick() {
    Timber.i("onButtonClick()")
    view?.showToast("Button clicked")
  }

  override fun getSharingText(): String {
    return str(R.string.me)
  }

  fun getRealm(): Realm? {
    return super.mRealm
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    loadArguments(intent.extras)
  }

  /* Private Methods ******************************************************************************/

  // Load arguments.
  fun loadArguments(state: Bundle?) {
    if (state?.containsKey(Argument.GAME_ID) == true) {
      mGameId = state.let { state.getString(Argument.GAME_ID) }
    }
    if (state?.containsKey(EXTRA_NOTIF_DATA) == true) {
      mNotifData = state.let { state.getString(EXTRA_NOTIF_DATA) }
    }

    if (!mNotifData.isNullOrBlank()) {
      view?.showToast(mNotifData)
    }
  }

  fun init() {
    Timber.d("init()")

  }

  /* Inner classes ********************************************************************************/

}