package com.tomaschlapek.tcbasearchitecture.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * Item decoration with equal space between items.
 * Designed for LinearLayoutManager.
 */
public class SpaceItemDecoration extends ItemDecoration {
  private int mHalfSpace;

  /**
   * Constructor.
   *
   * @param space In pixels.
   */
  public SpaceItemDecoration(int space) {
    mHalfSpace = space / 2;
  }

  /**
   * @see ItemDecoration#getItemOffsets(Rect, View, RecyclerView, State)
   */
  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
    // Setup padding of the recycler view.
    if (parent.getPaddingLeft() != mHalfSpace) {
      parent.setPadding(mHalfSpace, mHalfSpace, mHalfSpace, mHalfSpace);
      parent.setClipToPadding(false);
    }
    // Set padding of the item.
    outRect.left = mHalfSpace;
    outRect.right = mHalfSpace;
    outRect.top = mHalfSpace;
    outRect.bottom = mHalfSpace;
  }
}
