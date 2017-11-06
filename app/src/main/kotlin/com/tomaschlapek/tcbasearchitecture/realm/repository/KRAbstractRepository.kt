package com.tomaschlapek.tcbasearchitecture.realm.repository

import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import io.realm.*

/**
 * Created by tomaschlapek on 15/9/17.
 */
abstract class KRAbstractRepository<T : RealmModel>(preferenceHelper: KPreferenceHelper) : KRBaseRepository(preferenceHelper) {

  // No instance defined

  fun addOrUpdateAsync(item: T, onSuccess: Realm.Transaction.OnSuccess, onError: Realm.Transaction.OnError) {
    Realm.getDefaultInstance().use { realm -> addOrUpdateAsync(realm, item, onSuccess, onError) }
  }

  fun addOrUpdateAsync(item: Iterable<T>, onSuccess: Realm.Transaction.OnSuccess, onError: Realm.Transaction.OnError) {
    Realm.getDefaultInstance().use { realm -> addOrUpdateAsync(realm, item, onSuccess, onError) }
  }

  fun addOrUpdate(item: Iterable<T>) {
    Realm.getDefaultInstance().use { realm -> addOrUpdate(realm, item) }
  }

  fun addOrUpdate(item: T) {
    Realm.getDefaultInstance().use { realm -> addOrUpdate(realm, item) }
  }

  // Instance defined

  internal fun addOrUpdateAsync(realm: Realm, item: T, onSuccess: Realm.Transaction.OnSuccess, onError: Realm.Transaction.OnError) {
    realm.executeTransactionAsync(Realm.Transaction { realmInstance -> create(realmInstance, item) }, onSuccess, onError)
  }

  internal fun addOrUpdateAsync(realm: Realm, item: Iterable<T>, onSuccess: Realm.Transaction.OnSuccess, onError: Realm.Transaction.OnError) {
    realm.executeTransactionAsync(Realm.Transaction { realmInstance -> create(realmInstance, item) }, onSuccess, onError)
  }

  internal fun addOrUpdate(realm: Realm, item: Iterable<T>) {
    realm.executeTransaction({ realmInstance -> create(realmInstance, item) })
  }

  internal fun addOrUpdate(realm: Realm, item: T) {
    realm.executeTransaction({ realmInstance -> create(realmInstance, item) })
  }

  // Simple create

  fun <T : RealmModel> create(realmInstance: Realm, item: T): T {
    return realmInstance.copyToRealmOrUpdate(item)
  }

  fun <T : RealmModel> create(realmInstance: Realm, item: Iterable<T>): List<T> {
    return realmInstance.copyToRealmOrUpdate(item)
  }

  // Simple delete

  fun remove(query: RealmQuery<*>): Boolean {
    return query.findAll().deleteAllFromRealm()
  }

  // Simple read

  operator fun get(query: RealmQuery<*>): RealmResults<T> {
    return query.findAll() as RealmResults<T>
  }

  fun getFirst(query: RealmQuery<*>?): T? {
    return query?.findFirst() as T?
  }

  // Transform

  fun mapToRealm(list: List<T>): RealmList<T> {
    val realmList = RealmList<T>()
    realmList.addAll(list)
    return realmList
  }

}