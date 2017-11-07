package com.tomaschlapek.tcbasearchitecture.util

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Custom network interceptor.
 */

class NetworkInterceptor : Interceptor {

  companion object {
    private val HEADER_CONTENT_TYPE_KEY = "Content-Type"
    private val HEADER_CONTENT_TYPE_VALUE = "application/json"
  }

  /* Public Methods *******************************************************************************/

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain
      .request()
      .newBuilder()
      .addHeader(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE)
      .build()

    Timber.d("Request: [%s] %s", request.method(), request.url())
    Timber.d("Request: %s", request.headers().toString())
    if (request.body() != null) {
      Timber.d("Request: %s", request.body()?.bodyToString())
    }

    // TODO Uncomment this line for slow connection simulation
    // expensiveOperation()

    return chain.proceed(request)
  }

  /* Private Methods ******************************************************************************/

  /**
   * Simulates time-expensive operation.
   */
  private fun expensiveOperation() {
    try {
      Thread.sleep(8000)
    } catch (e: InterruptedException) {
      // this is ok
    }

  }
}
