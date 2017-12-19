package com.tomaschlapek.tcbasearchitecture.core.component

import android.content.Context
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.core.module.KAppModule
import com.tomaschlapek.tcbasearchitecture.core.module.KBuildersModule
import com.tomaschlapek.tcbasearchitecture.core.module.KNetModule
import com.tomaschlapek.tcbasearchitecture.core.module.KNotifModule
import com.tomaschlapek.tcbasearchitecture.engine.UserEngine
import com.tomaschlapek.tcbasearchitecture.helper.KNetworkHelper
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import com.tomaschlapek.tcbasearchitecture.helper.KRealmHelper
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Base app component.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, KAppModule::class, KNotifModule::class, KNetModule::class, KBuildersModule::class))
interface KAppComponent : AndroidInjector<App> {

  fun provideContext(): Context

  fun provideKNetworkHelper(): KNetworkHelper

  fun provideUserEngine(): UserEngine

  fun provideKPreferenceHelper(): KPreferenceHelper

  fun provideRealmHelper(): KRealmHelper

  fun provideRetrofit(): Retrofit

  /**
   * Description:
   * https://proandroiddev.com/dagger-2-component-builder-1f2b91237856
   */
  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: App): Builder

    fun build(): KAppComponent
  }

  /*
  void inject(BaseFragment activity);
  ToolbarHelper provideToolbarHelper();
  KRealmHelper provideRealmHelper();*/
}

