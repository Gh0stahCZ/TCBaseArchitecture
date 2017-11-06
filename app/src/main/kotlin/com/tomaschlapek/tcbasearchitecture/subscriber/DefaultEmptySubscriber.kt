package com.tomaschlapek.tcbasearchitecture.subscriber

import com.tomaschlapek.tcbasearchitecture.domain.model.GeneralError
import com.tomaschlapek.tcbasearchitecture.util.getGeneralError
import retrofit2.Response

/**
 * Default Subscriber
 */
abstract class DefaultEmptySubscriber<T : Response<Void>> : rx.Subscriber<T>() {
  override fun onCompleted() {
    // no-op by default.
  }

  override fun onError(e: Throwable) {
    onNextError(GeneralError())
  }

  override fun onNext(t: T?) {

    t?.let {

      if (it.isSuccessful) {
        onNextSuccess()
      } else {
        onNextError(it.errorBody()?.getGeneralError() ?: GeneralError())
      }

    } ?: onNextError(GeneralError())

  }

  abstract fun onNextSuccess()
  abstract fun onNextError(error: GeneralError)
}