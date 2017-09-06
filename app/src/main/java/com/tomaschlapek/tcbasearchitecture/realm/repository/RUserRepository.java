package com.tomaschlapek.tcbasearchitecture.realm.repository;

import android.support.annotation.Nullable;

import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;
import com.tomaschlapek.tcbasearchitecture.realm.RUser;

import io.realm.Realm;

import javax.inject.Inject;

/**
 * Repository manages Realm CRUD operation on RUser.
 */
public class RUserRepository extends RAbstractRepository<RUser> {

  @Inject
  public RUserRepository(PreferenceHelper preferenceHelper) {
    super(preferenceHelper);
  }

  public RUser getLoggedUser(Realm realm) {
    return getUserByToken(realm, mPreferenceHelper.getUserLoginToken());
  }

  public RUser getUserByToken(Realm realm, @Nullable String token) {
    //    return getFirst(realm.where(RUser.class).equalTo("token", token));
    return getFirst(realm.where(RUser.class));
  }
}
