package com.tomaschlapek.tcbasearchitecture.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.tomaschlapek.tcbasearchitecture.R;

/**
 * Created by tomaschlapek on 26/7/17.
 */

public class ReadMoreTextView extends android.support.v7.widget.AppCompatTextView
  implements OnClickListener {

  private static final int MAX_LINES = 3;
  private int currentMaxLines = Integer.MAX_VALUE;

  public ReadMoreTextView(Context context) {
    super(context);
    setOnClickListener(this);
  }

  public ReadMoreTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setOnClickListener(this);
  }

  public ReadMoreTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setOnClickListener(this);
  }

  @Override
  protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        /* If text longer than MAX_LINES set DrawableBottom - I'm using '...' icon */
    post(() -> {
      if (getLineCount() > MAX_LINES) {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_down);
      } else {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
      }

      setMaxLines(MAX_LINES);
    });
  }

  @Override
  public void setMaxLines(int maxLines) {
    currentMaxLines = maxLines;
    super.setMaxLines(maxLines);
  }

  /* Custom method because standard getMaxLines() requires API > 16 */
  public int getMyMaxLines() {
    return currentMaxLines;
  }

  @Override
  public void onClick(View v) {
    if (getLineCount() <= MAX_LINES) {
      return;
    }

    /* Toggle between expanded collapsed states */
    if (getMyMaxLines() == Integer.MAX_VALUE) {
      setMaxLines(MAX_LINES);
      setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_down);
    } else {
      setMaxLines(Integer.MAX_VALUE);
      setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_up);
    }
  }
}

