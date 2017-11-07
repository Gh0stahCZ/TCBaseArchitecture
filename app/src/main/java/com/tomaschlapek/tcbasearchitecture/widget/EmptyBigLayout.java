package com.tomaschlapek.tcbasearchitecture.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import android.support.annotation.DrawableRes;

import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.databinding.WidgetEmptyBigLayoutBinding;

/**
 * Empty layout with title, subtitle and button
 */
public class EmptyBigLayout extends FrameLayout {

  private WidgetEmptyBigLayoutBinding mViews;

  private String mEmptyTitle;
  private String mEmptySubtitle;
  private String mEmptyButtonText;
  //  private boolean mEmptyFabShown;

  @DrawableRes
  private int mEmptyImageRes;

  public EmptyBigLayout(Context context) {
    super(context);
    init();
  }

  public EmptyBigLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
    manageAttributes(attrs);
  }

  public EmptyBigLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
    manageAttributes(attrs);
  }

  /* Private Methods ******************************************************************************/

  private void manageAttributes(AttributeSet attr) {

    TypedArray attributes = getContext().obtainStyledAttributes(attr, R.styleable.EmptyBigLayout);

    try {
      setEmptyTitle(attributes.getString(R.styleable.EmptyBigLayout_emptyBigTitle));
      setEmptySubtitle(attributes.getString(R.styleable.EmptyBigLayout_emptyBigSubtitle));
      setEmptyButtonText(attributes.getString(R.styleable.EmptyBigLayout_emptyBigButtonText));
      setEmptyImageRes(
        attributes.getResourceId(R.styleable.EmptyBigLayout_emptyBigImage, R.drawable.ic_empty));
    } finally {
      attributes.recycle();
    }
  }

  private void init() {

    // Inflate the widget layout.
    LayoutInflater inflater = LayoutInflater.from(getContext());

    mViews = DataBindingUtil
      .inflate(inflater, R.layout.widget_empty_big_layout, this, true);
  }

  public String getEmptyTitle() {
    return mEmptyTitle;
  }

  public void setEmptyTitleRes(int emptyTitleResId) {
    mEmptyTitle = getContext().getResources().getString(emptyTitleResId);

    if (!TextUtils.isEmpty(mEmptyTitle)) {
      mViews.emptyTitle.setText(mEmptyTitle);
    }
  }

  public void setEmptyTitle(String emptyTitle) {
    mEmptyTitle = emptyTitle;

    if (!TextUtils.isEmpty(mEmptyTitle)) {
      mViews.emptyTitle.setText(mEmptyTitle);
    }
  }

  public String getEmptySubtitle() {
    return mEmptySubtitle;
  }

  public void setEmptySubtitle(String emptyTitle) {
    mEmptySubtitle = emptyTitle;

    if (!TextUtils.isEmpty(mEmptySubtitle)) {
      mViews.emptySubtitle.setText(mEmptySubtitle);
    }
  }

  public void setEmptySubtitleRes(int emptyTitleResId) {
    mEmptySubtitle = getContext().getResources().getString(emptyTitleResId);

    if (!TextUtils.isEmpty(mEmptySubtitle)) {
      mViews.emptySubtitle.setText(mEmptySubtitle);
    }
  }

  public int getEmptyImageRes() {
    return mEmptyImageRes;
  }

  public void setEmptyImageRes(int emptyImageRes) {
    mEmptyImageRes = emptyImageRes;

    mViews.emptyImage.setImageResource(emptyImageRes);
  }

  public String getEmptyButtonText() {
    return mEmptyButtonText;
  }

  public void setEmptyButtonText(String emptyButtonText) {
    mEmptyButtonText = emptyButtonText;

    if (!TextUtils.isEmpty(mEmptyButtonText)) {
      mViews.emptyButton.setVisibility(View.VISIBLE);
      mViews.emptyButton.setText(mEmptyButtonText);
    } else {
      mViews.emptyButton.setVisibility(View.GONE);
    }
  }

  public void setEmptyButtonTextRes(int emptyButtonTextResId) {
    mEmptyButtonText = getContext().getResources().getString(emptyButtonTextResId);

    if (!TextUtils.isEmpty(mEmptyButtonText)) {
      mViews.emptyButton.setVisibility(View.VISIBLE);
      mViews.emptyButton.setText(mEmptyButtonText);
    } else {
      mViews.emptyButton.setVisibility(View.GONE);
    }
  }

  public void setOnEmptyButtonClick(OnClickListener listener) {
    mViews.emptyButton.setOnClickListener(listener);
  }
}

