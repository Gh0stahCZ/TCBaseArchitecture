package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import eu.inloop.viewmodel.IViewModelProvider;
import eu.inloop.viewmodel.ViewModelProvider;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * All activities must extent this class even if they have no view model. The fragment view models
 * are using the {@link IViewModelProvider} interface to get the {@link ViewModelProvider} from the
 * current activity.
 */
public abstract class PresenterEmptyActivity extends AppCompatActivity
  implements IViewModelProvider {


  /* Protected Attributes *************************************************************************/

  protected Transformer<Object, Object> mSchedulersTransformer;

  @Inject
  public PreferenceHelper mPreferenceHelper;

  /* Private Attributes ***************************************************************************/

  /**
   * View-model provider.
   */
  private ViewModelProvider mPresenterProvider;

  /* Public Methods *******************************************************************************/

  /**
   * @see FragmentActivity#onRetainCustomNonConfigurationInstance()
   */
  @Override
  @Nullable
  public Object onRetainCustomNonConfigurationInstance() {
    return mPresenterProvider;
  }

  /**
   * @see IViewModelProvider#getViewModelProvider()
   */
  @Override
  public ViewModelProvider getViewModelProvider() {
    return mPresenterProvider;
  }

  /* Protected Methods ****************************************************************************/

  /**
   * @see FragmentActivity#onCreate(Bundle)
   */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {

    AndroidInjection.inject(this);

    super.onCreate(savedInstanceState);
    mPresenterProvider = ViewModelProvider.newInstance(this);

    mSchedulersTransformer = tObservable -> tObservable
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

// TODO   App.getAppComponent().inject(this);
  }

  /**
   * @see FragmentActivity#onStop()
   */
  @Override
  protected void onStop() {
    super.onStop();
    if (isFinishing()) {
      mPresenterProvider.removeAllViewModels();
    }
  }

  /**
   * Gets main content container.
   *
   * @return View group to inflate layouts.
   */
  public abstract ViewGroup getContentContainer();

  /**
   * Apply schedulers for setting observable on working thread from Thread pool and setting
   * subscriber on MainUiThread
   *
   * @param <T>
   *
   * @return Transformer with set observable.
   */
  protected <T> Transformer<T, T> applySchedulers() {
    return (Transformer<T, T>) mSchedulersTransformer;
  }
}