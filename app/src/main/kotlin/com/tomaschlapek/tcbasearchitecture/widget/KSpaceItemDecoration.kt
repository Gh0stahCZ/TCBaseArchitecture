package com.tomaschlapek.tcbasearchitecture.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.view.View

/**
 * Item decoration with equal space between items.
 * Designed for LinearLayoutManager.
 */
class KSpaceItemDecoration(space: Int) : ItemDecoration() {
  private val mHalfSpace = space / 2

  /**
   * @see ItemDecoration.getItemOffsets
   */
  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
    // Setup padding of the recycler view.
    if (parent.paddingLeft != mHalfSpace) {
      parent.setPadding(mHalfSpace, mHalfSpace, mHalfSpace, mHalfSpace)
      parent.clipToPadding = false
    }
    // Set padding of the item.
    outRect.left = mHalfSpace
    outRect.right = mHalfSpace
    outRect.top = mHalfSpace
    outRect.bottom = mHalfSpace
  }
}
