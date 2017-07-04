package com.tomaschlapek.tcbasearchitecture.core.component;

import android.app.Application;
import android.content.Context;

import com.tomaschlapek.tcbasearchitecture.core.module.AppModule;
import com.tomaschlapek.tcbasearchitecture.core.module.NetModule;
import com.tomaschlapek.tcbasearchitecture.helper.NetworkHelper;
import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.PresenterEmptyActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Base app component.
 */
@Singleton
@Component(modules = { AppModule.class, NetModule.class })
public interface AppComponent {

  void inject(PresenterEmptyActivity activity);

  Application provideApplication();

  Context provideContext();

  NetworkHelper provideNetworkHelper();

  PreferenceHelper providePreferenceHelper();

  /*
  void inject(BaseFragment activity);
  ToolbarHelper provideToolbarHelper();
  RealmHelper provideRealmHelper();*/
}

