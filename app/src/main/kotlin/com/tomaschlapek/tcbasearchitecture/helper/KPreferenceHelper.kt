package com.tomaschlapek.tcbasearchitecture.helper

import android.content.Context
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.util.bindSharedPreference

const val INVALID_STRING = ""

/**
 * Simple accessor to preferences items.
 */
class KPreferenceHelper(context: Context) {

  /* Private Attributes ***************************************************************************/

  /**
   * Determine if application runs first time.
   */
  var firstRun: Boolean by bindSharedPreference(context, R.string.pref_key_first_run, false)

  /**
   * Returns saved user login token, or null if not set yet.
   */
  var userLoginToken: String by bindSharedPreference(context, R.string.pref_key_user_login_token, INVALID_STRING)

  /**
   * Returns saved fb login token, or null if not set yet.
   */
  var userFbToken: String by bindSharedPreference(context, R.string.pref_key_fb_access_token, INVALID_STRING)

  /**
   * Returns saved firebase token, or null if not set yet.
   */
  var userFirebaseToken: String by bindSharedPreference(context, R.string.pref_key_fb_access_token, INVALID_STRING)

  /**
   * Returns saved user uid, or null if not set yet.
   */
  var userUID: String? by bindSharedPreference(context, R.string.pref_key_user_uid, INVALID_STRING)

  //////////////////////////////////////////////////////////////////////////////////////////////////

  fun clearUserData() {
    userLoginToken = INVALID_STRING
    userFbToken = INVALID_STRING
    userFirebaseToken = INVALID_STRING
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

}