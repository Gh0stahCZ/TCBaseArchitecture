package com.tomaschlapek.tcbasearchitecture.realm.repository

import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper
import com.tomaschlapek.tcbasearchitecture.realm.RUser
import io.realm.Realm
import javax.inject.Inject

/**
 * Created by tomaschlapek on 15/9/17.
 */
class KRUserRepository @Inject constructor(preferenceHelper: PreferenceHelper) : KRAbstractRepository<RUser>(preferenceHelper) {

  fun getLoggedUser(realm: Realm?): RUser? {
    return getUserByToken(realm, preferenceHelper.userLoginToken)
  }

  fun getUserByToken(realm: Realm?, token: String?): RUser? {
    return getFirst(realm?.where(RUser::class.java))
  }
}