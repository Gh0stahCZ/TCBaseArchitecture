package com.tomaschlapek.tcbasearchitecture.presentation.presenter

import android.os.Bundle
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.KISamplePresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISampleActivityView
import io.realm.Realm
import timber.log.Timber

/**
 * Created by tomaschlapek on 15/9/17.
 */
class KSamplePresenterImpl : ActivityPresenter<KISampleActivityView>(), KISamplePresenter {

  /* Public Constants *****************************************************************************/

  /**
   * Extra identifiers.
   */
  object Argument {
    const val GAME_ID = "game_id"
  }

  /* Private Attributes ***************************************************************************/

  var mGameId: String? = null

  /* Public Methods *******************************************************************************/

  override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
    super.onCreate(arguments, savedInstanceState)
    val state = savedInstanceState ?: arguments
    loadArguments(state) // Load arguments.

    if (mRealm?.isClosed ?: false) {
      mRealm.close()
      mRealm = null
    }

  }

  override fun onBindView(view: KISampleActivityView) {
    super.onBindView(view)
  }

  override fun onButtonClick() {
    Timber.i("onButtonClick()")
    view?.showToast("Button clicked")
  }

  override fun getSharingText(): String {
    return App.getResString(R.string.me)
  }

  fun getRealm(): Realm {
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

}