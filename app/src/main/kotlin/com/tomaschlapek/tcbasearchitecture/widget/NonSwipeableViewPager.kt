package com.tomaschlapek.tcbasearchitecture.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * view pager with disabled swiping.
 */
class NonSwipeableViewPager : ViewPager {

  var isSwipeable: Boolean = false

  /* Public Methods *******************************************************************************/

  /**
   * @see ViewPager.ViewPager
   */
  constructor(context: Context) : super(context) {}

  /**
   * @see ViewPager.ViewPager
   */
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * @see ViewPager.onInterceptTouchEvent
   */
  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    // Never allow swiping to switch between pages.
    return isSwipeable
  }

  /**
   * @see ViewPager.onTouchEvent
   */
  override fun onTouchEvent(event: MotionEvent): Boolean {
    // Never allow swiping to switch between pages.
    return isSwipeable
  }

}