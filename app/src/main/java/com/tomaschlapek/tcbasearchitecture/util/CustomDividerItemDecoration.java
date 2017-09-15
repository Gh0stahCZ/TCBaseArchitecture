package com.tomaschlapek.tcbasearchitecture.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;

/**
 * Item decoration with custom divider.
 * Possible define left padding.
 */
public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {

  public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
  public static final int VERTICAL = LinearLayout.VERTICAL;

  private static final int[] ATTRS = new int[] { android.R.attr.listDivider };

  private Drawable mDivider;
  private int mPaddingLeft;

  /**
   * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
   */
  private int mOrientation;

  private final Rect mBounds = new Rect();

  /**
   * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
   * {@link LinearLayoutManager}.
   *
   * @param context Current context, it will be used to access resources.
   * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
   */
  public CustomDividerItemDecoration(Context context, int orientation) {
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    mDivider = a.getDrawable(0);
    mPaddingLeft = 0;
    a.recycle();
    setOrientation(orientation);
  }

  /**
   * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
   * {@link LinearLayoutManager}.
   *
   * @param context Current context, it will be used to access resources.
   * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
   */
  public CustomDividerItemDecoration(Context context, int orientation, int paddingLeft) {
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    mDivider = a.getDrawable(0);
    mPaddingLeft = paddingLeft;
    a.recycle();
    setOrientation(orientation);
  }

  /**
   * Sets the orientation for this divider. This should be called if
   * {@link RecyclerView.LayoutManager} changes orientation.
   *
   * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
   */
  public void setOrientation(int orientation) {
    if (orientation != HORIZONTAL && orientation != VERTICAL) {
      throw new IllegalArgumentException(
        "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
    }
    mOrientation = orientation;
  }

  /**
   * Sets the {@link Drawable} for this divider.
   *
   * @param drawable Drawable that should be used as a divider.
   */
  public void setDrawable(@NonNull Drawable drawable) {
    if (drawable == null) {
      throw new IllegalArgumentException("Drawable cannot be null.");
    }
    mDivider = drawable;
  }

  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    if (parent.getLayoutManager() == null) {
      return;
    }
    if (mOrientation == VERTICAL) {
      drawVertical(c, parent);
    } else {
      drawHorizontal(c, parent);
    }
  }

  @SuppressLint("NewApi")
  private void drawVertical(Canvas canvas, RecyclerView parent) {
    canvas.save();
    final int leftLast;
    final int left;
    final int right;
    if (parent.getClipToPadding()) {
      leftLast = parent.getPaddingLeft();
      left = parent.getPaddingLeft() + mPaddingLeft;
      right = parent.getWidth() - parent.getPaddingRight();
      canvas.clipRect(left, parent.getPaddingTop(), right,
        parent.getHeight() - parent.getPaddingBottom());
    } else {
      leftLast = 0;
      left = 0 + mPaddingLeft;
      right = parent.getWidth();
    }

    final int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      parent.getDecoratedBoundsWithMargins(child, mBounds);
      final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
      final int top = bottom - mDivider.getIntrinsicHeight();

      if (i == childCount - 1) {
        mDivider.setBounds(leftLast, top, right, bottom);
      } else {
        mDivider.setBounds(left, top, right, bottom);
      }
      mDivider.draw(canvas);
    }
    canvas.restore();
  }

  @SuppressLint("NewApi")
  private void drawHorizontal(Canvas canvas, RecyclerView parent) {
    canvas.save();
    final int top;
    final int bottom;
    if (parent.getClipToPadding()) {
      top = parent.getPaddingTop();
      bottom = parent.getHeight() - parent.getPaddingBottom();
      canvas.clipRect(parent.getPaddingLeft(), top,
        parent.getWidth() - parent.getPaddingRight(), bottom);
    } else {
      top = 0;
      bottom = parent.getHeight();
    }

    final int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
      final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
      final int left = right - mDivider.getIntrinsicWidth();
      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(canvas);
    }
    canvas.restore();
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
    RecyclerView.State state) {
    if (mOrientation == VERTICAL) {
      outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    } else {
      outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
    }
  }
}
