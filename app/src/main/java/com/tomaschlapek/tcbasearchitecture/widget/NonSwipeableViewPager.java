package com.tomaschlapek.tcbasearchitecture.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.support.v4.view.ViewPager;

/**
 * Created by tomaschlapek on 15/2/17.
 */

public class NonSwipeableViewPager extends ViewPager {

  private boolean mSwipeable;

  /* Public Methods *******************************************************************************/

  /**
   * @see ViewPager#ViewPager(Context)
   */
  public NonSwipeableViewPager(Context context) {
    super(context);
  }

  /**
   * @see ViewPager#ViewPager(Context, AttributeSet)
   */
  public NonSwipeableViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * @see ViewPager#onInterceptTouchEvent(MotionEvent)
   */
  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    // Never allow swiping to switch between pages.
    return mSwipeable;
  }

  /**
   * @see ViewPager#onTouchEvent(MotionEvent)
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // Never allow swiping to switch between pages.
    return mSwipeable;
  }

  public boolean isSwipeable() {
    return mSwipeable;
  }

  public void setSwipeable(boolean swipeable) {
    mSwipeable = swipeable;
  }

}
