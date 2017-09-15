package com.tomaschlapek.tcbasearchitecture.util;

/**
 * Constants used across the app.
 */

public class Constants {


  public static final int SIZE_MEGABYTE = 1024 * 1024;

  /**
   * Size of cache used for network requests.
   */
  public static final int CACHE_SIZE = 10 * SIZE_MEGABYTE;

  /**
   * Date format for JSON.
   */
  public static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

  /**
   * Code used in Google Sign In.
   */
  public static final int RC_SIGN_IN = 9009;
}
