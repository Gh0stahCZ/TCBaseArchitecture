package com.tomaschlapek.tcbasearchitecture.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View


/**
 * Item decoration with equal space between items.
 * Designed for GridLayoutManager.
 */
class KEqualGridSpaceItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
    state: RecyclerView.State?) {

    val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams

    if (layoutParams.isFullSpan) {
      outRect.set(0, 0, 0, 0)
    } else {
      val spanIndex = layoutParams.spanIndex
      val layoutPosition = layoutParams.viewLayoutPosition
      val itemCount = parent.adapter.itemCount

      val leftEdge = spanIndex == 0
      val rightEdge = spanIndex == spanCount - 1

      val topEdge = spanIndex < spanCount
      val bottomEdge = layoutPosition >= itemCount - spanCount

      val halfSpacing = spacing / 2

      outRect.set(
        if (leftEdge) spacing else halfSpacing,
        if (topEdge) spacing else halfSpacing,
        if (rightEdge) spacing else halfSpacing,
        if (bottomEdge) spacing else 0
      )
    }
  }
}
