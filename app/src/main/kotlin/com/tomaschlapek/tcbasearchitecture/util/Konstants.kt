package com.tomaschlapek.tcbasearchitecture.util

/**
 * Created by tomaschlapek on 18/9/17.
 */
class Konstants {

  companion object {

    /**
     * Size of MB
     */
    @JvmStatic
    val SIZE_MEGABYTE = 1024 * 1024

    /**
     * Size of cache used for network requests.
     */
    @JvmStatic
    val CACHE_SIZE = 10 * SIZE_MEGABYTE

    /**
     * Date format for JSON.
     */
    @JvmStatic
    val JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    /**
     * Code used in Google Sign In.
     */
    @JvmStatic
    val RC_SIGN_IN = 9009


    /**
    Auto update tag.
     */
    @JvmStatic
    val AUTO_UPDATE_JOB_TAG = "tag_auto_update_job"
  }


}