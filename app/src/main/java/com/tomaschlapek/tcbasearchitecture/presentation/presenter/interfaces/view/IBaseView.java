package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view;

import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;

import eu.inloop.viewmodel.IView;

/**
 * Base interface for all views.
 */
public interface IBaseView extends IView {

  /**
   * Closes the current activity/fragment.
   */
  void finish();

  /**
   * Shows the keyboard.
   */
  void showKeyboard(View view);

  /**
   * Hides the keyboard, if shown.
   */
  void hideKeyboard();

  /**
   * Clears the focus from the currently focused view.
   */
  void clearFocus();

  /**
   * Open share dialog with share message with URL
   *
   * @param text share message
   */
  void openShareDialog(@NonNull String text);

  void setLoadingProgressVisibility(boolean visible);
}
