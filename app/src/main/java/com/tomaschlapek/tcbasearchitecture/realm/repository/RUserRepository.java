package com.tomaschlapek.tcbasearchitecture.realm.repository;

import android.support.annotation.Nullable;

import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.realm.RUser;

import io.realm.Realm;

/**
 * Repository manages Realm CRUD operation on RUser.
 */
public class RUserRepository extends RAbstractRepository<RUser> {

  public RUser getLoggedUser(Realm realm) {
    return getUserByToken(realm,
      App.getAppComponent().providePreferenceHelper().getUserLoginToken());
  }

  public RUser getUserByToken(Realm realm, @Nullable String token) {
    //    return getFirst(realm.where(RUser.class).equalTo("token", token));
    return getFirst(realm.where(RUser.class));
  }
}
