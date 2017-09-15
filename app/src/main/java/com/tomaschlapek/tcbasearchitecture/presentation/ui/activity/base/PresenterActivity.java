package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.BasePresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IBaseView;

import eu.inloop.viewmodel.ViewModelHelper;

/**
 * Base class for all application activities that use view-model concept.
 */
public abstract class PresenterActivity<TView extends IBaseView, TViewModel extends
  BasePresenter<TView>> extends PresenterEmptyActivity implements IBaseView {

   /* Private Attributes ***************************************************************************/

  /**
   * View-model helper.
   */
  private ViewModelHelper<TView, TViewModel> mPresenterHelper = new ViewModelHelper<>();

  /* Public Abstract Methods **********************************************************************/

  /**
   * Returns class of view-model coupled with this activity.
   */
  public abstract Class<TViewModel> getPresenterClass();

  /* Public Methods *******************************************************************************/

  /**
   * Returns the activity view model.
   *
   * @return Activity view model.
   */
  public TViewModel getPresenter() {
    return mPresenterHelper.getViewModel();
  }

  /**
   * Call this after your view is ready - usually on the end of
   * {@link FragmentActivity#onCreate(Bundle)}.
   *
   * @param view The view-model view.
   */
  public void setPresenter(@NonNull TView view) {
    mPresenterHelper.setView(view);
  }

  /* Protected Methods ****************************************************************************/

  /**
   * @see FragmentActivity#onCreate(Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  protected void onCreatePresenter(Bundle savedInstanceState) {
    mPresenterHelper.onCreate(this, savedInstanceState,
      getPresenterClass(), getIntent().getExtras());
    setPresenter((TView) this);
  }

  /**
   * @see FragmentActivity#onPostCreate(Bundle)
   */
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  /**
   * @see FragmentActivity#onStart()
   */
  @Override
  protected void onStart() {
    super.onStart();
    mPresenterHelper.onStart();
  }

  /**
   * @see FragmentActivity#onStop()
   */
  @Override
  protected void onStop() {
    super.onStop();
    mPresenterHelper.onStop();
  }

  /**
   * @see FragmentActivity#onDestroy()
   */
  @Override
  protected void onDestroy() {
    mPresenterHelper.onDestroy(this);
    super.onDestroy();
  }

  /**
   * @see FragmentActivity#onSaveInstanceState(Bundle)
   */
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mPresenterHelper.onSaveInstanceState(outState);
  }

  @Override
  public void openShareDialog(@NonNull String text) {

    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.lib_app_name));
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.lib_app_name));
    shareIntent.putExtra(Intent.EXTRA_TEXT, text);

    startActivity(Intent.createChooser(shareIntent, null));
  }
}
