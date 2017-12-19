package com.tomaschlapek.tcbasearchitecture.realm

import com.google.firebase.auth.FirebaseUser
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Class holds information about certain user.
 */
open class RUser() : RealmObject() {
  constructor(firebaseUser: FirebaseUser) : this() {
    this.id = firebaseUser.uid
    this.email = firebaseUser.email
    this.nickName = firebaseUser.displayName
    this.verified = firebaseUser.isEmailVerified
  }

  /* Attributes ***********************************************************************************/

  @PrimaryKey
  var id: String? = null

  var email: String? = null
  var nickName: String? = null
  var verified: Boolean = false
}