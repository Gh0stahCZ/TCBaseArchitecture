package com.tomaschlapek.tcbasearchitecture.subscriber

import timber.log.Timber

/**
 * Default Subscriber
 */
abstract class LoadingSubscriber<Boolean> : rx.Subscriber<Boolean>() {
  override fun onCompleted() {
    // no-op by default.
  }

  override fun onError(e: Throwable) {
    Timber.e("Loading subscriber had an error : ${e.message}")
  }

  override fun onNext(t: Boolean) {
    when (t) {
      true -> onLoadingStarted()
      false -> onLoadingFinished()
    }
  }

  abstract fun onLoadingStarted()
  abstract fun onLoadingFinished()
}