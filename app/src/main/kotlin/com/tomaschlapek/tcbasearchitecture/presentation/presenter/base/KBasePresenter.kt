package com.tomaschlapek.tcbasearchitecture.presentation.presenter.base

import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView
import com.tomaschlapek.tcbasearchitecture.util.str
import eu.inloop.viewmodel.AbstractViewModel

/**
 * Base class for presenter
 */
abstract class KBasePresenter<TView : KIBaseView> : AbstractViewModel<TView>() {

  /* Public Methods *******************************************************************************/

  /**
   * This method should be overridden if presenter has sharing
   * capabilities and should return text for sharing
   */
  open fun getSharingText(): String {
    return str(R.string.me)
  }

  /**
   * Trigger sharing functionality, called as onclick listener in view
   */
  fun onShareDialog() {
    val sharingText = getSharingText()
    if (view != null) {
      view!!.openShareDialog(sharingText)
    }
  }

}
