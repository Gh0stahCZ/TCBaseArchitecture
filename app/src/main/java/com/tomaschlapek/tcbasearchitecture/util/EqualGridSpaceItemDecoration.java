package com.tomaschlapek.tcbasearchitecture.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Item decoration with equal space between items.
 * Designed for GridLayoutManager.
 */
public class EqualGridSpaceItemDecoration extends RecyclerView.ItemDecoration {

  private int spanCount;
  private int spacing;

  public EqualGridSpaceItemDecoration(int spanCount, int spacing) {
    this.spanCount = spanCount;
    this.spacing = spacing;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
    RecyclerView.State state) {

    StaggeredGridLayoutManager.LayoutParams layoutParams =
      (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

    if (layoutParams.isFullSpan()) {
      outRect.set(0, 0, 0, 0);
    } else {
      int spanIndex = layoutParams.getSpanIndex();
      int layoutPosition = layoutParams.getViewLayoutPosition();
      int itemCount = parent.getAdapter().getItemCount();

      boolean leftEdge = spanIndex == 0;
      boolean rightEdge = spanIndex == (spanCount - 1);

      boolean topEdge = spanIndex < spanCount;
      boolean bottomEdge = layoutPosition >= (itemCount - spanCount);

      int halfSpacing = spacing / 2;

      outRect.set(
        leftEdge ? spacing : halfSpacing,
        topEdge ? spacing : halfSpacing,
        rightEdge ? spacing : halfSpacing,
        bottomEdge ? spacing : 0
      );
    }
  }
}
