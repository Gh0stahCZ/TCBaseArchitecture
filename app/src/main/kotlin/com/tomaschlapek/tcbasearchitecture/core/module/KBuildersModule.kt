package com.tomaschlapek.tcbasearchitecture.core.module

import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.*
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.KInitActivity
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.DefaultPushIntentService
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.KAutoResendPushTokenJobService
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.KustomFirebaseInstanceIDService
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.KustomFirebaseMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This module contains all the binding to the sub component builders in the app
 */

@Module
abstract class KBuildersModule {

  @ContributesAndroidInjector
  abstract fun contributeInitActivityInjector(): KInitActivity

  @ContributesAndroidInjector
  abstract fun contributeKSampleActivityInjector(): KSampleActivity

  @ContributesAndroidInjector
  abstract fun contributeKMapActivityInjector(): KMapActivity

  @ContributesAndroidInjector
  abstract fun contributeKChatActivityInjector(): KChatActivity

  @ContributesAndroidInjector
  abstract fun contributeKSettingsActivityInjector(): KSettingsActivity

  //  @ContributesAndroidInjector
  //  public abstract OnboardingActivity contributeOnboardingActivityInjector();

  @ContributesAndroidInjector
  abstract fun contributeSignInActivityInjector(): KSignInActivity

  @ContributesAndroidInjector
  abstract fun contributeFirebaseMessagingServiceInjector(): KustomFirebaseMessagingService

  @ContributesAndroidInjector
  abstract fun contributeFirebaseInstanceIDServiceInjector(): KustomFirebaseInstanceIDService

  @ContributesAndroidInjector
  abstract fun contributeDefaultPushServiceInjector(): DefaultPushIntentService

  @ContributesAndroidInjector
  abstract fun contributeAutoUpdateJobServiceInjector(): KAutoResendPushTokenJobService

}
