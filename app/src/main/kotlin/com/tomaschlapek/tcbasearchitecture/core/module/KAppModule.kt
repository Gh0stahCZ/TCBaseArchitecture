package com.tomaschlapek.tcbasearchitecture.core.module

import android.content.Context
import com.squareup.picasso.Picasso
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.engine.UserEngine
import com.tomaschlapek.tcbasearchitecture.helper.KNetworkHelper
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import com.tomaschlapek.tcbasearchitecture.helper.KRealmHelper
import com.tomaschlapek.tcbasearchitecture.realm.repository.KRBaseRepository
import com.tomaschlapek.tcbasearchitecture.realm.repository.KRUserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Base app module.
 * Application module refers to sub components and provides application level dependencies.
 */
@Module
class KAppModule {

  @Provides
  @Singleton
  internal fun provideContext(application: App): Context {
    return application.applicationContext
  }

  @Provides
  @Singleton
  fun provideKPreferenceHelper(context: Context): KPreferenceHelper {
    return KPreferenceHelper(context)
  }

  @Provides
  @Singleton
  fun provideKNetworkHelper(context: Context, picasso: Picasso): KNetworkHelper {
    return KNetworkHelper(context, picasso)
  }

  @Singleton
  @Provides
  fun provideRealmHelper(rUserRepository: KRUserRepository): KRealmHelper {
    return KRealmHelper(rUserRepository)
  }

  @Singleton
  @Provides
  fun provideRBaseRepository(preferenceHelper: KPreferenceHelper): KRBaseRepository {
    return KRBaseRepository(preferenceHelper)
  }

  @Singleton
  @Provides
  fun provideUserEngine(retrofit: Retrofit, preferenceHelper: KPreferenceHelper, netHelper: KNetworkHelper, realmHelper: KRealmHelper): UserEngine {
    return UserEngine(retrofit, preferenceHelper, netHelper, realmHelper)
  }
}
