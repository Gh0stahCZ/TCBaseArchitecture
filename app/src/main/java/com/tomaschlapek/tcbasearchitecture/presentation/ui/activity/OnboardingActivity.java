package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.tomaschlapek.fancyonboarding.OnboardingPageAdapter;
import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.databinding.ActivityOnboardingBinding;
import com.tomaschlapek.tcbasearchitecture.helper.NavigationHelper;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.OnboardingPresenterImpl;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IOnboardingActivityView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.BaseActivity;

import timber.log.Timber;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * Created by tomaschlapek on 18/5/17.
 */

public class OnboardingActivity
  extends BaseActivity<IOnboardingActivityView, OnboardingPresenterImpl>
  implements IOnboardingActivityView {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  /**
   * Data binding.
   */
  ActivityOnboardingBinding mViews;

  private OnboardingPageAdapter mOnboardingPageAdapter;


  /* Constructor **********************************************************************************/
  /* Public Methods *******************************************************************************/

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViews = DataBindingUtil
      .inflate(getLayoutInflater(), R.layout.activity_onboarding, getContentContainer(), true);

    onCreatePresenter(savedInstanceState);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    init();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public Class getPresenterClass() {
    return OnboardingPresenterImpl.class;
  }

  @Override
  public void onPermissionRequest() {
    Toast.makeText(this, App.getResString(R.string.sample_toast_permission_request),
      Toast.LENGTH_SHORT).show();
  }

  /* Private Methods ******************************************************************************/

  private void init() {

    mViews.indicator.setViewPager(mViews.onboardingViewpager);

    mOnboardingPageAdapter = new OnboardingPageAdapter(this,
      mViews.onboardingViewpager,
      () -> onOnboardingFinish());

    mViews.onboardingViewpager.setAdapter(mOnboardingPageAdapter);

    mViews.onboardingViewpager.setOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageSelected(int position) {
        mOnboardingPageAdapter.onPageSelected(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {
        Timber.i("onPageScrollStateChanged(): " + state);
        if (state == SCROLL_STATE_IDLE) {
          for (int i = 0; i < mViews.onboardingViewpager.getChildCount(); i++) {
            mViews.onboardingViewpager.getChildAt(i).requestLayout();
            mViews.onboardingViewpager.getChildAt(i).invalidate();
          }
        }
      }
    });

    mOnboardingPageAdapter.setOnBoardingPageList(getPresenter().createPageList());
    addListener(mViews.onboardingViewpager);
  }

  private void onOnboardingFinish() {
    mPreferenceHelper.setFirstRun(false);
    NavigationHelper.openSignInActivity(this, false);
    finish();
  }

  private void addListener(ViewGroup targetView) {
    OnGlobalLayoutListener backgroundCityListener = new OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        OnGlobalLayoutListener victim = this;

        mOnboardingPageAdapter.onPageSelected(0);

        removeListener(targetView, victim);
      }
    };

    if (targetView.getViewTreeObserver().isAlive()) {
      targetView.getViewTreeObserver().addOnGlobalLayoutListener(backgroundCityListener);
    }
  }

  private void removeListener(View targetView, OnGlobalLayoutListener victim) {
    Timber.d("removeListener()");

    ViewTreeObserver treeObserver = targetView.getViewTreeObserver();

    if (treeObserver != null && targetView.getViewTreeObserver().isAlive() && victim != null) {

      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
        treeObserver.removeGlobalOnLayoutListener(victim);
      } else {
        treeObserver.removeOnGlobalLayoutListener(victim);
      }
    }
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}
