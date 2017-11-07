package com.tomaschlapek.tcbasearchitecture.presentation.presenter

import android.os.Bundle
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.KIChatPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIChatActivityView
import com.tomaschlapek.tcbasearchitecture.util.str
import io.realm.Realm
import timber.log.Timber

/**
 * Chat presenter.
 */
class KChatPresenterImpl : KActivityPresenter<KIChatActivityView>(), KIChatPresenter {

  /* Public Constants *****************************************************************************/

  /**
   * Extra identifiers.
   */
  companion object Argument {
    const val GAME_ID = "game_id"
  }

  /* Private Attributes ***************************************************************************/

  var mGameId: String? = null

  /* Public Methods *******************************************************************************/

  override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
    super.onCreate(arguments, savedInstanceState)
    val state = savedInstanceState ?: arguments
    loadArguments(state) // Load arguments.
  }

  override fun onBindView(view: KIChatActivityView) {
    super.onBindView(view)
  }

  override fun registerSubscribers() {
  }

  override fun unregisterSubscribers() {
  }


  override fun getSharingText(): String {
    return str(R.string.me)
  }

  override fun onSettingsClick() {
    view?.openSettings()
  }

  fun getRealm(): Realm? {
    return super.mRealm
  }

  /* Private Methods ******************************************************************************/

  // Load arguments.
  fun loadArguments(state: Bundle?) {
    if (state?.containsKey(Argument.GAME_ID) ?: false) {
      mGameId = state?.let { state.getString(Argument.GAME_ID) }
    }
  }

  fun init() {
    Timber.d("init()")
  }

  /* Inner classes ********************************************************************************/

}