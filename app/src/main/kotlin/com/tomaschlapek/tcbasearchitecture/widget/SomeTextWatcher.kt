package com.tomaschlapek.tcbasearchitecture.widget

import android.text.Editable
import android.text.TextWatcher

/**
 * TextWatcher triggers events when new text is written or when there is no text in input.
 */
class SomeTextWatcher(val noText: () -> Unit, val someText: () -> Unit) : TextWatcher {

  override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    // TODO Auto-generated method stub
  }

  override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    // TODO Auto-generated method stub
  }

  override fun afterTextChanged(s: Editable) {
    if (s.isNullOrBlank()) {
      noText()
    } else {
      someText()
    }
  }
}