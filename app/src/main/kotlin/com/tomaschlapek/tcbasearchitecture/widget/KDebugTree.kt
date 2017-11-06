package com.tomaschlapek.tcbasearchitecture.widget

import android.util.Log
import timber.log.Timber

/**
 * Crashlytics Debug tree.
 */
class KDebugTree : Timber.DebugTree() {

  override fun isLoggable(priority: Int): Boolean {
    return priority >= Log.VERBOSE
  }

  override fun createStackElementTag(element: StackTraceElement): String {
    return super.createStackElementTag(element) + ":" + element.lineNumber
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    when (priority) {
      Log.VERBOSE -> Log.v(tag, message, t)
      Log.DEBUG -> Log.d(tag, message, t)
      Log.INFO -> Log.i(tag, message, t)
      Log.WARN -> Log.w(tag, message, t)
      Log.ERROR -> Log.e(tag, message, t)
    }
  }
}