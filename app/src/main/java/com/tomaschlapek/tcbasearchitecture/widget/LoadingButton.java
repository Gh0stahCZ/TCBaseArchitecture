package com.tomaschlapek.tcbasearchitecture.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import android.support.v4.content.ContextCompat;

import com.tomaschlapek.tcbasearchitecture.R;

/**
 * Two state button showing either text or animated gif.
 */
public class LoadingButton extends FrameLayout {

  /* Private Attributes ***************************************************************************/

  /**
   * Normal button.
   */
  private Button mLoadingButton;

  /**
   * Animation that shows up when loading.
   */
  private ProgressBar mLoadingProgressBar;

  /**
   * Holds the button text.
   */
  private CharSequence mText;

  /**
   * Holds the button background resource ID.
   */
  private int mButtonBackgroundResId;

  /**
   * Indicates if button is in loading state.
   */
  private boolean mLoading;

  /* Public Methods *******************************************************************************/

  /**
   * @see FrameLayout#FrameLayout(Context)
   */
  public LoadingButton(Context context) {
    super(context);
    setup(null);
  }

  /**
   * @see FrameLayout#FrameLayout(Context, AttributeSet)
   */
  public LoadingButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    setup(attrs);
  }

  /**
   * @see FrameLayout#FrameLayout(Context, AttributeSet, int)
   */
  public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setup(attrs);
  }

  /**
   * @see Button#isEnabled()
   */
  public boolean isEnabled() {
    return mLoadingButton.isEnabled();
  }

  /**
   * @see Button#setEnabled(boolean)
   */
  public void setEnabled(boolean enabled) {
    mLoadingButton.setEnabled(enabled);
  }

  /**
   * Returns the button text.
   */
  public CharSequence getText() {
    return mText;
  }

  /**
   * Sets the button text.
   */
  public void setText(CharSequence text) {
    mText = text;

    if (!mLoading) {
      mLoadingButton.setText(mText);
    }
  }

  /**
   * Returns the button background resource ID.
   */
  public int getButtonBackgroundResId() {
    return mButtonBackgroundResId;
  }

  /**
   * Sets the button color resource ID.
   */
  public void setButtonBackgroundResId(int resId) {
    mButtonBackgroundResId = resId;

    // Set button background.
    if (mButtonBackgroundResId != 1) {
      if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
        mLoadingButton.setBackground(ContextCompat.getDrawable(
          getContext(), mButtonBackgroundResId));
      } else {
        mLoadingButton.setBackgroundDrawable(ContextCompat.getDrawable(
          getContext(), mButtonBackgroundResId));
      }
    }
  }

  /**
   * Indicates if button is in loading state.
   */
  public boolean isLoading() {
    return mLoading;
  }

  /**
   * Enable od disable loading state.
   */
  public void setLoading(boolean loading) {
    if (mLoading == loading) {
      return;
    }

    mLoading = loading;

    if (mLoading) {
      mText = mLoadingButton.getText();
      mLoadingButton.setText(null);
      mLoadingButton.setClickable(false);
      mLoadingProgressBar.setVisibility(VISIBLE);
    } else {
      mLoadingProgressBar.setVisibility(GONE);
      mLoadingButton.setClickable(true);
      mLoadingButton.setText(mText);
    }
  }

  /**
   * @see Button#setOnClickListener(OnClickListener)
   */
  @Override
  public void setOnClickListener(OnClickListener l) {
    mLoadingButton.setOnClickListener(l);
    if (mLoading) {
      mLoadingButton.setClickable(false);
    }
  }

  /* Protected Methods ****************************************************************************/

  /**
   * @see FrameLayout#onAttachedToWindow()
   */
  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (VERSION.SDK_INT >= 21) {
      disableClipOnParents(mLoadingButton);
    }
  }

  /**
   * Initializes attributes.
   *
   * @param attrs Set of attributes.
   */
  protected void setup(AttributeSet attrs) {
    // Initialize attributes.
    mText = null;
    mButtonBackgroundResId = -1;
    mLoading = false;

    // Inflate the widget layout.
    LayoutInflater inflater = LayoutInflater.from(getContext());
    View view = inflater.inflate(R.layout.widget_loading_button, this, true);

    // Find widget views.
    mLoadingButton = (Button) view.findViewById(R.id.loading_button);
    mLoadingProgressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);

    // Get attributes if any.
    int gifResource = -1;
    if (attrs != null) {
      TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingButton);
      mText = attributes.getString(R.styleable.LoadingButton_text);
      mButtonBackgroundResId = attributes.getResourceId(R.styleable.LoadingButton_buttonBackground,
        R.drawable.button_background_default);
      gifResource = attributes.getResourceId(R.styleable.LoadingButton_gif, -1);
      attributes.recycle();
    }

    // Set text button text.
    mLoadingButton.setText(mText);

    // Set button background.
    if (mButtonBackgroundResId != 1) {
      if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
        mLoadingButton.setBackground(ContextCompat.getDrawable(
          getContext(), mButtonBackgroundResId));
      } else {
        mLoadingButton.setBackgroundDrawable(ContextCompat.getDrawable(
          getContext(), mButtonBackgroundResId));
      }
    }

    // Make the progress bar white.
    mLoadingProgressBar.getIndeterminateDrawable().mutate().
      setColorFilter(Color.WHITE, Mode.SRC_IN);

    // To be sure progress is above button.
    if (VERSION.SDK_INT >= 21) {
      mLoadingProgressBar.setElevation(Float.MAX_VALUE);
    }
  }

  /**
   * Disables clipping of children on all view parents.
   *
   * @param view View that wants to draw outside of bounds.
   */
  protected void disableClipOnParents(View view) {
    if (view instanceof ViewGroup) {
      ((ViewGroup) view).setClipChildren(false);
    }
    ViewParent parent = view.getParent();
    if ((parent == null) || (view == getParent())) {
      return;
    }
    if (parent instanceof View) {
      disableClipOnParents((View) parent);
    }
  }
}
