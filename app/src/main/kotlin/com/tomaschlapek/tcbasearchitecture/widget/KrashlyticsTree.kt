package com.tomaschlapek.tcbasearchitecture.widget

import android.util.Log
import timber.log.Timber

/**
 * [Timber.Tree] class used for logging to Crashlytics.
 */
class KrashlyticsTree : Timber.DebugTree() {

  override fun isLoggable(priority: Int): Boolean {
    return priority >= Log.WARN
  }

  override fun createStackElementTag(element: StackTraceElement): String {
    return super.createStackElementTag(element) + ":" + element.lineNumber
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    // TODO Uncomment line below after Crashlytics implementation
    // TODO Crashlytics.log(priority, tag, message);
  }
}