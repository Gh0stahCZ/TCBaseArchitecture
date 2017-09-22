package com.tomaschlapek.tcbasearchitecture.presentation.presenter.base;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.tomaschlapek.tcbasearchitecture.domain.executor.MainThread;
import com.tomaschlapek.tcbasearchitecture.domain.executor.impl.ThreadExecutor;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.PresenterActivity;
import com.tomaschlapek.tcbasearchitecture.threading.MainThreadImpl;

import io.realm.Realm;

import eu.inloop.viewmodel.AbstractViewModel;
import eu.inloop.viewmodel.IView;
import timber.log.Timber;

/**
 * Base class for view models of {@link PresenterActivity}
 */
public abstract class ActivityPresenter<TView extends KIBaseView> extends BasePresenter<TView> {

  /* Protected Attributes *************************************************************************/

  /**
   * Link to view (FragmentActivity).
   */
  protected FragmentActivity mActivity;

  /**
   * Holds default instance of realm.
   */
  protected Realm mRealm;

  protected MainThread mMainThread;
  protected ThreadExecutor mThreadExecutor;

  private Bundle mState;

  /* Public Methods *******************************************************************************/

  @Override
  public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
    super.onCreate(arguments, savedInstanceState);
    mMainThread = MainThreadImpl.getInstance();
    mThreadExecutor = ThreadExecutor.getInstance();

    mState = (savedInstanceState != null) ? savedInstanceState : arguments;
  }

  /**
   * @see AbstractViewModel#onBindView(IView)
   */
  @Override
  public void onBindView(@NonNull TView view) {
    super.onBindView(view);

    // Remember the link to activity.
    mActivity = (FragmentActivity) view;

    mRealm = Realm.getDefaultInstance();

    if (mState != null) {
      loadArguments(mState);
    }
  }

  @Override
  public void clearView() {
    super.clearView();

    // Close the default realm instance.
    if ((mRealm != null) && !mRealm.isClosed()) {
      mRealm.close();
      mRealm = null;
    }

  }

  public void onNewIntent(Intent intent) {
    Timber.d("onNewIntent() : " + intent);
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle bundle) {
    super.onSaveInstanceState(bundle);
  }

  protected boolean isUserLogged() {
    return false; // TODO Add proper decision.
  }

  /**
   * Loads arguments from SavedInstance or passed bundle.
   *
   * @param state Bundle with data.
   */
  private void loadArguments(Bundle state) {
    // Load arguments.
    if (state != null) {
      //      if (state.containsKey(Argument.GAME_ID)) {
      //        mGameId = state.getString(Argument.GAME_ID);
      //      }
    }
  }
}
