package com.tomaschlapek.fancyonboarding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.tomaschlapek.fancyonboarding.databinding.PageOnboardingBinding;
import com.tomaschlapek.fancyonboarding.interfaces.OnOnboardingCompletedListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.ViewCompat.LAYER_TYPE_HARDWARE;
import static android.view.View.FOCUS_RIGHT;

public class OnboardingPageAdapter extends PagerAdapter implements ViewPager.PageTransformer {

  /* Private Constants ****************************************************************************/

  private static final int OFFSET_PAGE_LIMIT = 3;

  /* Private Attributes ***************************************************************************/

  private Context mContext;
  private ViewPager mViewPager;
  private List<OnBoardingPage> mOnBoardingPageList;
  private int mFinishedPages = 0; // Initial page.
  private OnOnboardingCompletedListener mOnOnboardingCompletedListener;

  /* Constructor **********************************************************************************/

  public OnboardingPageAdapter(Context context, ViewPager pager,
    OnOnboardingCompletedListener onOnboardingCompletedListener) {
    mContext = context;
    mViewPager = pager;
    mViewPager.setPageTransformer(false, this, LAYER_TYPE_HARDWARE);
    mViewPager.setOffscreenPageLimit(OFFSET_PAGE_LIMIT);
    mOnBoardingPageList = new ArrayList<>();
    mOnOnboardingCompletedListener = onOnboardingCompletedListener;
  }

  /* Public Methods *******************************************************************************/

  // Returns total number of pages
  @Override
  public int getCount() {
    return Math.min((mFinishedPages + 1), mOnBoardingPageList.size());
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view.getTag() != null && view.getTag()
      .equals(((ViewDataBinding) object).getRoot().getTag());
  }

  @Override
  public Object instantiateItem(ViewGroup container, final int position) {

    LayoutInflater inflater = LayoutInflater.from(mContext);

    final OnBoardingPage page = mOnBoardingPageList.get(position);
    int resId = R.layout.page_onboarding;

    PageOnboardingBinding binding =
      DataBindingUtil.inflate(inflater, resId, null, false);

    binding.setPage(page);

    final OnClickListener nextPageClickListener = new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (position < mOnBoardingPageList.size() - 1) {
          mViewPager.arrowScroll(FOCUS_RIGHT);
          //        mViewPager.setCurrentItem(position + 1, true);
        } else {
          mOnOnboardingCompletedListener.onboardingCompleted();
        }
      }
    };

    binding.continueButton.setOnClickListener(nextPageClickListener);

    if (page.hasPermission()) {
      binding.positiveButton.setOnClickListener(

        new OnClickListener() {
          @Override
          public void onClick(View v) {
            setFinishedPages(position + 1); // release next page

            if (page.getPermissionRequestedListener() != null) {
              page.getPermissionRequestedListener().permissionRequested();
            } else {
              nextPageClickListener.onClick(v);
            }
          }
        });

      binding.negativeButton.setOnClickListener(

        new OnClickListener() {
          @Override
          public void onClick(View v) {
            setFinishedPages(position + 1); // release next page
            nextPageClickListener.onClick(v);
          }
        });
    }

    if (!page.hasPermission()) { // disable swipe on permission pages
      setFinishedPages(position + 1); // release next page
    }

    binding.executePendingBindings();

    View rootView = binding.getRoot();

    rootView.setTag(page.getId());
    container.addView(rootView);
    return binding;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(((ViewDataBinding) object).getRoot());
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return "Page " + position;
  }

  @Override
  public void transformPage(View page, float position) {
    int pageWidth = page.getWidth();

    if (position < -1) { // [-Infinity,-1)
      page.setAlpha(0);
    } else if (position <= 1) { // [-1,1]
      page.setAlpha(1);

      View titleView = page.findViewById(R.id.title_text_view);
      View messageView = page.findViewById(R.id.message_text_view);
      View backgroundImageView = page.findViewById(R.id.background_image_view);

      if (titleView != null) {
        titleView.setTranslationX((float) ((position) * 0.8 * (pageWidth)));
      }

      if (messageView != null) {
        messageView.setTranslationX((float) ((position) * 0.5 * (pageWidth)));
      }

      if (backgroundImageView != null && backgroundImageView.getVisibility() == View.VISIBLE) {
        backgroundImageView.setTranslationX((float) ((position) * 0.5 * (pageWidth)));
      }
    } else { // (1,+Infinity]
      page.setAlpha(0);
    }
  }

  /* Getters / Setters ****************************************************************************/

  public void setOnBoardingPageList(List<OnBoardingPage> onBoardingPageList) {
    mOnBoardingPageList = onBoardingPageList;
    notifyDataSetChanged();
  }

  public void onPageSelected(int position) {
    OnBoardingPage page = mOnBoardingPageList.get(position);
    mViewPager.requestLayout();
  }

  private void setFinishedPages(int finishedPages) {
    if (finishedPages > mFinishedPages) {
      mFinishedPages = finishedPages;
      notifyDataSetChanged();
    }
  }

  public void setOnOnboardingCompletedListener(
    OnOnboardingCompletedListener onOnboardingCompletedListener) {
    mOnOnboardingCompletedListener = onOnboardingCompletedListener;
  }
}

