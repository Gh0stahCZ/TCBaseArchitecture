package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.tomaschlapek.tcbasearchitecture.engine.UserEngine
import com.tomaschlapek.tcbasearchitecture.helper.INVALID_STRING
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

/**
 * Firebase token refresh service.
 */
class KustomFirebaseInstanceIDService : FirebaseInstanceIdService() {

  @Inject internal lateinit var userEngine: UserEngine
  @Inject internal lateinit var preferenceHelper: KPreferenceHelper

  override fun onCreate() {
    AndroidInjection.inject(this)
    super.onCreate()
  }

  override fun onTokenRefresh() {
    // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
    val refreshedToken = FirebaseInstanceId.getInstance().token
    Timber.d("Firebase token: $refreshedToken")
    preferenceHelper.userFirebaseToken = refreshedToken ?: INVALID_STRING

    updateTokenOnServer(refreshedToken)
  }

  private fun updateTokenOnServer(token: String?) {
//    userEngine.TODO Uncomment when ready requestSendPushToken(token)
  }
}