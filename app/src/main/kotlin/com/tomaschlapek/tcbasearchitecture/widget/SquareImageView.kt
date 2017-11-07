package com.tomaschlapek.tcbasearchitecture.widget

import android.content.Context
import android.util.AttributeSet


class SquareImageView : android.support.v7.widget.AppCompatImageView {

  constructor(context: Context) : super(context) {}

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    setMeasuredDimension(measuredWidth, measuredWidth) //Snap to width
  }
}
