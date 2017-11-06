package com.tomaschlapek.tcbasearchitecture.util;

import java.io.IOException;

import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Useful static methods.
 */
public class MiscMethods {

  /**
   * Converts network request body to String.
   *
   * @param request Request body.
   *
   * @return Output string.
   */
  public static String bodyToString(final RequestBody request) {
    try {
      final RequestBody copy = request;
      final Buffer buffer = new Buffer();
      copy.writeTo(buffer);
      return buffer.readUtf8();
    } catch (final IOException e) {
      return "Do not work :/";
    }
  }

  /**
   * Simulates time-expensive operation.
   */
  public static void expensiveOperation() {
    try {
      Thread.sleep(8000);
    } catch (InterruptedException e) {
      // this is ok
    }
  }
}