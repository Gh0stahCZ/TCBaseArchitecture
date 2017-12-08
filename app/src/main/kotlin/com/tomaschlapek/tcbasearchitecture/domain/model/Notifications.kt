package com.tomaschlapek.tcbasearchitecture.domain.model

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.EXTRA_NOTIF_DATA
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSampleActivity
import com.tomaschlapek.tcbasearchitecture.util.generateNotifId
import com.tomaschlapek.tcbasearchitecture.util.now
import com.tomaschlapek.tcbasearchitecture.util.str
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Notification helper classes.
 */

//** ********************************* Interfaces ********************************* **//
//** ********************* DON'T CHANGE IF IT IS NOT NECESSARY ******************** **//

interface NotificationBuilder {
  fun build(context: Context, item: PushNotificationItem): Notification
}

interface NotificationItemResolver {
  fun resolve(context: Context, data: Map<String, String>): PushNotificationItem
}

interface PushNotificationItem {
  fun id(): Int

  fun messageId(): String

  fun type(): String

  fun channel(): PushNotificationChannel

  fun title(): String

  fun message(): String

  fun actions(): List<NotificationCompat.Action>?

  fun smallIcon(): Int

  fun sound(): Uri

  fun pendingIntent(): PendingIntent?

  fun deleteIntent(): PendingIntent?
}

//** **************************** Notification Builder **************************** **//
//** ********************* DON'T CHANGE IF IT IS NOT NECESSARY ******************** **//

class DefaultNotificationBuilder : NotificationBuilder {
  override fun build(context: Context, item: PushNotificationItem): Notification {
    val builder = NotificationCompat.Builder(context, item.channel().channelId)
      .setWhen(now())
      .setSmallIcon(item.smallIcon())
      .setContentTitle(item.title())
      .setContentText(item.message())
      .setSound(item.sound())
      .setTicker(item.message())
      .setAutoCancel(true)
      .setDefaults(Notification.DEFAULT_ALL)
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setContentIntent(item.pendingIntent())
      .setDeleteIntent(item.deleteIntent())

    val actions = item.actions()
    if (actions != null && actions.isNotEmpty()) {
      for (action in actions) {
        builder.addAction(action)
      }
    }

    return builder.build()
  }
}

//** ********************************* Notification Creator ********************************* **//
//** ********************* **** DON'T CHANGE IF IT IS NOT NECESSARY **** ******************** **//

@Singleton
internal class PushNotification @Inject constructor(
  private val notificationManager: NotificationManager,
  private val resolver: NotificationItemResolver,
  private val notificationBuilder: NotificationBuilder) {

  fun show(context: Context, data: Map<String, String>) {
    val notification = resolver.resolve(context, data)
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
      createChannel(context, notification.channel())
    }

    notificationManager.notify(notification.id(), notificationBuilder.build(context, notification))
  }

  fun notify(notificationId: Int, notification: Notification) {
    notificationManager.notify(notificationId, notification)
  }

  fun cancel(notificationId: Int) {
    notificationManager.cancel(notificationId)
  }

  private fun cancelAll(notificationIds: List<Int>) {
    for (notificationId in notificationIds) {
      cancel(notificationId)
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  fun createChannel(context: Context, channel: PushNotificationChannel) {
    val channelTitle = context.getString(channel.titleResource)
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val notificationChannel = NotificationChannel(channel.channelId, channelTitle, importance)
    notificationChannel.setShowBadge(true)
    notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
    notificationManager.createNotificationChannel(notificationChannel)
  }
}

//** **************************** Custom Notification Resolver **************************** **//


class PushNotificationItemResolver : NotificationItemResolver {

  override fun resolve(context: Context, data: Map<String, String>): PushNotificationItem {
    //    val content = data["value"] ?: ""
    return when (data["type"]) {
      "notification.user.demand.offer.new" -> NewOfferNotification(context, data)
      "notification.user.rating.new" -> NewRatingNotification(context, data)
      else -> GeneralNotification()
    }
  }
}

//** **************************** Custom Notification Types **************************** **//

internal class GeneralNotification : PushNotificationItem {

  override fun channel() = PushNotificationChannel.Empty()

  override fun smallIcon(): Int = R.mipmap.ic_launcher

  override fun title(): String = str(R.string.app_name)

  override fun message(): String = ""

  override fun pendingIntent(): PendingIntent? = null

  override fun id(): Int = 0

  override fun messageId(): String = ""

  override fun type(): String = ""

  override fun sound(): Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

  override fun actions(): List<NotificationCompat.Action>? = null

  override fun deleteIntent(): PendingIntent? = null

}

internal class NewOfferNotification(private val context: Context, private val content: Map<String, String>) : PushNotificationItem {

  override fun channel() = PushNotificationChannel.Offers()

  override fun smallIcon(): Int = R.mipmap.ic_launcher

  override fun title(): String = str(R.string.notification_title_offer)

  override fun message(): String = str(R.string.notification_message_offer, content["user_id"] ?: "?")

  override fun pendingIntent(): PendingIntent {
    val resultIntent = Intent(context, KSampleActivity::class.java)
    resultIntent.putExtra(EXTRA_NOTIF_DATA, "User: ${content["user_id"] ?: "?"} Demand: ${content["demand_id"] ?: "?"} Offer: ${content["offer_id"] ?: "?"}")
    resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    val requestID = System.currentTimeMillis().toInt()
    return PendingIntent.getActivity(context, requestID, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  override fun id(): Int = generateNotifId()

  override fun messageId(): String = content["message_id"] ?: ""

  override fun type(): String = content["type"] ?: ""

  override fun actions(): List<NotificationCompat.Action>? = null

  override fun sound(): Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

  override fun deleteIntent(): PendingIntent? = null
}

internal class NewRatingNotification(private val context: Context, private val content: Map<String, String>) : PushNotificationItem {

  override fun channel() = PushNotificationChannel.Ratings()

  override fun smallIcon(): Int = R.mipmap.ic_launcher

  override fun title(): String = str(R.string.notification_title_rating)

  override fun message(): String = str(R.string.notification_message_rating, content["user_id"] ?: "?")

  override fun pendingIntent(): PendingIntent {
    val resultIntent = Intent(context, KSampleActivity::class.java)
    resultIntent.putExtra(EXTRA_NOTIF_DATA, "User: ${content["userId"] ?: "?"}")
    resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    val requestID = System.currentTimeMillis().toInt()
    return PendingIntent.getActivity(context, requestID, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  override fun id(): Int = generateNotifId()

  override fun messageId(): String = content["message_id"] ?: ""

  override fun type(): String = content["type"] ?: ""

  override fun actions(): List<NotificationCompat.Action>? = null

  override fun sound(): Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

  override fun deleteIntent(): PendingIntent? = null
}

//** **************************** Custom Notification Channels **************************** **//


sealed class PushNotificationChannel(val channelId: String, val titleResource: Int) {

  class Offers : PushNotificationChannel("offers", R.string.notification_channel_offers)

  class Ratings : PushNotificationChannel("ratings", R.string.notification_channel_ratings)

  class Empty : PushNotificationChannel("empty", R.string.notification_title_general)
}