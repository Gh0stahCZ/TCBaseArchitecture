package com.tomaschlapek.tcbasearchitecture.util;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by tomaschlapek on 15/9/17.
 */

public class DebugTree extends Timber.DebugTree {

  @Override
  protected boolean isLoggable(int priority) {
    return (priority >= Log.VERBOSE);
  }

  @Override
  protected String createStackElementTag(StackTraceElement element) {
    return super.createStackElementTag(element) + ":" + element.getLineNumber();
  }

  @Override
  protected void log(int priority, String tag, String message, Throwable t) {
    switch (priority) {
      case Log.VERBOSE:
        Log.v(tag, message, t);
        break;
      case Log.DEBUG:
        Log.d(tag, message, t);
        break;
      case Log.INFO:
        Log.i(tag, message, t);
        break;
      case Log.WARN:
        Log.w(tag, message, t);
        break;
      case Log.ERROR:
        Log.e(tag, message, t);
        break;
    }
  }
}
