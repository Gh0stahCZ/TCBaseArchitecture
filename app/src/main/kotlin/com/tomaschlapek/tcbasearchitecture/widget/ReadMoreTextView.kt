package com.tomaschlapek.tcbasearchitecture.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.tomaschlapek.tcbasearchitecture.R

/**
 * Expandable TextView.
 */
class ReadMoreTextView : android.support.v7.widget.AppCompatTextView, View.OnClickListener {

  companion object {
    private val MAX_LINES = 3
  }

  /* Custom method because standard getMaxLines() requires API > 16 */
  var myMaxLines = Integer.MAX_VALUE
    private set

  constructor(context: Context) : super(context) {
    setOnClickListener(this)
  }

  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    setOnClickListener(this)
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    setOnClickListener(this)
  }

  override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
    /* If text longer than MAX_LINES set DrawableBottom - I'm using '...' icon */
    post {
      if (lineCount > MAX_LINES) {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_down)
      } else {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
      }

      maxLines = MAX_LINES
    }
  }

  override fun setMaxLines(maxLines: Int) {
    myMaxLines = maxLines
    super.setMaxLines(maxLines)
  }

  override fun onClick(v: View) {
    if (lineCount <= MAX_LINES) {
      return
    }

    /* Toggle between expanded collapsed states */
    if (myMaxLines == Integer.MAX_VALUE) {
      maxLines = MAX_LINES
      setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_down)
    } else {
      maxLines = Integer.MAX_VALUE
      setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_up)
    }
  }


}