package com.tomaschlapek.tcbasearchitecture.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.tomaschlapek.tcbasearchitecture.R;

/**
 * Simple accessor to preferences items.
 */
public class PreferenceHelper {

  /* Private Attributes ***************************************************************************/

  /**
   * Shared preferences for current context.
   */
  private SharedPreferences mPreferences;

  /**
   * Resources for current context.
   */
  private Resources mResources;


  /* Public Methods *******************************************************************************/

  /**
   * Constructor
   *
   * @param context Application context.
   */
  public PreferenceHelper(Context context) {
    // Initialize attributes.
    mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    mResources = context.getResources();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Determine if application runs first time.
   */
  public boolean getFirstRun() {
    return mPreferences.getBoolean(mResources.getString(R.string.pref_key_first_run), true);
  }

  /**
   * Sets when application has run first time.
   */
  public void setFirstRun(boolean firstRun) {
    SharedPreferences.Editor editor = mPreferences.edit();
    editor.putBoolean(mResources.getString(R.string.pref_key_first_run), firstRun);
    editor.apply();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns saved user login token, or null if not set yet.
   */
  public String getUserLoginToken() {
    return mPreferences.getString(mResources.getString(R.string.pref_key_user_login_token), null);
  }

  /**
   * Saves user login token to shared preferences.
   */
  public void setUserLoginToken(String token) {
    SharedPreferences.Editor editor = mPreferences.edit();
    editor.putString(mResources.getString(R.string.pref_key_user_login_token), token);
    editor.apply();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns saved user uid, or null if not set yet.
   */
  public String getUserUID() {
    return mPreferences.getString(mResources.getString(R.string.pref_key_user_uid), null);
  }

  /**
   * Saves user uid to shared preferences.
   */
  public void setUserUID(String token) {
    SharedPreferences.Editor editor = mPreferences.edit();
    editor.putString(mResources.getString(R.string.pref_key_user_uid), token);
    editor.apply();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  public void clearUserData() {
    setUserLoginToken(null);
    setUserUID(null);
  }
}
