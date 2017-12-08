package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import android.app.IntentService
import android.content.Intent
import com.tomaschlapek.tcbasearchitecture.domain.model.PushNotification
import dagger.android.AndroidInjection
import javax.inject.Inject

class DefaultPushIntentService : IntentService("DefaultPushIntentService") {

  @Inject internal lateinit var pushNotification: PushNotification

  override fun onCreate() {
    AndroidInjection.inject(this)
    super.onCreate()
  }

  override fun onHandleIntent(intent: Intent) {
    val messageData = intent.getSerializableExtra(EXTRA_NOTIFICATION)
    val number = intent.getSerializableExtra(EXTRA_INT)
    messageData?.let {
      pushNotification.show(applicationContext, it as Map<String, String>)
    }
  }

}