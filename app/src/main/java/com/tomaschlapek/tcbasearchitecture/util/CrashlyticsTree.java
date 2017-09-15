package com.tomaschlapek.tcbasearchitecture.util;

import android.util.Log;

import timber.log.Timber;

/**
 * {@link Timber.Tree} class used for logging to Crashlytics.
 */
public class CrashlyticsTree extends Timber.DebugTree {

  @Override
  protected boolean isLoggable(int priority) {
    return (priority >= Log.WARN);
  }

  @Override
  protected String createStackElementTag(StackTraceElement element) {
    return super.createStackElementTag(element) + ":" + element.getLineNumber();
  }

  @Override
  protected void log(int priority, String tag, String message, Throwable t) {
    // TODO Uncomment line below after Crashlytics implementation
    // TODO Crashlytics.log(priority, tag, message);
  }
}
