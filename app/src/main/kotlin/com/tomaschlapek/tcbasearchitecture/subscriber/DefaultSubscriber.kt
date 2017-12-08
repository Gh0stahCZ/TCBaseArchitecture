package com.tomaschlapek.tcbasearchitecture.subscriber

import com.tomaschlapek.tcbasearchitecture.domain.model.GeneralError
import com.tomaschlapek.tcbasearchitecture.util.getGeneralError
import retrofit2.Response

/**
 * Default Subscriber
 */
abstract class DefaultSubscriber<T : Response<out Any>, in Q : Any> : rx.Subscriber<T>() {
  override fun onCompleted() {
    // no-op by default.
  }

  override fun onError(e: Throwable) {
    onNextError(GeneralError())
  }

  override fun onNext(t: T?) {

    t?.let {

      if (it.isSuccessful) {
        val resp = it.body() as? Q
        resp?.let {
          onNextSuccess(it)
        } ?: onNextError(GeneralError())

      } else {
        onNextError(it.errorBody()?.getGeneralError() ?: GeneralError())
      }

    } ?: onNextError(GeneralError())

  }

  abstract fun onNextSuccess(data: Q)
  abstract fun onNextError(error: GeneralError)
}