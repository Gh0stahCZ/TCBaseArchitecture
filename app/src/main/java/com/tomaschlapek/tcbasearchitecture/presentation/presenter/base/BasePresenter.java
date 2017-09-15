package com.tomaschlapek.tcbasearchitecture.presentation.presenter.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.helper.NavigationHelper;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IBaseView;

import eu.inloop.viewmodel.AbstractViewModel;

/**
 * Base class for presenter
 */
public abstract class BasePresenter<TView extends IBaseView> extends AbstractViewModel<TView> {

  /* Private Methods ******************************************************************************/

  private BasePresenter mNestedPresenter;

  /* Public Methods *******************************************************************************/

  /**
   * If nested presenter exist, get current most deepest active presenter
   *
   * @return if exist, return deepest presenter; else return self
   */
  public BasePresenter getActiveNestedPresenter() {
    if (mNestedPresenter == this || mNestedPresenter == null) {
      return this;
    }

    return mNestedPresenter.getActiveNestedPresenter();
  }

  /**
   * Set nested presenter
   *
   * @param presenter current nested presenter
   */
  public void setNestedPresenter(BasePresenter presenter) {
    mNestedPresenter = presenter;
  }

  /**
   * This method should be overridden if presenter has sharing
   * capabilities and should return text for sharing
   */
  public String getSharingText() {
    return App.getResString(R.string.me);
  }

  /**
   * Trigger sharing functionality, called as onclick listener in view
   */
  public void onShareDialog() {
    String sharingText = getSharingText();
    if (getView() != null) {
      getView().openShareDialog(sharingText);
    }
  }

  public void setLoadingProgressVisibility(boolean visible) {
    TView view = getView();
    if (view != null) {
      view.setLoadingProgressVisibility(visible);
    }
  }

  @Override
  public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
    super.onCreate(arguments, savedInstanceState);

    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(App.getResString(R.string.broadcast_sign_up));
    intentFilter.addAction(App.getResString(R.string.broadcast_create_account));

    LocalBroadcastManager.getInstance(App.getAppComponent().provideContext()).registerReceiver(
      mLoginReceiver, intentFilter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    LocalBroadcastManager.getInstance(App.getAppComponent().provideContext())
      .unregisterReceiver(mLoginReceiver);
  }

  private static BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      // TODO Auto-generated method stub
      // Get extra data included in the Intent
      String message = intent.getStringExtra("message");
      String action = intent.getAction();

      if (TextUtils.isEmpty(action)) {
        return; // Unknown message.
      }

      if (action.equalsIgnoreCase(App.getResString(R.string.broadcast_create_account))) {
        NavigationHelper.openSampleActivity(App.getAppComponent().provideContext(), false);
      } else if (action.equalsIgnoreCase(App.getResString(R.string.broadcast_sign_up))) {
        NavigationHelper.openSignInActivity(App.getAppComponent().provideContext(), false);
      }
    }
  };
}
