package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class DefaultPushReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {

    val intent = Intent(context, DefaultPushIntentService::class.java)
    intent.putExtras(getResultExtras(true))
    context.startService(intent)
  }
}