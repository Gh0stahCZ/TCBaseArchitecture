package com.tomaschlapek.tcbasearchitecture.helper

import android.content.Context
import android.preference.PreferenceManager
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.util.restoreBoolean
import com.tomaschlapek.tcbasearchitecture.util.restoreString
import com.tomaschlapek.tcbasearchitecture.util.storeBoolean
import com.tomaschlapek.tcbasearchitecture.util.storeString

/**
 * Simple accessor to preferences items.
 */
class KPreferenceHelper(context: Context) {

  /* Private Attributes ***************************************************************************/

  /**
   * Shared preferences for current context.
   */
  private var mPreferences = PreferenceManager.getDefaultSharedPreferences(context)

  /* Public Methods *******************************************************************************/

  /**
   * Determine if application runs first time.
   */
  fun getFirstRun(): Boolean {
    return mPreferences.restoreBoolean(R.string.pref_key_first_run, true)
  }

  /**
   * Sets when application has run first time.
   */
  fun setFirstRun(firstRun: Boolean) {
    mPreferences.storeBoolean(R.string.pref_key_first_run, firstRun)
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns saved user login token, or null if not set yet.
   */
  fun getUserLoginToken(): String? {
    return mPreferences.restoreString(R.string.pref_key_user_login_token)
  }

  /**
   * Saves user login token to shared preferences.
   */
  fun setUserLoginToken(token: String?) {
    mPreferences.storeString(R.string.pref_key_user_login_token, token)
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns saved fb login token, or null if not set yet.
   */
  fun getFbLoginToken(): String? {
    return mPreferences.restoreString(R.string.pref_key_fb_access_token)
  }

  /**
   * Saves user fb token to shared preferences.
   */
  fun setFbLoginToken(token: String?) {
    mPreferences.storeString(R.string.pref_key_fb_access_token, token)
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns saved user uid, or null if not set yet.
   */
  fun getUserUID(): String? {
    return mPreferences.restoreString(R.string.pref_key_user_uid)
  }

  /**
   * Saves user uid to shared preferences.
   */
  fun setUserUID(token: String?) {
    mPreferences.storeString(R.string.pref_key_user_uid, token)
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns saved firebase token, or null if not set yet.
   */
  fun getFirebaseToken(): String? {
    return mPreferences.restoreString(R.string.pref_key_firebase_token)
  }

  /**
   * Saves firebase token to shared preferences.
   */
  fun setFirebaseToken(token: String?) {
    mPreferences.storeString(R.string.pref_key_firebase_token, token)
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  fun clearUserData() {
    setUserLoginToken(null)
    setUserUID(null)
  }

}