package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jetbrains.anko.toast
import timber.log.Timber

class KustomFirebaseMessagingService : FirebaseMessagingService() {

  override fun onMessageReceived(remoteMessage: RemoteMessage?) {
    remoteMessage?.let { message ->

      Timber.d("From: ${message.from}")

      // Check if message contains a data payload.
      val data = message.data
      if (data.isNotEmpty()) {
        showMessage(data)
      }
    }
  }

  private fun showMessage(data: Map<String, String>) {
    Timber.d("Message data payload: $data")
    toast("Message data payload: $data")

    // TODO
  }
}