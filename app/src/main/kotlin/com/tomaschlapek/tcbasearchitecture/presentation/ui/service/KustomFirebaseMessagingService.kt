package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tomaschlapek.tcbasearchitecture.domain.model.PushNotification
import com.tomaschlapek.tcbasearchitecture.util.Konstants
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

const val EXTRA_NOTIFICATION = "extra_notification"
const val EXTRA_INT = "extra_int"

class KustomFirebaseMessagingService : FirebaseMessagingService() {

  @Inject internal lateinit var pushNotification: PushNotification

  override fun onCreate() {
    AndroidInjection.inject(this)
    super.onCreate()
  }

  override fun onMessageReceived(remoteMessage: RemoteMessage?) {
    remoteMessage?.let { message ->

      Timber.d("From: ${message.from}")

      val arrayMap = message.data
      val hashMap = hashMapOf<String, String>()

      arrayMap.forEach {
        hashMap.put(it.key, it.value)
      }

      val dataExtras = Bundle().apply {
        putSerializable(EXTRA_NOTIFICATION, hashMap)
        putInt(EXTRA_INT, 5)
      }

      val bcIntent = Intent().apply {
        putExtras(dataExtras)
        action = Konstants.BROADCAST_NOTIFICATION
      }

      sendOrderedBroadcast(bcIntent, null, null, null, Activity.RESULT_OK, null, dataExtras);
    }
  }

  private fun showMessage(data: Map<String, String>) {
    Timber.d("Message data payload: $data")
    pushNotification.show(applicationContext, data)
  }
}