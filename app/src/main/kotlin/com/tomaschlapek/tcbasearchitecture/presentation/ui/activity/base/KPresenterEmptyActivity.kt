package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import dagger.android.support.DaggerAppCompatActivity
import eu.inloop.viewmodel.IViewModelProvider
import eu.inloop.viewmodel.ViewModelProvider
import javax.inject.Inject

/**
 * All activities must extent this class even if they have no view model. The fragment view models
 * are using the [IViewModelProvider] interface to get the [ViewModelProvider] from the
 * current activity.
 */
abstract class KPresenterEmptyActivity : DaggerAppCompatActivity(), IViewModelProvider {

  /* Protected Attributes *************************************************************************/

  @Inject
  lateinit var mPreferenceHelper: KPreferenceHelper


  /* Private Attributes ***************************************************************************/

  /**
   * View-model provider.
   */
  private var mPresenterProvider: ViewModelProvider? = null

  /* Public Methods *******************************************************************************/

  /**
   * @see FragmentActivity.onRetainCustomNonConfigurationInstance
   */
  override fun onRetainCustomNonConfigurationInstance(): Any? {
    return mPresenterProvider
  }

  /**
   * @see IViewModelProvider.getViewModelProvider
   */
  override fun getViewModelProvider(): ViewModelProvider {
    return mPresenterProvider!!
  }

  /* Protected Methods ****************************************************************************/

  /**
   * @see FragmentActivity.onCreate
   */
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mPresenterProvider = ViewModelProvider.newInstance(this)
  }

  /**
   * @see FragmentActivity.onStop
   */
  override fun onStop() {
    super.onStop()
    if (isFinishing) {
      mPresenterProvider!!.removeAllViewModels()
    }
  }

  /**
   * Gets main content container.

   * @return View group to inflate layouts.
   */
  abstract fun getContentContainer(): ViewGroup

  abstract fun blockUnauthorized(): Boolean



}