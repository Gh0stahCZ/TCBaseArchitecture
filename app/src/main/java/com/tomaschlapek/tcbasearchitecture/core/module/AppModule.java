package com.tomaschlapek.tcbasearchitecture.core.module;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.helper.NetworkHelper;
import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;
import com.tomaschlapek.tcbasearchitecture.helper.RealmHelper;
import com.tomaschlapek.tcbasearchitecture.realm.repository.RBaseRepository;
import com.tomaschlapek.tcbasearchitecture.realm.repository.RUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Base app module.
 * Application module refers to sub components and provides application level dependencies.
 */
@Module
public class AppModule {

  @Provides
  @Singleton
  Context provideContext(App application) {
    return application.getApplicationContext();
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

  @Singleton
	@Provides
  public RealmHelper provideRealmHelper(RUserRepository rUserRepository) {
    return new RealmHelper(rUserRepository);
  }

  @Singleton
  @Provides
  public RBaseRepository provideRBaseRepository(PreferenceHelper preferenceHelper) {
    return new RBaseRepository(preferenceHelper);
  }
}
