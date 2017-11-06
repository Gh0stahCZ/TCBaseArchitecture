package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.tomaschlapek.tcbasearchitecture.App
import timber.log.Timber

/**
 * Firebase token refresh service.
 */
class KustomFirebaseInstanceIDService : FirebaseInstanceIdService() {

  override fun onTokenRefresh() {
    // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
    val refreshedToken = FirebaseInstanceId.getInstance().token
    Timber.d("Firebase token: $refreshedToken")
    App.getAppComponent().provideKPreferenceHelper().setFirebaseToken(refreshedToken)

    sendRegistrationToServer(refreshedToken)
  }

  private fun sendRegistrationToServer(token: String?) {
    // TODO: Implement this method to send any registration to app's server.
  }
}