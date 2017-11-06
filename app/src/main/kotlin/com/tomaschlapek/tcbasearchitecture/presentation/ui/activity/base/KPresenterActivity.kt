package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KBasePresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView
import eu.inloop.viewmodel.ViewModelHelper

/**
 * Base class for all application activities that use view-model concept.
 */
abstract class KPresenterActivity<TView : KIBaseView, TViewModel : KBasePresenter<TView>> : KPresenterEmptyActivity(), KIBaseView {

  /* Private Attributes ***************************************************************************/

  /**
   * View-model helper.
   */
  private val mPresenterHelper = ViewModelHelper<TView, TViewModel>()

  /* Public Abstract Methods **********************************************************************/

  /**
   * Returns class of view-model coupled with this activity.
   */
  abstract fun getPresenterClass(): Class<TViewModel>?

  /* Public Methods *******************************************************************************/

  /**
   * Returns the activity view model.

   * @return Activity view model.
   */
  val presenter: TViewModel
    get() = mPresenterHelper.viewModel

  /**
   * Call this after your view is ready - usually on the end of
   * [FragmentActivity.onCreate].

   * @param view The view-model view.
   */
  fun setPresenter(view: TView) {
    mPresenterHelper.setView(view)
  }

  /* Protected Methods ****************************************************************************/

  open fun onCreatePresenter(savedInstanceState: Bundle?) {
    mPresenterHelper.onCreate(this, savedInstanceState, getPresenterClass(), intent.extras)
    setPresenter(this as TView)
  }

  /**
   * @see FragmentActivity.onStart
   */
  override fun onStart() {
    super.onStart()
    mPresenterHelper.onStart()
  }

  @SuppressLint("MissingSuperCall")
  override fun onStop() {
    super.onStop()
    mPresenterHelper.onStop()
  }

  /**
   * @see FragmentActivity.onDestroy
   */
  override fun onDestroy() {
    mPresenterHelper.onDestroy(this)
    super.onDestroy()
  }

  /**
   * @see FragmentActivity.onSaveInstanceState
   */
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mPresenterHelper.onSaveInstanceState(outState)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
  }

  override fun openShareDialog(text: String) {

    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name))
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)

    startActivity(Intent.createChooser(shareIntent, null))
  }
}
