package com.tomaschlapek.tcbasearchitecture.core.module

import android.app.NotificationManager
import android.content.Context
import com.tomaschlapek.tcbasearchitecture.domain.model.DefaultNotificationBuilder
import com.tomaschlapek.tcbasearchitecture.domain.model.NotificationBuilder
import com.tomaschlapek.tcbasearchitecture.domain.model.NotificationItemResolver
import com.tomaschlapek.tcbasearchitecture.domain.model.PushNotificationItemResolver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Notification module.
 * Application module refers to sub components and provides application level dependencies.
 */
@Module
class KNotifModule {

  @Provides
  @Singleton
  fun providePushNotificationManager(context: Context): NotificationManager =
    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

  @Provides
  @Singleton
  fun providePushNotificationItemResolver(): NotificationItemResolver =
    PushNotificationItemResolver()

  @Provides
  @Singleton
  fun provideNotificationBuilder(): NotificationBuilder =
    DefaultNotificationBuilder()

  /*@Provides
  @Singleton
  fun providePushNotification(notificationManager: NotificationManager,
    resolver: NotificationItemResolver,
    notificationBuilder: NotificationBuilder): PushNotification {
    return AppPushNotification(notificationManager, resolver, notificationBuilder)
  }*/
}
