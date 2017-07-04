package com.tomaschlapek.tcbasearchitecture.core.module;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;
import com.tomaschlapek.tcbasearchitecture.helper.NetworkHelper;
import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Base app module.
 */
@Module
public class AppModule {

  private Application mApplication;

  public AppModule(Application application) {
    this.mApplication = application;
  }

  @Provides
  @Singleton
  Application provideApplication() {
    return this.mApplication;
  }

  @Provides
  @Singleton
  Context provideContext() {
    return this.mApplication;
  }

  @Provides
  @Singleton
  public PreferenceHelper providePreferenceHelper(Context context) {
    return new PreferenceHelper(context);
  }

  @Provides
  @Singleton
  public NetworkHelper provideNetworkHelper(Context context, Retrofit retrofit, Picasso picasso) {
    return new NetworkHelper(context, retrofit, picasso);
  }


/*

	@Singleton
	@Provides
	public RealmHelper provideRealmHelper() {
		return new RealmHelper();
	}*/
}
