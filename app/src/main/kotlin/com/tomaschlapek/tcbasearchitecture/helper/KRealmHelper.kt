package com.tomaschlapek.tcbasearchitecture.helper

import com.google.firebase.auth.FirebaseUser
import com.tomaschlapek.tcbasearchitecture.realm.RUser
import com.tomaschlapek.tcbasearchitecture.realm.repository.KRUserRepository
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Manages access and CRUD operation to Realm.
 */
class KRealmHelper(val mRUserRepository: KRUserRepository) {

  /**
   * Initializes Realm configuration.
   */
  companion object {

    @JvmStatic
    var sRealmConfiguration: RealmConfiguration? = null

    @JvmStatic
    fun initializeRealmConfig() {
      if (sRealmConfiguration == null) {
        setRealmConfiguration(
          RealmConfiguration.Builder()
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(0)
//            .deleteRealmIfMigrationNeeded()
            .build()
        )
      }
    }

    /**
     * Sets defined Realm configuration.
     * @param realmConfiguration Defined configuration.
     */
    @JvmStatic
    fun setRealmConfiguration(realmConfiguration: RealmConfiguration) {
      sRealmConfiguration = realmConfiguration
      Realm.setDefaultConfiguration(realmConfiguration)
    }
  }


  /**
   * Clear all tables in DB.
   */
  fun clearDB() {
    Realm.getDefaultInstance().use { realm -> realm.executeTransaction { realmInstance -> realmInstance.deleteAll() } }
  }

  //**********************************************************************************************//

  /**
   * Gets logged user (with defined Realm instance).

   * @param realmInstance Used Realm instance.
   * *
   * *
   * @return Reference to logged user.
   */
  fun getLoggedUser(realmInstance: Realm): RUser? {
    return mRUserRepository.getLoggedUser(realmInstance)
  }

  /* Realm object creation ************************************************************************/

  //   TODO Uncomment when ready
  /**
   * Creates User Realm objects and persists them.
   *
   * @param firebaseUser UserInfo (from API).
   */
  fun createRUser(firebaseUser: FirebaseUser) {
    Realm.getDefaultInstance().use { realm ->
      realm.executeTransactionAsync { realmInstance ->
        val rUser = RUser(firebaseUser)
        createUser(realmInstance, rUser)
      }
    }
  }

  /* User creation and connection **************************************************************/

  /**
   * Adds RUser to DB.

   * @param realmInstance Used Realm instance.
   * *
   * @param rUser User to addition.
   */
  private fun createUser(realmInstance: Realm, rUser: RUser) {
    mRUserRepository.create(realmInstance, rUser)
  }

}