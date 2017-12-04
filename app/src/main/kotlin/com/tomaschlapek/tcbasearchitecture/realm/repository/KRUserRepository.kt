package com.tomaschlapek.tcbasearchitecture.realm.repository

import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import com.tomaschlapek.tcbasearchitecture.realm.KRUser
import io.realm.Realm
import javax.inject.Inject

/**
 * Created by tomaschlapek on 15/9/17.
 */
class KRUserRepository @Inject constructor(preferenceHelper: KPreferenceHelper) : KRAbstractRepository<KRUser>(preferenceHelper) {

  fun getLoggedUser(realm: Realm?): KRUser? {
    return getUserByToken(realm, preferenceHelper.userLoginToken)
  }

  fun getUserByToken(realm: Realm?, token: String?): KRUser? {
    return getFirst(realm?.where(KRUser::class.java))
  }
}