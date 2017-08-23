package com.tomaschlapek.tcbasearchitecture.core.component;

import android.content.Context;

import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.core.module.AppModule;
import com.tomaschlapek.tcbasearchitecture.core.module.BuildersModule;
import com.tomaschlapek.tcbasearchitecture.core.module.NetModule;
import com.tomaschlapek.tcbasearchitecture.helper.NetworkHelper;
import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Base app component.
 */
@Singleton
@Component(modules = {
  AndroidSupportInjectionModule.class, AppModule.class, NetModule.class, BuildersModule.class
})
public interface AppComponent extends AndroidInjector<App> {

  Context provideContext();

  NetworkHelper provideNetworkHelper();

  PreferenceHelper providePreferenceHelper();

  /**
   * Description:
   * https://proandroiddev.com/dagger-2-component-builder-1f2b91237856
   */
  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder application(App application);

    AppComponent build();
  }

  /*
  void inject(BaseFragment activity);
  ToolbarHelper provideToolbarHelper();
  RealmHelper provideRealmHelper();*/
}

