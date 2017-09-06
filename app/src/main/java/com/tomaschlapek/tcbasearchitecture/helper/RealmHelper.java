package com.tomaschlapek.tcbasearchitecture.helper;

import com.tomaschlapek.tcbasearchitecture.realm.RUser;
import com.tomaschlapek.tcbasearchitecture.realm.repository.RUserRepository;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import javax.inject.Inject;

/**
 * Manages access and CRUD operation to Realm.
 */
public class RealmHelper {

  private RUserRepository mRUserRepository;

  @Inject
  public RealmHelper(RUserRepository rUserRepository) {
    mRUserRepository = rUserRepository;
  }

  //**********************************************************************************************//

  public static RealmConfiguration sRealmConfiguration;

  /**
   * Initializes Realm configuration.
   */
  public static void initializeRealmConfig() {
    if (sRealmConfiguration == null) {
      setRealmConfiguration(
        new RealmConfiguration.Builder()
          .name(Realm.DEFAULT_REALM_NAME)
          .schemaVersion(0)
          .deleteRealmIfMigrationNeeded()
          .build()
      );
    }
  }

  /**
   * Sets defined Realm configuration.
   *
   * @param realmConfiguration Defined configuration.
   */
  public static void setRealmConfiguration(RealmConfiguration realmConfiguration) {
    RealmHelper.sRealmConfiguration = realmConfiguration;
    Realm.setDefaultConfiguration(realmConfiguration);
  }

  /**
   * Clear all tables in DB.
   */
  public void clearDB() {
    try (Realm realm = Realm.getDefaultInstance()) {
      realm.executeTransaction(realmInstance -> realmInstance.deleteAll());
    }
  }

  //**********************************************************************************************//

  /**
   * Gets logged user (with defined Realm instance).
   *
   * @param realmInstance Used Realm instance.
   *
   * @return Reference to logged user.
   */
  public RUser getLoggedUser(Realm realmInstance) {
    return mRUserRepository.getLoggedUser(realmInstance);
  }

  /* Realm object creation ************************************************************************/

  // TODO Uncomment when ready
  //  /**
  //   * Creates User Realm objects and persists them.
  //   *
  //   * @param userInfoResponse UserInfo (from API).
  //   * @param token User token (from API).
  //   */
  //  public void createRUser(UserInfoResponse userInfoResponse, String token) {
  //
  //    try (Realm realm = Realm.getDefaultInstance()) {
  //      realm.executeTransactionAsync(realmInstance -> {
  //        RUser rUser = new RUser(userInfoResponse, token);
  //        createUser(realmInstance, rUser);
  //      });
  //    }
  //  }

  /* User creation and connection **************************************************************/

  /**
   * Adds RUser to DB.
   *
   * @param realmInstance Used Realm instance.
   * @param rUser User to addition.
   */
  private void createUser(Realm realmInstance, RUser rUser) {
    mRUserRepository.create(realmInstance, rUser);
  }
}
