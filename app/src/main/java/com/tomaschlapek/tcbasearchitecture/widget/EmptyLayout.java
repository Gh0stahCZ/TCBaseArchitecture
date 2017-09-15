package com.tomaschlapek.tcbasearchitecture.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import android.support.annotation.DrawableRes;

import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.databinding.WidgetEmptyLayoutBinding;

public class EmptyLayout extends FrameLayout {

  private WidgetEmptyLayoutBinding mViews;

  private String mEmptyTitle;

  @DrawableRes
  private int mEmptyImageRes;

  public EmptyLayout(Context context) {
    super(context);
    init();
  }

  public EmptyLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
    manageAttributes(attrs);
  }

  public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
    manageAttributes(attrs);
  }

  /* Private Methods ******************************************************************************/

  private void manageAttributes(AttributeSet attr) {

    TypedArray attributes = getContext().obtainStyledAttributes(attr, R.styleable.EmptyLayout);

    try {
      setEmptyTitle(attributes.getString(R.styleable.EmptyLayout_emptyTitle));
      setEmptyImageRes(
        attributes.getResourceId(R.styleable.EmptyLayout_emptyImage, R.drawable.ic_empty));
    } finally {
      attributes.recycle();
    }
  }

  private void init() {

    // Inflate the widget layout.
    LayoutInflater inflater = LayoutInflater.from(getContext());

    mViews = DataBindingUtil
      .inflate(inflater, R.layout.widget_empty_layout, this, true);
  }

  public String getEmptyTitle() {
    return mEmptyTitle;
  }

  public void setEmptyTitle(String emptyTitle) {
    mEmptyTitle = emptyTitle;

    if (!TextUtils.isEmpty(mEmptyTitle)) {
      mViews.emptyTitle.setText(mEmptyTitle);
    }
  }

  public int getEmptyImageRes() {
    return mEmptyImageRes;
  }

  public void setEmptyImageRes(int emptyImageRes) {
    mEmptyImageRes = emptyImageRes;
    mViews.emptyImage.setImageResource(emptyImageRes);
  }
}
