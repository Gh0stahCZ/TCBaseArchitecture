package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber


class DefaultPushReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {

    if (getResultCode() != Activity.RESULT_OK) {
      Timber.e("Activity.RESULT_CANCEL")
    } else {
      Timber.e("Activity.RESULT_OK")
      val intent = Intent(context, DefaultPushIntentService::class.java)
      intent.putExtras(getResultExtras(true))
      context.startService(intent)
    }

  }
}