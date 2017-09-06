package com.tomaschlapek.tcbasearchitecture.widget;

import com.tomaschlapek.tcbasearchitecture.util.MiscMethods;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Custom network interceptor.
 */

public class NetworkInterceptor implements Interceptor {

  private static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
  private static final String HEADER_CONTENT_TYPE_VALUE = "application/json";

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request =
      chain
        .request()
        .newBuilder()
        .addHeader(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE)
        .build();

    Timber.d("Request: [%s] %s", request.method(), request.url());
    Timber.d("Request: %s", request.headers().toString());
    if (request.body() != null) {
      Timber.d("Request: %s", MiscMethods.bodyToString(request.body()));
    }
    return chain.proceed(request);
  }
}
