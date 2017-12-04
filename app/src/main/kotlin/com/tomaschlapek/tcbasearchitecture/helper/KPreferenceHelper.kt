package com.tomaschlapek.tcbasearchitecture.helper

import android.content.Context
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.util.bindSharedPreference

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
  var userLoginToken: String? by bindSharedPreference(context, R.string.pref_key_user_login_token, null)

  /**
   * Returns saved fb login token, or null if not set yet.
   */
  var userFbToken: String? by bindSharedPreference(context, R.string.pref_key_fb_access_token, null)

  /**
   * Returns saved firebase token, or null if not set yet.
   */
  var userFirebaseToken: String? by bindSharedPreference(context, R.string.pref_key_fb_access_token, null)

  /**
   * Returns saved user uid, or null if not set yet.
   */
  var userUID: String? by bindSharedPreference(context, R.string.pref_key_user_uid, null)

  //////////////////////////////////////////////////////////////////////////////////////////////////

  fun clearUserData() {
    userLoginToken = null
    userFbToken = null
    userFirebaseToken = null
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

}