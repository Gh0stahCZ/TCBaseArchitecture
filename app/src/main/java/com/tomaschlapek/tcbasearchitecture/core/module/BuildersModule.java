package com.tomaschlapek.tcbasearchitecture.core.module;

import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.InitActivity;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSampleActivity;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.SignInActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This module contains all the binding to the sub component builders in the app
 */

@Module
public abstract class BuildersModule {

  @ContributesAndroidInjector
  public abstract InitActivity contributeInitActivityInjector();

  @ContributesAndroidInjector
  public abstract KSampleActivity contributeKSampleActivityInjector();

  //  @ContributesAndroidInjector
  //  public abstract OnboardingActivity contributeOnboardingActivityInjector();

  @ContributesAndroidInjector
  public abstract SignInActivity contributeSignInActivityInjector();

}
