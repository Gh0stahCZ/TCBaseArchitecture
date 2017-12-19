package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view

import android.view.View
import eu.inloop.viewmodel.IView

/**
 * Base interface for all views.
 */
interface KIBaseView : IView {

  /**
   * Closes the current activity/fragment.
   */
  fun finish()

  /**
   * Shows the keyboard.
   */
  fun showKeyboard(view: View)

  /**
   * Hides the keyboard, if shown.
   */
  fun hideKeyboard()

  /**
   * Clears the focus from the currently focused view.
   */
  fun clearFocus()

  /**
   * Open share dialog with share message with URL

   * @param text share message
   */
  fun openShareDialog(text: String)

  fun showSnack(text: String)

  fun showSnack(textResId: Int)

  fun showSnack(text: String, buttonText: String, onClick: (android.view.View) -> kotlin.Unit)

  fun showSnack(textResId: Int, buttonTextResId: Int, onClick: (View) -> Unit)

  fun showToast(text: String)

  fun showToast(textResId: Int)

  fun showNotImplemented()

  fun setLoadingProgressVisibility(visible: Boolean)

  fun onNotificationReceived(message: String)

  fun showVerifyBlock()

  fun showVerifyEmailSentDialog(textResId: Int)
}