package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.databinding.ActivityBaseBinding;
import com.tomaschlapek.tcbasearchitecture.helper.NavigationHelper;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.InitActivity;

import timber.log.Timber;

/**
 * Base class for all application activities.
 */
public abstract class BaseActivity<TView extends KIBaseView, TViewModel extends
  ActivityPresenter<TView>> extends PresenterActivity<TView, TViewModel> {

  /* Public Attributes ****************************************************************************/

  /* Public Abstract Methods **********************************************************************/

  /* Private Attributes ***************************************************************************/

  /**
   * Listens to connectivity changes.
   */
  //  private NetworkStateReceiver mNetworkStateReceiver = new NetworkStateReceiver();

  /* Protected Attributes *************************************************************************/

  protected ViewGroup mBaseContainer;

  /* Public Methods *******************************************************************************/

  /**
   * Page title.
   */
  public String getPageTitle() {
    return getClass().getName();
  }

  /**
   * @see FragmentActivity#onBackPressed()
   */
  @Override
  public void onBackPressed() {
    super.onBackPressed();
    // TODO Handle onBack();
  }

  /**
   * @see FragmentActivity#onLowMemory()
   */
  @Override
  public void onLowMemory() {
    super.onLowMemory();
    // TODO Implement behavior on low memory (like images loading stop).
  }

  /**
   * Shows the keyboard.
   */
  public void showKeyboard(final View view) {
    InputMethodManager manager = (InputMethodManager)
      getSystemService(Context.INPUT_METHOD_SERVICE);
    manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
  }

  /**
   * Hides the keyboard, if shown.
   */
  public void hideKeyboard() {
    InputMethodManager manager = (InputMethodManager)
      getSystemService(Context.INPUT_METHOD_SERVICE);
    View currentFocus = getCurrentFocus();
    if (currentFocus != null) {
      manager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
  }

  /**
   * Clears the focus from the currently focused view.
   */
  public void clearFocus() {
    View focus = getCurrentFocus();
    if (focus != null) {
      focus.clearFocus();
    }
  }

  /**
   * Sets loading progress visibility
   *
   * @param visible
   */
  public void setLoadingProgressVisibility(boolean visible) {

  }


  /* Protected Methods ****************************************************************************/

  @SuppressLint("MissingSuperCall")
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {

    // If this is initial launcher activity, start
    if (getPageTitle().equals(InitActivity.class.getName())) {
      attemptFirstInit(savedInstanceState);
    } else {
      attemptClassicInit(savedInstanceState);
    }
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    getPresenter().onNewIntent(intent);
    setIntent(intent);
  }

  /**
   * Create presenter and binds view to it.
   * It needs to be seperate method, otherwise view is not created during onbindview
   *
   * @param savedInstanceState Bundle with data.
   */
  @Override
  protected void onCreatePresenter(Bundle savedInstanceState) {
    super.onCreatePresenter(savedInstanceState);
  }

  private void attemptClassicInit(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_base);
    mBaseContainer = binding.baseContainer;

    registerBroadcasts();
  }

  private void attemptFirstInit(@Nullable Bundle savedInstanceState) {
    // Dagger injection
    //    App.getAppComponent().inject(this);
    String initActivityName = App.INIT_ACTIVITY_NAME;

    Bundle sharingBundle = null;
    Intent intent = getIntent();
    String action = intent.getAction();
    String data = intent.getDataString();
    boolean isFirstRun = App.getAppComponent().providePreferenceHelper().getFirstRun();
    boolean isLogged =
      !TextUtils.isEmpty(App.getAppComponent().providePreferenceHelper().getUserUID());

    NavigationHelper.openInitActivity(getApplicationContext(),
      initActivityName, isFirstRun, isLogged, sharingBundle);
    super.onCreate(savedInstanceState);

    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Unregister broadcast receivers.
    try {
      unregisterBroadcasts();
    } catch (IllegalArgumentException e) {
      // Some receiver was not registered.
    }
  }

  @Override
  public abstract Class getPresenterClass();

  @Override
  public ViewGroup getContentContainer() {
    return mBaseContainer;
  }

  /**
   * Get data from sharing
   *
   * @return data from sharing
   */
  public Bundle getSharingData() {
    return getIntent().getExtras();
  }

  /**
   * Registers broadcast receivers.
   */
  protected void registerBroadcasts() {
    //    registerReceiver(mNetworkStateReceiver, new IntentFilter(
    //      ConnectivityManager.CONNECTIVITY_ACTION));
  }

  /**
   * Unregisters broadcast receivers.
   */
  protected void unregisterBroadcasts() {
    //    unregisterReceiver(mNetworkStateReceiver);
  }

  /**
   * Called when connectivity status changes.
   *
   * @param isConnected Tri if connected or connecting.
   */
  protected void onConnectivityChanged(boolean isConnected) {
    Timber.d("onConnectivityChanged(): isConnected: " + isConnected);
  }

  /* Nested Classes *******************************************************************************/

  //  /**
  //   * Listens to connectivity changes.
  //   */
  //  private class NetworkStateReceiver extends BroadcastReceiver {
  //    @Override
  //    public void onReceive(Context context, Intent intent) {
  //      ConnectivityManager manager = (ConnectivityManager)
  //        getSystemService(Context.CONNECTIVITY_SERVICE);
  //      NetworkInfo info = manager.getActiveNetworkInfo();
  //      if (info != null) {
  //        onConnectivityChanged(info.isConnectedOrConnecting());
  //      }
  //    }
  //  }
}
