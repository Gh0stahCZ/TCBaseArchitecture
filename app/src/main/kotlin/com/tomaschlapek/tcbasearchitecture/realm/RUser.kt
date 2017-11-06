package com.tomaschlapek.tcbasearchitecture.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tomaschlapek on 6/11/17.
 */


/**
 * Class holds information about certain user.
 */
/* Public methods *******************************************************************************/

/**
 * Default constructor.
 */
open class KRUser : RealmObject() {

  /* Attributes ***********************************************************************************/

  @PrimaryKey
  var token: String? = null

  var nickName: String? = null
  var credits: Int = 0
  var avatar: String? = null
}